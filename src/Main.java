import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {
	
	public static void main(String[] args) {
		//chargement de la lib OpenCV
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Window win = new Window();
		
		Mat toShow = Picross.createGrid(10, 10);
		win.showMat(toShow);
	}	
}