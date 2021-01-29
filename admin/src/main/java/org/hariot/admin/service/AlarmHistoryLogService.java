package org.hariot.admin.service;

import com.aliyuncs.exceptions.ClientException;
import org.hariot.admin.dao.entity.bo.DeviceEvent;
import org.hariot.admin.dao.entity.po.AlarmHistoryLog;


import java.util.Date;
import java.util.List;

public interface AlarmHistoryLogService {
    List<AlarmHistoryLog> listAlarmHistoryLogs(String deviceName, Date startTime, Date endTime);

    Boolean insertAlarmHistoryLogByAliyunQuery(DeviceEvent deviceEvent) throws ClientException;

    Boolean insertAlarmHistoryLogByMysqlQuery(DeviceEvent deviceEvent);
}
