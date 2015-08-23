package designMode.create.singleton;

/**
 * @author liu
 * @description Initialization on Demand Holder
 */
public class Singleton {

	private Singleton() {
		System.out.println("initial");
	}

	/**
	 * @description HolderClassΪSingleton�ľ�̬�ڲ��࣬
	 *              ����Singleton��ʱ�򲻻���������ڲ��ֻ࣬���õ��ڲ����ʱ��Ż����
	 */
	private static class HolderClass {
		private final static Singleton instance = new Singleton();

		static {
			System.out.println("װ�� HolderClass");
		}

	}

	public static Singleton getInstance() {
		return HolderClass.instance;
	}

	public static void main(String args[]) {
		Singleton s;
		s = Singleton.getInstance();
		System.out.println("");
	}

	/*	
	 *   ����ʽ�����಻��ʵ���ӳټ��أ����ܽ����ò���ʼ��ռ���ڴ棻����ʽ�������̰߳�ȫ���Ʒ���������������Ӱ�졣
		 ���ھ�̬��������û����ΪSingleton�ĳ�Ա����ֱ��ʵ������������ʱ����ʵ����Singleton����һ�ε���getInstance()
		 ʱ�������ڲ���HolderClass���ڸ��ڲ����ж�����һ��static���͵ı���instance����ʱ�����ȳ�ʼ�������Ա������
		 ��Java���������֤���̰߳�ȫ�ԣ�ȷ���ó�Ա����ֻ�ܳ�ʼ��һ�Ρ�����getInstance()����û���κ��߳�������
		 ��������ܲ�������κ�Ӱ�졣
		 ͨ��ʹ��IoDH�����Ǽȿ���ʵ���ӳټ��أ��ֿ��Ա�֤�̰߳�ȫ����Ӱ��ϵͳ���ܣ���ʧΪһ����õ�Java���Ե���ģʽ
		 ʵ�ַ�ʽ����ȱ�����������Ա����������أ��ܶ�����������Բ�֧��IoDH����
	*/
}
