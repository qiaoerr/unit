package demo.classLoader;

public class MyApp {
	public static void main(String[] args) throws Exception {
		FileClassLoader loader = new FileClassLoader();
		Class objClass = loader.findClass("MyApp");
		Object obj = objClass.newInstance();
		System.out.println(objClass.getName());
		System.out.println(objClass.getClassLoader());
		System.out.println(obj);
	}
}
