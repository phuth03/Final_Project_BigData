package MidTerm

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructType}
import org.apache.spark.sql.functions._
object MidTerm {
  case class SuperheroGraph(id: Int, connections: Array[Int])
  case class SuperheroName(id: Int, name: String)
  case class SuperheroPopularity(id: Int, name: String, coAppearances: Long)

    def main(args: Array[String]): Unit = {
      val spark = SparkSession.builder()
        .appName("MostPopularSuperhero")
        .master("local[*]")
        .getOrCreate()

      import spark.implicits._

      val namesDS: Dataset[SuperheroName] = spark.read
        .textFile("data/data/Marvel-names.txt")
        .map(line => {
          val fields = line.split("\"")
          val id = fields(0).trim.toInt
          val name = if (fields.length > 1) fields(1) else ""
          SuperheroName(id, name)
        })

      val graphDS: Dataset[SuperheroGraph] = spark.read
        .textFile("data/data/Marvel-graph.txt")
        .map(line => {
          val fields = line.split("\\s+")
          val id = fields(0).toInt
          val connections = fields.tail.map(_.toInt)
          SuperheroGraph(id, connections)
        })

      val coAppearancesDS = graphDS.map(hero => (hero.id, hero.connections.length.toLong))
        .toDF("id", "coAppearances")
        .as[(Int, Long)]

      val superheroesByPopularityDS = coAppearancesDS
        .join(namesDS, "id")
        .as[(Int, Long, String)]
        .map { case (id, coAppearances, name) =>
          SuperheroPopularity(id, name, coAppearances)
        }

      val mostPopular = superheroesByPopularityDS
        .orderBy(desc("coAppearances"))
        .first()

      println(s"'${mostPopular.name}' is the most popular superhero with ${mostPopular.coAppearances} co-appearances")

      spark.stop()
    }
  }


/*case class Weather(station_id: String, date: Int, measure_type: String, temperature: Double)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Min Temperature by Weather Station")
      .master("local[1]")
      .getOrCreate()

    import spark.implicits._
    val weatherSchema = new StructType()
      .add("station_id", StringType, nullable = true)
      .add("date", IntegerType, nullable = true)
      .add("measure_type", StringType, nullable = true)
      .add("temperature", DoubleType, nullable = true)

    val weatherDF = spark.read
      .option("header", "false")
      .schema(weatherSchema)
      .csv("data/data/1800.csv")
      .as[Weather]

    weatherDF.createOrReplaceTempView("Weather")
    val minTempDF = spark.sql(
      "SELECT station_id, MIN(temperature) AS min_temperature " +
        "FROM Weather WHERE measure_type = 'TMIN' GROUP BY station_id ORDER BY min_temperature ASC"
    )
    minTempDF.show()
    spark.stop()
  }
}*/
  /*case class Customer(customer_id: Int, product_id: Int, amount_spent: Double)
   def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Total Amount Spent per Customer")
      .master("local[1]")
      .getOrCreate()

    import spark.implicits._

    val orderSchema = new StructType()
      .add("customer_id", IntegerType, nullable = true)
      .add("product_id", IntegerType, nullable = true)
      .add("amount_spent", DoubleType, nullable = true)

    val ordersDF = spark.read
      .option("header", "false")
      .schema(orderSchema)
      .csv("data/data/customer-orders.csv")
      .as[Customer]
    ordersDF.createOrReplaceTempView("Customers")
    val totalSpentDF = spark.sql("SELECT customer_id, SUM(amount_spent) AS total_spent FROM Customers GROUP BY customer_id ORDER BY total_spent DESC")
    totalSpentDF.show()
    spark.stop()
  }
}*/

