package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan7_2 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setMaster("local")
        conf.setAppName("MySpark")
        val sc = new SparkContext(conf)
        //创建RDD
        val words_data = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\实例数据\\第3章\\words.txt")
        //分割单词
        val words = words_data.flatMap(x => x.split("([ ,?.])"))
        //println(words.collect().mkString("Array(", ", ", ")"))
        //给每个单词加上数量1
        val word = words.map(x=>(x,1))
        //println(word.collect().mkString("Array(", ", ", ")"))
        //过滤为空的情况
        val filter = word.filter(x => x._1.nonEmpty)
        println(filter.collect().mkString("Array(", ", ", ")"))
        //单词为key，计数
        val word_count = filter.reduceByKey((x,y)=>x+y)
        println(word_count.collect().mkString("Array(", ", ", ")"))
        
        val word_count_filter = word_count.filter(x=>x._2>3)
        println(word_count_filter.collect().mkString("Array(", ", ", ")"))
    }
}
