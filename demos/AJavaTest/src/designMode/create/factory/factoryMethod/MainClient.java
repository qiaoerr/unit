package designMode.create.factory.factoryMethod;

import designMode.create.factory.factoryMethod.util.XMLUtil;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoggerFactory factory;
		Logger logger;
		factory = new FileLoggerFactory(); // 可引入配置文件实现
		logger = factory.createLogger();
		logger.writeLog();

		/* 引入配置文件
		 * 
		 * 引入XMLUtil类和XML配置文件后，如果要增加新的日志记录方式，只需要执行如下几个步骤：

		 (1) 新的日志记录器需要继承抽象日志记录器Logger；

		 (2) 对应增加一个新的具体日志记录器工厂，继承抽象日志记录器工厂LoggerFactory，并实现其中的工厂方法createLogger()，设置好初始化参数和环境变量，返回具体日志记录器对象；

		 (3) 修改配置文件config.xml，将新增的具体日志记录器工厂类的类名字符串替换原有工厂类类名字符串；

		 (4) 编译新增的具体日志记录器类和具体日志记录器工厂类，运行客户端测试类即可使用新的日志记录方式，而原有类库代码无须做任何修改，完全符合“开闭原则”。
		*/
		factory = (LoggerFactory) XMLUtil.getBean(); // getBean()的返回类型为Object，需要进行强制类型转换
		logger = factory.createLogger();
		logger.writeLog();

		/* 
		 * 有人说：可以在客户端代码中直接通过反射机制来生成产品对象，在定义产品对象时使用抽象类型，同样可以确保系统的灵活性和可扩展性，增加新的具体产品类无须修改源代码，只需要将其作为抽象产品类的子类再修改配置文件即可，根本不需要抽象工厂类和具体工厂类。

		 试思考这种做法的可行性？如果可行，这种做法是否存在问题？为什么？
		 
		 反射生成对象只能适用一些最简单的情况，如果对象的创建过程比较复杂，例如要调用有参构造函数、创建之前要配置环境等等，需要将这些代码封装到工厂中。需要仔细理解工厂的作用，而不是简单的创建一个对象。

		*/
	}

}
