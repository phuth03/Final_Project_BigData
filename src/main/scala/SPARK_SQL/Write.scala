package SPARK_SQL
import  org.apache.spark.sql.SparkSession

object Write {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("WriteCSV1")
      .master("local[1]")
      .getOrCreate()
    import spark.implicits._
    val data = Seq(
      (21,"A","10-08-2001",23),
      (22,"D","20-09-1996",28),
      (23,"B","20-12-2000",24),
      (24,"C","19-02-1999",25)
    ).toDF("id", "name", "dob", "age")
    data.write
      .option("header", value=true)
      .mode(saveMode="overwrite")
      .csv(path="data/data/write1.csv")

    val dataf2=spark.read
      .option("header", value=true)
      .csv(path="data/data/write1.csv")

    dataf2.show()
    spark.stop()
  }
}
