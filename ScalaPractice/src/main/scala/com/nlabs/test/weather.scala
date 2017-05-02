package com.nlabs.test

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object weather {
  
  var  dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  def get(url: String) = scala.io.Source.fromURL(url).mkString
  def add = (x:(Int,Float),y:(Int,Float)) => {(x._1+y._1,x._2+y._2)}
  def sleep(seconds:Int) :Unit = {
    Thread sleep seconds
  }
  
  def main(args: Array[String]){
     
    try{
       println("  ----  Job begin  -----  ")
     
       val reg = "\\+?\\-?[0-9]{1,}\\.?[0-9]*"
       val reg2 = "clouds:[0-9]{1,}\\.?[0-9]*"
       
       val conf = new SparkConf().setAppName("Weather API")
       val sc = new SparkContext(conf)
       
       println(" - SparkContext init finished - ")
  
       var times = 10
       while(times >0){
        
  //       val result = get("http://api.weatherbit.io/v1.0/forecast/3hourly/geosearch?city=London&country=UK&key=aeff1e614e1e419281dd1078818d38ea")
         println("The "+times+" times run start")
         val result = get("https://api.weatherbit.io/v1.0/forecast/3hourly/geosearch?city=bejing&days=10&key=aeff1e614e1e419281dd1078818d38ea")
         println("\n"+result.substring(1, 500)+ "... \n")
         
         val b = sc.parallelize(result.replaceAll("\"", "").split(",")).map(l => l.replace("[","")).map(l => l.replace("]","")).map(l => l.replace("{","")).map(l => l.replace("}",""))
         println("-- get from weather API and init handle --")
         
         //b.cache();
         //println(times+" cache")
  
         val res1 = b.filter(f => f.contains("temp")&& !f.contains("_temp")).map(l => {val ar = l.split(":");(1,ar(1).toFloat)}).reduce(add)
         println("get temp")
         
         //平均风速  wind_spd
         val res2 = b.filter(f => f.contains("wind_spd")).map(l => {val ar = l.split(":");(1,ar(1).toFloat)}).reduce(add)
         println("get wind_spd")
         
         //地表温度 app_temp
         val res3 = b.filter(f => f.contains("app_temp")).map(l => {val ar = l.split(":");(1,ar(1).toFloat)}).reduce(add)
         println("get app_temp")
         
         //气压  pres
         val res4 = b.filter(f => f.contains("pres")).map(l => {val ar = l.split(":");(1,ar(1).toFloat)}).reduce(add)
         println("get pres")
         
         //云覆盖率 clouds
         val res5 = b.filter(f => f.contains("clouds") && f.matches(reg2)).map(l => {val ar = l.split(":");(1,ar(1).toFloat)}).reduce(add)
         println("get clouds")
         
         println(dateFormat.format(new Date())+" 3hrs future ==>  Avg Cloud cover as a percentage (%):"
             +(res5._2/res5._1)+"  "+"Avg Pressure (mb):"+(res4._2/res4._1)+"  "+"Avg Apparent Temperature:"+(res3._2/res3._1)+" "
             +"Avg Wind Speed:"+(res2._2/res2._1)+"  "+"Avg Temperature:"+(res1._2/res1._1))
         println("The "+times+" times run end")
         
         times = times - 1
         
         //没两分钟跑一次
         println(" ++ begin to sleep 2 mins ... ")
         sleep(1000 * 60 * 2)
         
       } 
       println("  ----  Job end  -----  ")
    }catch{
      case ex:Exception => {
        println("Error1=>"+ex.getMessage)
        println("Error1=>"+ex.getStackTraceString)
      }
      case tb : Throwable => {
        println("Error2=>"+tb.getMessage)
        println("Error2=>"+tb.getStackTraceString)
      }
    }finally{
      println("Game over at finally!")
    }
     
    
  }
  
}



     
      /*
       val sc = new SparkContext(new SparkConf())   // this will be remove
       val rdd = sc.parallelize(result.replaceAll("\"", "").split(","))
       //val map1 = rdd.map(line => if(line.startsWith("{data:[")) line.replaceFirst("data:[",""))
       
       val map1 = rdd.map(line => {val temArr = line.split(":");if(temArr.length==2)(temArr(0),temArr(1))})*/
      
       //sc.parallelize(result.replaceAll("\"", "").split(",")).map(l => l.replace("[","")).map(l => l.replace("]","")).map(l => l.replace("{","")).map(l => l.replace("}",""))
       //val result4 = sc.parallelize(result.replaceAll("\"", "").split(",")).map(l => l.replace("[","")).map(l => l.replace("]","")).map(l => l.replace("{","")).map(l => l.replace("}","")).map(line => {val temArr = line.split(":");if(temArr.length==2 && temArr(1).matches("\\+?\\-?[0-9]{1,}\\.?[0-9]*"))(temArr(0),temArr(1).toFloat)})
       //result4.filter(e => e.toString.length > 2).foreach(println)
    
    // val temp = "{data:[{wind_cdir:SW,rh:16,wind_spd:2,pop:0,wind_cdir_full:southwest,app_temp:20.45,pres:1010.5,dewpt:-5.2,snow:0,uv:2,wind_dir:225,weather:{icon:c02d,code:802,description:Scattered clouds},precip:0,ts:1492765200,datetime:2017-04-21:09,temp:21.8,slp:1013.5,clouds:50,vis:10}],city_name:Bejing,lon:116.39723,timezone:Asia Shanghai,lat:39.9075,country_code:CN,state_code:22}"
     
     //val arrs = temp.split(",").map(line => {val temArr = line.split(":");if(temArr.length==2 && temArr(1).matches("\\+?\\-?[0-9]{1,}\\.?[0-9]*"))(temArr(0),temArr(1).toFloat)})
     
       //求出平均温度  temp
       //val avgTemp = result5.filter(f => f.contains("temp")).map(line => {val temArr = line.split(":");if(temArr.length==2 && temArr(1).matches(reg))(temArr(0),temArr(1).toFloat)})
       // val avgTemp = result5.filter(f => f.contains("temp")).map(line => {val temArr = line.split(":");if(temArr.length==2 && temArr(1).matches(reg)) Map(temArr(0) -> temArr(1).toFloat)})
       //val avgTemp = result5.filter(f => f.contains("temp")).map(line => {val temArr = line.split(":");if(temArr.length==2 && temArr(1).matches(reg))(temArr(0).toString,temArr(1).toFloat)})
       
       //val d = c.map(l => (l.split(":")(0),l.split(":")(1).toFloat))
       //val d = c.map(l => {val ar = l.split(":");(ar(0),ar(1).toFloat)})
       // val d = c.map(l => {val ar = l.split(":");(1,ar(1).toFloat)})
       
//     print(arrs.toList)s
     //println(arrs.getClass)
     //arrs.foreach(e => println(e.getClass()+" "+ e ))
//     arrs.foreach(p => println(p.asInstanceOf[scala.runtime.BoxedUnit]))
//     arrs.filter(p => !p.isInstanceOf[scala.runtime.BoxedUnit]).foreach(println)
//     arrs.filter(p => p.isInstanceOf[scala.Tuple2[String,Float]]).foreach(e => println(e._1+"---"+e._2))
     
//     val result2 = arrs.filter(p => p._1 > 0)
     
     //val result2 = arrs.filter(p => if(p._1.isInstanceOf[Float]) p._1)
     
     /*val location_start = temp.indexOf("[")
     val location_end = temp.indexOf("]")
     
     print(location_start +" "+ location_end+" \n")
     
     print(temp.substring(location_start+1, location_end))*/