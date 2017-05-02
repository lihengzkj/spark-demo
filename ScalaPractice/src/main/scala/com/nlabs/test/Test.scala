package com.nlabs.test

import java.text.SimpleDateFormat
import java.util.Date

object Test {
  def main(args : Array[String]){
    /*var times = 100;
    while(times > 0){
      sleep(1000)
      println(times)
      times = times - 1
    }*/
    
    val reg2 = "clouds:[0-9]{1,}\\.?[0-9]*"
    
    println("description:Scattered clouds".matches(reg2))
    println("description:Broken clouds".matches(reg2))
    
    println("clouds:0".matches(reg2))
    println("clouds:23.3".matches(reg2))
    println("clouds:32".matches(reg2))
    
    var now:Date = new Date()
    var  dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var hehe = dateFormat.format( now )
    println(hehe)

  }
  
  def sleep(seconds:Int) :Unit = {
    Thread sleep seconds
  }
}