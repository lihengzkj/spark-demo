## spark-demo

1. scalapractice_2.11-1.0.jar
	
	 Desc: This is the first demo. To calculate PI <br />
	 Main class: com.nlabs.test.ScalaPI <br />
	 Execute command: `dcos spark run --submit-args='--class com.nlabs.test.ScalaPI https://github.com/lihengzkj/spark-demo/raw/master/scalapractice_2.11-1.0.jar 30'`
	 <br /> <br />
	 Desc: This jar will call a weather API to get bejing 3hrs weather information.<br />
	 Main class : com.nlabs.test.weather <br />
	 Execute command: `dcos spark run --submit-args='--driver-cores 0.5 --driver-memory 512M --class com.nlabs.test.weather https://github.com/lihengzkj/spark-demo/raw/master/scalapractice_2.11-1.0.jar 30'`<br />

2. sparkkafkatest2_2.11-1.0.jar
	  Desc: This demo just run on standalone; Will calculate words using kafka and spark <br />
	  Main class: com.nlabs.kafka.KafkaWordProducer;com.nlabs.kafka.KafkaWordCount <br />
	  Note:  Before run below commands, pls confirm that zookeeper and kafka services are running<br />
	  Start the two server:<br />
	        `./bin/zookeeper-server-start.sh config/zookeeper.properties` <br />
	        `bin/kafka-server-start.sh config/server.properties` <br />
	  Execute command: <br />
			a. run producer: `/usr/local/spark/bin/spark-submit --driver-class-path /usr/local/spark/jars/*:/usr/local/spark/jars/kafka/* --class "com.nlabs.kafka.KafkaWordProducer" /home/hadoop/test-jars/sparkkafkatest2_2.11-1.0.jar localhost:9092 wordsender 100 200` <br />
			b. run consumer: `/usr/local/spark/bin/spark-submit --driver-class-path /usr/local/spark/jars/*:/usr/local/spark/jars/kafka/* --class "com.nlabs.kafka.KafkaWordCount" /home/hadoop/test-jars/sparkkafkatest2_2.11-1.0.jar localhost:2181 1 wordsender 1` <br />