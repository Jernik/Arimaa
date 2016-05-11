package test_runner;

import java.io.File;
import java.util.ArrayList;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class DynamicSuite extends Suite {
	public DynamicSuite(Class<?> clazz, String packageName) throws InitializationError {
		super(clazz, generateClazzes(packageName));

	}

	protected static Class<?>[] generateClazzes(String packageName) {
		File dir = new File("test/" + packageName);
		String basePath = new File("").getAbsolutePath();
		ArrayList<Class<?>> clazzez = new ArrayList<Class<?>>();
		System.out.println("testing classes in package: " + packageName + " ");
		for (File f : dir.listFiles()) {
			String fileName = f.getName().replace(".java", "");
			Class<?> clazz;
			try {
				clazz = Class.forName(packageName + "." + fileName);
				if (clazz != null) {
					clazzez.add(clazz);
					System.out.println("\t" + clazz.getCanonicalName());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("couldn't find the class: " + f.getAbsolutePath().replace(basePath, ""));
			}
		}
		System.out.println();
		return clazzez.toArray(new Class<?>[clazzez.size()]);
	}

}
