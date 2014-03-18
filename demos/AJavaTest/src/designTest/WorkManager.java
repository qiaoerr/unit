package designTest;


public class WorkManager extends SupportManager<WorkInterface> {

	public WorkManager() {
		super(WorkInterface.class, new DefaultWork());
		setMap("dog", "designTest.DogWork");
	}
}
