import org.opencv.core.Core;

public class Main {
	
	public static void main(String[] args) {
		//chargement de la lib OpenCV
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

		Menu me = new Menu();
		me.setVisible(true);
	}	
}