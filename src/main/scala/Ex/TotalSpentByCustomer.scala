package Ex
import org.apache.spark._
import org.apache.log4j._

object TotalSpentByCustomer {
  def extractCustomerPricePairs(line: String): (Int, Float) = {
    val fields = line.split(",")
    (fields(0).toInt, fields(2).toFloat)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc =new SparkContext("local[*]", "TotalSpentByCustomer")
    val input = sc.textFile("data/data/.txt")
    val words = input.flatMap(x => x.split("\\W+"))
    val lowercaseWords = words.map(x=> x.toLowerCase())
    val wordCounts = lowercaseWords.countByValue()
    wordCounts.foreach(println)
  }
  }
