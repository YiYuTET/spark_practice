package zuoye

import scala.io.StdIn

object shiyan1_2 {
    //gcd(a,b)=gcd(b,a mod b)
    def gcd(a: Int, b: Int): Int = {
        if (b == 0)
            a
        else
            gcd(b, a % b)
    }
    
    def main(args: Array[String]): Unit = {
        println("请输入两个数：")
        val a = StdIn.readInt()
        val b = StdIn.readInt()
        println("最大公约数为：" + gcd(a, b))
    }
}
