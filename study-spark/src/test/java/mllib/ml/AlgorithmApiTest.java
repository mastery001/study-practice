package mllib.ml;

import mllib.MLlibSparkComponent;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.junit.Test;
import scala.Tuple2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Spark MLlib机器学习算法相关API测试
 * Created by zouziwen on 2017/10/8.
 */
public class AlgorithmApiTest implements MLlibSparkComponent{

    /**
     * 测试linear SVM算法
     */
    @Test
    public void testSVM() throws IOException {
        String path = "data/mllib/sample_libsvm_data.txt";

        String savePath = "data/target/tmp/javaSVMWithSGDModel";

        File saveDir = new File(savePath);

        if(saveDir.exists()) {
            FileUtils.deleteDirectory(saveDir);
        }

        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc , path).toJavaRDD();

        JavaRDD<LabeledPoint> training = data.sample(false , 0.6 , 11L);

        training.cache();

        JavaRDD<LabeledPoint> test = data.subtract(training);

        int numIterations = 100;

        // 训练产出SVM模型
        final SVMModel svmModel = SVMWithSGD.train(training.rdd() , numIterations);

        // 清楚threshold，使得输出原始预测分数
        svmModel.clearThreshold();

        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(p ->
                new Tuple2<>(svmModel.predict(p.features()), p.label()));

        // 获取评估指标
        BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));

        double auROC = metrics.areaUnderROC();

        System.out.println("Area under ROC = " + auROC);

        svmModel.save(sc , savePath);

        SVMModel sameModel = SVMModel.load(sc , savePath);

    }

    @Test
    public void testALS() throws Exception{
        String path = "data/mllib/als/test.data";
        JavaRDD<Rating> ratingsRDD = jsc.textFile(path).map(str -> {
            String[] fields = str.split(",");
            int userId = Integer.parseInt(fields[0]);
            int movieId = Integer.parseInt(fields[1]);
            float rating = Float.parseFloat(fields[2]);
            return new Rating(userId , movieId , rating);
        });

        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratingsRDD) , 10 , 5 );

//        System.out.println(model.userFeatures().count());
//        System.out.println(model.productFeatures().count());

//        System.out.println( model.predict(1 , 3));
        System.out.println(Arrays.toString(model.recommendUsers(3 , 2)));

        List<Tuple2<Object, Rating[]>> userProducts = model.recommendUsersForProducts(2).toJavaRDD().collect();
        for(Tuple2<Object, Rating[]> u : userProducts) {
            System.out.println("u=" + u._1() + ";product:" + Arrays.toString(u._2()));
        }

        // Evaluate the model on rating data
//        JavaRDD<Tuple2<Object, Object>> userProducts =
//                ratingsRDD.map(r -> new Tuple2<>(r.user(), r.product()));
//        JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD.fromJavaRDD(
//                model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
//                        .map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating()))
//        );
//        JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD.fromJavaRDD(
//                ratingsRDD.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())))
//                .join(predictions).values();
//        double MSE = ratesAndPreds.mapToDouble(pair -> {
//            double err = pair._1() - pair._2();
//            return err * err;
//        }).mean();
//        System.out.println("Mean Squared Error = " + MSE);
    }
}
