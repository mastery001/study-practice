package lang.generic;

/** 
* @ClassName: GenericSeqTest 

* @Description: TODO		���Ͷ�����ʾ

* @author lingfeng

* @date 2014-4-27 ����08:57:59 

*  
*/ 

public class GenericSeqTest {
	
}

class Seq<T> {
	/** 
	* @Fields head : TODO	��ͷ��
	*/ 
	T head;		
	/** 
	* @Fields tail : TODO 	��β��
	*/ 
	Seq<T> tail;	
	
	/** 
	* <p>Title: �޲ι��췽��</p> 
	* <p>Description:	�����г�Ա������ֵΪnull </p> 
	*/ 
	Seq() {
		this.head = null;
		this.tail = null;
	}
	
	/** 
	* <p>Title:	�вι��췽�� </p> 
	* <p>Description: �����г�Ա������ֵ</p> 
	* @param head
	* @param tail 
	*/ 
	Seq(T head,  Seq<T> tail) {
		this.head = head;
		this.tail = tail;
	}
	
	
	/** 
	
	* @Title: isEmpty 
	
	* @Description: TODO 	�ж϶����Ƿ�Ϊ��
	
	* @param @return   
	
	* @return boolean    �������� 
	
	* @throws 
	
	*/ 
	boolean isEmpty() {
		return this.tail == null;
	}
	
	class Zipper<S> {
		Seq<Pair<T,S>> zip(Seq<S> that) {
			
			if(isEmpty() || that.isEmpty()) {
				
				return new Seq<Pair<T,S>>();
				
			}else {
				
				return new Seq<Pair<T,S>>(
						
						new Pair<T,S>(head,that.head),
						zip(that.tail)
				);
			}
		}
	}
}

class Pair<T,S> {
	T fst;
	S snd;
	Pair(T f , S s) {
		this.fst = f;
		this.snd = s;
	}
}
