package test;  
  
final class immudemo
{
      private static final StringBuffer bf = new StringBuffer("Yaxita");
      public StringBuffer getter(){
           return new StringBuffer(bf);
      }
      
      public String toString() {
    	  return bf.toString();
      }
}

public class HelloWorld{

     public static void main (String args[])
     {
//         immudemo obj1 = new immudemo();
//         StringBuffer bf2 = obj1.getter();
//         bf2.append("Shah");
//         System.out.println(obj1);
    	 System.out.println(~5);
     }
}