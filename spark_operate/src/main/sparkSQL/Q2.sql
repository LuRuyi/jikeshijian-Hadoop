-- 1构建一条 SQL，同时 apply 下面三条优化规则：
-- CombineFilters
-- CollapseProject
-- BooleanSimplificatio

CREATE TABLE t1(a1 INT, a2 INT) USING parquet;

SELECT a11, (a2+1) AS a21
FROM (
	SELECT (a1 +1) AS a11, a2 FROM t1 WHERE a1>10
) WHERE a11>1 AND 1=1;


-- 2构建一条 SQL，同时 apply 下面五条优化规则：
-- ConstantFolding
-- PushDownPredicates
-- ReplaceDistinctWithAggregate
-- ReplaceExceptWithAntiJoin
-- FoldablePropagation

CREATE TABLE t1(a1 INT, a2 INT) USING parquet;
CREATE TABLE t2(b1 INT, b2 INT) USING parquet;

SELECT DISTINCT a1, a2, 'custom' a3
FROM (
	SELECT * FROM t1 WHERE a2=10 AND 1=1
) WHERE a1>5 AND 1=1
EXCEPT SELECT b1, b2, 1.0 b3 FROM t2 WHERE b2=10;