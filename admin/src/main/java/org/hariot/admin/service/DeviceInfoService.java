package org.hariot.admin.service;

import org.hariot.admin.dao.entity.bo.DeviceLifecyclePost;
import org.hariot.admin.dao.entity.bo.DevicePropertyPost;
import org.hariot.admin.dao.entity.po.DeviceInfo;


import java.util.Set;

public interface DeviceInfoService {
    boolean updateStatus(String deviceName, String status);

    DeviceInfo getDeviceInfoByDeviceName(String deviceName);

    Set<String> listDeviceNames();

    Boolean updateOrInsertDeviceProperty(DevicePropertyPost devicePropertyPost);

    Boolean insertOrDelete(DeviceLifecyclePost deviceLifecycle);

    Boolean updateAlarmStatus(String deviceName,Float currentTemperatureValue, boolean b);

    void updateTempThreshold(String deviceName, Float tempThreshold);
}
