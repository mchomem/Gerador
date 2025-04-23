package br.com.misatech.gerador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.misatech.gerador.model.InfoProjeto;
import br.com.misatech.gerador.view.FrmMenu;
import br.com.misatech.gerador.view.FrmSplash;

public class CtrMenu implements ActionListener, ComponentListener {

	private FrmSplash frmSplash;
	private FrmMenu frmMenu;

	public CtrMenu() {

		this.iniciarSplash();
		this.ativarTemaNimbus();

		this.frmMenu = new FrmMenu();
		this.frmMenu.setTitle(InfoProjeto.obterNomeEVersao());
		this.frmMenu.getMntmSair().addActionListener(this);
		this.frmMenu.getMntmConfiguracao().addActionListener(this);
		this.frmMenu.getMntmGerador().addActionListener(this);
		this.frmMenu.addComponentListener(this);

	}

	private void ativarTemaNimbus() {

		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);

		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {

			JOptionPane.showMessageDialog(this.frmMenu, "Falha ao carregar thema Nimbus", "Erro", JOptionPane.ERROR);

		} finally {

			// Caso houver uma falha ao carregar o tema Nimbus, carrega o tema Swing do java
			// puro.
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);

		}

	}

	private void iniciarSplash() {

		this.frmSplash = new FrmSplash();
		this.frmSplash.getLblValorVersao().setText(InfoProjeto.obterVersao());
		this.frmSplash.getLblNomeProduto().setText(InfoProjeto.obterNomeProduto());

		this.encerrarSplashTemporizado(5);

		// Enquanto o splash ainda é visível
		while (this.frmSplash.isVisible()) {

			/*
			 * Não executa nada dentro do while apenas impede que o fluxo do programa
			 * prossiga enquanto o formulário de splah continuar sendo visível.
			 */
			;

		}

	}

	/**
	 * Encerra o formulário de splash após um tempo determinado em segundos.
	 * 
	 * @param segundos - o tempo em segundos.
	 */
	private void encerrarSplashTemporizado(int segundos) {

		// Multiplicação para transformar o tempo de segundo para milisegundo.
		segundos *= 1000;

		// Declarar novo evento.
		ActionListener tarefa = new ActionListener() {

			// Determinando a ação do evento.
			public void actionPerformed(ActionEvent e) {

				// Encerrar o formulário splash
				frmSplash.dispose();

			}

		};

		// Disparar a tarefa após x tempo.
		new Timer(segundos, tarefa).start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.frmMenu.getMntmConfiguracao()) {

			// Chamada do formúlário atraves do Controlador.
			new CtrConfiguracao(this.frmMenu);

		} else if (e.getSource() == this.frmMenu.getMntmGerador()) {

			new CtrGerador(this.frmMenu);

		} else if (e.getSource() == this.frmMenu.getMntmSair()) {

			this.finalizar();

		}

	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {

		this.frmMenu.getMnbMenu().setSize(this.frmMenu.getWidth(), 21);
		this.frmMenu.getDesktopPane().setSize(this.frmMenu.getWidth(), this.frmMenu.getHeight());

	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	private void finalizar() {

		System.exit(0);

	}

}
