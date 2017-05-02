package com.nlabs.kafka

object Test {
  def main(args : Array[String]){
    
    println(scala.util.Random.nextInt(10).toString().mkString(" "))
    
    val num = 20
    
    (1 to num.toInt).foreach{
        messageNum => 
          val str = (1 to num.toInt).map(x => scala.util.Random.nextInt(10).toString()).mkString(" ")
          print(str)
          println  
      }
    
    
    
    val topics = "wordsender"
    val topicMap = topics.split(",").map((_,1.toInt)).toMap
    
    println(topicMap)
  }
}