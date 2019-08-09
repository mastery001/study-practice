package metric;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zouziwen on 2018/6/20.
 */
public class CpuComponent {

    private static final String LINUX_COMMAND = "top -b -p ";

    private static final String MAC_COMMAND = "top -pid ";

    private static final String CPU_COMMAND = MAC_COMMAND;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void run() {
        executorService.execute(() -> {
            while(true) {
                try {
                    System.out.println("cpuRate is " + getCpuRate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new CpuComponent().run();
    }

    public double getCpuRate() throws Exception {

//        JMXServiceURL remoteURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi" );
//        JMXConnector connector = JMXConnectorFactory.connect(remoteURL);
//
//        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection() , ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME , OperatingSystemMXBean.class);

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        Method getProcessCpuTimeMethod = operatingSystemMXBean.getClass().getDeclaredMethod("getProcessCpuTime");

        getProcessCpuTimeMethod.setAccessible(true);

        long start = System.currentTimeMillis();

        // 获取cpu时间
        long startCpuTime = (long) getProcessCpuTimeMethod.invoke(operatingSystemMXBean);

        TimeUnit.SECONDS.sleep(1);

        long end = System.currentTimeMillis();

        long endCpuTime = (long) getProcessCpuTimeMethod.invoke(operatingSystemMXBean);

        //end - start 即为当前采集的时间单元，单位ms
        //endCpuTime - startCpuTime 为当前时间单元内cpu使用的时间，单位为ns
        double cpuRate = (endCpuTime - startCpuTime) / 1000000.0 / (end - start) / operatingSystemMXBean.getAvailableProcessors();

        return cpuRate;
    }


    private int getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        try {
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            return -1;
        }
    }

    public void getCpuBySigar() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc[] cpuList = sigar.getCpuPercList();
        for(CpuPerc cpu : cpuList) {
            System.out.println("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
            System.out.println("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
            System.out.println("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
            System.out.println("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
            System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
            System.out.println("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率
        }

    }

    /**
     * linux环境下获取JVM的PID
     */
    public String getJvmPIDOnLinux() throws Exception {
        String command = "pidof java";
        BufferedReader in = null;
        Process pro = null;
        pro = Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
        in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        StringTokenizer ts = new StringTokenizer(in.readLine());
        return ts.nextToken();

    }

    private String getCpuRate(String pid) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader brStat = null;
        StringTokenizer tokenStat = null;
        String user = "";
        String linuxVersion = System.getProperty("os.version");
        try {
            System.out.println("Linux版本: " + linuxVersion);

            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", CPU_COMMAND  + pid});
            try {
                // top命令默认3秒动态更新结果信息，让线程睡眠5秒以便获取最新结果
                Thread.sleep(5000);
                is = process.getInputStream();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isr = new InputStreamReader(is);
            brStat = new BufferedReader(isr);

            if (linuxVersion.equals("2.4")) {
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();

                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();
                tokenStat.nextToken();
                user = tokenStat.nextToken();
                tokenStat.nextToken();
                String system = tokenStat.nextToken();
                tokenStat.nextToken();
                String nice = tokenStat.nextToken();

                System.out.println(user + " , " + system + " , " + nice);

                user = user.substring(0, user.indexOf("%"));
                system = system.substring(0, system.indexOf("%"));
                nice = nice.substring(0, nice.indexOf("%"));

                float userUsage = new Float(user).floatValue();
                float systemUsage = new Float(system).floatValue();
                float niceUsage = new Float(nice).floatValue();
                return String.valueOf((userUsage + systemUsage + niceUsage) / 100);
            } else {
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();
                tokenStat.nextToken();
                String userUsage = tokenStat.nextToken(); // 用户空间占用CPU百分比
                user = userUsage;
//                user = userUsage.substring(0, userUsage.indexOf("%"));
                process.destroy();
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            freeResource(is, isr, brStat);
            return "100";
        } finally {
            freeResource(is, isr, brStat);
        }
        return user; // jvm cpu占用率
    }

    private void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
        try {
            if (is != null)
                is.close();
            if (isr != null)
                isr.close();
            if (br != null)
                br.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
