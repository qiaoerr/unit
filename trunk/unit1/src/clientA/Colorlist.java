/*
 * 这是设置音乐列表背景色的类
 */
package clientA;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings({ "serial" })
public class Colorlist extends JLabel implements ListCellRenderer {

	private Color[] colors = new Color[] { Color.PINK, Color.GREEN };

	public Colorlist() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		setText(value.toString());

		Color background;
		Color foreground;
		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert()
				&& dropLocation.getIndex() == index) {
			background = Color.GREEN;
			foreground = Color.GREEN;
		} else if (isSelected) {
			background = Color.YELLOW;
			foreground = Color.WHITE;
		} else {
			background = colors[index % 2];
			foreground = Color.BLACK;
		}
		setBackground(background);
		setForeground(foreground);
		return this;
	}

}
