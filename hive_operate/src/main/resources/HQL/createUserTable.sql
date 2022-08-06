CREATE EXTERNAL TABLE `t_user`(
  `userid` int,
  `sex` string,
  `age` int,
  `occupation` string,
  `zipcode` bigint)
ROW FORMAT SERDE
  'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES (
  'field.delim'='::',
  'serialization.format'='::')
STORED AS INPUTFORMAT
  'org.apache.hadoop.mapred.TextInputFormat'
OUTPUTFORMAT
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  '/rhea/hive/data/user';