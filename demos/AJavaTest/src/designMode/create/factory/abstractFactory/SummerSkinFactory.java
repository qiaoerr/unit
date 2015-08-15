package designMode.create.factory.abstractFactory;

/**
 * @author liu
 * @description SummerƤ�����������幤��
 */
class SummerSkinFactory implements SkinFactory {
	public Button createButton() {
		return new SummerButton();
	}

	public TextField createTextField() {
		return new SummerTextField();
	}

}