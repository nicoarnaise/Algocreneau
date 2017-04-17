import javax.swing.JPanel;

import org.opencv.core.Mat;

public class Cell extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private Mat mat;
	private Window window;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public Mat getMat() {
		return mat;
	}
	public void setMat(Mat mat) {
		this.mat = mat;
	}
	public Window getWindow() {
		return window;
	}
	public void setWindow(Window window) {
		this.window = window;
	}
	public Cell(int row, int col, Mat mat, Window window) {
		super();
		this.row = row;
		this.col = col;
		this.mat = mat;
		this.window = window;
	}
}
