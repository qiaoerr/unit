package designMode.create.factory.abstractFactory;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 使用抽象层定义
		SkinFactory factory;
		Button bt;
		TextField tf;
		factory = new SpringSkinFactory();// 一个具体工厂生产一个产品族
		bt = factory.createButton();
		tf = factory.createTextField();
		bt.display();
		tf.display();

	}

}
