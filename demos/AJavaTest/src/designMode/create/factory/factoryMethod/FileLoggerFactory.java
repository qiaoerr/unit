package designMode.create.factory.factoryMethod;

/**
 * @author liu
 * @description �ļ���־��¼�������ࣺ���幤��
 */
public class FileLoggerFactory implements LoggerFactory {
	public Logger createLogger() {
		// �����ļ���־��¼������
		Logger logger = new FileLogger();
		// �����ļ�������ʡ��
		return logger;
	}

}
