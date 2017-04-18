# spark-demo

1. scalapractice_2.11-1.0.jar
	
	This is the first demo. To calculate PI.
	Main class: com.nlabs.test.ScalaPI
	Execute command: dcos spark run --submit-args='-Dspark.mesos.coarse=true --driver-cores 1 --driver-memory 400M --class com.nlabs.test.ScalaPI https://github.com/lihengzkj/spark-demo/raw/master/scalapractice_2.11-1.0.jar 30'
