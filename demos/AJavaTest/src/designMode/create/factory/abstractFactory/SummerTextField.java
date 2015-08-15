package designMode.create.factory.abstractFactory;

/**
 * @author liu
 * @description Summer文本框类：具体产品
 */
class SummerTextField implements TextField {
	public void display() {
		System.out.println("显示蓝色边框文本框。");
	}
}