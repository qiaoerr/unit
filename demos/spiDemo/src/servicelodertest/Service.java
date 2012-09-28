package servicelodertest;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Service {

	private static ServiceLoader<SpiServer> loader;

	public static synchronized ServiceLoader<SpiServer> getLoader() {
		if (loader == null) {
			loader = ServiceLoader.load(SpiServer.class);
		}
		return loader;
	}

	public static void main(String[] args) {
		ServiceLoader<SpiServer> loade = getLoader();
		Iterator<SpiServer> iterator = loade.iterator();
		while (iterator.hasNext()) {
			SpiServer SServer = iterator.next();
			SServer.set("str");
			SServer.test();

		}
	}

}
