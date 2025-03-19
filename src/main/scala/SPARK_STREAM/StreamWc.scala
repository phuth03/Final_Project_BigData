package SPARK_STREAM

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object StreamWc extends Serializable {
  def main(args: Array[String]): Unit = {
      val spark=SparkSession.builder()
        .master(master = "local[*]")
        .appName(name = "Streaming Word Count")
        .config("spark.streaming.stopGracefullyOnShutDown","true")
        .config("spark.sql.shuffle.partition",4)
        .getOrCreate()
    val lineDF=spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port","9999")
      .load()

    val wordsDF=lineDF.select(expr("explode(split(value, ' ')) as word"))
      val countDF=wordsDF.groupBy("word").count()
    val wordCountQuery=countDF.writeStream
      .format("console")
      .option("checkpointLocation", "check-point-dir")
      .outputMode("complete")
      .start()

    wordCountQuery.awaitTermination()
    spark.stop()
  }

}
