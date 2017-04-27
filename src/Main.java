import java.lang.reflect.Field;

import org.opencv.core.Core;

public class Main {
	
	public static void main(String[] args) {
		//chargement de la lib OpenCV
		try {

	        System.setProperty("java.library.path", "lib/");

	        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
	        fieldSysPath.setAccessible(true);
	        fieldSysPath.set(null, null);

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        throw new RuntimeException(ex);
	    }
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		Menu me = new Menu();
		me.setVisible(true);
	}	
}