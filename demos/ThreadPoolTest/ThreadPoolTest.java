package ThreadPoolTest;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
		ThreadPool threadPool = new ThreadPool(3); // ����һ���и�3�����̵߳��̳߳�
		Thread.sleep(500); // ����500����,�Ա����̳߳��еĹ����߳�ȫ������
		// ��������
		for (int i = 0; i <= 5; i++) { // ����6������
			threadPool.execute(createTask());
		}
		threadPool.waitFinish(); // �ȴ���������ִ�����
		threadPool.closePool(); // �ر��̳߳�

	}

	private static Runnable createTask() {
		return new Runnable() {
			public void run() {
				System.out.println("Hello world");
			}
		};
	}
}