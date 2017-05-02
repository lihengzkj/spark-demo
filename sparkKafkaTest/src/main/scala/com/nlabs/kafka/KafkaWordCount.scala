package com.nlabs.kafka

import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils

/**
 * KafkaWordCount is used to count words from KafkaWordProducer
 */
object KafkaWordCount {
  def main(args : Array[String]){
    //val sc = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    println(" consumer start://")
    
    if(args.length < 4){
      System.err.println("Usage: KafkaWordCount <zkURL> <group> <topics> <numThreads>")
    }
    
    val Array(zkURL,group,topics,numThreads) = args
    println(args)
    
    val sc = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sc,Seconds(2))
    
    /**设置检查点，如果是存放 在HDFS上面，则 写成类似ssc.checkpoint("/user/hadoop/checkpoint")这种形式，但是，要启动hadoop*/
    ssc.checkpoint("file:///usr/local/spark/mycode/kafka/checkpoint")
    
    /**zookeeper的服务器地址
    val zkURL = "localhost:2181" */
    
    /**topic所在的group，可以设置为自己想要的名称
    val group = "1" */
    
    /**topic名称
    val topics = "wordsender"*/
    
    /**topic 分区
    val numThreads = 1*/
    
    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap
    
    val lineMap = KafkaUtils.createStream(ssc,zkURL,group,topicMap)
    
    val lines = lineMap.map(_._2)
    
    val words = lines.flatMap(_.split(" "))
    
    val pair = words.map(x => (x,1))
    
    val wordCounts = pair.reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
    
    //val wordCounts = pair.reduceByKey(_ + _)
    
    wordCounts.print()
    wordCounts.saveAsTextFiles("test_result", System.currentTimeMillis().toString)
    
    ssc.start
    println(" consumer end://")
    ssc.awaitTermination
  }
}