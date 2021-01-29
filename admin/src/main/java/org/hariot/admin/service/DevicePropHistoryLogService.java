package org.hariot.admin.service;

import org.hariot.admin.dao.entity.bo.DevicePropertyPost;
import org.hariot.admin.dao.entity.po.DevicePropHistoryLog;

import java.util.Date;
import java.util.List;

public interface DevicePropHistoryLogService {
    List<DevicePropHistoryLog> listDevicePropHistoryLogs(String deviceName, Date startTime, Date endTime);

    Boolean insertDevicePropHistoryLog(DevicePropertyPost devicePropertyPost);
}
