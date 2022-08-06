-- 题目一（简单）展示电影 ID 为 2116 这部电影各年龄段的平均影评分。
SELECT c.age, avg(b.rate)
FROM t_movie_rhea a JOIN t_rating_rhea b ON a.movieid = b.movieid
JOIN t_user_rhea c ON c.userid = b.userid
WHERE a.movieid = 2116
GROUP BY c.age
ORDER BY c.age;

-- 题目二（中等）找出男性评分最高且评分次数超过 50 次的 10 部电影，展示电影名，平均影评分和评分次数。
select c.moviename as name, avg(a.rate) as avgrate, count(c.moviename) as total
from t_rating_rhea a
join t_user_rhea b on a.userid=b.userid
join t_movie_rhea c on a.movieid=c.movieid
where b.sex="M"
group by c.moviename
having total > 50
order by avgrate desc
limit 10;

-- 题目三（选做）找出影评次数最多的女士所给出最高分的 10 部电影的平均影评分，展示电影名和平均影评分（可使用多行 SQL）。
-- 女性影评次数最多，useid=1150
select a.userid
from t_user_rhea a
join t_rating_rhea b on a.userid=b.userid
where a.sex = "F"
group by a.userid
ORDER BY COUNT(1) DESC limit 1;

-- 评分最高的电影
create table t_top_ten_movies_rhea as
select movieid, rate from t_rating_rhea where userid = 1150 order by rate desc limit 10;

select b.movieid as movieid, c.moviename as moviename, avg(b.rate) as avgrate
from t_top_ten_movies_rhea a
join t_rating_rhea b on a.movieid=b.movieid
join t_movie_rhea c on b.movieid=c.movieid
group by b.movieid,c.moviename;