import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;

import org.opencv.core.Mat;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Cell> gridCellPan;
	private GridLayout layout;
	
	public Window(){
		super();
		gridCellPan = new Vector<Cell>();
	}
	
	public void showMat(Mat toShow){
		
		int height = toShow.rows();
		int width = toShow.cols();
		
		layout = new GridLayout(height,width,1,1);
		this.setLayout(layout);
		this.setBackground(Color.black);
		
		int[] buffer = new int[1];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				toShow.get(i, j, buffer);
				Cell cell = new Cell(i,j, toShow, this);
				cell.addMouseListener(new OnClickListener());
				cell.setSize(10, 10);
				cell.setBackground(new Color(buffer[0],buffer[0],buffer[0]));
				gridCellPan.add(cell);
				this.add(cell);
			}
		}
		
		this.pack();
		this.setVisible(true);
	}
	
	public void majMat(Mat newMat){
		for(Cell c : gridCellPan){
			c.setMat(newMat);
		}
	}
}
