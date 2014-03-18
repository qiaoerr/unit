package designTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.SortedMap;

public class SupportManager<T> {
	SortedMap<String, String> map;
	T defaultWork;
	Class<T> interWork;

	public SupportManager(Class<T> interWork, T defaultWork) {
		super();
		this.interWork = interWork;
		this.defaultWork = defaultWork;
	}

	T getWorker() {
		try {
			String condition = "dog";
			if (condition.equals("dog")) {
				String className = map.get("dog");
				Class<? extends T> clazz = Class.forName(className).asSubclass(
						interWork);
				Constructor<? extends T> constructor = clazz.getConstructor();
				return constructor.newInstance();
			} else {
				return defaultWork;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setMap(String key, String value) {
		map.put(key, value);
	}

}
