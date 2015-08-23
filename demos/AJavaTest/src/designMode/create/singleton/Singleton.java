package designMode.create.singleton;

/**
 * @author liu
 * @description Initialization on Demand Holder
 */
public class Singleton {

	private Singleton() {
		System.out.println("initial");
	}

	/**
	 * @description HolderClass为Singleton的静态内部类，
	 *              加载Singleton的时候不会加载它的内部类，只有用到内部类的时候才会加载
	 */
	private static class HolderClass {
		private final static Singleton instance = new Singleton();

		static {
			System.out.println("装载 HolderClass");
		}

	}

	public static Singleton getInstance() {
		return HolderClass.instance;
	}

	public static void main(String args[]) {
		Singleton s;
		s = Singleton.getInstance();
		System.out.println("");
	}

	/*	
	 *   饿汉式单例类不能实现延迟加载，不管将来用不用始终占据内存；懒汉式单例类线程安全控制烦琐，而且性能受影响。
		 由于静态单例对象没有作为Singleton的成员变量直接实例化因此类加载时不会实例化Singleton，第一次调用getInstance()
		 时将加载内部类HolderClass，在该内部类中定义了一个static类型的变量instance，此时会首先初始化这个成员变量，
		 由Java虚拟机来保证其线程安全性，确保该成员变量只能初始化一次。由于getInstance()方法没有任何线程锁定，
		 因此其性能不会造成任何影响。
		 通过使用IoDH，我们既可以实现延迟加载，又可以保证线程安全，不影响系统性能，不失为一种最好的Java语言单例模式
		 实现方式（其缺点是与编程语言本身的特性相关，很多面向对象语言不支持IoDH）。
	*/
}
