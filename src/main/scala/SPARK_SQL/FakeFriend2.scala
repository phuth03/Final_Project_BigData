package SPARK_SQL

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType,IntegerType,StringType,StructType}
import org.apache.spark.sql.types.StructType
object FakeFriend2 {
  case class people (id: Int,name: String,age:Int, friends: String )

  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder()
      .appName("FakeFriend2")
      .master("local[1]")
      .getOrCreate()
      //create own structure
    val struct=new StructType()
      .add("ID",IntegerType,nullable=true)
      .add("Name",StringType,nullable=true)
      .add("Age",IntegerType,nullable=true)
      .add("Friends",StringType,nullable=true)
    import spark.implicits._

    val data =spark.read
      .schema(struct)
      .csv("data/data/fakefriends-noheader.csv")
      .as[people]
    data.createOrReplaceTempView("Emp")
    data.printSchema()
    data.show()
    spark.stop()

  }
}
