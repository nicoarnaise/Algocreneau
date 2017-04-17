import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Picross {
	
	public static Mat createGrid(int height, int width){
		
		int[] buffer = new int[1];
		buffer[0] = 255;
		Mat grid = Mat.ones(height, width, CvType.CV_32SC1);
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				grid.put(i, j, buffer);
			}
		}
		return grid;
		
	}
	
	public static Mat setBlack(Mat orig, int row, int col){
		
		int[] buffer = new int[1];
		buffer[0] = 0;
		orig.put(row, col, buffer);
		
		return orig;
	}
	
	public static Mat setWhite(Mat orig, int row, int col){
		
		int[] buffer = new int[1];
		buffer[0] = 255;
		orig.put(row, col, buffer);
		
		return orig;
	}
	
}
