CREATE TABLE `alarm_history_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名',
  `alarm_temperature` float DEFAULT NULL COMMENT '报警时温度值',
  `temp_threshold` float DEFAULT NULL COMMENT '报警时温度阈值',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE `device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名',
  `current_temperature` float DEFAULT NULL COMMENT '当前温度值',
  `current_humidity` float DEFAULT NULL COMMENT '当前湿度值',
  `temp_threshold` float DEFAULT NULL COMMENT '温度报警阈值',
  `alarm_status` tinyint(1) DEFAULT NULL COMMENT '报警状态',
  `last_alarm_date` datetime DEFAULT NULL COMMENT '最近一次报警时间',
  `last_online_date` datetime DEFAULT NULL COMMENT '最近一次上线时间',
  `connect_status` varchar(50) DEFAULT NULL COMMENT '在线状态',
  `gmt_update` datetime DEFAULT NULL COMMENT '刷新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_name` (`device_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE `device_prop_history_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名',
  `current_temperature` float DEFAULT NULL COMMENT '当前温度值',
  `current_humidity` float DEFAULT NULL COMMENT '当前湿度值',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

