import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opencv.core.Mat;

public class OnClickCellListener implements MouseListener {

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Cell clicked = ((Cell)e.getComponent());
		
		int col = clicked.getCol();
		int row = clicked.getRow();
		Mat mat = clicked.getWindow().getToShow();
		
		double[] buffer = new double[1];
		buffer = mat.get(row, col);
		
		if(buffer[0]>127){
			clicked.getWindow().majMat(Picross.setBlack(mat, row, col));
			clicked.setBackground(new Color(0,0,0));
		}else{
			clicked.getWindow().majMat(Picross.setWhite(mat, row, col));
			clicked.setBackground(new Color(255,255,255));
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
