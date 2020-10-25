package org.example

object StructuredStreamingApp {


  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("StreamingApp")
      .getOrCreate()


    def readFromKafka()={

      val kafkaDF = spark.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", "localhost:9092")
        .option("subscribe","test")
        .load()


      val udfFunction: String => String = "Hi..."+ _.toUpperCase()
      spark.udf.register("valueTransform", udfFunction)

      val kafkaDF_ = kafkaDF.selectExpr("valueTransform(value) as value")


      kafkaDF_
        .writeStream
        .format(("kafka"))
        .option("kafka.bootstrap.servers", "localhost:9092")
        .option("topic","test2")
        .option("checkpointLocation", "checkpoints")
        .start()


      val kafkaDF2 = spark.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", "localhost:9092")
        .option("subscribe","test2")
        .load()

      kafkaDF2
        .select(col("topic"), expr("cast(value as string) as actualValue"))
        .writeStream
        .format(("console"))
        .outputMode("append")
        .start()
        .awaitTermination()

    }

    readFromKafka()

  }

}
