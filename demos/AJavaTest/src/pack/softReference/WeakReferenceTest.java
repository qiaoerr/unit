/**
  @Title: WeakReferenceTest.java
  @Package referencetest
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-2-14 下午04:47:46
  @version V1.0
 */

package pack.softReference;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		A a = new A();
		a.str = "Hello, reference";
		WeakReference<A> weak = new WeakReference<A>(a);
		a = null;// 注意此行代码a已经被赋空，否则后面System.gc()时，a的引用计数不为0，不会被销毁回收。
		int i = 0;
		while (weak.get() != null) {
			System.out.println(String.format(
					"Get str from object of WeakReference: %s, count: %d",
					weak.get().str, ++i));
			if (i % 10 == 0) {
				System.gc();
				System.out.println("System.gc() was invoked!");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
		}
		System.out.println("object a was cleared by JVM!");
	}

}
