package test;  
  
public class HashTest {

	private static int indexOf(String pushIdentity , int bucket) {
		int hash = pushIdentity.hashCode();
		hash &= 0x7FFFFFFF;
		return hash % bucket;
	}
	
	public static void main(String[] args) {
		System.out.println(indexOf("D95616F6-626F-402C-86A7-DF73D722643F", 421));
	}
}
