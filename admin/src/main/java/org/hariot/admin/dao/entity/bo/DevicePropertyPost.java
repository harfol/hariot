package org.hariot.admin.dao.entity.bo;



import java.util.Date;

public class DevicePropertyPost {
    private String deviceType;
    private String iotId;
    private String productKey;
    private Date gmtCreate;
    private String deviceName;
    private Data items;

    public String getDeviceType() {
        return deviceType;
    }

    public String getIotId() {
        return iotId;
    }

    public String getProductKey() {
        return productKey;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Data getItems() {
        return items;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setItems(Data items) {
        this.items = items;
    }

    public static class Data {
        private CurrentHumidityPropertyInfo CurrentHumidity;
        private CurrentTemperaturePropertyInfo CurrentTemperature;
        private TempThresholdPropertyInfo TempThreshold;

        public CurrentHumidityPropertyInfo getCurrentHumidity() {
            return CurrentHumidity;
        }

        public CurrentTemperaturePropertyInfo getCurrentTemperature() {
            return CurrentTemperature;
        }

        public TempThresholdPropertyInfo getTempThreshold() {
            return TempThreshold;
        }

        public void setCurrentHumidity(CurrentHumidityPropertyInfo currentHumidity) {
            CurrentHumidity = currentHumidity;
        }

        public void setCurrentTemperature(CurrentTemperaturePropertyInfo currentTemperature) {
            CurrentTemperature = currentTemperature;
        }

        public void setTempThreshold(TempThresholdPropertyInfo tempThreshold) {
            TempThreshold = tempThreshold;
        }
    }
    public static class CurrentHumidityPropertyInfo{
        private Float value;
        private Date  time;

        public Float getValue() {
            return value;
        }

        public Date getTime() {
            return time;
        }

        public void setValue(Float value) {
            this.value = value;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }
    public static class CurrentTemperaturePropertyInfo{
        private Float value;
        private Date  time;

        public Float getValue() {
            return value;
        }

        public Date getTime() {
            return time;
        }

        public void setValue(Float value) {
            this.value = value;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }
    public static class TempThresholdPropertyInfo{
        private Float value;
        private Date time;

        public Float getValue() {
            return value;
        }

        public Date getTime() {
            return time;
        }

        public void setValue(Float value) {
            this.value = value;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }

}
