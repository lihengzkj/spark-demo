package com.nlabs.kafka

import java.util.HashMap
import org.apache.kafka.clients.producer.{KafkaProducer,ProducerConfig,ProducerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
/***
    usr/local/spark/bin/spark-submit 
    --driver-class-path /usr/local/spark/jars/  *:/usr/local/spark/jars/kafka/ * 
    --class "com.nlabs.kafka.KafkaWordProducer" /home/hadoop/test-jars/sparkkafkatest_2.11-1.0.jar 
    localhost:9092 wordsender 10 20
*/

object KafkaWordProducer {
  def main(args : Array[String]){
    if(args.length < 4){
      System.err.println("Usage: KafkaWordProducer <metadataBrokerList> <topic> <messagesPersec> <wordPerMessage>")
      System.exit(1)
    }
    
    /*
    brokers: localhost:9092
    topic:   wordsender 
    messagesPersec:10 
    wordsPerMessage:20
    */
    val Array(brokers, topic, messagesPersec,wordsPerMessage) = args
    
    //zookeeper connection properties
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    
    val producer = new KafkaProducer[String,String](props)
    
    //send some message
    while(true){
      (1 to messagesPersec.toInt).foreach{
        messageNum => 
          val str = (1 to wordsPerMessage.toInt).map(x => scala.util.Random.nextInt(10).toString()).mkString(" ")
          print(str)
          println
          
          val message = new ProducerRecord[String,String](topic,null,str)
          producer.send(message)
      }
      Thread.sleep(1000)
    }
    
  }
}