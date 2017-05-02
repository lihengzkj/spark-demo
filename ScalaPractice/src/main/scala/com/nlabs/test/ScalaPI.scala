

package com.nlabs.test 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import scala.math.random

object ScalaPI {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Spark PI")
    val sparkContext = new SparkContext(conf)
    
    val slices = if (args.length>0) args(0).toInt else 2
    
    val n = math.min(100000L * slices, Int.MaxValue).toInt
    
    println("[log]the final value of n is %d".format(n))
    
    val count = sparkContext.parallelize(1 until n, slices).map{
      i => 
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x*x+y*y <=1) 1 else 0
    }.reduce(_ + _)
    
    println("PI is roughtly "+ 4.0*count/(n-1))
    sparkContext.stop()
    
  }
}