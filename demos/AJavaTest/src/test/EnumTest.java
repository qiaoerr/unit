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
		System.out.println(MyEnum.afdsaf.getValue());

	}

	public enum MyEnum {
		afdsaf(1), bfads(2), cfas(4);
		private final int value;

		public int getValue() {
			return value;
		}

		private MyEnum(int v) {
			this.value = v;
		}
	}
}
