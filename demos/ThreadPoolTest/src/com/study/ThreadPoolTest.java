package com.study;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ThreadPoolTest {
	static int i = 1;

	public static void main(String[] args) throws InterruptedException {
		ThreadPool threadPool = new ThreadPool(4); // 创建一个有个3工作线程的线程池
		Thread.sleep(500); // 休眠500毫秒,以便让线程池中的工作线程全部运行
		// 运行任务
		long start = System.currentTimeMillis();
		for (int i = 0; i <= 11; i++) { // 创建6个任务
			threadPool.execute(createTask());
		}
		threadPool.waitFinish(); // 等待所有任务执行完毕
		threadPool.closePool(); // 关闭线程池
		System.out.println("耗时" + (System.currentTimeMillis() - start));

	}

	private static Runnable createTask() {
		return new Runnable() {
			public void run() {
				try {
					PrintWriter pw = new PrintWriter("E:/test/out" + (i++)
							+ ".txt");
					FileInputStream in = new FileInputStream(
							"E:/test/wenben.txt");
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
					String str = br.readLine();
					while (str != null) {
						// System.out.println(str);
						pw.println(str);
						str = br.readLine();
					}
					br.close();
					pw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}