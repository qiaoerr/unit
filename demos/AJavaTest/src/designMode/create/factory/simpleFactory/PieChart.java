package designMode.create.factory.simpleFactory;

/**
 * @author liu
 * @description ��״ͼ�ࣺ�����Ʒ��
 */
public class PieChart implements Chart {

	public PieChart() {
		System.out.println("������״ͼ��");
	}

	@Override
	public void display() {
		System.out.println("��ʾ��״ͼ��");
	}

}
