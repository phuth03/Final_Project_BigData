package Ex
import scala.io.StdIn

object Lab1 {
    def main(args: Array[String]): Unit = {
      println("Enter first number: ")
      val num1 = StdIn.readDouble()

      println("Enter second number: ")
      val num2 = StdIn.readDouble()

      println("Choose operation (+, -, *, /): ")
      val operation = StdIn.readChar()

      val result = operation match {
        case '+' => num1 + num2
        case '-' => num1 - num2
        case '*' => num1 * num2
        case '/' => if (num2 != 0) num1 / num2 else "Cannot divide by zero"
        case _ => "Invalid operation"
      }
      println(s"Result: $num1 $operation $num2 = $result")
    }
}
