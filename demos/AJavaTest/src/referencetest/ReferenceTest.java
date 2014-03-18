/**
  @Title: SoftReferenceTest.java
  @Package referencetest
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-2-14 下午04:49:16
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
		A a = new A();// 对象a的引用计数加1
		a.str = "Hello, reference";
		ArrayList<A> arrayList = new ArrayList<A>();
		arrayList.add(a);// 把对象a加入到数组。数组arrayList就对对象a就有强引用，对象a的引用计数加1
		// SoftReference<A> sr = new SoftReference<A>(a);
		WeakReference<A> sr = new WeakReference<A>(a);
		a = null;// 变量a对对象a不再有强引用关系。 对象a的引用计数减1
		// arrayList = null;// 把数组arrayList置空，数组arrayList对对象a不再有强引用关系，对象a的引用计数减1
		System.gc();
		// for (int j = 0; j < arrayList.size(); j++) {
		// System.out.println(arrayList.get(j).str);
		// }
		System.out.println(sr.get().str);
	}

}