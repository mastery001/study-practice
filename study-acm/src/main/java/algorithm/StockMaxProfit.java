package algorithm;  
  
  
/**  
 *@Description:  股票最大利润问题
 *@Author:zouziwen
 *@Since:2017年2月16日  
 *@Version:1.1.0  
 */
public class StockMaxProfit {
	
	int maxProfit(int[] moneys) {
		if(moneys == null || moneys.length < 2) throw new RuntimeException("股票数据不正常");
		
		int max = moneys[1] - moneys[0] , min =  moneys[1] >  moneys[0] ? moneys[0] : moneys[1];
		
		for(int i = 2 ; i < moneys.length ; i++) {
			int tmp = moneys[i] - min;
			if(moneys[i] < min) min = moneys[i];
			if(tmp > max) max = tmp;
		}
		return max;
	}
}
