/**
  @Title: EnumTest.java
  @Package test
  @Description: TODO
  Copyright: Copyright (c) 2011 
  Company:�������»����Ƽ����޹�˾
  
  @author Comsys-Administrator
  @date 2013-9-6 ����11:38:37
  @version V1.0
 */

package test;

/**
 * @ClassName: EnumTest
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-9-6 ����11:38:37
 * 
 */

public class EnumTest {

	public static void main(String[] args) {
		System.out.println(EnumTest.MyEnum.afdsaf.getValue());
		System.out.println(EnumTest.MyEnum.bfads.getValue());
		// MyEnum��ö�ٶ��󲻻��ٴ�create
		System.out.println(EnumTest.MyEnum.afdsaf.getValue());
		System.out.println(EnumTest.MyEnum.bfads.getValue());

	}

	/* ö��Ҳ������һ�����һ����ӷ���������,�����Ϊ����Ӿ�̬�ͷǾ�̬�����Ի򷽷�,
	 ��һ�ж�������һ���������������. */
	public enum MyEnum {
		// ö���б����д����ǰ�棬����������
		// ͨ�����Ÿ�ֵ,�����д��ι�����
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
