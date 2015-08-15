package designMode;

public class Start {

	public static void main(String[] args) {
		WorkManager workManager = new WorkManager();
		workManager.getWorker().doWork();
	}

}
