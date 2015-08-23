package designMode;

public class WorkManager extends SupportManager<WorkInterface> {

	public WorkManager() {
		super(WorkInterface.class, new DefaultWork());
		setMap("dog", "designMode.DogWork");
	}
}
