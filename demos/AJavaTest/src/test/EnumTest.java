/**
  @Title: EnumTest.java
  @Package test
  @Description: TODO
  Copyright: Copyright (c) 2011 
  Company:北京天下互联科技有限公司
  
  @author Comsys-Administrator
  @date 2013-9-6 上午11:38:37
  @version V1.0
 */

package test;

/**
 * @ClassName: EnumTest
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-9-6 上午11:38:37
 * 
 */

public class EnumTest {

	public static void main(String[] args) {
		System.out.println(EnumTest.MyEnum.afdsaf.getValue());
		System.out.println(EnumTest.MyEnum.bfads.getValue());
		// MyEnum的枚举对象不会再次create
		System.out.println(EnumTest.MyEnum.afdsaf.getValue());
		System.out.println(EnumTest.MyEnum.bfads.getValue());

	}

	/* 枚举也可以象一般的类一样添加方法和属性,你可以为它添加静态和非静态的属性或方法,
	 这一切都象你在一般的类中做的那样. */
	public enum MyEnum {
		// 枚举列表必须写在最前面，否则编译出错
		// 通过括号赋值,必须有带参构造器
		afdsaf(2), bfads;
		private final int value;

		public int getValue() {
			return value;
		}

		private MyEnum(int v) {
			System.out.println("create");
			this.value = v;
		}

		private MyEnum() {
			this.value = 1;
			System.out.println("create");
		}
	}
}
