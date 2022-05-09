package zuoye

import org.apache.spark.graphx.{Graph, TripletFields}
import org.apache.spark.{SparkConf, SparkContext}

object shiyan10 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("GraphX").setMaster("local[*]")
        val sc = new SparkContext(conf)
        
        // 第1种方法创建图
        //        val web_Google = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\web-Google.txt").map { case line =>
        //            val lines = line.split("\t")
        //            Edge(lines(0).toLong, lines(1).toLong, 1L)
        //        }
        //
        //        val graph = Graph.fromEdges(web_Google, 1L)
        //
        // 第2种方法创建图
        val web_Google = sc.textFile("F:\\\\IDEA_scala_workspace\\\\Maven\\\\Data\\\\web-Google.txt")
            .map(x => x.split("\t"))
            .map(x => (x(0).toLong, x(1).toLong))
        val graph = Graph.fromEdgeTuples(web_Google, 1)
        //graph.vertices.take(10).foreach(println(_))
        // 三种视图
        //        graph.vertices.take(10).foreach(println(_))
        //        graph.edges.take(10).foreach(println(_))
        //        graph.triplets.take(10).foreach(println(_))
        
        // 顶点数和边数
//        println(graph.numVertices)
//        println(graph.numEdges)
        
        // 统计链接数
        val aggregateMessage = graph.aggregateMessages[Int](
            triplet => {
                triplet.sendToDst(1)
            },
            (x, y) => x + y,
            TripletFields.All
        )
        // aggregateMessage.take(10).foreach(println(_))

        //
        val outerJoinGraph = graph.outerJoinVertices(aggregateMessage) {
            case (id, attr, Some(count)) => count
            case (id, attr, None) => 0
        }
        outerJoinGraph.vertices.take(10).foreach(println(_))

        val subGraph = outerJoinGraph.subgraph(vpred = (id, count) => count > 1)
        subGraph.vertices.take(10).foreach(println(_))
    }
}
