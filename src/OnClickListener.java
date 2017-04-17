import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opencv.core.Mat;

public class OnClickListener implements MouseListener {

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Cell clicked = ((Cell)e.getComponent());
		
		int col = clicked.getCol();
		int row = clicked.getRow();
		Mat mat = clicked.getMat();
		
		int[] buffer = new int[1];
		mat.get(row, col, buffer);
		
		switch (buffer[0]) {
		case 255:
			clicked.getWindow().majMat(Picross.setBlack(mat, row, col));
			clicked.setBackground(new Color(0,0,0));
			break;
		case 0:
			clicked.getWindow().majMat(Picross.setWhite(mat, row, col));
			clicked.setBackground(new Color(255,255,255));
			break;
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
