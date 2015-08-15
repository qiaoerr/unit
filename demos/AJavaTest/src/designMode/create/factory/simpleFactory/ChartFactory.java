package designMode.create.factory.simpleFactory;

/**
 * @author liu
 * @description 图表工厂类
 */
public class ChartFactory {

	/**
	 * @description 柱状图类型
	 */
	public static final String HISTOGRAM = "histogram";
	/**
	 * @description 饼状图类型
	 */
	public static final String PIE = "pie";

	/**
	 * @param type
	 * @description 静态工厂方法
	 * @return
	 */
	public static Chart getChart(String type) {
		Chart chart = null;
		if (HISTOGRAM.equalsIgnoreCase(type)) {
			chart = new HistogramChart();
			System.out.println("初始化设置柱状图！");
		} else if (PIE.equalsIgnoreCase(type)) {
			chart = new PieChart();
			System.out.println("初始化设置饼状图！");
		}
		return chart;

	}
}
