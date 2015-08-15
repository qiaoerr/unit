package designMode.create.factory.abstractFactory;

/**
 * @author liu
 * @description Summer皮肤工厂：具体工厂
 */
class SummerSkinFactory implements SkinFactory {
	public Button createButton() {
		return new SummerButton();
	}

	public TextField createTextField() {
		return new SummerTextField();
	}

}