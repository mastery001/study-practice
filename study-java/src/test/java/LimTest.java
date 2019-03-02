import com.google.common.util.concurrent.RateLimiter;
import metric.CpuComponent;
import org.junit.Test;

/**
 * Created by zouziwen on 2018/6/12.
 */
public class LimTest {

    @Test
    public void testTime() throws InterruptedException {
        System.out.println(System.currentTimeMillis());
        Thread.sleep(1);
        System.out.println(System.currentTimeMillis());
        Thread.sleep(1);
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() / 1000);
        Thread.sleep(1);
        System.out.println(System.currentTimeMillis() / 1000);
        Thread.sleep(1);
        System.out.println(System.currentTimeMillis() / 1000);
    }

    @Test
    public void testLimmiter() {
        RateLimiter rl = RateLimiter.create(5);
        System.out.println(rl.acquire());
        System.out.println(rl.acquire());
        System.out.println(rl.acquire());
        System.out.println(rl.acquire());
        System.out.println(rl.acquire());
        System.out.println(rl.acquire());


        rl = RateLimiter.create(5);
        System.out.println(rl.acquire(1));
        System.out.println(rl.acquire(1));
        System.out.println(rl.acquire(1));
        System.out.println(rl.acquire(1));
        System.out.println(rl.acquire(1));
        System.out.println(rl.acquire(1));
    }

    /**
     * @link https://my.oschina.net/clming/blog/883072 -- java获取JVM的CPU占用率、内存占用率、线程数及服务器的网口吞吐率、磁盘读写速率
     * @link https://blog.csdn.net/hemingwang0902/article/details/4054549 -- java获得CPU使用率,系统内存,虚拟机内存等情况(不用JNI)
     * @link https://www.jianshu.com/p/996758ef22f7  -- linux性能指标分析
     * @throws Exception
     */
    @Test
    public void getCpu() throws Exception {
//        System.out.println(getCpuRate("71238"));
//        getCpuBySigar();

//        System.out.println(getPid());

//        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
//        System.out.println("load is " + operatingSystemMXBean.getSystemLoadAverage());
//
//        Method getProcessCpuTimeMethod = operatingSystemMXBean.getClass().getDeclaredMethod("getProcessCpuTime");
//
//        getProcessCpuTimeMethod.setAccessible(true);
//
//        // 获取cpu时间
//        long cpuTime = (long) getProcessCpuTimeMethod.invoke(operatingSystemMXBean);
//
//        System.out.println(cpuTime);

        CpuComponent cpuComponent = new CpuComponent();

        System.out.println(cpuComponent.getCpuRate());


    }


}
