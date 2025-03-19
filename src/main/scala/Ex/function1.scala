package Ex

object function1 {
  def main(args: Array[String]): Unit = {
    def squareIT(a:Int):Int= {
      a * a
    }
    def cubeIT(b:Double): Double = {
      b * b * b
    }
    def callit(x:Int,f:Int=>Int):Int= {
      f(x)
    }
    def callcubeit(y:Double,f:Double=>Double):Double= {
      f(y)
    }
    val z= callit(5,squareIT)
    val q= callcubeit(2.6,cubeIT)
    println(s"Square is $z")
    println(s"Square is $q")
  }

}

