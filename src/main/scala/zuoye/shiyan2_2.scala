package zuoye

import scala.io.{Source, StdIn}

object shiyan2_2 {
    def main(args: Array[String]): Unit = {
        var yidong = 0
        var dianxin = 0
        var liantong = 0
        print("请输入省份：")
        val province: String = StdIn.readLine()
        val file = Source.fromFile("F:\\Office文档\\Word文档\\Spark_scala\\2016phonelocation.txt")
        for (line <- file.getLines()) {
            if (line.contains(province) && line.contains("中国移动")) {
                yidong += 1
            }
            if (line.contains(province) && line.contains("中国电信")) {
                dianxin += 1
            }
            if (line.contains(province) && line.contains("中国联通")) {
                liantong += 1
            }
        }
        println(s"${province}有${yidong + dianxin + liantong}个号码")
        println(s"移动：${yidong},电信：${dianxin},联通：${liantong}")
    }
}
