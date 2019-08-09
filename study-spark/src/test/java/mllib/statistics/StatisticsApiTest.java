package mllib.statistics;

import com.google.common.collect.ImmutableMap;
import mllib.MLlibSparkComponent;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Matrices;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.mllib.stat.test.ChiSqTestResult;
import org.junit.Test;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Spark MLlib统计相关API测试
 * Created by zouziwen on 2017/10/7.
 */
public class StatisticsApiTest implements MLlibSparkComponent {

    /**
     * colStats方法测试，数据集中列的统计结果
     */
    @Test
    public void testColStats() {
        JavaRDD<Vector> mat = jsc.parallelize(Arrays.asList(
                // 构建稠密向量
                Vectors.dense(1.0, 10.0, 100.0),
                Vectors.dense(2.0, 20.0, 200.0),
                Vectors.dense(3.0, 30.0, 300.0)
        ));

        MultivariateStatisticalSummary summary = Statistics.colStats(mat.rdd());

        System.out.println(summary.mean());

        System.out.println(summary.variance());

        System.out.println(summary.numNonzeros());
    }

    /**
     * 相似度计算
     */
    @Test
    public void testCorr() {
        JavaDoubleRDD seriesX = jsc.parallelizeDoubles(Arrays.asList(1.0, 2.0, 3.0, 33.0, 5.0));
        JavaDoubleRDD seriesY = jsc.parallelizeDoubles(Arrays.asList(11.0, 22.0, 33.0, 33.0, 555.0));

        /**
         * 参数3为相似度计算函数的选择
         * 可选为：
         *      1. pearson -- 皮尔逊相关系数
         *      2. Spearman -- 斯皮尔曼等级相关系数
         */
        Double correlation = Statistics.corr(seriesX.srdd(), seriesY.srdd(), "pearson");

        System.out.println(" correlation is " + correlation);

        JavaRDD<Vector> mat = jsc.parallelize(Arrays.asList(
                // 构建稠密向量
                Vectors.dense(1.0, 10.0, 100.0),
                Vectors.dense(2.0, 20.0, 200.0),
                Vectors.dense(3.0, 30.0, 300.0)
        ));

        /**
         * 相关系数矩阵：由矩阵各列间的相关系数构成。换句话说，相关矩阵的第i行第j列的元素是原矩阵第i列和第j列的相关系数
         */
        Matrix correlMatrix = Statistics.corr(mat.rdd(), "pearson");

        /**
         * print:
         *      1.0  1.0  1.0
         *      1.0  1.0  1.0
         *      1.0  1.0  1.0
         *
         */


        System.out.println(correlMatrix.toString());
    }

    /**
     * 测试分层抽样：
     * ​分层取样（Stratified sampling）顾名思义，就是将数据根据不同的特征分成不同的组，然后按特定条件从不同的组中获取样本，并重新组成新的数组。
     * 分层取样算法是直接集成到键值对类型 RDD[(K, V)] 的 sampleByKey 和 sampleByKeyExact 方法，无需通过额外的 spark.mllib 库来支持。
     */
    @Test
    public void testStratifiedSampling() {
        List<Tuple2<Integer, Character>> list = Arrays.asList(
                new Tuple2<>(1, 'a'),
                new Tuple2<>(1, 'b'),
                new Tuple2<>(2, 'c'),
                new Tuple2<>(2, 'd'),
                new Tuple2<>(2, 'e'),
                new Tuple2<>(3, 'f')
        );

        JavaPairRDD<Integer, Character> data = jsc.parallelizePairs(list);

        ImmutableMap<Integer , Double> fractions = ImmutableMap.of(1 , 0.1 , 2 , 0.6 , 3 , 0.3);

        // sampleByKey 每次都通过给定的概率以一种类似于掷硬币的方式来决定这个观察值是否被放入样本，因此一遍就可以过滤完所有数据，最后得到一个近似大小的样本，但往往不够准确
        JavaPairRDD<Integer, Character> approxSample = data.sampleByKey(false, fractions);

        // sampleByKeyExtra 会对全量数据做采样计算。对于每个类别，其都会产生 （fk⋅nk）个样本，其中fk是键为k的样本类别采样的比例；nk是键k所拥有的样本数。
        // sampleByKeyExtra 采样的结果会更准确，有99.99%的置信度，但耗费的计算资源也更多
        JavaPairRDD<Integer, Character> exactSample = data.sampleByKeyExact(false, fractions);

        System.out.println(approxSample.collectAsMap());
        System.out.println(exactSample.collectAsMap());

    }

    /**
     * 假设检验 -- 目前Spark支持卡方(chi-squared(X2))检验
     */
    @Test
    public void testHypothesisTesting() {
        Vector vec = Vectors.dense(0.1, 0.15, 0.2, 0.3, 0.25);

        // 如果只有一个参数作为输入，则只能测试均匀分布的矩阵或向量
        ChiSqTestResult goodnessOfFitTestResult = Statistics.chiSqTest(vec);

        /**
         * 结果输出为：
         *      1. 相似度函数：默认为pearson
         *      2. 自由度
         *      3. 检验计算值 X2
         *      4. 概率
         *      5. 假设结果
         */
        // No presumption against null hypothesis: observed follows the same distribution as expected..
        System.out.println(goodnessOfFitTestResult + "\n");

        Matrix mat = Matrices.dense(3, 2, new double[]{1.0, 3.0, 5.0, 2.0, 4.0, 6.0});

        ChiSqTestResult independenceTestResult = Statistics.chiSqTest(mat);
        // No presumption against null hypothesis: the occurrence of the outcomes is statistically independent..
        System.out.println(independenceTestResult + "\n");

        JavaRDD<LabeledPoint> obs = jsc.parallelize(
                Arrays.asList(
                        new LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0)),
                        new LabeledPoint(1.0, Vectors.dense(1.0, 2.0, 0.0)),
                        new LabeledPoint(-1.0, Vectors.dense(-1.0, 0.0, -0.5))
                )
        );

        // The contingency table is constructed from the raw (feature, label) pairs and used to conduct
        // the independence test. Returns an array containing the ChiSquaredTestResult for every feature
        // against the label.
        ChiSqTestResult[] featureTestResults = Statistics.chiSqTest(obs.rdd());
        int i = 1;
        for (ChiSqTestResult result : featureTestResults) {
            System.out.println("Column " + i + ":");
            System.out.println(result + "\n");  // summary of the test
            i++;
        }
    }

}
