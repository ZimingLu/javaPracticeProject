
public class ArrayTest{
	private Object[] obj;
	obj = new Object[]{"hello", "world"};
	public static void main(String[] args){
		for(String s : obj)
			System.out.println(s);
	}
}