## spark-demo

1. scalapractice_2.11-1.0.jar
	
	 Desc: This is the first demo. To calculate PI <br />
	 Main class: com.nlabs.test.ScalaPI <br />
	 Execute command: `dcos spark run --submit-args='--class com.nlabs.test.ScalaPI https://github.com/lihengzkj/spark-demo/raw/master/scalapractice_2.11-1.0.jar 30'`
	 <br /> <br />
	 Desc: This jar will call a weather API to get bejing 3hrs weather information.
	 Main class : com.nlabs.test.weather <br />
	 Execute command: `dcos spark run --submit-args='--driver-cores 0.5 --driver-memory 512M --class com.nlabs.test.weather https://github.com/lihengzkj/spark-demo/raw/master/scalapractice_2.11-1.0.jar 30'`<br />
