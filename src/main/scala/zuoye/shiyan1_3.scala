package zuoye

import scala.io.StdIn

object shiyan1_3 {
    def YangHuiTriangle(row: Int): Unit = {
        var arr = new Array[Int](row + 2)
        arr(1) = 1
        for (i <- 1 to row)
            arr = OneRow(arr, i)
    }
    
    def OneRow(arr: Array[Int], length: Int): Array[Int] = {
        var previous = 0
        for (j <- 1 to length) {
            val current = arr(j)
            arr(j) = previous + current
            previous = current
            print(s"${arr(j)} ")
        }
        println()
        arr
    }
    
    def main(args: Array[String]): Unit = {
        println("请输入行数：")
        val row = StdIn.readInt()
        YangHuiTriangle(row)
    }
}
