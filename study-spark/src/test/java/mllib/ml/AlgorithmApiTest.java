package mllib.ml;

import mllib.MLlibSparkComponent;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.junit.Test;
import scala.Tuple2;

import java.io.File;
import java.io.IOException;

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
}
