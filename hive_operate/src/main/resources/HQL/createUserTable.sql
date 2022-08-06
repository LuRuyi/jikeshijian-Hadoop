CREATE TABLE `t_user_rhea`(
  `userid` int,
  `sex` string,
  `age` int,
  `occupation` int,
  `zipcode` string)
ROW FORMAT SERDE
  'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
  'field.delim'='::',
  'serialization.format'='::')
STORED AS INPUTFORMAT
  'org.apache.hadoop.mapred.TextInputFormat'
OUTPUTFORMAT
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  '/data/hive/users.dat';