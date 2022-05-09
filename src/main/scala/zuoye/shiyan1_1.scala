package zuoye

import scala.io.StdIn

object shiyan1_1 {
    
    def square(num1: Int, num2: Int): Int = {
        if (num1 == num2)
            num1 * num1
        else
            num1 * num1 + square(num1 + 1, num2)
    }
    
    def cube(num1: Int, num2: Int): Int = {
        if (num1 == num2)
            num1 * num1 * num1
        else
            num1 * num1 * num1 + cube(num1 + 1, num2)
    }
    
    def sum(num1: Int, num2: Int): Int = {
        if (num1 == num2)
            num1
        else
            num1 + sum(num1 + 1, num2)
    }
    
    def main(args: Array[String]): Unit = {
        println("请输入区间：")
        val num1 = StdIn.readInt()
        val num2 = StdIn.readInt()
        println("区间和为：" + sum(num1, num2))
        println("区间平方和为：" + square(num1, num2))
        println("区间立方和为：" + cube(num1, num2))
    }
}
