package designMode.create.factory.simpleFactory;

import designMode.create.factory.simpleFactory.util.XMLUtil;

public class MainClient {

	/**
	 * @param args
	 * @description 通过静态工厂方法创建产品
	 */
	public static void main(String[] args) {
		Chart chart = ChartFactory.getChart(ChartFactory.PIE);// 创建产品对象
		chart.display();

		/**
		 * 以下为简单工厂模式的扩展
		 * 
		 * @description 可以将获取产 品类型的参数放在配置文件中，这样需 要更换具体图表对象， 只需修改配置文件config.xml，
		 *              无须修改任何源代码，符合“开闭原则”。
		 */
		String type = XMLUtil.getChartType(); // 读取配置文件中的参数
		chart = ChartFactory.getChart(type); // 创建产品对象
		chart.display();

	}

}
