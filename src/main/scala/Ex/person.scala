package Ex

object person {
  def main(args: Array[String]): Unit = {
    val person1= new person("Pankaj",20)
    println(person1.greet())
  }
}
  class person(name:String,age:Int) {
    def greet(): String = s"Hello $name, you are $age years old"
  }



