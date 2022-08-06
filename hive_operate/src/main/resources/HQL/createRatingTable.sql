CREATE EXTERNAL TABLE `t_rating_rhea`(
  `userid` int,
  `movieid` int,
  `rate` int,
  `times` bigint)
ROW FORMAT SERDE
  'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES (
  'field.delim'='::')
STORED AS INPUTFORMAT
  'org.apache.hadoop.mapred.TextInputFormat'
OUTPUTFORMAT
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  '/data/hive/ratings.dat';
