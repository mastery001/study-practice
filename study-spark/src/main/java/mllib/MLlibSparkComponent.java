package mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;

/**
 * Created by zouziwen on 2017/10/8.
 */
public interface MLlibSparkComponent extends Serializable {

    SparkContext sc = new SparkContext(new SparkConf().setAppName("MLlib Test Application").setMaster("local"));

    JavaSparkContext jsc = new JavaSparkContext(sc);

}
