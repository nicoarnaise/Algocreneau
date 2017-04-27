import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Menu extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel titleLabel = new JLabel("Welcome to the Picross Casino !");
	private JLabel credits = new JLabel("Game created by Nicolas ARNAISE & Vincent NGUYEN");
	private JButton recon = new JButton("Recognition");
	private JButton crea = new JButton("Creation");
	private JButton quitButton = new JButton("Quit");
	
	private GroupLayout layout = new GroupLayout(this.getContentPane());
	
	public Menu(){
		recon.addActionListener(this);
		crea.addActionListener(this);
		quitButton.addActionListener(this);
		
		this.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(titleLabel)
					.addGap(50)
					.addComponent(recon)
					.addComponent(crea)
					.addComponent(quitButton)
					.addGap(20)
					.addComponent(credits)
		);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(titleLabel)
					.addGap(50)
					.addComponent(recon)
					.addComponent(crea)
					.addComponent(quitButton)
					.addGap(20)
					.addComponent(credits)
		);
		
		this.setTitle("Casino Picross");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == quitButton){
			System.exit(0);
		}
		if(e.getSource() == crea){
			Window win = new Window(this, Window.CREATION);

			int[] gridSize = NumInput.askGridSize(this, "How many rows and columns ?");
			
			Mat toShow = Picross.createGrid(gridSize[0], gridSize[1]);
			win.showMat(toShow);
			this.setVisible(false);
		}
		if(e.getSource() == recon){
			
			JFileChooser chooser = new JFileChooser(new File("./Grids"));
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Images", "jpg", "gif", "png", "bmp", "jpeg");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		      Mat toShow = Imgcodecs.imread(chooser.getSelectedFile().getAbsolutePath());
		       
		      Window originDraw = new Window(this, Window.RESULT);
		      originDraw.showMat(Picross.MonteCarlo(toShow, Picross.V8));
		      originDraw.setOrig(toShow);
			
		      Window drawing = new Window(this, Window.RECOGNITION);
		      Mat toDrow = Picross.createGrid(toShow.rows(), toShow.cols());
		      drawing.showMat(toDrow);
		      drawing.setChild(originDraw);
		      this.setVisible(false);
		    }
		}
	}
	
}
