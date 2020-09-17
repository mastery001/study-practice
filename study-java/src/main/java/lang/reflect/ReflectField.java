package lang.reflect;


/** 
* @ClassName: ReflectField 

* @Description: TODO	�����ֶ���

* @author lingfeng

* @date 2014-4-19 ����09:44:23 

*  
*/ 

public class ReflectField {
	
	private int x;
	public int y;
	public String str1 = "ball";
	public String str2 = "basketball";
	public String str3 = "school";
	
	public ReflectField(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	/* (�� Javadoc) 
	
	* <p>Title: hashCode</p> 
	
	* <p>Description: ͨ��x��y������дhashCode�������������ϣֵ</p> 
	
	* @return 
	
	* @see java.lang.Object#hashCode() 
	
	*/ 
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (�� Javadoc) 
	
	* <p>Title: equals</p> 
	
	* <p>Description:��дequals���� </p> 
	
	* @param obj
	* @return 
	
	* @see java.lang.Object#equals(java.lang.Object) 
	
	*/ 
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReflectField other = (ReflectField) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return str1 + ","+ str2 + ","+str3;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
