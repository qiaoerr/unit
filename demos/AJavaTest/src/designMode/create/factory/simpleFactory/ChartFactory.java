package designMode.create.factory.simpleFactory;

/**
 * @author liu
 * @description ͼ������
 */
public class ChartFactory {

	/**
	 * @description ��״ͼ����
	 */
	public static final String HISTOGRAM = "histogram";
	/**
	 * @description ��״ͼ����
	 */
	public static final String PIE = "pie";

	/**
	 * @param type
	 * @description ��̬��������
	 * @return
	 */
	public static Chart getChart(String type) {
		Chart chart = null;
		if (HISTOGRAM.equalsIgnoreCase(type)) {
			chart = new HistogramChart();
			System.out.println("��ʼ��������״ͼ��");
		} else if (PIE.equalsIgnoreCase(type)) {
			chart = new PieChart();
			System.out.println("��ʼ�����ñ�״ͼ��");
		}
		return chart;

	}
}
