import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;

public class Window extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Cell> gridCellVect;
	private Mat toShow;
	private JButton applyMonteCarlo;
	private GroupLayout windowLayout;
	private JPanel gridContainer;

	public Window() {
		super();
		gridCellVect = new Vector<Cell>();
		applyMonteCarlo = new JButton("Apply Monte-Carlo");
		applyMonteCarlo.setActionCommand("DoMC");
		applyMonteCarlo.addActionListener(this);

		windowLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(windowLayout);
		windowLayout.setAutoCreateGaps(true);
		windowLayout.setAutoCreateContainerGaps(true);
	}

	public void showMat(Mat toShow) {

		this.toShow = toShow;

		int height = toShow.rows();
		int width = toShow.cols();

		if (!this.isVisible()) {
			gridContainer = new JPanel();
			GridLayout layout = new GridLayout(height, width, 1, 1);
			gridContainer.setLayout(layout);
			gridContainer.setBackground(Color.black);

			int[] buffer = new int[1];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					toShow.get(i, j, buffer);
					Cell cell = new Cell(i, j, this);
					cell.addMouseListener(new OnClickCellListener());
					cell.setSize(10, 10);
					cell.setBackground(new Color(buffer[0], buffer[0], buffer[0]));
					this.gridCellVect.add(cell);
					gridContainer.add(cell);
				}
			}

			windowLayout.setHorizontalGroup(
					windowLayout.createSequentialGroup()
						.addComponent(gridContainer)
						.addComponent(applyMonteCarlo)
			);
			windowLayout.setVerticalGroup(
					windowLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(gridContainer)
						.addComponent(applyMonteCarlo)
			);

			this.pack();
			this.setVisible(true);
		}else{
			int[] buffer = new int[1];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					toShow.get(i, j, buffer);
					Cell cell = this.gridCellVect.get(j + i*width);
					cell.setBackground(new Color(buffer[0], buffer[0], buffer[0]));
				}
			}
			this.repaint();
		}
	}

	public void majMat(Mat newMat) {
		this.toShow = newMat;
	}

	public Mat getToShow() {
		return this.toShow;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("DoMC".equals(e.getActionCommand())) {
			this.showMat(Picross.MonteCarlo(this.toShow, Picross.V4));
		}
	}
}
