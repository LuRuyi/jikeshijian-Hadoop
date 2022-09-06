package cn.jike.week6;

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.{FileSplit, TextInputFormat}
import org.apache.spark.rdd.{NewHadoopRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

object Index {
  //倒排索引实现
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("index").setMaster("local[*]")
    val sc =  new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")

    val hadoopConfiguration: Configuration = sc.hadoopConfiguration
    //Text是一行文本内容
    val lineValues: RDD[(LongWritable, Text)] = sc.newAPIHadoopFile("/Users/rhlu/IdeaProjects/personal/Hadoop/spark_operate/src/main/resources", classOf[TextInputFormat], classOf[LongWritable], classOf[Text], hadoopConfiguration)

    //调用mapPartitionsWithInputSplit  传入了一个匿名函数
    //inputSplit 分区数据的切片
    //iterator从迭代器，包含当前分区数据
    val result: RDD[(Path, Text)] = lineValues.asInstanceOf[NewHadoopRDD[LongWritable, Text]].mapPartitionsWithInputSplit((inputSplit, iterator) => {
        //将FileSplit强转为inputSplit 从而获取到文件名  file.getPath.getName
        val file: FileSplit = inputSplit.asInstanceOf[FileSplit]
        iterator.map(tup => (file.getPath, tup._2))
      })

    //获取到每个单词出现在哪个文件
    val lineName: RDD[(String, String)] = result.flatMap(filePaths => {
      //得到文件名 0 1 2
      val fileName: String = filePaths._1.getName.replace(".txt", "")
      //循环 得到每一行数据 去掉"" 以空格切分
      val eachWord: Array[String] = filePaths._2.toString.replace("\"", "").split(" ")
      var empty: Array[Tuple2[String, String]] = Array.empty

      eachWord.foreach(eachWord => {
        //单词+文件名作为key
        empty = empty.+:((eachWord + "\t" + fileName, fileName))
      })
      empty
    })

    //每个单词在哪个文件出现一次 记做一次
    val value: RDD[(String, Int)] = lineName.map(eachLine => {
      val first: String = eachLine._1
      (first, 1)
    }).reduceByKey(_ + _) //累加

    //提取单词 出现在哪个文件，出现几次
    val result2now: RDD[(String, String)] = value.map(eachResult => {
      val printFirst: String = eachResult._1
      val printSecond: Int = eachResult._2
      val eachWord = printFirst.split("\t")(0)
      val resultNum = "(" + printFirst.split("\t")(1) + "," + printSecond + ")"
      (eachWord, resultNum)
    })

    //合并相同key
    val groupResult: RDD[(String, Iterable[String])] = result2now.groupByKey()
    val tuples: Array[(String, Iterable[String])] = groupResult.collect()
    tuples.foreach(eachTuple => {
      println(eachTuple._1)
      println(eachTuple._2.toString())
    })
  }
}
