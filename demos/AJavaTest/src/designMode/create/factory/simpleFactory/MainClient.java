package designMode.create.factory.simpleFactory;

import designMode.create.factory.simpleFactory.util.XMLUtil;

public class MainClient {

	/**
	 * @param args
	 * @description ͨ����̬��������������Ʒ
	 */
	public static void main(String[] args) {
		Chart chart = ChartFactory.getChart(ChartFactory.PIE);// ������Ʒ����
		chart.display();

		/**
		 * ����Ϊ�򵥹���ģʽ����չ
		 * 
		 * @description ���Խ���ȡ�� Ʒ���͵Ĳ������������ļ��У������� Ҫ��������ͼ����� ֻ���޸������ļ�config.xml��
		 *              �����޸��κ�Դ���룬���ϡ�����ԭ�򡱡�
		 */
		String type = XMLUtil.getChartType(); // ��ȡ�����ļ��еĲ���
		chart = ChartFactory.getChart(type); // ������Ʒ����
		chart.display();

	}

}
