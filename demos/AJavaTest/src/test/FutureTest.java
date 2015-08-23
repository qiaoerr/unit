package test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		funcc();
		/*
				List<Future<String>> results = new ArrayList<Future<String>>();
				ExecutorService es = Executors.newFixedThreadPool(4);
				for (int i = 0; i < 10; i++)
					results.add(es.submit(new Task()));
				for (Future<String> res : results)
					System.out.println(res.get());

				List<Future<?>> results1 = new ArrayList<Future<?>>();
				ExecutorService es1 = Executors.newFixedThreadPool(4);
				for (int i = 0; i < 10; i++)
					results1.add(es1.submit(new TaskR()));
				for (Future<?> res : results1)
					System.out.println(res.get());*/
	}

	public static class Task implements Callable<String> {
		@Override
		public String call() throws Exception {
			String tid = String.valueOf(Thread.currentThread().getId());
			System.out.printf("Thread#%s : in call\n", tid);
			return tid;
		}
	}

	public static class TaskR implements Runnable {
		@Override
		public void run() {
			String tid = String.valueOf(Thread.currentThread().getId());
			System.out.printf("Thread#%s : in call\n", tid);

		}
	}

	//
	public static void funcc() {
		Callable<Integer> callable = new Callable<Integer>() {

			public Integer call() throws Exception {
				System.out.println("enter");
				Thread.sleep(5000);// 可能做一些事情
				System.out.println("outer");
				return new Random().nextInt(100);
			}

		};
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		new Thread(future).start();
		try {
			// Thread.sleep(8000);// 可能做一些事情
			System.out.println("geter");
			System.out.println(future.get());
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
