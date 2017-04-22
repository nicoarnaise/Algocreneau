import javax.swing.JPanel;

public class Cell extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
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
	public Window getWindow() {
		return window;
	}
	public void setWindow(Window window) {
		this.window = window;
	}
	public Cell(int row, int col, Window window) {
		super();
		this.row = row;
		this.col = col;
		this.window = window;
	}
}
