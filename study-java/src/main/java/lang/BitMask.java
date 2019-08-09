package lang;

public class BitMask {
	
	private static final byte STATE_BUSY = 0x01 << 2;
	
	private int state;
	
	public void setState(boolean flag) {
		if(flag) {
			state |= STATE_BUSY;
		}else {
			state &= ~STATE_BUSY;
		}
	}
	
	public int getState() {
		return state;
	}
	
	public static void main(String[] args) {
		BitMask bm = new BitMask();
		bm.setState(false);
		System.out.println(bm.getState());
	}
}
