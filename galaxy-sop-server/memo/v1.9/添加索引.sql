ALTER TABLE `information_title_relate` ADD INDEX index_title_id ( `title_id` );
ALTER TABLE `information_title_relate` ADD INDEX index_report_type ( `report_type` );
ALTER TABLE `information_result` ADD INDEX index_title_id ( `title_id` );