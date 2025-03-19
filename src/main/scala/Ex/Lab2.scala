package Ex

import scala.io.Source
import scala.collection.mutable
object Lab2 {
  def main(args: Array[String]): Unit = {
    val filePath = "C:/data/Lab2_BigData.txt"
    try {
      val source = Source.fromFile(filePath)
      val wordCounts = mutable.Map[String, Int]().withDefaultValue(0)

      for (line <- source.getLines()) {
        val words = line.toLowerCase.replaceAll("[^a-zA-Z0-9 ]", "").split(" ").filter(_.nonEmpty)
        words.foreach(word => wordCounts(word) += 1)
      }
      source.close()

      val sortedWordCounts = wordCounts.toSeq.sortBy(-_._2)
      println("Top "  + " words by frequency:")
      sortedWordCounts.foreach { case (word, count) => println(s"$word: $count") }
    } catch {
      case e: Exception => println("Error reading file: " + e.getMessage)
    }
  }
}