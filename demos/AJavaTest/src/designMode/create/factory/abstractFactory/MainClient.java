package designMode.create.factory.abstractFactory;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ʹ�ó���㶨��
		SkinFactory factory;
		Button bt;
		TextField tf;
		factory = new SpringSkinFactory();// һ�����幤������һ����Ʒ��
		bt = factory.createButton();
		tf = factory.createTextField();
		bt.display();
		tf.display();

	}

}
