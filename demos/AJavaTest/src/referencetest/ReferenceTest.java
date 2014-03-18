/**
  @Title: SoftReferenceTest.java
  @Package referencetest
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-2-14 ����04:49:16
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
		A a = new A();// ����a�����ü�����1
		a.str = "Hello, reference";
		ArrayList<A> arrayList = new ArrayList<A>();
		arrayList.add(a);// �Ѷ���a���뵽���顣����arrayList�ͶԶ���a����ǿ���ã�����a�����ü�����1
		// SoftReference<A> sr = new SoftReference<A>(a);
		WeakReference<A> sr = new WeakReference<A>(a);
		a = null;// ����a�Զ���a������ǿ���ù�ϵ�� ����a�����ü�����1
		// arrayList = null;// ������arrayList�ÿգ�����arrayList�Զ���a������ǿ���ù�ϵ������a�����ü�����1
		System.gc();
		// for (int j = 0; j < arrayList.size(); j++) {
		// System.out.println(arrayList.get(j).str);
		// }
		System.out.println(sr.get().str);
	}

}