package lang.enumDemo;

/**
 * ������ͨ��ö��
 * @author Administrator
 *
 */
public enum TrafficLamp {
	RED(30) {

		@Override
		public TrafficLamp nextLamp() {
			// TODO Auto-generated method stub
			return GREEN;
		}
		
	},
	GREEN(45) {

		@Override
		public TrafficLamp nextLamp() {
			// TODO Auto-generated method stub
			return YELLOW;
		}
		
	},
	YELLOW(5) {

		@Override
		public TrafficLamp nextLamp() {
			// TODO Auto-generated method stub
			return RED;
		}
		
	};
	
	private int time; 
	
	private TrafficLamp() {
		System.out.println(true);
	}
	
	private TrafficLamp(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
	
	public void show() {
		System.out.println("��ǰ����ĵ�Ϊ��" + this.toString());
	}
	
	public abstract TrafficLamp nextLamp();
	
	static void lamoWork(TrafficLamp trafficLamp) throws Exception{
		trafficLamp.show();
		Thread.sleep(trafficLamp.getTime() * 1000);
		lamoWork(trafficLamp.nextLamp());
	}
}
