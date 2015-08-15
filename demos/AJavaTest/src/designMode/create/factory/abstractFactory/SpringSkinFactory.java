package designMode.create.factory.abstractFactory;

/**
 * @author liu
 * @description SpringƤ�����������幤��
 */
class SpringSkinFactory implements SkinFactory {
	public Button createButton() {
		return new SpringButton();
	}

	public TextField createTextField() {
		return new SpringTextField();
	}

}
