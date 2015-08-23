package designMode.create.singleton;

/**
 * @author liu
 * @description ����ʽ�����ࣺ�ڶ��徲̬������ʱ��ʵ���������࣬ ���������ص�ʱ����Ѿ������˵�������
 */
public class EagerSingleton {

	private static final EagerSingleton instance = new EagerSingleton();

	private EagerSingleton() {

	}

	public static EagerSingleton getInstance() {
		return instance;
	}
}
