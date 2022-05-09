import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object chapter6 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
        val sc = new SparkContext(conf)
        sc.setLogLevel("WARN")
        val ssc = new StreamingContext(sc, Seconds(5))
        
        // 黑名单RDD
        // 例：(hadoop, true)
        // val md = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\mingdan.txt").map { x => val y = x.split(" "); (y(0), y(1).toBoolean) }
        
        // 读取监听端口8888输入的内容
        val st = ssc.socketTextStream("192.168.17.129", 8888)
    
        val results = st.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)
        results.print()
//        // 将内容处理
//        // 例如：(spark, 1111 spark)
//        val users = st.map(x => (x.split(" ")(1), x))
//
//        // 对源DStream的RDD应用transform, 返回一个新的DStream
//        val validRddDS = users.transform(x => {
//            val joinRdd = x.leftOuterJoin(md) // 类似SQL表连接
//            // 例如：(hadoop, 1111 hadoop) join (hadoop, true) -> (hadoop, (1111 hadoop, true)
//
//            val filterRdd = joinRdd.filter(y => { // 过滤值为true的内容
//                if (y._2._2.getOrElse(false)) {
//                    false
//                }
//                else {
//                    true
//                }
//            })
//
//            val validRdd = filterRdd.map(y => y._2._1) // 返回原内容
//            validRdd
//        })
//        // 输出结果
//        validRddDS.print()
        
        // 启动流式计算
        ssc.start()
        ssc.awaitTermination()
    }
}
