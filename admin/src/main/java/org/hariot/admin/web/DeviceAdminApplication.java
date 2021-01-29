package org.hariot.admin.web;

import org.hariot.admin.service.impl.DeviceInfoServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "org.hariot.admin")
@MapperScan(basePackages = "org.hariot.admin.dao.mapper")
public class DeviceAdminApplication {
	public static void main(String[] args) {
		 SpringApplication.run(DeviceAdminApplication.class, args);

    }
}


