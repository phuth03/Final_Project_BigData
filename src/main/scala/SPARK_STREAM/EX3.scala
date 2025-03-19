package SPARK_STREAM

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object EX3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("File Streaming")
      .master("local[*]")
      .config("spark.sql.streaming.schemaInference", "true")
      .getOrCreate()

    val inputDF = spark.readStream
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/data/Streaming")

    val processedDF = inputDF
      .withColumn("Total_Cost", col("Item_price") * col("Item_qty"))
      .withColumn("FName", regexp_extract(input_file_name(), "([^/]+)$", 1))
      .withColumn("timestamp", current_timestamp())
      .select("FName", "date", "timestamp", "Total_Cost")
      .dropDuplicates("FName", "date", "Total_Cost")

    val query = processedDF.writeStream
      .outputMode("append")
      .format("console")
      .option("truncate", "false")
      .option("checkpointLocation", "data/checkpoints")
      .start()
      .awaitTermination()
  }
}