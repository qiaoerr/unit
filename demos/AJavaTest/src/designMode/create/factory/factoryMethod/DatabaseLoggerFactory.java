package designMode.create.factory.factoryMethod;

/**
 * @author liu
 * @description ���ݿ���־��¼�������ࣺ���幤��
 */
public class DatabaseLoggerFactory implements LoggerFactory {

	@Override
	public Logger createLogger() {
		// �������ݿ⣬����ʡ��
		// �������ݿ���־��¼������
		Logger logger = new DatabaseLogger();
		// ��ʼ�����ݿ���־��¼��������ʡ��
		return logger;
	}

}
