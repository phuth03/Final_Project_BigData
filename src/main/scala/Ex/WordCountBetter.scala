package Ex

import org.apache.log4j._
import org.apache.spark._

object WordCountBetter {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc =new SparkContext("local[*]", "WordCountBetter")
    val input = sc.textFile("data/data/book.txt")
    val words = input.flatMap(x => x.split("\\W+"))
    val lowercaseWords = words.map(x=> x.toLowerCase())
    val wordCounts = lowercaseWords.countByValue()
    wordCounts.foreach(println)

  }
}
