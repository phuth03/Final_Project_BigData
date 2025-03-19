package Ex

import org.apache.log4j._
import org.apache.spark._

object RatingsCounter {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc =new SparkContext("local[*]", "RatingsCounter")
    val lines = sc.textFile("data/data/ml-100k/u.data")
        val ratings = lines.map(x => x.split("\t")(2))
    val results = ratings.countByValue()
      val sortedResults = results.toSeq.sortBy(_._1)
      sortedResults.foreach(println)
  }
}
