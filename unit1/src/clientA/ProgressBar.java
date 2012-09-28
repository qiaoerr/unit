/*
 * 进度条类
 */
package clientA;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class ProgressBar extends DefaultTableCellRenderer{

	public JProgressBar pb ;
	public ProgressBar() {
	    pb = new JProgressBar(0, 100);	
		pb.setForeground(Color.green);	
		pb.setStringPainted(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,	boolean isSelected, boolean hasFocus, int row, int colum) {
		Integer i = (Integer) value;
		pb.setValue(i);
//		pb.setString(i+"%");
		return pb;
	}

}
