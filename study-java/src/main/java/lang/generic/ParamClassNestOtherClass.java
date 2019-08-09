package lang.generic;

/** 
* @ClassName: ParamClassNestOtherClass 

* @Description: TODO

* @author lingfeng

* @date 2014-4-26 ����08:54:02 

*  
*/ 

public class ParamClassNestOtherClass {
	
	public static void main(String[] args) {
		ReprChange r = new ReprChange();
	}
}

interface ConvertibleTo<T>{		//Convertible means �ɸı�ģ�ͬ����ģ��ɽ�����
	T convert();
}

class ReprChange<T extends ConvertibleTo<S>,S extends ConvertibleTo<T>> {
	
	T t; 
	
	void set(S s) {
		t = s.convert();
	}
	
	S get() {
		return t.convert();
	}
}
