package stack;

import java.io.Serializable;

public class Pair<T extends Serializable & Comparable<?>> {

	private T value;
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public static boolean and(boolean o1 , boolean o2) {
		return o1 & o2;
	}

	public static void main(String[] args) {
		Pair<S<String>> p = new Pair<S<String>>();
		p.setValue(new S<String>());
	}
	
}

class S<T> implements Serializable,Comparable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4624001705190284400L;

	@Override
	public int compareTo(T o) {
		// TODO 自动生成的方法存根
		return 0;
	}
	
}

class S1<T extends Serializable > {
	
}