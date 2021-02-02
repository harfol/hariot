package org.hariot.admin.web.config;

import java.util.Arrays;
import java.lang.Integer;
import com.alibaba.fastjson.JSON;
/*
import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.aliyun.openservices.iot.api.message.entity.MessageToken;
import com.aliyuncs.exceptions.ClientException;
*/
import org.hariot.admin.dao.entity.bo.DeviceEvent;
import org.hariot.admin.dao.entity.bo.DeviceLifecyclePost;
import org.hariot.admin.dao.entity.bo.DevicePropertyPost;
import org.hariot.admin.dao.entity.bo.DeviceStatusPost;
import org.hariot.admin.service.AlarmHistoryLogService;
import org.hariot.admin.service.DeviceInfoService;
import org.hariot.admin.service.DevicePropHistoryLogService;
import org.hariot.admin.web.util.AliyunMessageTransformUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class DeviceMessageAmpqRegister implements InitializingBean {

    @Value("${user.accessKeyID}")
    private String accessKey;

    @Value("${user.accessKeySecret}")
    private String accessSecret;

    @Value("${iot.regionId}")
    private String regionId;

    @Value("${user.uid}")
    private String uid;

    @Value("${iot.productKey}")
    private String productKey;

	@Value("${user.clientId}")
	private String clientId;
    
	@Value("${iot.ampq.domain}")
    private String domain;

    @Autowired
    private Topic topic;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private DevicePropHistoryLogService devicePropHistoryLogService;

    @Autowired
    private AlarmHistoryLogService alarmHistoryLogService;
    // private final static Logger logger = LoggerFactory.getLogger(App.class);
    private Logger logger = LoggerFactory.getLogger(getClass());

    //业务处理异步线程池，线程池参数可以根据您的业务特点调整，或者您也可以用其他异步方式处理接收到的消息。
    private final  ExecutorService executorService = new ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors(),
        Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(50000));

    @Override
    public void afterPropertiesSet() throws Exception {
        String consumerGroupId = "DEFAULT_GROUP";
        //iotInstanceId：购买的实例请填写实例ID，公共实例请填空字符串""。
        String iotInstanceId = ""; 
        long timeStamp = System.currentTimeMillis();
        //签名方法：支持hmacmd5、hmacsha1和hmacsha256。
        String signMethod = "hmacsha1";

        //userName组装方法，请参见AMQP客户端接入说明文档。
        String userName = clientId + "|authMode=aksign"
            + ",signMethod=" + signMethod
            + ",timestamp=" + timeStamp
            + ",authId=" + accessKey
            + ",iotInstanceId=" + iotInstanceId
            + ",consumerGroupId=" + consumerGroupId
            + "|";
        //计算签名，password组装方法，请参见AMQP客户端接入说明文档。
        String signContent = "authId=" + accessKey + "&timestamp=" + timeStamp;
        String password = doSign(signContent,accessSecret, signMethod);
        //接入域名，请参见AMQP客户端接入说明文档。
        String connectionUrl = "failover:(amqps://" + uid +"." + domain + ":5671?amqp.idleTimeout=80000)"
            + "?failover.reconnectDelay=30";

		logger.info(connectionUrl);
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("connectionfactory.SBCF",connectionUrl);
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        Context context = new InitialContext(hashtable);
        ConnectionFactory cf = (ConnectionFactory)context.lookup("SBCF");
        Destination queue = (Destination)context.lookup("QUEUE");
        // 创建连接。
        Connection connection = cf.createConnection(userName, password);
        ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
        // 创建会话。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        // 创建Receiver连接。
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(messageListener);

	}
    private MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessage(Message message) {
            try {
                executorService.submit(() -> processMessage(message));
            } catch (Exception e) {
                logger.error("submit task occurs exception ", e);
            }
        }
    };

    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private void processMessage(Message message) {
        try {
            byte[] body = message.getBody(byte[].class);
            String content = new String(body);
            String top = message.getStringProperty("topic");
            String mid = message.getStringProperty("messageId");
            logger.info("receive message"
                + ", topic = " + top
                + ", messageId = " + mid
                + ", content = " + content);
			String[] token = top.split("/");
			int token_size = token.length;
			switch( token_size ){
				case 6:{
					// /as/mqtt/status/a1vZTRTxXyE/SrMwDFMVGJekOz1gJuhs
					if( token[1].equals("as") && token[2].equals("mqtt") && token[3].equals("status") ){
            		    DeviceStatusPost deviceStatus = JSON.parseObject(content, DeviceStatusPost.class);
            		    String status = deviceStatus.getStatus();
            		    String deviceName = deviceStatus.getDeviceName();
            		    deviceInfoService.updateStatus(deviceName, status);
					}
				}; break;
				case 7: {
            		//属性上报  topic:   /productKey/#/thing/event/property/post
					if( token[3].equals("thing") &&  token[4].equals("event")  && token[5].equals("property") && token[6].equals("post") ){
                		//logger.info("receive 属性上报" + content);
                		DevicePropertyPost devicePropertyPost = JSON.parseObject(content, DevicePropertyPost.class);
                		Boolean isUpdateDeviceProperty = deviceInfoService.updateOrInsertDeviceProperty(devicePropertyPost);
                		Boolean isUpdateDevicePropHistoryLog = devicePropHistoryLogService.insertDevicePropHistoryLog(devicePropertyPost);
					}
            		//事件上报  topic: /a1vZTRTxXyE/${deviceName}/thing/event/${tsl.event.identifier}/post
					else if( token[3].equals("thing")  && token[4].equals("event") && token[6].equals("post") ){
                		//logger.info("receive 属性上报" + content);
                		//logger.info("receive 设备事件上报" + content);
                		DeviceEvent deviceEvent = JSON.parseObject(content, DeviceEvent.class);
                    	//alarmHistoryLogService.insertAlarmHistoryLogByAliyunQuery(deviceEvent);
                    	alarmHistoryLogService.insertAlarmHistoryLogByMysqlQuery(deviceEvent);
                    	deviceInfoService.updateAlarmStatus(deviceEvent.getDeviceName(), deviceEvent.getValue().getCurrentTemperature(),true);
					}
				};break;
				default : break;
			}
			logger.info(Integer.toString(token_size));
			logger.info(Arrays.toString(token));
        } catch (Exception e) {
            logger.error("processMessage occurs error ", e);
        }
    }
	/*
*/
    private  JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立。
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            logger.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            logger.error("onConnectionFailure, {}", error.getMessage());
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            logger.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            logger.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {}

        @Override
        public void onSessionClosed(Session session, Throwable cause) {}

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {}

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {}
    };

    /**
     * 计算签名，password组装方法，请参见AMQP客户端接入说明文档。
     */
    private  String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }


        /*


        //设备生命周期上报 {payload={"iotId":"DszqFgXkdL0fv6TGaSI70010f4b200","action":"delete","messageCreateTime":1547196768873,
        // "productKey":"a1I64MeQmoo","deviceName":"test_zhinengjiaju"}
        client.setMessageListener(topic.getDeviceLifecyclePostTopic(), new MessageCallback() {
            @Override
            public Action consume(MessageToken messageToken) {
                Message m = messageToken.getMessage();
                logger.info("receive 设备生命周期上报" + new String(messageToken.getMessage().getPayload()));
                DeviceLifecyclePost deviceLifecycle = JSON.parseObject(new String(m.getPayload()), DeviceLifecyclePost.class);
                Boolean b = deviceInfoService.insertOrDelete(deviceLifecycle);
                if (b) {
                    return Action.CommitSuccess;
                }
                return Action.CommitFailure;

            }
        });

        client.setMessageListener(topic.getTempHumUploadTopic(), new MessageCallback() {
            @Override
            public Action consume(MessageToken messageToken) {
                Message m = messageToken.getMessage();
                logger.info("receive 设备透传方式上报温湿度" + new String(messageToken.getMessage().getPayload()));
                //收到数据以后转化格式
                DevicePropertyPost devicePropertyPost = AliyunMessageTransformUtils.getDevicePropertyPost(m, productKey);
                //更新设备信息表
                Boolean isUpdateDeviceProperty = deviceInfoService.updateOrInsertDeviceProperty(devicePropertyPost);
                //更新设备历史温湿度表
                Boolean isUpdateDevicePropHistoryLog = devicePropHistoryLogService.insertDevicePropHistoryLog(devicePropertyPost);
                if (isUpdateDeviceProperty && isUpdateDevicePropHistoryLog) {
                    return Action.CommitSuccess;
                }
                return Action.CommitFailure; }});


        client.setMessageListener(topic.getTempAlarmTopic(), new MessageCallback() {
            @Override
            public Action consume(MessageToken messageToken) {
                Message m = messageToken.getMessage();
                logger.info("receive 透传方式设备报警" + new String(messageToken.getMessage().getPayload()));
                DeviceEvent deviceEvent = AliyunMessageTransformUtils.getDeviceEvent(m, productKey);
                Boolean isInsertAlarmHistoryLog = null;
                Boolean isUpdateAlarmStatus = null;
                isInsertAlarmHistoryLog = alarmHistoryLogService.insertAlarmHistoryLogByMysqlQuery(deviceEvent);
                isUpdateAlarmStatus = deviceInfoService.updateAlarmStatus(deviceEvent.getDeviceName(), null,true);
                if (isInsertAlarmHistoryLog && isUpdateAlarmStatus) {
                    return Action.CommitSuccess;
                }
                return Action.CommitFailure;
            }
        });


        // 数据接收
        client.connect(messageToken -> {
            Message m = messageToken.getMessage();
            byte[] payload = m.getPayload();
            System.out.println("receive message from " + m);
            System.out.println("m.getTopic() = " + m.getTopic());
            return MessageCallback.Action.CommitSuccess;
        });
	*/


}
