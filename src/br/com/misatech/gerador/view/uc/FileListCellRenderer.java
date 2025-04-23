package br.com.misatech.gerador.view.uc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

// Implementação baseada em http://stackoverflow.com/questions/13336802/how-to-create-jlist-with-icon-and-text

public class FileListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -7799441088157759804L;
	private JLabel label;
	private Color textSelectionColor = Color.BLACK;
	private Color backgroundSelectionColor = new Color(224, 240, 255);
	private Color textNonSelectionColor = Color.BLACK;
	private Color backgroundNonSelectionColor = Color.WHITE;

	public FileListCellRenderer() {

		label = new JLabel();
		label.setOpaque(true);

	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected,
			boolean expanded) {

		try {
			InputStream inputStream = FileListCellRenderer.class
					.getResourceAsStream("/br/com/misatech/gerador/view/images/application_view_columns.png");
			label.setIcon(new ImageIcon(ImageIO.read(inputStream)));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		label.setText(value.toString());

		if (selected) {
			label.setBackground(backgroundSelectionColor);
			label.setForeground(textSelectionColor);
			label.setFont(new Font("Tahoma", Font.BOLD, 11));
		} else {
			label.setBackground(backgroundNonSelectionColor);
			label.setForeground(textNonSelectionColor);
			label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}

		return label;
	}

}
