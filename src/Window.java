import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Window extends JFrame implements ActionListener {

	public static final int RESULT      = 0;
	public static final int CREATION    = 1;
	public static final int RECOGNITION = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Cell> gridCellVect;
	private Mat toShow;
	private Mat orig;
	
	private JButton importBtn;
	private JButton validBtn;
	private JLabel percentLbl;
	private JButton applyMonteCarlo;
	private JButton retour;
	private GroupLayout windowLayout;
	private JPanel gridContainer;
	private JFrame menu;
	
	private int type;
	private int life;
	
	private Window child;
	private boolean hasChild = false;

	public Window(final JFrame menu){
		this(menu, RESULT);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        menu.setVisible(true);
		    }
		});
	}
	
	public Window(JFrame menu, int type) {
		super();
		this.menu = menu;
		this.type = type;
		
		gridCellVect = new Vector<Cell>();
		
		if(this.type == CREATION){
			importBtn = new JButton("Import image");
			importBtn.setActionCommand("ImportImg");
			importBtn.addActionListener(this);
			applyMonteCarlo = new JButton("Apply Monte-Carlo");
			applyMonteCarlo.setActionCommand("DoMC");
			applyMonteCarlo.addActionListener(this);
		}
		if(this.type == RECOGNITION){
			percentLbl = new JLabel(" ");
			validBtn = new JButton("Check");
			validBtn.setActionCommand("Validate");
			validBtn.addActionListener(this);
		}
		if(this.type > 0){
			retour = new JButton("Return");
			retour.setActionCommand("Return");
			retour.addActionListener(this);
		}
		
		windowLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(windowLayout);
		windowLayout.setAutoCreateGaps(true);
		windowLayout.setAutoCreateContainerGaps(true);
		this.setLocationRelativeTo(null);
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

			double[] buffer = new double[1];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					buffer = toShow.get(i, j);
					Cell cell = new Cell(i, j, this);
					if(this.type > 0) {
						cell.addMouseListener(new OnClickCellListener());
					}
					cell.setSize(20, 20);
					
					cell.setBackground(new Color((int)buffer[0], (int)buffer[0], (int)buffer[0]));
					this.gridCellVect.add(cell);
					gridContainer.add(cell);
				}
			}
			switch(this.type){
			case CREATION:
				windowLayout.setHorizontalGroup(
						windowLayout.createSequentialGroup()
							.addComponent(gridContainer)
							.addGroup(windowLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(importBtn)
									.addComponent(applyMonteCarlo)
									.addComponent(retour)
							)
				);
				windowLayout.setVerticalGroup(
						windowLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(gridContainer)
							.addGroup(windowLayout.createSequentialGroup()
									.addComponent(importBtn)
									.addComponent(applyMonteCarlo)
									.addComponent(retour)
							)
				);
				break;
			case RECOGNITION:
				this.life = (int)(height*width/20);
				windowLayout.setHorizontalGroup(
						windowLayout.createSequentialGroup()
							.addComponent(gridContainer)
							.addGroup(windowLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(percentLbl)
									.addComponent(validBtn)
									.addComponent(retour)
							)
				);
				windowLayout.setVerticalGroup(
						windowLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(gridContainer)
							.addGroup(windowLayout.createSequentialGroup()
									.addComponent(percentLbl)
									.addComponent(validBtn)
									.addComponent(retour)
							)
				);
				break;
			default:
				windowLayout.setHorizontalGroup(
						windowLayout.createSequentialGroup()
							.addComponent(gridContainer)
				);
				windowLayout.setVerticalGroup(
						windowLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(gridContainer)
				);
				break;	
			}

			this.pack();
			this.setVisible(true);
		}else{
			double[] buffer = new double[1];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					buffer = toShow.get(i, j);
					Cell cell = this.gridCellVect.get(j + i*width);
					cell.setBackground(new Color((int)buffer[0], (int)buffer[0], (int)buffer[0]));
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
	
	public void setChild(Window child){
		this.child = child;
		if(this.getWidth()+child.getWidth()<Toolkit.getDefaultToolkit().getScreenSize().getWidth()){
			child.setLocation((int)(child.getLocation().getX() - child.getWidth()/2), (int)this.getLocation().getY());
			this.setLocation((int)(this.child.getLocation().getX() + child.getWidth()), (int)this.getLocation().getY());
		}
		this.hasChild = true;
	}
	
	public void setOrig(Mat orig){
		this.orig = orig;
	}
	
	public Mat getOrig() {
		return orig;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("ImportImg".equals(e.getActionCommand())) {
			JFileChooser chooser = new JFileChooser("./Grids");
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Images", "jpg", "gif", "png", "bmp", "jpeg");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       Mat importMat = Imgcodecs.imread(chooser.getSelectedFile().getAbsolutePath());
		       Window newWin = new Window(this.menu, CREATION);
		       newWin.showMat(importMat);
		       this.setVisible(false);
		    }
		}
		if ("Validate".equals(e.getActionCommand())){
			int score = Picross.checkScore(this.getToShow(), child.getOrig());
			percentLbl.setText(score+"%");
			if(score>90){
				JOptionPane.showMessageDialog(this, "Congrats !\nYou found the original image at "+score+"% !\n\nHere it is.");
				this.child.showMat(this.child.getOrig());
			}else{
				this.life--;
				if(this.life>0){
					JOptionPane.showMessageDialog(this, "Sorry,\nyou found the original image at only "+score+"%.\n\nYou have now "+life+" tries left.");
				}else{
					JOptionPane.showMessageDialog(this, "Sorry, you didn't find the original image !\n\nHere it is.");
					Window res = new Window(this.menu);
					res.showMat(this.child.getOrig());
					this.setVisible(false);
					this.child.setVisible(false);
				}
				
			}
		}
		if ("DoMC".equals(e.getActionCommand())) {
			if(this.hasChild && child.isVisible()){
				this.child.showMat(Picross.MonteCarlo(this.toShow, Picross.V8));
			}else{
				Window resShow = new Window(this.menu, RESULT);
				resShow.showMat(Picross.MonteCarlo(this.toShow, Picross.V8));
				this.setChild(resShow);
			}
		}
		if ("Return".equals(e.getActionCommand())) {
			if(this.hasChild){
				child.setVisible(false);
			}
			this.setVisible(false);
			this.menu.setVisible(true);
		}
	}
}
