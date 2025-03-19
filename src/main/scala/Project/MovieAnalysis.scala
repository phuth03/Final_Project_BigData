package Project
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import scala.io.StdIn

object MovieAnalysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Movie Analysis")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    // Load movies data
    val moviesDF = spark.read.option("delimiter", "::").csv("data/Project/movies.dat")
      .toDF("MovieID", "Title", "Genres")

    // Load ratings data
    val ratingsDF = spark.read.option("delimiter", "::").csv("data/Project/ratings.dat")
      .toDF("UserID", "MovieID", "Rating", "Timestamp")

    var exit = false
    while (!exit) {
      println("\nMovie Analysis Menu:")
      println("1. Top 10 most viewed movies")
      println("2. Latest released movies")
      println("3. Number of movies starting with each letter/number")
      println("4. Number of movies per genre")
      println("5. Distinct list of genres")
      println("6. Exit")
      print("Enter your choice: ")

      val choice = StdIn.readInt()

      choice match {
        case 1 =>
          val topMovies = ratingsDF.groupBy("MovieID")
            .agg(count("MovieID").alias("ViewCount"))
            .orderBy(desc("ViewCount"))
            .limit(10)
            .join(moviesDF, "MovieID")
            .select("Title", "ViewCount")
          println("Top 10 most viewed movies:")
          topMovies.show(false)

        case 2 =>
          val latestMovies = moviesDF.withColumn("Year", regexp_extract(col("Title"), "\\((\\d{4})\\)", 1))
            .filter(col("Year").isNotNull && col("Year") =!= "")
            .orderBy(desc("Year"))
            .select("Title", "Year")
            .limit(10)
          println("Latest released movies:")
          latestMovies.show(false)

        case 3 =>
          val movieStartCounts = moviesDF.withColumn("FirstChar", substring(col("Title"), 1, 1))
            .groupBy("FirstChar").count()
            .orderBy("FirstChar")
          println("Number of movies starting with each letter/number:")
          movieStartCounts.show(false)

        case 4 =>
          val genreCounts = moviesDF.withColumn("Genre", explode(split(col("Genres"), "\\|")))
            .groupBy("Genre").count()
            .orderBy(desc("count"))
          println("Number of movies per genre:")
          genreCounts.show(false)

        case 5 =>
          val distinctGenres = moviesDF.select(explode(split(col("Genres"), "\\|"))).distinct()
            .toDF("Genre")
            .orderBy("Genre")
          println("Distinct list of genres:")
          distinctGenres.show(false)

        case 6 =>
          println("Exiting program...")
          exit = true

        case _ =>
          println("Invalid choice. Please try again.")
      }
    }
    spark.stop()
  }
}