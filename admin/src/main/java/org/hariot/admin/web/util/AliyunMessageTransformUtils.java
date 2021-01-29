package org.hariot.admin.web.util;

import com.aliyun.openservices.iot.api.message.entity.Message;
import org.hariot.admin.dao.entity.bo.DeviceEvent;
import org.hariot.admin.dao.entity.bo.DevicePropertyPost;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class AliyunMessageTransformUtils {

    public static DevicePropertyPost getDevicePropertyPost(Message m, String productKey) {
        DevicePropertyPost devicePropertyPost=new DevicePropertyPost();
        devicePropertyPost.setDeviceName(getDeviceName(m.getTopic(),productKey));

        byte[] payload = m.getPayload();
        //收到数据为byte数组，数组第一位为当前温度值
        DevicePropertyPost.Data data=new DevicePropertyPost.Data();
        DevicePropertyPost.CurrentTemperaturePropertyInfo currentTemperaturePropertyInfo=new  DevicePropertyPost.CurrentTemperaturePropertyInfo();
        currentTemperaturePropertyInfo.setValue((float)payload[0]);
        data.setCurrentTemperature(currentTemperaturePropertyInfo);

       //收到数据为byte数组，数组第二位为当前湿度值
        DevicePropertyPost.CurrentHumidityPropertyInfo currentHumidityPropertyInfo=new DevicePropertyPost.CurrentHumidityPropertyInfo();
        currentHumidityPropertyInfo.setValue((float)payload[1]);
        data.setCurrentHumidity(currentHumidityPropertyInfo);

        //收到数据为byte数组，数组第三位为温度阈值
        DevicePropertyPost.TempThresholdPropertyInfo tempThresholdPropertyInfo=new  DevicePropertyPost.TempThresholdPropertyInfo();
        tempThresholdPropertyInfo.setValue((float)payload[2]);
        data.setTempThreshold(tempThresholdPropertyInfo);
        devicePropertyPost.setItems(data);
        devicePropertyPost.setGmtCreate(new Date(m.getGenerateTime()));
        return devicePropertyPost;

    }

    public static String getDeviceName(String topic,String productKey) {
        String deviceName = StringUtils.substringBetween(topic, "/" + productKey + "/", "/");
        return deviceName;
    }

    public static DeviceEvent getDeviceEvent(Message m, String productKey) {
        DeviceEvent deviceEvent=new DeviceEvent();
        deviceEvent.setDeviceName(getDeviceName(m.getTopic(),productKey));
        deviceEvent.setTime(new Date(m.getGenerateTime()));
        return deviceEvent;
    }
}
