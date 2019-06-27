package br.com.misatech.gerador.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class FrmMenu extends JFrame {

	private static final long serialVersionUID = -1531658634942162353L;
	private JPanel pnl;
	private JMenuBar mnbMenu;
	private JDesktopPane desktopPane;
	private JMenu mnSistema;
	private JMenuItem mntmConfiguracao;
	private JMenuItem mntmGerador;
	private JSeparator separator;
	private JMenuItem mntmSair;
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Create the frame.
	 */
	public FrmMenu() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, dimension.width, dimension.height);
		pnl = new JPanel();
		pnl.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnl);
		pnl.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 20, this.getWidth(), this.getHeight());
		desktopPane.setBackground(Color.GRAY);
		pnl.add(desktopPane);
		
		mnbMenu = new JMenuBar();
		mnbMenu.setBounds(0, 0, this.getWidth(), 21);
		pnl.add(mnbMenu);
		
		mnSistema = new JMenu("Sistema");
		mnbMenu.add(mnSistema);
		
		mntmConfiguracao = new JMenuItem("Configuração...");
		mnSistema.add(mntmConfiguracao);
		
		mntmGerador = new JMenuItem("Gerador");
		mnSistema.add(mntmGerador);
		
		separator = new JSeparator();
		mnSistema.add(separator);
		
		mntmSair = new JMenuItem("Sair");
		mnSistema.add(mntmSair);
		
		setVisible(true);
	}

	public JPanel getPnl() {
		return pnl;
	}

	public void setPnl(JPanel pnl) {
		this.pnl = pnl;
	}

	public JMenuBar getMnbMenu() {
		return mnbMenu;
	}

	public void setMnbMenu(JMenuBar mnbMenu) {
		this.mnbMenu = mnbMenu;
	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	public JMenu getMnSistema() {
		return mnSistema;
	}

	public void setMnSistema(JMenu mnSistema) {
		this.mnSistema = mnSistema;
	}

	public JMenuItem getMntmConfiguracao() {
		return mntmConfiguracao;
	}

	public void setMntmConfiguracao(JMenuItem mntmConfiguracao) {
		this.mntmConfiguracao = mntmConfiguracao;
	}

	public JMenuItem getMntmGerador() {
		return mntmGerador;
	}

	public void setMntmGerador(JMenuItem mntmGerador) {
		this.mntmGerador = mntmGerador;
	}

	public JSeparator getSeparator() {
		return separator;
	}

	public void setSeparator(JSeparator separator) {
		this.separator = separator;
	}

	public JMenuItem getMntmSair() {
		return mntmSair;
	}

	public void setMntmSair(JMenuItem mntmSair) {
		this.mntmSair = mntmSair;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

}
