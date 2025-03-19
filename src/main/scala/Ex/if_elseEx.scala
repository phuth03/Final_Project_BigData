package Ex

import java.time.LocalDate
object if_elseEx {
  def main(args: Array[String]): Unit = {

    /*  print("Enter a number: ")
      val d=scala.io.StdIn.readInt()
      for(d <-1 to d)
      if(d%2==0)
        println(s" $d is even number")
      else
        println(s" $d is odd number")*/

    //month
    /*print("Enter a month number: ")
    val month=scala.io.StdIn.readInt()
    val monthName= month match {
      case 1 => "January"
      case 2 => "February"
      case 3 => "March"
      case 4 => "April"
      case 5 => "May"
      case 6 => "June"
      case 7 => "July"
      case 8 => "August"
      case 9 => "September"
      case _=> "Invalid month"
    }
    println(monthName)
    println(s"$month is $monthName ")*/


    print("Enter a day number: ")
    val today = LocalDate.now()
    val day = today.getDayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH)
    val typeofday= day match {
      case "Monday" | "Tuesday" | "Wednesday" | "Thursday" | "Friday" => "Weekday"
      case "Saturday" | "Sunday" => "Weekend"
      case _ => "Unknown"
    }

    println(s"Today is $day, which is $typeofday")
  }
}
