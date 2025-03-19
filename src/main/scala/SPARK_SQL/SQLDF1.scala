package SPARK_SQL

import org.apache.spark.sql.SparkSession

object SQLDF1 {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder()
      .appName("sql 1st data frame")
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._
    val data= Seq(
      (12, "Alice", "20-09-2004", 20),
      (13, "Bob", "20-08-1998", 26),
      (14, "C", "20-07-2003", 23)
    ).toDF("id", "name", "dob", "age")
    //data.show()
   // data.printSchema()
    //data.select($"age">22).show()
    //data.groupBy("name").count().show()

    data.createOrReplaceTempView("people")
    spark.sql("Select * from people")
    //spark.sql("Select name, age+5 from people").show()
    //spark.sql("Select name from people where age > 22").show()
    //spark.sql("Select name, count(1) from people group by name").show()
    data.createOrReplaceGlobalTempView("people")
    spark.sessionState
    spark.stop()
  }
}
