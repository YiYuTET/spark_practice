package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan8_2__4 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setMaster("local")
        conf.setAppName("ShiYan8")
        val sc = new SparkContext(conf)
        val movie_data = sc.textFile("F:\\Office文档\\Word文档\\Spark_scala\\Dataset\\ml-1m\\movies.dat")
        val ratings_data = sc.textFile("F:\\Office文档\\Word文档\\Spark_scala\\Dataset\\ml-1m\\ratings.dat")
        val users_data = sc.textFile("F:\\Office文档\\Word文档\\Spark_scala\\Dataset\\ml-1m\\users.dat")
        val movie = movie_data.map(x => x.split("::")).map(x => (x(0), x(1), x(2)))
        val ratings = ratings_data.map(x => x.split("::")).map(x => (x(0), x(1), x(2), x(3)))
        val users = users_data.map(x => x.split("::")).map(x => (x(0), x(1), x(2), x(3), x(4)))
        
        val usersID_movieID = ratings.map(x => (x._1, x._2))
        val usersID_sex_age = users.map(x => (x._1, (x._2, x._3)))
        val movieID_name = movie.map(x=>(x._1,x._2))
        // 用户ID， 电影ID，性别，年龄
        val usersID_movieID_sex_age = usersID_movieID.join(usersID_sex_age)
        //usersID_movieID_sex_age.foreach(println(_))
        
        val usersID_movieID_man18 = usersID_movieID_sex_age.filter(x => x._2._2._1 == "M").filter(x => x._2._2._2 == "18")
        //usersID_movieID_man18.foreach(println(_))
        val movieID_Count = usersID_movieID_man18.map(x=>(x._2._1, 1)).reduceByKey((x1,x2)=>x1+x2).sortBy(x=>x._2,false)
        val movieID_top10 =sc.parallelize(movieID_Count.take(10))
        val result = movieID_top10.join(movieID_name).sortBy(x=>x._2._1).map(x=>x._2._2)
        result.foreach(println(_))
    }
}
