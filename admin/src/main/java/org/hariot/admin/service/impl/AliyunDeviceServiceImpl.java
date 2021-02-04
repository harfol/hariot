package org.hariot.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.iot.model.v20180120.*;
import org.hariot.admin.service.AliyunDeviceService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AliyunDeviceServiceImpl implements AliyunDeviceService{
    private static final Logger LOGGER = LoggerFactory.getLogger(AliyunDeviceService.class);
    //@Value("${iot.regionId}")是加载application.properties配置文件对象的值
    @Value("${iot.regionId}")
    private  String regionId;

    @Value("${iot.domain}")
    private  String domain;

    @Value("${iot.productCode}")
    private  String productCode;


    @Value("${user.accessKeyID}")
    private  String accessKeyID;

    @Value("${user.accessKeySecret}")
    private  String accessKeySecret;
 
    @Value("${iot.productKey}")
    private String productKey;


    DefaultAcsClient client = null;
    //阿里云物联网平台SDK已经封装了API使用方法，只需调用方法即可。首先初始化SDK，被@PostConstruct修饰的方法会在初始化的时候运行，并且只会被服务器调用一次.
    @PostConstruct
    public DefaultAcsClient getClient() {

        try {
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyID, accessKeySecret);
            // 初始化client
            client = new DefaultAcsClient(profile);

        } catch (Exception e) {
            LOGGER.error("初始化client失败！exception:" + e.getMessage());
        }
        return client;
    }
    @Override
    public List<QueryDevicePropertyStatusResponse.Data.PropertyStatusInfo> queryDevicePro(String deviceName) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion("2018-01-20");
        request.setSysAction("QueryDevicePropertyStatus");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("ProductKey", productKey);
        request.putQueryParameter("DeviceName", deviceName);
        try {
            CommonResponse response = client.getCommonResponse(request);
            QueryDevicePropertyStatusResponse data =
                JSON.parseObject(response.getData(), QueryDevicePropertyStatusResponse.class);
            System.out.println(response.getData());
            return data.getData().getList();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  Boolean setDeviceProperty(String deviceName, String name, String value) throws ClientException {
		CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion("2018-01-20");
        request.setSysAction("SetDeviceProperty");
        request.putQueryParameter("RegionId", regionId);
		String pol = "{\""+ name  +"\": "+ value +" }";
        request.putQueryParameter("Items", pol );
        request.putQueryParameter("ProductKey", productKey);
        request.putQueryParameter("DeviceName", deviceName);
        try {
            CommonResponse response = client.getCommonResponse(request);
			return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
		return false;
    }

    @Override
    public Boolean invokeThingService(String deviceName, String identifier, String args) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion("2018-01-20");
        request.setSysAction("InvokeThingService");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("Args", args);
        request.putQueryParameter("Identifier", identifier);
        request.putQueryParameter("ProductKey", productKey);
        request.putQueryParameter("DeviceName", deviceName);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return  true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
		return false;
    }


    @Override
    public  Boolean pub(String deviceName, String identifier, String msg) throws ClientException {
	/*
        String topic ="/"+productKey+"/"+deviceName+"/user/"+identifier;
        PubRequest request = new PubRequest();
        request.setProductKey(productKey);
        request.setTopicFullName(topic);
        request.setMessageContent(Base64.encodeBase64String(msg.getBytes()));
        request.setAcceptFormat(FormatType.JSON);
        request.setQos(1);
        PubResponse acsResponse =client.getAcsResponse(request);
        if(acsResponse!=null){
            return acsResponse.getSuccess();
        }
		*/
        return false;
    }



}
