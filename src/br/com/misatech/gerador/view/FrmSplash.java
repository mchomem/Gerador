package br.com.misatech.gerador.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FrmSplash extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel pnl;
	private JLabel lblNomeProduto;
	private JLabel lblVersao;
	private JLabel lblValorVersao;
	private JLabel lblcCopyrigth;
	private JLabel lblcCopyrigth01;
	private JLabel lblImagemSplash;

	/**
	 * Create the frame.
	 */
	public FrmSplash() {
		
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 345);
		this.setLocation((this.dimension.width - this.getWidth()) / 2, (this.dimension.height - this.getHeight()) / 2);
		pnl = new JPanel();
		pnl.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnl);
		pnl.setLayout(null);
		
		lblNomeProduto = new JLabel("Nome Produto");
		lblNomeProduto.setForeground(new Color(212, 0, 0));
		lblNomeProduto.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeProduto.setFont(new Font("Impact", Font.PLAIN, 60));
		lblNomeProduto.setBounds(0, 38, 500, 64);
		pnl.add(lblNomeProduto);
		
		lblVersao = new JLabel("Vers\u00E3o:");
		lblVersao.setBounds(10, 280, 46, 14);
		pnl.add(lblVersao);
		
		lblValorVersao = new JLabel("0.00.000");
		lblValorVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblValorVersao.setBounds(81, 280, 96, 14);
		pnl.add(lblValorVersao);
		
		lblcCopyrigth = new JLabel("(c) Copyright, 2014. Todos os direitos reservados. Este software  \u00E9 marca registrada de Misael da C. Homem.");
		lblcCopyrigth.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblcCopyrigth.setBounds(10, 305, 480, 14);
		pnl.add(lblcCopyrigth);
		
		lblcCopyrigth01 = new JLabel("Oracle e Java s\u00E3o marcas registradas da Oracle e/ou suas afiliadas.");
		lblcCopyrigth01.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblcCopyrigth01.setBounds(10, 320, 480, 14);
		pnl.add(lblcCopyrigth01);
		
		lblImagemSplash = new JLabel("");
		lblImagemSplash.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblImagemSplash.setIcon(new ImageIcon(FrmSplash.class.getResource("/br/com/misatech/gerador/view/images/Splash.png")));
		lblImagemSplash.setBounds(0, 0, 500, 345);
		pnl.add(lblImagemSplash);
		
		setVisible(true);
		
	}

	public JPanel getPnl() {
		return pnl;
	}

	public void setPnl(JPanel pnl) {
		this.pnl = pnl;
	}

	public JLabel getLblNomeProduto() {
		return lblNomeProduto;
	}

	public void setLblNomeProduto(JLabel lblNomeProduto) {
		this.lblNomeProduto = lblNomeProduto;
	}

	public JLabel getLblVersao() {
		return lblVersao;
	}

	public void setLblVersao(JLabel lblVersao) {
		this.lblVersao = lblVersao;
	}

	public JLabel getLblValorVersao() {
		return lblValorVersao;
	}

	public void setLblValorVersao(JLabel lblValorVersao) {
		this.lblValorVersao = lblValorVersao;
	}

	public JLabel getLblcCopyrigth() {
		return lblcCopyrigth;
	}

	public void setLblcCopyrigth(JLabel lblcCopyrigth) {
		this.lblcCopyrigth = lblcCopyrigth;
	}

	public JLabel getLblcCopyrigth01() {
		return lblcCopyrigth01;
	}

	public void setLblcCopyrigth01(JLabel lblcCopyrigth01) {
		this.lblcCopyrigth01 = lblcCopyrigth01;
	}

	public JLabel getLblImagemSplash() {
		return lblImagemSplash;
	}

	public void setLblImagemSplash(JLabel lblImagemSplash) {
		this.lblImagemSplash = lblImagemSplash;
	}


}
