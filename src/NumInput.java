import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class NumInput extends JDialog implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner colNum;
	private JSpinner rowNum;
	
	public NumInput(Component parentComponent, String title){
		GroupLayout diagLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(diagLayout);
		diagLayout.setAutoCreateGaps(true);
		diagLayout.setAutoCreateContainerGaps(true);
		
		SpinnerModel modelCol = new SpinnerNumberModel(10, 0, 100, 1);
		SpinnerModel modelRow = new SpinnerNumberModel(10, 0, 100, 1);

	    colNum = new JSpinner(modelCol);
	    rowNum = new JSpinner(modelRow);
	    JComponent editorCol = new JSpinner.NumberEditor(colNum, "#,##0.###");
	    JComponent editorRow = new JSpinner.NumberEditor(rowNum, "#,##0.###");
	    colNum.setEditor(editorCol);
	    rowNum.setEditor(editorRow);
	    
	    JButton ok = new JButton("OK");
	    ok.setActionCommand("Valid");
	    ok.addActionListener(this);
	    
	    JLabel message = new JLabel(title);
	    JLabel nbRowLbl = new JLabel("Rows number : ");
	    JLabel nbColLbl = new JLabel("Cols number : ");
	    
	    diagLayout.setVerticalGroup(
				diagLayout.createSequentialGroup()
					.addComponent(message)
					.addGap(20)
					.addGroup(diagLayout.createParallelGroup()
							.addComponent(nbRowLbl)
							.addComponent(rowNum)
							)
					.addGroup(diagLayout.createParallelGroup()
							.addComponent(nbColLbl)
							.addComponent(colNum)
							)
					.addComponent(ok)
		);
		diagLayout.setHorizontalGroup(
				diagLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(message)
					.addGap(20)
					.addGroup(diagLayout.createSequentialGroup()
							.addComponent(nbRowLbl)
							.addComponent(rowNum)
							)
					.addGroup(diagLayout.createSequentialGroup()
							.addComponent(nbColLbl)
							.addComponent(colNum)
							)
					.addComponent(ok)
		);
		
		this.setLocationRelativeTo(parentComponent);
		this.setResizable(false);
		this.setModal(true);
		this.pack();
	}

	private int[] getResult() {
		
		int[] result = new int[2];
		result[0] = (Integer) rowNum.getValue();
		result[1] = (Integer) colNum.getValue();
		return result;
	}
	
	public static int[] askGridSize(Component parentComponent, String title){
		NumInput diag = new NumInput(parentComponent, title);
		diag.setVisible(true);
		
		return diag.getResult();
	}

	public void actionPerformed(ActionEvent e) {
		if("Valid".equals(e.getActionCommand())){
			this.setVisible(false);
		}
	}

}
