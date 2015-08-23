package designMode.create.singleton;

/**
 * @author liu
 * @description 懒汉式单例类：在第一次调用getInstance()方法时实例化，在类加载时并不自行实例化，这种技术又称为延迟加载(Lazy
 *              Load)技术，即需要的时候再加载实例，为了避免多个线程同时调用getInstance()方法，
 *              我们可以使用关键字synchronized
 */
public class LazySingleton {

	private static LazySingleton instance = null;

	private LazySingleton() {

	}

	synchronized public static LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
	/*  
	上面的懒汉式单例类在getInstance()方法前面增加了关键字synchronized进行线程锁，
	以处理多个线程同时访问的问题。但是，上述代码虽然解决了线程安全问题，但是
	每次调用getInstance()时都需要进行线程锁定判断，在多线程高并发访问环境中，
	将会导致系统性能大大降低。如何既解决线程安全问题又不影响系统性能呢？我们
	继续对懒汉式单例进行改进。事实上，我们无须对整个getInstance()方法进行锁定，
	只需对其中的代码“instance= new LazySingleton();”进行锁定即可。因此getInstance()
	方法可以进行如下改进：
	
	public static LazySingleton getInstance() { 
	if (instance == null) {
	    synchronized (LazySingleton.class) {
	        instance = new LazySingleton(); 
	    }
	}
	return instance; 
	}
	
	问题貌似得以解决，事实并非如此。如果使用以上代码来实现单例，还是会存在单
	例对象不唯一。原因如下：假如在某一瞬间线程A和线程B都在调用getInstance()
	方法，此时instance对象为null值，均能通过instance == null的判断。由于实
	现了synchronized加锁机制，线程A进入synchronized锁定的代码中执行实例创建
	代码，线程B处于排队等待状态，必须等待线程A执行完毕后才可以进入synchronized锁定
	代码。但当A执行完毕时，线程B并不知道实例已经创建，将继续创建新的实例，导致
	产生多个单例对象，违背单例模式的设计思想，因此需要进行进一步改进，在synchronized中
	再进行一次(instance == null)判断，这种方式称为双重检查锁定(Double-Check Locking)。
	使用双重检查锁定实现的懒汉式单例类完整代码如下所示：
	
	class LazySingleton { 
	private volatile static LazySingleton instance = null; 

	private LazySingleton() { } 

	public static LazySingleton getInstance() { 
	    //第一重判断
	    if (instance == null) {
	        //锁定代码块
	        synchronized (LazySingleton.class) {
	            //第二重判断
	            if (instance == null) {
	                instance = new LazySingleton(); //创建单例实例
	             }
	         }
	     }
	    return instance; 
	 }
	}
	
	需要注意的是，如果使用双重检查锁定来实现懒汉式单例类，需要在静态成员变量instance之前
	增加修饰符volatile，被volatile修饰的成员变量可以确保多个线程都能够正确处理，且该代码
	只能在JDK 1.5及以上版本中才能正确执行。由于volatile关键字会屏蔽Java虚拟机所做的一些
	代码优化，可能会导致系统运行效率降低，因此即使使用双重检查锁定来实现单例模式也不是一种
	完美的实现方式。 
	
	*/

}
