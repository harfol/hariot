package org.hariot.admin.dao.mapper;



import org.hariot.admin.dao.entity.po.DeviceInfo;
import org.hariot.admin.dao.util.BaseMapper;


public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

     int insertOrUpdateDeviceProperty(DeviceInfo deviceInfo);

    int insertOrUpdateStatus(DeviceInfo deviceInfo);
}