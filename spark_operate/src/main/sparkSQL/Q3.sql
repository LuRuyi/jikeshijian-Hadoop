CREATE TABLE t1(a1 INT, a2 INT) USING parquet;

SELECT a1, a2*1 from t1;