package org.hariot.admin.dao.entity.po;




import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class DeviceInfo  {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String deviceName;

    private Float currentTemperature;

    private Float currentHumidity;

    private Float tempThreshold;

    private Boolean alarmStatus;

    private String connectStatus;

    private Date gmtUpdate;

    private Date lastAlarmDate;

    private Date lastOnlineDate;

    public Date getLastAlarmDate() {
        return lastAlarmDate;
    }

    public Date getLastOnlineDate() {
        return lastOnlineDate;
    }

    public void setLastAlarmDate(Date lastAlarmDate) {
        this.lastAlarmDate = lastAlarmDate;
    }

    public void setLastOnlineDate(Date lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Float getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(Float currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public Float getTempThreshold() {
        return tempThreshold;
    }

    public void setTempThreshold(Float tempThreshold) {
        this.tempThreshold = tempThreshold;
    }

    public Boolean getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Boolean alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(String connectStatus) {
        this.connectStatus = connectStatus == null ? null : connectStatus.trim();
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}