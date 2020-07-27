/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.6.42-log : Database - anyest3_0523
*********************************************************************
*/


/*Table structure for table `hibernate_sequence` */
--  hibernate的序列化表,用于生产序列化id

CREATE TABLE  IF NOT EXISTS  `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_board_template` */
-- 看板模板实体类

CREATE TABLE IF NOT EXISTS  `ins_board_template` (
  `board_template_id` int(11) NOT NULL  COMMENT '模板id' ,
  `content_code` varchar(50) DEFAULT NULL COMMENT '机构标识',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_person` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `project_id` int(11) DEFAULT NULL COMMENT '所属资源集或项目id',
  `config` longtext COMMENT '包含 linkages，filters',
  `default_template` bit(1) DEFAULT NULL COMMENT '是否为默认模板',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `index` int(11) DEFAULT NULL COMMENT '展示顺序',
  `name` varchar(50) DEFAULT NULL COMMENT '模板名称',
  `topic_info_id` int(11) DEFAULT NULL COMMENT '实体id',
  `type` varchar(20) DEFAULT NULL COMMENT '模板类型：0分群画像模板，1个人画像模板',
  PRIMARY KEY (`board_template_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_board_template_widget_info` */
-- 报表模板的组件

CREATE TABLE IF NOT EXISTS  `ins_board_template_widget_info` (
  `widget_id` int(11) NOT NULL comment '主键id',
  `content_code` varchar(50) DEFAULT NULL comment '机构号',
  `create_person` varchar(50) DEFAULT NULL comment '创建人',
  `create_time` datetime DEFAULT NULL comment '创建时间',
  `update_person` varchar(50) DEFAULT NULL comment '更新人',
  `update_time` datetime DEFAULT NULL comment '更新时间',
  `project_id` int(11) DEFAULT NULL comment '所属资源集或项目id',
  `board_template_id` int(11) DEFAULT NULL comment '关联模板id',
  `category` varchar(255) DEFAULT NULL comment '组件分类,标题,字段,图表,分割线',
  `config` longtext comment '配置信息',
  `description` varchar(200) DEFAULT NULL comment '描述',
  `frequency` int(11) DEFAULT NULL comment '轮询频率',
  `height` int(11) DEFAULT NULL comment '高度',
  `polling` bit(1) DEFAULT NULL comment '是否轮询',
  `publish` bit(1) DEFAULT NULL comment '发布',
  `topic_info_id` int(11) DEFAULT NULL comment '实体模型对象的id',
  `widget_name` varchar(50) DEFAULT NULL comment '报表名称',
  `widget_type` varchar(50) DEFAULT NULL comment '报表类型',
  `width` int(11) DEFAULT NULL comment '宽度',
  `x` int(11) DEFAULT NULL comment '位置x',
  `y` int(11) DEFAULT NULL comment '位置y',
  PRIMARY KEY (`widget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_dashboard_group` */


CREATE TABLE IF NOT EXISTS  `ins_dashboard_group` (
  `dashboard_group_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `dashboard_group_name` varchar(50) DEFAULT NULL,
  `dashboard_id` int(11) DEFAULT NULL,
  `group_owner` varchar(20) DEFAULT NULL,
  `group_type` varchar(20) DEFAULT NULL,
  `order_weight` float DEFAULT NULL,
  `parent_group_id` int(11) DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`dashboard_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_dashboard_view` */


CREATE TABLE IF NOT EXISTS  `ins_dashboard_view` (
  `dashboard_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `board_template_id` int(11) DEFAULT NULL,
  `board_type` varchar(20) DEFAULT NULL,
  `dashboard_name` varchar(50) DEFAULT NULL,
  `object_id` int(11) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dashboard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_market_channel` */


CREATE TABLE IF NOT EXISTS  `ins_market_channel` (
  `channel_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `auto_unpacking` bit(1) DEFAULT NULL,
  `channel_name` varchar(50) DEFAULT NULL,
  `channel_status` varchar(15) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `send_number` int(11) DEFAULT NULL,
  `send_period` int(11) DEFAULT NULL,
  `time_unit` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_market_material` */


CREATE TABLE IF NOT EXISTS  `ins_market_material` (
  `content_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `channel_type` varchar(20) DEFAULT NULL,
  `config` longtext,
  `content` longtext,
  `description` varchar(200) DEFAULT NULL,
  `fields` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `push_mode` varchar(20) DEFAULT NULL,
  `repeat_count` int(11) DEFAULT NULL,
  `send_period` varchar(20) DEFAULT NULL,
  `send_time` varchar(50) DEFAULT NULL,
  `send_time_mode` varchar(20) DEFAULT NULL,
  `time_unit` varchar(20) DEFAULT NULL,
  `trans_period` int(11) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_market_web_hook` */


CREATE TABLE IF NOT EXISTS  `ins_market_web_hook` (
  `config_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `auth_key` varchar(64) DEFAULT NULL,
  `call_back_url` varchar(500) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `config_name` varchar(40) DEFAULT NULL,
  `description` varchar(40) DEFAULT NULL,
  `invoke_times` int(11) DEFAULT NULL,
  `invoke_type` varchar(20) DEFAULT NULL,
  `marketing_type` varchar(20) DEFAULT NULL,
  `params` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_report_field` */


CREATE TABLE IF NOT EXISTS  `ins_report_field` (
  `report_field_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `configs` longtext,
  `field_name` varchar(255) DEFAULT NULL,
  `model_type` varchar(255) DEFAULT NULL,
  `sql_type` varchar(255) DEFAULT NULL,
  `used` bit(1) DEFAULT NULL,
  PRIMARY KEY (`report_field_id`),
  KEY `idx_resource_id` (`resource_id`),
  KEY `idx_version_state` (`version_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_segment_classify` */


CREATE TABLE IF NOT EXISTS  `ins_segment_classify` (
  `segment_classification_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `classification_condition` longtext,
  PRIMARY KEY (`segment_classification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_segment_classify_info` */


CREATE TABLE IF NOT EXISTS  `ins_segment_classify_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `customer_number` int(11) DEFAULT NULL,
  `mutual_exclusion_priority` int(11) DEFAULT NULL,
  `segment_model_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ins_segment_model` */


CREATE TABLE IF NOT EXISTS  `ins_segment_model` (
  `segment_model_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `classification_number` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `exclusives` bit(1) DEFAULT NULL,
  `model_code` varchar(255) DEFAULT NULL,
  `model_condition` longtext,
  `model_name` varchar(50) DEFAULT NULL,
  `selected_fields` varchar(2000) DEFAULT NULL,
  `update_period` int(11) DEFAULT NULL,
  PRIMARY KEY (`segment_model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_category` */


CREATE TABLE IF NOT EXISTS  `meta_category` (
  `category_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `category_type` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `used` bit(1) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_data_object` */


CREATE TABLE IF NOT EXISTS  `meta_data_object` (
  `data_object_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `configs` longtext,
  `params_json` longtext,
  `used` bit(1) DEFAULT NULL,
  PRIMARY KEY (`data_object_id`),
  KEY `idx_resource_id` (`resource_id`),
  KEY `idx_version_state` (`version_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_data_object_info` */


CREATE TABLE IF NOT EXISTS  `meta_data_object_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `business_data_type` varchar(50) DEFAULT NULL,
  `charge_info_json` longtext,
  `data_key` varchar(255) DEFAULT NULL,
  `data_source_id` int(11) DEFAULT NULL,
  `data_table_name` varchar(255) DEFAULT NULL,
  `object_type` varchar(50) DEFAULT NULL,
  `valid_time_hour` int(11) DEFAULT NULL,
  `valid_time_unit` int(11) DEFAULT NULL,
  `view_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resource_id`),
  KEY `data_source_id` (`data_source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_data_source_info` */


CREATE TABLE IF NOT EXISTS  `meta_data_source_info` (
  `data_source_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `db_config_json` varchar(255) DEFAULT NULL,
  `db_type` varchar(20) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `header_json` longtext,
  `source_code` varchar(255) DEFAULT NULL,
  `source_name` varchar(100) DEFAULT NULL,
  `store_type` varchar(20) DEFAULT NULL,
  `write_state` bit(1) DEFAULT NULL,
  PRIMARY KEY (`data_source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_dictionary_field` */


CREATE TABLE IF NOT EXISTS  `meta_dictionary_field` (
  `dict_id` int(11) NOT NULL,
  `dict_name` varchar(100) DEFAULT NULL,
  `dict_type` varchar(20) DEFAULT NULL,
  `dict_value` varchar(100) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dict_id`),
  KEY `field_id` (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_feature_topic_object` */


CREATE TABLE IF NOT EXISTS  `meta_feature_topic_object` (
  `feature_topic_object_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `feature_field_ids` varchar(2000) DEFAULT NULL,
  `table_version_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`feature_topic_object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_feature_topic_object_info` */


CREATE TABLE IF NOT EXISTS  `meta_feature_topic_object_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `feature_scope` varchar(50) DEFAULT NULL,
  `object_type` varchar(50) DEFAULT NULL,
  `update_period` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_function` */


CREATE TABLE IF NOT EXISTS  `meta_function` (
  `function_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `final_scripts` longtext,
  `function_type` varchar(20) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `param_fields` longtext,
  `return_value_type` varchar(20) DEFAULT NULL,
  `scripts` longtext,
  `state_value_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_function_info` */


CREATE TABLE IF NOT EXISTS  `meta_function_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `used` bit(1) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_model_object` */


CREATE TABLE IF NOT EXISTS  `meta_model_object` (
  `model_object_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `parent_object_id` int(11) DEFAULT NULL,
  `used` bit(1) DEFAULT NULL,
  PRIMARY KEY (`model_object_id`),
  KEY `idx_resource_id` (`resource_id`),
  KEY `idx_version_state` (`version_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_model_object_info` */


CREATE TABLE IF NOT EXISTS  `meta_model_object_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `object_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_object_field` */


CREATE TABLE IF NOT EXISTS  `meta_object_field` (
  `object_field_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `column_code` varchar(500) DEFAULT NULL,
  `compute_period` varchar(30) DEFAULT NULL,
  `data_object_id` int(11) DEFAULT NULL,
  `default_value` varchar(200) DEFAULT NULL,
  `derive_content` longtext,
  `description` varchar(500) DEFAULT NULL,
  `field_code` varchar(500) DEFAULT NULL,
  `field_format` varchar(200) DEFAULT NULL,
  `field_length` int(11) DEFAULT NULL,
  `field_name` varchar(100) DEFAULT NULL,
  `field_state` varchar(30) DEFAULT NULL,
  `field_type` varchar(20) DEFAULT NULL,
  `is_dimension` bit(1) DEFAULT NULL,
  `is_key` bit(1) DEFAULT NULL,
  `is_target` bit(1) DEFAULT NULL,
  `list_value_type` varchar(20) DEFAULT NULL,
  `object_type` varchar(20) DEFAULT NULL,
  `object_versions` varchar(100) DEFAULT NULL,
  `refer_field_ids` varchar(500) DEFAULT NULL,
  `resource_object_category_id` int(11) DEFAULT NULL,
  `resource_object_id` int(11) DEFAULT NULL,
  `resource_object_version_id` int(11) DEFAULT NULL,
  `scale_length` int(11) DEFAULT NULL,
  `source_field_id` int(11) DEFAULT NULL,
  `sql_fragment` varchar(1000) DEFAULT NULL,
  `update_mode` varchar(100) DEFAULT NULL,
  `value_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`object_field_id`),
  KEY `idx_resource_id` (`resource_object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_resource_info` */


CREATE TABLE IF NOT EXISTS  `meta_resource_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `latest_version_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_topic_object` */


CREATE TABLE IF NOT EXISTS  `meta_topic_object` (
  `object_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `compute_period` varchar(20) DEFAULT NULL,
  `derive_ids` longtext,
  `refer_data_object_ids` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`object_id`),
  KEY `idx_resource_id` (`resource_id`),
  KEY `idx_version_state` (`version_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_topic_object_info` */


CREATE TABLE IF NOT EXISTS  `meta_topic_object_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `bill_day` int(11) DEFAULT NULL,
  `data_source_id` int(11) DEFAULT NULL,
  `feature_table_code` varchar(255) DEFAULT NULL,
  `index_count` int(11) DEFAULT NULL,
  `info_state` varchar(255) DEFAULT NULL,
  `primary_data_object_id` int(11) DEFAULT NULL,
  `primary_dimension_id` int(11) DEFAULT NULL,
  `primary_field_id` int(11) DEFAULT NULL,
  `primary_key_field_ids` varchar(200) DEFAULT NULL,
  `primary_object_type` varchar(255) DEFAULT NULL,
  `primary_resource_id` int(11) DEFAULT NULL,
  `second_dimension_ids` varchar(255) DEFAULT NULL,
  `stat_time_field_id` int(11) DEFAULT NULL,
  `table_number` int(11) DEFAULT NULL,
  `time_splice_num` int(11) DEFAULT NULL,
  `time_splice_unit` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `meta_topic_object_relation` */


CREATE TABLE IF NOT EXISTS  `meta_topic_object_relation` (
  `object_relation_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `join_type` varchar(255) DEFAULT NULL,
  `primary_data_object_id` int(11) DEFAULT NULL,
  `primary_field_ids` varchar(200) DEFAULT NULL,
  `primary_resource_id` int(11) DEFAULT NULL,
  `relation` varchar(20) DEFAULT NULL,
  `relation_field_ids` varchar(500) DEFAULT NULL,
  `relation_object_id` int(11) DEFAULT NULL,
  `relation_object_type` varchar(255) DEFAULT NULL,
  `relation_stat_id` int(11) DEFAULT NULL,
  `topic_object_info_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`object_relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_action_record` */


CREATE TABLE IF NOT EXISTS  `res_action_record` (
  `action_record_id` int(11) NOT NULL,
  `action` varchar(20) DEFAULT NULL,
  `after_app_state` varchar(20) DEFAULT NULL,
  `after_state` varchar(20) DEFAULT NULL,
  `before_app_state` varchar(20) DEFAULT NULL,
  `before_state` varchar(20) DEFAULT NULL,
  `person` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL,
  `target_id` int(11) DEFAULT NULL,
  `target_name` varchar(255) DEFAULT NULL,
  `target_type` varchar(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`action_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_approval` */


CREATE TABLE IF NOT EXISTS  `res_approval` (
  `approval_id` int(11) NOT NULL,
  `applicant` bigint(20) DEFAULT NULL,
  `apply_state` varchar(255) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `approval_state` varchar(255) DEFAULT NULL,
  `approval_time` datetime DEFAULT NULL,
  `before_state` varchar(255) DEFAULT NULL,
  `cancel_auditor` bigint(20) DEFAULT NULL,
  `cancel_time` datetime DEFAULT NULL,
  `current_level` int(11) DEFAULT NULL,
  `final_auditor` varchar(255) DEFAULT NULL,
  `first_auditor` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `relation_id` int(11) DEFAULT NULL,
  `second_auditor` varchar(255) DEFAULT NULL,
  `strategy_info_id` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`approval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_config_item` */


CREATE TABLE IF NOT EXISTS  `res_config_item` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `field_bid` varchar(50) DEFAULT NULL,
  `strategy_info_id` int(11) DEFAULT NULL,
  `strategy_version` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_graph_meta` */


CREATE TABLE IF NOT EXISTS  `res_graph_meta` (
  `graph_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `edges_json` longtext,
  `graph_name` varchar(255) DEFAULT NULL,
  `vertex_info_json` longtext,
  `vertices_json` longtext,
  PRIMARY KEY (`graph_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_graph_meta_info` */


CREATE TABLE IF NOT EXISTS  `res_graph_meta_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_matrix` */


CREATE TABLE IF NOT EXISTS  `res_matrix` (
  `matrix_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `column_field_ids` varchar(50) DEFAULT NULL,
  `field_ranges` longtext,
  `m_chosens` longtext,
  `m_values` longtext,
  `output_field_ids` varchar(50) DEFAULT NULL,
  `row_field_ids` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`matrix_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_matrix_info` */


CREATE TABLE IF NOT EXISTS  `res_matrix_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_oper_audit` */


CREATE TABLE IF NOT EXISTS  `res_oper_audit` (
  `audit_id` int(11) NOT NULL,
  `operate` varchar(50) DEFAULT NULL,
  `operate_date` datetime DEFAULT NULL,
  `operate_person` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_name` varchar(100) DEFAULT NULL,
  `target_type` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`audit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_project_member` */


CREATE TABLE IF NOT EXISTS  `res_project_member` (
  `project_member_id` int(11) NOT NULL,
  `approval_way` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `project_role_type` varchar(30) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_member_id`),
  KEY `i_user_project` (`user_id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_project_role` */


CREATE TABLE IF NOT EXISTS  `res_project_role` (
  `project_role_type` varchar(20) NOT NULL,
  `resource_ids` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`project_role_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_project_setting` */


CREATE TABLE IF NOT EXISTS  `res_project_setting` (
  `setting_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `setting_key` varchar(100) DEFAULT NULL,
  `setting_name` varchar(100) DEFAULT NULL,
  `setting_value` varchar(2000) DEFAULT NULL,
  `value_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`setting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_project_url_resource` */


CREATE TABLE IF NOT EXISTS  `res_project_url_resource` (
  `url_resource_id` int(11) NOT NULL,
  `method` varchar(20) DEFAULT NULL,
  `operation_type` varchar(20) DEFAULT NULL,
  `url_resource_name` varchar(50) DEFAULT NULL,
  `url_resource_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`url_resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_project_user` */


CREATE TABLE IF NOT EXISTS  `res_project_user` (
  `user_id` bigint(20) NOT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_resource_project` */

CREATE TABLE IF NOT EXISTS  `res_resource_project` (
  `project_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `approval_way` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `project` varchar(255) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `use_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_resource_set_item` */


CREATE TABLE IF NOT EXISTS  `res_resource_set_item` (
  `item_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_rule` */


CREATE TABLE IF NOT EXISTS  `res_rule` (
  `rule_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `conditions` longtext,
  `hit_output` longtext,
  `miss_output` longtext,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_rule_info` */


CREATE TABLE IF NOT EXISTS  `res_rule_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_rule_set` */


CREATE TABLE IF NOT EXISTS  `res_rule_set` (
  `rule_set_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `matching` varchar(255) DEFAULT NULL,
  `rule_items` longtext,
  `rule_set_output` longtext,
  `threshold` double DEFAULT NULL,
  `weight_field_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rule_set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_rule_set_info` */


CREATE TABLE IF NOT EXISTS  `res_rule_set_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_rule_tree` */


CREATE TABLE IF NOT EXISTS  `res_rule_tree` (
  `rule_tree_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `nodes` longtext,
  PRIMARY KEY (`rule_tree_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_rule_tree_info` */


CREATE TABLE IF NOT EXISTS  `res_rule_tree_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_score_card` */


CREATE TABLE IF NOT EXISTS  `res_score_card` (
  `score_card_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `items` longtext,
  `score_field_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`score_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_score_card_info` */


CREATE TABLE IF NOT EXISTS  `res_score_card_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_script` */


CREATE TABLE IF NOT EXISTS  `res_script` (
  `script_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `in_param_fields` longtext,
  `out_param_fields` longtext,
  `script_language` varchar(20) DEFAULT NULL,
  `scripts` longtext,
  PRIMARY KEY (`script_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_script_info` */


CREATE TABLE IF NOT EXISTS  `res_script_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy` */


CREATE TABLE IF NOT EXISTS  `res_strategy` (
  `strategy_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `field_ids` varchar(8000) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `version_code` varchar(255) DEFAULT NULL,
  `version_state` varchar(20) DEFAULT NULL,
  `approval_state` varchar(255) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `node_number` int(11) DEFAULT NULL,
  `publish_mode` varchar(30) DEFAULT NULL,
  `run_time` bigint(20) DEFAULT NULL,
  `start_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`strategy_id`),
  KEY `idx_resource_id` (`resource_id`),
  KEY `idx_version_state` (`version_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy_business_goal` */


CREATE TABLE IF NOT EXISTS  `res_strategy_business_goal` (
  `strategy_business_goal_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `business_goal_name` varchar(50) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `conditions` longtext,
  `description` varchar(200) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  `model_object_id` int(11) DEFAULT NULL,
  `strategy_id` int(11) DEFAULT NULL,
  `target_type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`strategy_business_goal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy_group` */


CREATE TABLE IF NOT EXISTS  `res_strategy_group` (
  `strategy_group_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `group_mode` varchar(30) DEFAULT NULL,
  `mutual_exclude_period` varchar(30) DEFAULT NULL,
  `period` int(11) DEFAULT NULL,
  `strategy_group_name` varchar(50) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`strategy_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy_info` */


CREATE TABLE IF NOT EXISTS  `res_strategy_info` (
  `resource_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `resource_state` varchar(255) DEFAULT NULL,
  `resource_type` varchar(20) DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `batch_time` varchar(255) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `group_mode` varchar(30) DEFAULT NULL,
  `model_category_id` int(11) DEFAULT NULL,
  `model_object_id` int(11) DEFAULT NULL,
  `model_object_type` varchar(255) DEFAULT NULL,
  `model_resource_id` int(11) DEFAULT NULL,
  `route_field_id` int(11) DEFAULT NULL,
  `route_value` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `strategy_group_id` int(11) DEFAULT NULL,
  `strategy_key` varchar(20) DEFAULT NULL,
  `strategy_type` varchar(30) DEFAULT NULL,
  `time_range` varchar(255) DEFAULT NULL,
  `time_type` varchar(30) DEFAULT NULL,
  `topic_object_info_resource_id` int(11) DEFAULT NULL,
  `uuid_ref_field` varchar(255) DEFAULT NULL,
  `view_content` longtext,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy_node` */


CREATE TABLE IF NOT EXISTS  `res_strategy_node` (
  `node_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `execute_mode` varchar(30) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `junction` varchar(16) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `node_content` longtext,
  `node_key` varchar(20) DEFAULT NULL,
  `node_type` varchar(30) DEFAULT NULL,
  `strategy_id` int(11) DEFAULT NULL,
  `text` varchar(50) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy_node_link` */


CREATE TABLE IF NOT EXISTS  `res_strategy_node_link` (
  `node_link_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `from_key` varchar(18) DEFAULT NULL,
  `position` varchar(12) DEFAULT NULL,
  `strategy_id` int(11) DEFAULT NULL,
  `to_key` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`node_link_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_strategy_report_indicator` */


CREATE TABLE IF NOT EXISTS  `res_strategy_report_indicator` (
  `indicator_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `data_object_version_id` int(11) DEFAULT NULL,
  `default_value` varchar(200) DEFAULT NULL,
  `derive_content` longtext,
  `description` varchar(200) DEFAULT NULL,
  `field_code` varchar(500) DEFAULT NULL,
  `field_format` varchar(200) DEFAULT NULL,
  `field_name` varchar(100) DEFAULT NULL,
  `field_type` varchar(20) DEFAULT NULL,
  `list_value_type` varchar(20) DEFAULT NULL,
  `max_length` int(11) DEFAULT NULL,
  `object_type` varchar(20) DEFAULT NULL,
  `refer_field_ids` varchar(500) DEFAULT NULL,
  `resource_object_version_id` int(11) DEFAULT NULL,
  `value_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`indicator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_system_param` */


CREATE TABLE IF NOT EXISTS  `res_system_param` (
  `param_id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `param_key` varchar(100) DEFAULT NULL,
  `param_name` varchar(100) DEFAULT NULL,
  `param_scope` varchar(20) DEFAULT NULL,
  `param_value` varchar(2000) DEFAULT NULL,
  `scope_id` int(11) DEFAULT NULL,
  `value_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `table_entity_conversion_rule` */


CREATE TABLE IF NOT EXISTS  `table_entity_conversion_rule` (
  `id` int(11) NOT NULL,
  `content_code` varchar(50) DEFAULT NULL,
  `create_person` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `data_conversion_model` varchar(255) DEFAULT NULL,
  `entity_key` varchar(255) DEFAULT NULL,
  `entity_value` varchar(255) DEFAULT NULL,
  `table_code` varchar(255) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_resource_id` (`table_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


