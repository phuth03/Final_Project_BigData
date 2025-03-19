package SPARK_SQL

import org.apache.spark.sql.SparkSession

object FakeFriends {
  case class people (id: Int,name: String,age:Int, friends: String )

  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder()
      .appName("FakeFriends")
      .master("local[1]")
      .getOrCreate()
    import spark.implicits._
    val data=spark.read
      .option("header",value=true)
      .option("delimiter",",")
      .option("inferSchema","true")
      .csv(path = "data\\data\\fakefriends.csv")
      .as[people]
    data.createOrReplaceTempView("Emp")
    val teen=spark.sql("select * from Emp where age >=13 And age <=19").collect()
    teen.foreach(println)
    spark.stop()


  }
}
