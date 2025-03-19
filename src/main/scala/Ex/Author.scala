package Ex

object Author {
  def main(args: Array[String]): Unit = {
    val obj1= new author1("Nam","VietNam")
    println(obj1)
    val obj2= new book("Abc",obj1,"2000")
    println(obj2)
    val obj3= new author1("Pankaj","India")
    println(obj3)
    val obj4= new book("Big Data",obj3,"2000")
    println(obj4)
  }
}
class author1(name:String,nationality:String) {
  override def toString: String = s"$name is from $nationality"
}

class book(title:String,author: author1,year:String) {
  override def toString: String = s"$title by $author is published in $year"
}