/**
  @Title: SoftReferenceTest.java
  @Package referencetest
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-2-14 ÏÂÎç04:49:16
  @version V1.0
 */

package referencetest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ReferenceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String str = new String("hello");
		// ReferenceQueue<String> rq = new ReferenceQueue<String>();
		// WeakReference<String> wf = new WeakReference<String>(str, rq);
		// rq.poll();
		A a = new A();
		a.str = "Hello, reference";
		ArrayList<A> arrayList = new ArrayList<A>();
		arrayList.add(a);
		// SoftReference<A> sr = new SoftReference<A>(a);
		WeakReference<A> sr = new WeakReference<A>(a);
		a = null;
		// arrayList = null;
		System.gc();
		int i = 0;
		// for (int j = 0; j < arrayList.size(); j++) {
		// System.out.println(arrayList.get(j).str);
		// }
		System.out.println(sr.get().str);
		// while (sr.get() != null) {
		// System.out.println(String.format(
		// "Get str from object of SoftReference: %s, count: %d",
		// sr.get().str, ++i));
		// if (i % 10 == 0) {
		// System.gc();
		// System.out.println("System.gc() was invoked!");
		// }
		// try {
		// Thread.sleep(500);
		// } catch (InterruptedException e) {
		//
		// }
		// }
		System.out.println("object a was cleared by JVM!");
	}

}