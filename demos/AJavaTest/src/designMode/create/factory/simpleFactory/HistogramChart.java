package designMode.create.factory.simpleFactory;

/**
 * @author liu
 * @description ��״ͼ�ࣺ�����Ʒ��
 */
public class HistogramChart implements Chart {

	public HistogramChart() {
		System.out.println("������״ͼ��");
	}

	@Override
	public void display() {
		System.out.println("��ʾ��״ͼ��");
	}

}
