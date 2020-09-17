package life;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 贷款计算器
 * @Author zouziwen
 * @CreateTime 2020-09-16 22:21
 */
public class LoanCalculator {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.00");

    /**
     * 一次性提前还款计算（等额本息）
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param payTimes  已还次数
     * @param rate      贷款利率
     * @return
     */
    public static String[] calculateEqualPrincipalAndInterest(double principal, int months, int payTimes, double rate) {
        ArrayList<String> data = new ArrayList<String>();
        double monthRate = rate / (100 * 12);//月利率
        double preLoan = (principal * monthRate * Math.pow((1 + monthRate), months)) / (Math.pow((1 + monthRate), months) - 1);//每月还款金额
        double totalMoney = preLoan * months;//还款总额
        double interest = totalMoney - principal;//还款总利息
        double leftLoan = principal * Math.pow(1 + monthRate, payTimes) - preLoan * (Math.pow(1 + monthRate, payTimes) - 1) / monthRate;//n个月后欠银行的钱
        double payLoan = principal - leftLoan;//已还本金
        double payTotal = preLoan * payTimes;//已还总金额
        double payInterest = payTotal - payLoan;//已还利息
        double totalPayAhead = leftLoan * (1 + monthRate);//剩余一次还清
        double saveInterest = totalMoney - payTotal - totalPayAhead;
        data.add(FORMAT.format(totalMoney));//原还款总额
        data.add(FORMAT.format(principal));//贷款总额
        data.add(FORMAT.format(interest));//原还款总利息
        data.add(FORMAT.format(preLoan));//原还每月还款金额
        data.add("0");//原每月递减利息
        data.add(FORMAT.format(payTotal));//已还总金额
        data.add(FORMAT.format(payLoan));//已还本金
        data.add(FORMAT.format(payInterest));//已还利息
        data.add(FORMAT.format(totalPayAhead));//一次还清支付金额
        data.add(FORMAT.format(saveInterest));//节省利息
        data.add(String.valueOf(0));//剩余还款期限
        return data.toArray(new String[data.size()]);
    }

    /**
     * 一次性提前还款计算(等额本金)
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param payTimes  已还次数
     * @param rate      贷款利率
     * @return
     */
    public static String[] calculateEqualPrincipal(double principal, int months, int payTimes, double rate) {
        ArrayList<String> data = new ArrayList<String>();
        double monthRate = rate / (100 * 12);//月利率
        double prePrincipal = principal / months;//每月还款本金
        double firstMonth = prePrincipal + principal * monthRate;//第一个月还款金额
        double decreaseMonth = prePrincipal * monthRate;//每月利息递减
        double interest = (months + 1) * principal * monthRate / 2;//还款总利息
        double totalMoney = principal + interest;//还款总额
        double payLoan = prePrincipal * payTimes;//已还本金
        double payInterest = (principal * payTimes - prePrincipal * (payTimes - 1) * payTimes / 2) * monthRate;//已还利息
        double payTotal = payLoan + payInterest;//已还总额
        double totalPayAhead = (principal - payLoan) * (1 + monthRate);//提前还款金额（剩余本金加上剩余本金当月利息）
        double saveInterest = totalMoney - payTotal - totalPayAhead;
        data.add(FORMAT.format(totalMoney));//原还款总额
        data.add(FORMAT.format(principal));//贷款总额
        data.add(FORMAT.format(interest));//原还款总利息
        data.add(FORMAT.format(firstMonth));//原首月还款金额
        data.add(FORMAT.format(decreaseMonth));//原每月递减利息
        data.add(FORMAT.format(payTotal));//已还总金额
        data.add(FORMAT.format(payLoan));//已还本金
        data.add(FORMAT.format(payInterest));//已还利息
        data.add(FORMAT.format(totalPayAhead));//一次还清支付金额
        data.add(FORMAT.format(saveInterest));//节省利息
        data.add(String.valueOf(0));//剩余还款期限
        return data.toArray(new String[data.size()]);
    }

    private static void show(String[] strs) {
        int i = 0;
        System.out.println("原还款总额:" + strs[i++]);
        System.out.println("贷款总额:" + strs[i++]);
        System.out.println("原还款总利息:" + strs[i++]);
        System.out.println("原首月还款金额:" + strs[i++]);
        System.out.println("原每月递减利息:" + strs[i++]);
        System.out.println("已还总金额:" + strs[i++]);
        System.out.println("已还本金:" + strs[i++]);
        System.out.println("已还利息:" + strs[i++]);
        System.out.println("一次还清支付金额:" + strs[i++]);
        System.out.println("节省利息:" + strs[i++]);
        System.out.println("剩余还款期限:" + strs[i++]);
    }

    /**
     * 等额本金
     * @param principal     本金
     * @param interestRate  利率
     * @param years         还款年限
     */
    public static PayLoan equalPrincipal(double principal , double interestRate , int years) {
        int month = years * 12;
        double monthRate = interestRate / 100 / 12;
        double fixedPrincipal = principal / month;
        double sum = 0;
        double currentPrincipal = principal;
        List<MonthLoan> monthLoans = new ArrayList<MonthLoan>();
        for(int i = 0 ; i < month ; i++) {
            double interest = currentPrincipal * monthRate;
            double payMoney = fixedPrincipal + interest;
            sum += payMoney;
            currentPrincipal = currentPrincipal - fixedPrincipal;

            MonthLoan monthLoan = new MonthLoan(i + 1 , fixedPrincipal , interest , payMoney);
            monthLoans.add(monthLoan);
        }
        return new PayLoan(principal , interestRate , month , sum , monthLoans);
    }

    public static void main(String[] args) {
//        double sum = equalPrincipal(120000 , 4.86 , 10);
//        PayLoan payLoan = equalPrincipal(1780000 , 5.35 , 30);
//        System.out.println(payLoan);
        show(calculateEqualPrincipalAndInterest(1780000 , 30 * 12 ,  5 * 12 , 5.35));
        System.out.println("等额本金");
        show(calculateEqualPrincipal(1780000 , 30 * 12 ,  5 * 12 , 5.35));
    }

    private static class PayLoan {
        private final double principal;

        private final double interestRate;

        private final int period;

        private final double total;

        private final List<MonthLoan> monthLoanList;

        public PayLoan(double principal, double interestRate, int period, double total, List<MonthLoan> monthLoanList) {
            this.principal = principal;
            this.interestRate = interestRate;
            this.period = period;
            this.total = total;
            this.monthLoanList = monthLoanList;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("本金：").append(principal).append("，利率：").append(interestRate).append("%，期数：").append(period).append("，总计还款：").append(total).append("\n");
            for(MonthLoan monthLoan : monthLoanList) {
                sb.append(monthLoan).append("\n");
            }
            return sb.toString();
        }
    }

    private static class MonthLoan {

        private final int i;

        private final double principal;

        private final double interest;

        private final double payMoney;

        public MonthLoan(int i,  double principal , double interest, double payMoney) {
            this.i = i;
            this.principal = principal;
            this.interest = interest;
            this.payMoney = payMoney;
        }

        @Override
        public String toString() {
            return "第" + i + "期：当月本金：" + principal + "，支付利息：" + interest + "，还款额：" + payMoney;
        }
    }
}
