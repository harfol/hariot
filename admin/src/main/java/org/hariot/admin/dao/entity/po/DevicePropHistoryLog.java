package org.hariot.admin.dao.entity.po;



import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class DevicePropHistoryLog {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;


    private String deviceName;

    private Float currentTemperature;

    private Float currentHumidity;

    private Date gmtCreate;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}