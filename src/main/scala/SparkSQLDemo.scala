import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.SparkConf

object SparkSQLDemo {
    def main(args: Array[String]): Unit = {
        //创建spark配置信息
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQLDemo")
        
        //创建SparkSQL的环境对象，即创建SparkSession
        val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
        
        //读取json文件，构建DataFrame对象
        val frame: DataFrame = spark.read.csv("F:\\学习\\Spark\\书籍资源\\Spark大数据技术与应用-正文数据和代码\\第5章\\data\\law_utf8.csv")
        
        //展示数据
        frame.show()
        
        //释放资源
        spark.close()
    }
}