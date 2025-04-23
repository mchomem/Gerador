package br.com.misatech.gerador.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.com.misatech.gerador.model.InfoProjeto;
import br.com.misatech.gerador.model.SessaoGerador;
import br.com.misatech.gerador.model.Utils;
import br.com.misatech.gerador.model.bo.BoArquivo;
import br.com.misatech.gerador.model.bo.BoMetadado;
import br.com.misatech.gerador.model.dao.Dao;
import br.com.misatech.gerador.model.dao.DaoArquivoConfiguracao;
import br.com.misatech.gerador.model.en.EnSGBDR;
import br.com.misatech.gerador.model.en.EnTecnologia;
import br.com.misatech.gerador.model.vo.VoArquivoConfiguracao;
import br.com.misatech.gerador.model.vo.VoMetadado;
import br.com.misatech.gerador.model.vo.VoTabela;
import br.com.misatech.gerador.view.FrmGerador;
import br.com.misatech.gerador.view.FrmMenu;

public class CtrGerador implements ActionListener, KeyListener, InternalFrameListener {

	private FrmGerador frmGerador;
	private Dao dao;
	private VoMetadado voMetadado;
	private BoMetadado boMetadado;
	private BoArquivo boArquivo;

	private DaoArquivoConfiguracao daoArquivoConfiguracao;
	private List<VoArquivoConfiguracao> voArquivosConfiguracoes;

	private String caminhoArquivo;
	private String caminhoArquivoView;
	private String caminhoArquivoController;
	private String caminhoArquivoModel;

	public CtrGerador(FrmMenu frmMenu) {

		this.voMetadado = new VoMetadado();
		this.boMetadado = new BoMetadado();
		this.boArquivo = new BoArquivo();
		this.daoArquivoConfiguracao = new DaoArquivoConfiguracao();
		this.frmGerador = new FrmGerador();

		this.frmGerador.getRdbtnInformado().addActionListener(this);
		this.frmGerador.getRdbtnPerfil().addActionListener(this);
		this.frmGerador.getRdbtnJava().addActionListener(this);
		this.frmGerador.getRdbtnCSnet().addActionListener(this);
		this.frmGerador.getBtnTestar().addActionListener(this);
		this.frmGerador.getBtnConectar().addActionListener(this);
		this.frmGerador.getBtnDesconectar().addActionListener(this);
		this.frmGerador.getBtnDestinoFontes().addActionListener(this);
		this.frmGerador.getBtnGerar().addActionListener(this);
		this.frmGerador.getBtnLimparLog().addActionListener(this);
		this.frmGerador.getBtnTodosDireita().addActionListener(this);
		this.frmGerador.getBtnTodosEsquerda().addActionListener(this);
		this.frmGerador.getBtnSelDireita().addActionListener(this);
		this.frmGerador.getBtnSelEsquerda().addActionListener(this);
		this.frmGerador.getBtnAjudaPacote().addActionListener(this);
		this.frmGerador.getBtnConfigPerfil().addActionListener(this);

		this.frmGerador.getCmbPerfil().addKeyListener(this);

		this.frmGerador.addInternalFrameListener(this);

		this.inicializarForm();
		this.esconderControlePerfil(false);

		frmMenu.getDesktopPane().add(this.frmGerador);

		// 04/06/2015: Correção do foco para o JInternalFrame.
		frmMenu.getDesktopPane().selectFrame(true);

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {

		// 28/07/2015 - MISAEL - substitução para um método melhor escrito.
		// this.popularCombo();
		this.popularComoboPerfil();

		this.popularComboSgbdr();

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		this.encerrarAplicacao();
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		// Foi pressionado o botão na caixa de texto de perfil?
		if ((e.getSource() == this.frmGerador.getCmbPerfil())) {
			// O código da tecla é 10 (ENTER)
			if (e.getKeyCode() == 10) {

				if (this.verificarDados()) {
					return;
				}

				// Executar a rotina de conexão no banco.
				this.conectar();

			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.frmGerador.getRdbtnInformado()) {

			if (this.frmGerador.getRdbtnInformado().isSelected()) {
				this.esconderControleInformado(true);
				this.esconderControlePerfil(false);
			}

		} else if (e.getSource() == this.frmGerador.getRdbtnPerfil()) {

			if (this.frmGerador.getRdbtnPerfil().isSelected()) {
				this.esconderControlePerfil(true);
				this.esconderControleInformado(false);
			}

		} else if (e.getSource() == this.frmGerador.getRdbtnJava()) {

			this.frmGerador.getLblPacote().setText("Package");
			this.frmGerador.getBtnAjudaPacote().setToolTipText("Exemplo de Package");
			SessaoGerador.enTecnologiaEscolhida = EnTecnologia.JAVA;

		} else if (e.getSource() == this.frmGerador.getRdbtnCSnet()) {

			this.frmGerador.getLblPacote().setText("Namespace");
			this.frmGerador.getBtnAjudaPacote().setToolTipText("Exemplo de Namespace");
			SessaoGerador.enTecnologiaEscolhida = EnTecnologia.CSNET;

		} else if (e.getSource() == this.frmGerador.getBtnTodosDireita()) {

			this.enviarTodasTabelasParaDireita();

		} else if (e.getSource() == this.frmGerador.getBtnTodosEsquerda()) {

			this.enviarTodasTabelasParaEsquerda();

		} else if (e.getSource() == this.frmGerador.getBtnSelDireita()) {

			this.enviarTodasSelEsquerdaParaDireita();

		} else if (e.getSource() == this.frmGerador.getBtnSelEsquerda()) {

			this.enviarTodasSelDireitaParaEsquerda();

		} else if (e.getSource() == this.frmGerador.getBtnConectar()) {

			if (this.verificarDados()) {
				return;
			}

			this.conectar();

		} else if (e.getSource() == this.frmGerador.getBtnDesconectar()) {

			this.desconectar();

		} else if (e.getSource() == this.frmGerador.getBtnTestar()) {

			if (this.verificarDados()) {
				return;
			}

			this.testarConexao();
			this.desconectar();
			this.verificaListaLog();

		} else if (e.getSource() == this.frmGerador.getBtnLimparLog()) {

			this.frmGerador.getDlModelo().removeAllElements();
			this.verificaListaLog();

		} else if (e.getSource() == this.frmGerador.getBtnAjudaPacote()) {

			if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {
				this.preencherExemploPacoteJava();
			} else if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {
				this.preencherExemploNamespaceCSNet();
			}

		} else if (e.getSource() == this.frmGerador.getBtnConfigPerfil()) {

			// TODO melhorar isto, pois está muito feio!

			// MISAEL 28/08/2015
			// Mas que gambiarra! Atenção, não alterar a estrutura de navegação (troca de
			// mensagens entre as classes)
			// para não ter problemas de funcionamento, ou mudar a forma como os formulários
			// são chamadas pelo FrmMenu.

			// Toda as classes formulário deste projeto, precisam da classe FrmMenu para
			// serem adicionadas ao
			// container JDesktop (erro de projeto!), apesar desta classe receber uma
			// instância do FrmMenu no argumento do
			// seu construtor, não quis subir o seu escopo para um atributo desta classe
			// FrmGerador, sendo desta forma
			// deve ser feito uma navegabilidade partir deste formulário até chegar até
			// chegar a classe instanciada
			// FrmMenu e copia-la para uma variável local, afim de aproveitar a sua
			// estrutura instanciada.
			FrmMenu frmMenu = (FrmMenu) this.frmGerador.getParent().getParent().getParent().getParent().getParent();

			new CtrConfiguracao(frmMenu);

		} else if (e.getSource() == this.frmGerador.getBtnDestinoFontes()) {

			// MISAEL 10/08/2015 - implementação pacotes para os fontes: alteração no método
			// selecionarDestino não retorna mais String para alimentar caminhoArquivo.
			this.selecionarDestino();

		} else if (e.getSource() == this.frmGerador.getBtnGerar()) {

			// Caminho dos fontes foi informado?
			if (this.frmGerador.getTxtDestinoFontes().getText().length() == 0) {

				JOptionPane.showMessageDialog(this.frmGerador, "Informe o destino dos fontes.",
						InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
				return;

			}

			try {

				// MISAEL 10/08/2015 - implementação pacotes para os fontes: chamada do método
				// que cria pacote informado.
				this.criarCaminhoPacote();

				// MISAEL - 01/08/2015 - removido deste ponto o método para gerar fonte, antes
				// o método para catpurar as tabelas selecionadas pelo usuário é adicionado.

				// this.gerarCodigoFonte(this.frmGerador.getChckbxGerarComentario().isSelected());
				this.capturarTabelasSelecionadas();

			} catch (Exception ex) {

				JOptionPane.showMessageDialog(this.frmGerador, "Falha ao gerar os fontes.", "",
						JOptionPane.ERROR_MESSAGE);

			}

		}

	}

	private void inicializarForm() {

		this.frmGerador.getTabsGerador().setEnabledAt(1, false);
		this.frmGerador.getBtnTestar().setEnabled(true);
		this.frmGerador.getBtnConectar().setEnabled(true);
		this.frmGerador.getBtnDesconectar().setEnabled(false);
		this.verificaListaLog();

		this.frmGerador.getCmbPerfil().setEnabled(true);
		this.frmGerador.getRdbtnPerfil().setEnabled(true);
		this.frmGerador.getRdbtnInformado().setEnabled(true);
		this.frmGerador.getBtnConfigPerfil().setEnabled(true);

		this.frmGerador.getChckbxClassesDeNegocio().setSelected(false);
		this.frmGerador.getChckbxClassesDao().setSelected(false);
		this.frmGerador.getChckbxGerarComentario().setSelected(false);
		this.frmGerador.getRdbtnJava().setSelected(true);
		this.frmGerador.getChkMvc().setSelected(false);
		this.frmGerador.getTxtPacote().setText("");
		this.frmGerador.getTxtDestinoFontes().setText("");

		SessaoGerador.enTecnologiaEscolhida = EnTecnologia.JAVA;

	}

	private void preencherExemploPacoteJava() {

		String nomeProjeto = SessaoGerador.nomeProjeto.replace("_", "");
		this.frmGerador.getTxtPacote().setText("br.com.empresa." + nomeProjeto);

	}

	private void preencherExemploNamespaceCSNet() {

		String nomeProjeto = SessaoGerador.nomeProjeto.replace("_", "");
		this.frmGerador.getTxtPacote().setText(
				"Br.Com.Empresa." + String.valueOf(nomeProjeto.charAt(0)).toUpperCase() + nomeProjeto.substring(1));

	}

	// MISAEL 28/07/2015 - método removido por estar mau escrito.
	/*
	 * private void popularCombo() {
	 * 
	 * try { DefaultComboBoxModel<VoArquivoConfiguracao> cmbModelo =
	 * ((DefaultComboBoxModel) this.frmGerador.getCmbPerfil().getModel());
	 * cmbModelo.removeAllElements(); cmbModelo.addElement(null);
	 * 
	 * this.voArquivosConfiguracoes = this.daoArquivoConfiguracao.consultar();
	 * 
	 * for(int linha = 0; linha < this.voArquivosConfiguracoes.size(); linha++) {
	 * 
	 * VoArquivoConfiguracao voArquivoConfiguracao =
	 * this.voArquivosConfiguracoes.get(linha);
	 * cmbModelo.addElement(voArquivoConfiguracao);
	 * 
	 * } } catch(IOException e) { JOptionPane.showMessageDialog(this.frmGerador ,
	 * "Falha ao carregar a combo Perfil" , InfoProjeto.obterNomeEVersao() ,
	 * JOptionPane.ERROR_MESSAGE); } }
	 */

	// MISAEL 28/07/2015 - método subsitudo ao método popularCombo, por este melhor
	// escrito.
	private void popularComoboPerfil() {

		try {

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			this.voArquivosConfiguracoes = this.daoArquivoConfiguracao.consultar();

			for (VoArquivoConfiguracao arquivoConfiguracao : this.voArquivosConfiguracoes) {
				model.addElement(arquivoConfiguracao.toString());
			}

			this.frmGerador.getCmbPerfil().setModel(model);

		} catch (IOException e) {

			JOptionPane.showMessageDialog(this.frmGerador, "Falha ao carregar a combo Perfil.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.ERROR_MESSAGE);

		}
	}

	private void popularComboSgbdr() {

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement(EnSGBDR.MYSQL.toString());
		model.addElement(EnSGBDR.POSTGRESQL.toString());

		this.frmGerador.getCmbSgbdr().setModel(model);

	}

	// MISAEL - 01/08/2015 - Inserido novo método para carregar a JLista com o nome
	// dos objetos do banco.
	private void carregarListaEsquerdaTabelas() {

		DefaultListModel<String> listaTabDisponivel = new DefaultListModel<String>();
		this.frmGerador.getListaTabDisponivel().setModel(listaTabDisponivel);

		DefaultListModel<String> listaTabSelecionadas = new DefaultListModel<String>();
		this.frmGerador.getListaTabSelecionadas().setModel(listaTabSelecionadas);

		for (VoTabela tabela : this.voMetadado.getTabelas()) {
			listaTabDisponivel.addElement(tabela.getNomeTabela().toUpperCase());
		}

		this.sortElements(listaTabDisponivel);

	}

	// MISAEL - 01/08/2015
	private void enviarTodasTabelasParaDireita() {

		DefaultListModel<String> listaTabDisponivel = (DefaultListModel<String>) this.frmGerador.getListaTabDisponivel()
				.getModel();
		DefaultListModel<String> listaTabSelecionadas = new DefaultListModel<String>();

		// Tem elemento selecionado?
		if (listaTabDisponivel.isEmpty()) {
			return;
		}

		// Copia os elementos do modelo da esquerda para o modelo da direita.
		for (int i = 0; i < listaTabDisponivel.getSize(); i++) {
			listaTabSelecionadas.addElement(listaTabDisponivel.getElementAt(i));
		}

		// Copia adicional do modelo da direita para um modelo backup para que o modelo
		// da direita não seja perdido.
		DefaultListModel<String> listaTabSelecionadasCopia = (DefaultListModel<String>) this.frmGerador
				.getListaTabSelecionadas().getModel();

		// Copia os elementos da copia adicional para o modelo da direita.
		for (int i = 0; i < listaTabSelecionadasCopia.getSize(); i++) {
			listaTabSelecionadas.addElement(listaTabSelecionadasCopia.getElementAt(i));
		}

		this.sortElements(listaTabSelecionadas);

		this.frmGerador.getListaTabSelecionadas().setModel(listaTabSelecionadas);
		listaTabDisponivel.removeAllElements();

	}

	// MISAEL - 01/08/2015
	private void enviarTodasSelEsquerdaParaDireita() {

		DefaultListModel<String> listaTabDisponivel = (DefaultListModel<String>) this.frmGerador.getListaTabDisponivel()
				.getModel();
		DefaultListModel<String> listaTabSelecionadas = new DefaultListModel<String>();
		List<String> tabelasSelecionadas = this.frmGerador.getListaTabDisponivel().getSelectedValuesList();

		// Tem elemento selecionado?
		if (tabelasSelecionadas.size() == 0) {
			return;
		}

		// Adiciona os selecionados no modelo da direita.
		for (String value : tabelasSelecionadas) {
			listaTabSelecionadas.addElement(value);
		}

		// Remove os selecionados do modelo da esquerda.
		for (String value : tabelasSelecionadas) {
			listaTabDisponivel.removeElement(value);
		}

		// Obtem o modelo com possíveis elementos.
		DefaultListModel<String> listaTabSelecionadasCopia = (DefaultListModel<String>) this.frmGerador
				.getListaTabSelecionadas().getModel();

		// adiciona os novos com os existentes na lista da direita.
		for (int i = 0; i < listaTabSelecionadasCopia.getSize(); i++) {
			listaTabSelecionadas.addElement(listaTabSelecionadasCopia.getElementAt(i));
		}

		this.sortElements(listaTabSelecionadas);
		this.frmGerador.getListaTabSelecionadas().setModel(listaTabSelecionadas);

	}

	// MISAEL - 01/08/2015
	private void enviarTodasSelDireitaParaEsquerda() {

		DefaultListModel<String> listaTabSelecionadas = (DefaultListModel<String>) this.frmGerador
				.getListaTabSelecionadas().getModel();
		DefaultListModel<String> listaTabDisponivel = new DefaultListModel<String>();
		List<String> tabelasSelecionadas = this.frmGerador.getListaTabSelecionadas().getSelectedValuesList();

		// Tem elemento selecionado?
		if (tabelasSelecionadas.size() == 0) {
			return;
		}

		// Adiciona os selecionados no modelo da direita.
		for (String value : tabelasSelecionadas) {
			listaTabDisponivel.addElement(value);
		}

		// Remove os selecionados do modelo da esquerda.
		for (String value : tabelasSelecionadas) {
			listaTabSelecionadas.removeElement(value);
		}

		// Obtem o modelo com possíveis elementos.
		DefaultListModel<String> listaTabDisponivelCopia = (DefaultListModel<String>) this.frmGerador
				.getListaTabDisponivel().getModel();

		// adiciona os novos com os existentes na lista da direita.
		for (int i = 0; i < listaTabDisponivelCopia.getSize(); i++) {
			listaTabDisponivel.addElement(listaTabDisponivelCopia.getElementAt(i));
		}

		this.sortElements(listaTabDisponivel);
		this.frmGerador.getListaTabDisponivel().setModel(listaTabDisponivel);

	}

	// MISAEL - 01/08/2015
	private void enviarTodasTabelasParaEsquerda() {

		DefaultListModel<String> listaTabSelecionadas = (DefaultListModel<String>) this.frmGerador
				.getListaTabSelecionadas().getModel();
		DefaultListModel<String> listaTabDisponivel = new DefaultListModel<String>();

		if (listaTabSelecionadas.isEmpty()) {
			return;
		}

		for (int i = 0; i < listaTabSelecionadas.getSize(); i++) {
			listaTabDisponivel.addElement(listaTabSelecionadas.getElementAt(i));
		}

		DefaultListModel<String> listaTabDisponivelCopia = (DefaultListModel<String>) this.frmGerador
				.getListaTabDisponivel().getModel();

		for (int i = 0; i < listaTabDisponivelCopia.getSize(); i++) {
			listaTabDisponivel.addElement(listaTabDisponivelCopia.getElementAt(i));
		}

		this.sortElements(listaTabDisponivel);
		this.frmGerador.getListaTabDisponivel().setModel(listaTabDisponivel);
		listaTabSelecionadas.removeAllElements();

	}

	// MISAEL - 01/08/2015 - método para ordenar elementos da JList.
	private void sortElements(DefaultListModel<String> list) {

		// Ordena os elementos em ordem alfabética.
		ArrayList<String> arrayList = Collections.list(list.elements());
		Collections.sort(arrayList);
		list.clear();

		// Recarrega o modelo de lista.
		for (String o : arrayList) {
			list.addElement(o);
		}

	}

	private void conectar() {

		// TODO atenção no dia 30/08/2015 - houve exceção de ponteiro nulo, foi
		// corrigido validando o getSelectedItem (não aconteceia antes), este erro
		// começou a acontecer após a implementação
		// da chamada da tela de configuração de perfi aqui na tela do Gerador. Caso
		// haja novo erro, remover a chamada da tela, e o botão com o ícone de
		// engrenagem.
		String perfil = (this.frmGerador.getCmbPerfil().getSelectedItem() == null ? ""
				: this.frmGerador.getCmbPerfil().getSelectedItem().toString());
		String sgbdr = (this.frmGerador.getCmbSgbdr().getSelectedItem() == null ? ""
				: this.frmGerador.getCmbSgbdr().getSelectedItem().toString());
		String driver = this.frmGerador.getTxtDriver().getText();
		String url = this.frmGerador.getTxtUrl().getText();
		String usuario = this.frmGerador.getTxtUsuario().getText();
		String senha = this.obterSenha();

		try {

			if (this.frmGerador.getRdbtnInformado().isSelected()) {

				this.dao = new Dao(driver, url, usuario, senha, sgbdr);

			} else if (this.frmGerador.getRdbtnPerfil().isSelected()) {

				this.dao = new Dao(perfil);

			}

			this.lerMetadados(this.dao.conectarBD());

			this.frmGerador.getPnlTabelas()
					.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
							"Tabelas do banco: " + this.voMetadado.getNomeBanco().toUpperCase(), TitledBorder.LEADING,
							TitledBorder.TOP, null, new Color(0, 0, 255)));

			// adiciona valor no log
			this.frmGerador.getDlModelo().addElement("[" + new Utils().getDataHora() + "]: Conectado na base "
					+ this.voMetadado.getNomeBanco().toUpperCase() + " servidor: " + this.voMetadado.getNomeSGBD());

			// MISAEL - 11/10/2015 - o nome do projeto é o nome do banco de dados.
			SessaoGerador.nomeProjeto = this.voMetadado.getNomeBanco().toLowerCase();

			// 09/07/2015 - O botão de teste deve ser desativado também.
			this.frmGerador.getBtnTestar().setEnabled(false);
			// Desativa o botão conectar
			this.frmGerador.getBtnConectar().setEnabled(false);
			// Ativa o botão desconectar
			this.frmGerador.getBtnDesconectar().setEnabled(true);
			// Ativa a aba
			this.frmGerador.getTabsGerador().setEnabledAt(1, true);

			// MISAEL - 14/10/2015 - coloca o foco sobre a 2º abra (indice 1).
			this.frmGerador.getTabsGerador().setSelectedIndex(1);

			// MISAEL - 29/07/2015 - controles devem ser desativados ao conectar.
			this.frmGerador.getCmbPerfil().setEnabled(false);
			this.frmGerador.getRdbtnPerfil().setEnabled(false);
			this.frmGerador.getRdbtnInformado().setEnabled(false);
			// MISAEL - 11/10/2015 - controle também deve ser desativado.
			this.frmGerador.getBtnConfigPerfil().setEnabled(false);

			// MISAEL - 01/08/2015 - método para alimentar a JTree foi substituido pelo da
			// JList.
			// this.montarArvore();
			this.carregarListaEsquerdaTabelas();

		} catch (ClassNotFoundException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
		} catch (SQLException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
			;
		} catch (FileNotFoundException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
		} catch (IOException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
		}

		// MISAEL - 30/07/2015 - a chamada de verificação de log deve ser feita neste
		// ponto do código.
		this.verificaListaLog();

	}

	private void desconectar() {

		try {

			// MISAEL - 04/08/2015 - inserido teste no getNomeBanco caso não tenha
			// consiguido conectar por falha (Via Teste), não recupera o nome da base.
			String nomeBase = (this.voMetadado.getNomeBanco() == null ? ""
					: this.voMetadado.getNomeBanco().toUpperCase());

			dao.desconectarBD();

			// MISAEL - 04/08/2015 - caso tenha ocorrido falha ao conectar (via Teste) e não
			// houver nome da base, não adiciona no log.
			if (nomeBase.length() != 0) {
				// Adiciona valor no log
				this.frmGerador.getDlModelo()
						.addElement("[" + new Utils().getDataHora() + "]: Desconectado da base " + nomeBase);
			}

			/*
			 * // MISAEL - 09/07/2015 - O botão Testar deve ser reativado novamente.
			 * this.frmGerador.getBtnTestar().setEnabled(true); // Ativa o botão de
			 * conectar. this.frmGerador.getBtnConectar().setEnabled(true); // Desatova o
			 * botão desconectar. this.frmGerador.getBtnDesconectar().setEnabled(false); //
			 * Desativa a aba this.frmGerador.getTabsGerador().setEnabledAt(1, false);
			 * 
			 * // MISAEL - 29/07/2015 - controles devem ser ativados ao desconectar.
			 * this.frmGerador.getCmbPerfil().setEnabled(true);
			 * this.frmGerador.getRdbtnPerfil().setEnabled(true);
			 * this.frmGerador.getRdbtnInformado().setEnabled(true); // MISAEL - 11/10/2015
			 * - controle deve ser ativado.
			 * this.frmGerador.getBtnConfigPerfil().setEnabled(true);
			 */

			this.inicializarForm();
			this.verificaListaLog();

			nomeBase = "";

		} catch (SQLException e) {

			this.frmGerador.getDlModelo().addElement("Falha ao desconectar da base. Motivo: " + e.getMessage());

		}

	}

	// 28/10/2015 - Método criado a parte para não misturar responsabilidade que já
	// existem em conectar();
	private void testarConexao() {

		// TODO atenção no dia 30/08/2015 - houve exceção de ponteiro nulo, foi
		// corrigido validando o getSelectedItem (não aconteceia antes), este erro
		// começou a acontecer após a implementação
		// da chamada da tela de configuração de perfi aqui na tela do Gerador. Caso
		// haja novo erro, remover a chamada da tela, e o botão com o ícone de
		// engrenagem.
		String perfil = (this.frmGerador.getCmbPerfil().getSelectedItem() == null ? ""
				: this.frmGerador.getCmbPerfil().getSelectedItem().toString());
		String sgbdr = (this.frmGerador.getCmbSgbdr().getSelectedItem() == null ? ""
				: this.frmGerador.getCmbSgbdr().getSelectedItem().toString());
		String driver = this.frmGerador.getTxtDriver().getText();
		String url = this.frmGerador.getTxtUrl().getText();
		String usuario = this.frmGerador.getTxtUsuario().getText();
		String senha = this.obterSenha();

		try {

			if (this.frmGerador.getRdbtnInformado().isSelected()) {

				this.dao = new Dao(driver, url, usuario, senha, sgbdr);

			} else if (this.frmGerador.getRdbtnPerfil().isSelected()) {

				this.dao = new Dao(perfil);

			}

			this.lerMetadados(this.dao.conectarBD());

			// adiciona valor no log
			this.frmGerador.getDlModelo().addElement("[" + new Utils().getDataHora() + "]: Conectado na base "
					+ this.voMetadado.getNomeBanco().toUpperCase() + " servidor: " + this.voMetadado.getNomeSGBD());

		} catch (ClassNotFoundException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
		} catch (SQLException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
			;
		} catch (FileNotFoundException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
		} catch (IOException ex) {
			this.frmGerador.getDlModelo().addElement("Falha ao conectar na base. Motivo: " + ex.getMessage());
		}

		// MISAEL - 30/07/2015 - a chamada de verificação de log deve ser feita neste
		// ponto do código.
		this.verificaListaLog();

	}

	private void verificaListaLog() {

		this.frmGerador.getBtnLimparLog().setEnabled(this.frmGerador.getDlModelo().isEmpty() ? false : true);

	}

	private void esconderControleInformado(boolean esconder) {

		this.frmGerador.getLblSgbdr().setVisible(esconder);
		this.frmGerador.getCmbSgbdr().setVisible(esconder);
		this.frmGerador.getCmbSgbdr().setSelectedItem(null);

		this.frmGerador.getLblUrl().setVisible(esconder);
		this.frmGerador.getTxtUrl().setVisible(esconder);
		this.frmGerador.getTxtUrl().setText("");

		this.frmGerador.getLblDriver().setVisible(esconder);
		this.frmGerador.getTxtDriver().setVisible(esconder);
		this.frmGerador.getTxtDriver().setText("");

		this.frmGerador.getLblUsuario().setVisible(esconder);
		this.frmGerador.getTxtUsuario().setVisible(esconder);
		this.frmGerador.getTxtUsuario().setText("");

		this.frmGerador.getLblSenha().setVisible(esconder);
		this.frmGerador.getPwsSenha().setVisible(esconder);
		this.frmGerador.getPwsSenha().setText("");

	}

	private void esconderControlePerfil(boolean esconder) {

		this.frmGerador.getLblPerfil().setVisible(esconder);
		this.frmGerador.getCmbPerfil().setVisible(esconder);
		this.frmGerador.getCmbPerfil().setSelectedItem(null);
		this.frmGerador.getBtnConfigPerfil().setVisible(esconder);

	}

	private String obterSenha() {

		String valor = "";

		for (int i = 0; i < this.frmGerador.getPwsSenha().getPassword().length; i++) {
			valor += this.frmGerador.getPwsSenha().getPassword()[i];
		}

		return valor;

	}

	/**
	 * Verifica se os valores de campos requeridos foram informados.
	 * 
	 * @return Retorna true se algum campo requirido não foi informado.
	 */
	private boolean verificarDados() {

		boolean validado = false;

		if (this.frmGerador.getRdbtnInformado().isSelected()) {

			if (this.frmGerador.getTxtUrl().getText().length() == 0
					&& this.frmGerador.getTxtDriver().getText().length() == 0
					&& this.frmGerador.getTxtUsuario().getText().length() == 0
					&& this.frmGerador.getPwsSenha().getPassword().length == 0) {

				JOptionPane.showMessageDialog(this.frmGerador,
						"Para conexão com dados informados, preencha os campos Url, Driver, Usuário e Senha para conectar no servidor de banco",
						InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
				validado = true;

			}

		} else if (this.frmGerador.getRdbtnPerfil().isSelected()) {

			int index = this.frmGerador.getCmbPerfil().getSelectedIndex();

			if (index < 0) {

				JOptionPane.showMessageDialog(this.frmGerador,
						"Para conexão com dados por perfil, selecione um Perfil para conectar no servidor de banco.",
						InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
				validado = true;

			}

		}

		return validado;
	}

	// MISAEL - 01/08/2015 - remoção do método para montar a JTree
	/*
	 * private void montarArvore() {
	 * 
	 * DefaultMutableTreeNode dmtnBanco = null; DefaultMutableTreeNode dmtnTabela =
	 * null; DefaultMutableTreeNode dmtnColuna = null;
	 * 
	 * try {
	 * 
	 * // Obter o nome da base de dados da conexão. // dmtnBanco = new
	 * DefaultMutableTreeNode(dao.nomeBaseConexao().toUpperCase()); dmtnBanco = new
	 * DefaultMutableTreeNode(this.voMetadado.getNomeBanco().toUpperCase());
	 * 
	 * // Obter o nome das tabelas da base. for(VoTabela voTabela :
	 * this.voMetadado.getTabelas()) {
	 * 
	 * dmtnTabela = new
	 * DefaultMutableTreeNode(voTabela.getNomeTabela().toUpperCase());
	 * 
	 * for(VoColuna voColuna : voTabela.getColunas()) {
	 * 
	 * dmtnColuna = new
	 * DefaultMutableTreeNode(voColuna.getNomeColuna().toUpperCase());
	 * dmtnTabela.add(dmtnColuna);
	 * 
	 * }
	 * 
	 * dmtnBanco.add(dmtnTabela); }
	 * 
	 * } catch (Exception e) {
	 * 
	 * JOptionPane.showMessageDialog(this.frmGerador ,
	 * "Não foi possível ler a estrutura de entidades(tabelas) da base de dados." +
	 * "\nVerifique a conexão com o servidor da base de dados ." ,
	 * InfoProjeto.obterNomeEVersao() , JOptionPane.ERROR_MESSAGE);
	 * 
	 * }
	 * 
	 * this.frmGerador.setTreeTabelas(new JTree(dmtnBanco));
	 * this.frmGerador.getSpnlTabelas().setViewportView(this.frmGerador.
	 * getTreeTabelas()); this.frmGerador.getTreeTabelas().repaint();
	 * 
	 * }
	 */

	// MISAEL 10/08/2015 - implementação pacotes para os fontes
	private void criarCaminhoPacote() throws IOException {

		String pacote = this.frmGerador.getTxtPacote().getText();

		if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {
			pacote = this.tratarPackage(pacote);
		} else if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {
			pacote = this.tratarNamespace(pacote);
		}

		// Pacote foi informado?
		if (pacote.length() > 0) {
			// Substitui o ponto por barra para poder criar os diretórios e subdiretórios.
			pacote = pacote.replace('.', '\\');
		}

		caminhoArquivo = this.frmGerador.getTxtDestinoFontes().getText() + "\\"
				+ this.ajustarNomeClasse(SessaoGerador.nomeProjeto) + "\\"
				+ (pacote.length() > 0 ? pacote : "JellyClass");

		// 12/08/2015 - MISAEL - trabalhar na estrutura MVC neste ponto.

		// Gerar a estrutura MVC depois do nome do pacote definido?
		if (this.frmGerador.getChkMvc().isSelected()) {

			if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {
				caminhoArquivoView = caminhoArquivo + "\\view";
				caminhoArquivoController = caminhoArquivo + "\\controller";
				caminhoArquivoModel = caminhoArquivo + "\\model";
			} else if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {
				caminhoArquivoView = caminhoArquivo + "\\View";
				caminhoArquivoController = caminhoArquivo + "\\Controller";
				caminhoArquivoModel = caminhoArquivo + "\\Model";
			}

			new File(caminhoArquivoView).mkdirs();
			new File(caminhoArquivoController).mkdirs();
			new File(caminhoArquivoModel).mkdirs();

			// Usuário definiu um valor de pacote?
			if (pacote.length() > 0) {
				// Grava na sessão do gerador o valor de pacote
				if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {
					SessaoGerador.pacote = pacote.replace('\\', '.') + ".model";
				} else if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {
					SessaoGerador.pacote = pacote.replace('\\', '.') + ".Model";
				}
			}

			// Se não, gera o caminho normal.
		} else {

			// Usuário definiu um valor de pacote?
			if (pacote.length() > 0) {
				SessaoGerador.pacote = pacote.replace('\\', '.');
			} else {
				SessaoGerador.pacote = "";
			}

			new File(caminhoArquivo).mkdirs();

		}

	}

	private String tratarPackage(String _package) {

		return _package.toLowerCase();

	}

	private String tratarNamespace(String namespace) {

		if (namespace.contains(".")) {

			String[] partes = namespace.split("\\.");

			for (int i = 0; i < partes.length; i++) {
				// Converte a 1 letra de cada parte do namespace em maiúscula.
				partes[i] = String.valueOf(partes[i].charAt(0)).toUpperCase() + partes[i].substring(1);

			}

			namespace = "";

			for (int i = 0; i < partes.length; i++) {

				if (i == partes.length - 1) {
					namespace += partes[i];
				} else {
					namespace += partes[i] + ".";
				}

			}

		}

		return namespace;

	}

	private void selecionarDestino() {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(InfoProjeto.obterNomeEVersao() + " - Abrir");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(this.frmGerador);

		this.frmGerador.getTxtDestinoFontes().setText((jfc.getSelectedFile() != null ? jfc.getSelectedFile().getPath()
				: this.frmGerador.getTxtDestinoFontes().getText()));

	}

	private void lerMetadados(Connection conn) {

		try {

			// TODO analisar, dividir os leitores de metados um para MySql e outro para
			// PostgreSQL aqui?

			this.voMetadado = boMetadado.lerMetadado(conn);

		} catch (ClassNotFoundException | SQLException | IOException e) {

			JOptionPane.showMessageDialog(this.frmGerador, "Falha ao ler metadados do banco.", "Erro",
					JOptionPane.ERROR_MESSAGE);

		}

	}

	// MISAEL - 01/08/2015 - método para trabalhar com a JList.
	private void capturarTabelasSelecionadas() throws Exception {

		DefaultListModel<String> listaTabSelecionadas = (DefaultListModel<String>) this.frmGerador
				.getListaTabSelecionadas().getModel();

		if (listaTabSelecionadas.isEmpty()) {

			JOptionPane.showMessageDialog(this.frmGerador, "Escolha ao menos uma tabela para ser processada.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;

		}

		List<VoTabela> tabelasParaGeracao = new ArrayList<VoTabela>();

		for (VoTabela tabela : this.voMetadado.getTabelas()) {

			for (int i = 0; i < listaTabSelecionadas.getSize(); i++) {

				if (tabela.getNomeTabela().toUpperCase().equals(listaTabSelecionadas.getElementAt(i))) {
					tabelasParaGeracao.add(tabela);
				}

			}

		}

		SessaoGerador.gerarComentário = this.frmGerador.getChckbxGerarComentario().isSelected();

		if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {

			this.gerarClasseEntidadeJava(tabelasParaGeracao);

			// Gerar as classes de negócio?
			if (this.frmGerador.getChckbxClassesDeNegocio().isSelected()) {
				this.gerarClasseNegocioJava(tabelasParaGeracao);
			}

			// MISAEL - 08/10/2015 - implementação para criação das classes DAO.
			// Gerar as classes DAO?
			if (this.frmGerador.getChckbxClassesDao().isSelected()) {
				this.gerarClassesDAOJava(tabelasParaGeracao);
			}

		} else if (SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {

			this.gerarClasseEntidadeCSNet(tabelasParaGeracao);

			// Gerar as classes de negócio?
			if (this.frmGerador.getChckbxClassesDeNegocio().isSelected()) {
				this.gerarClasseNegocioCSNet(tabelasParaGeracao);
			}

			// MISAEL - 08/10/2015 - implementação para criação das classes DAO.
			// Gerar as classes DAO?
			if (this.frmGerador.getChckbxClassesDao().isSelected()) {
				this.gerarClassesDAOCSNet(tabelasParaGeracao);
			}

		}

		JOptionPane.showMessageDialog(this.frmGerador, "Fontes gerados com sucesso no destino.",
				InfoProjeto.obterNomeEVersao(), JOptionPane.INFORMATION_MESSAGE);

		JOptionPane.showMessageDialog(this.frmGerador, "Altere os dados de autenticação na classe Dao.",
				InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);

	}

	private void gerarClasseEntidadeJava(List<VoTabela> tabelasParaGeracao) throws Exception {

		// 12/08/2015 - MISAEL - gravar os fontes java no caminho de pacote com o padrão
		// MVC.
		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		// MISAEL - 01/08/2015 - alteração, subsitutição do this.voMetadado.getTabelas()
		// por tabelasParaGeracao
		// pois a ideia é gerar código somente do que o usuário selecionar na JList de
		// "Selecionadas".
		for (VoTabela voTabela : tabelasParaGeracao) {

			String conteudo = this.boMetadado.gerarStringClasseEntidadeJava(voTabela);
			String nomeArquivo = this.ajustarNomeClasse(voTabela.getNomeTabela()) + ".java";

			this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

		}

	}

	// MISAEL - 17/08/2015 - método para gerar as classes de negócio (somente o
	// esqueleto).
	private void gerarClasseNegocioJava(List<VoTabela> tabelasParaGeracao) throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		for (VoTabela voTabela : tabelasParaGeracao) {

			String conteudo = this.boMetadado.gerarStringClasseNegocioJava(voTabela);
			String nomeArquivo = this.ajustarNomeClasse(voTabela.getNomeTabela()) + "Business.java";

			this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

		}

	}

	// MISAEL - 07/10/2015 - método para gerar as classes DAO.
	private void gerarClassesDAOJava(List<VoTabela> tabelasParaGeracao) throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		for (VoTabela voTabela : tabelasParaGeracao) {

			String conteudo = this.boMetadado.gerarStringClasseDaoJava(voTabela);
			String nomeArquivo = this.ajustarNomeClasse(voTabela.getNomeTabela()) + "Dao.java";

			this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

		}

		this.gerarClasseDAOConexaoJava();

	}

	// MISAEL - 09/10/2015 - método para gerar a classe DAO para conexão.
	private void gerarClasseDAOConexaoJava() throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		String conteudo = this.boMetadado.gerarStringClasseDaoConexaoJava();
		String nomeArquivo = "Dao.java";

		this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

	}

	private void gerarClasseEntidadeCSNet(List<VoTabela> tabelasParaGeracao) throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		for (VoTabela voTabela : tabelasParaGeracao) {

			String conteudo = this.boMetadado.gerarStringClasseEntidadeCSNet(voTabela);
			String nomeArquivo = this.ajustarNomeClasse(voTabela.getNomeTabela()) + ".cs";

			this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

		}

	}

	private void gerarClasseNegocioCSNet(List<VoTabela> tabelasParaGeracao) throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		for (VoTabela voTabela : tabelasParaGeracao) {

			String conteudo = this.boMetadado.gerarStringClasseNegocioCSNet(voTabela);
			String nomeArquivo = this.ajustarNomeClasse(voTabela.getNomeTabela()) + "Business.cs";

			this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

		}

	}

	private void gerarClassesDAOCSNet(List<VoTabela> tabelasParaGeracao) throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		for (VoTabela voTabela : tabelasParaGeracao) {

			String conteudo = this.boMetadado.gerarStringClasseDaoCSNet(voTabela);
			String nomeArquivo = this.ajustarNomeClasse(voTabela.getNomeTabela()) + "Dao.cs";

			this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

		}

		this.gerarClasseDAOConexaoCSNet();

	}

	private void gerarClasseDAOConexaoCSNet() throws Exception {

		if (this.frmGerador.getChkMvc().isSelected()) {
			caminhoArquivo = caminhoArquivoModel;
		}

		String conteudo = this.boMetadado.gerarStringClasseDaoConexaoCSNet();
		String nomeArquivo = "Dao.cs";

		this.boArquivo.gravar(nomeArquivo, conteudo, caminhoArquivo);

	}

	// NOTA: avaliar a permanência deste método no controller.
	// Modelar melhor as classes do projeto, pois esse metódo não deveria ficar
	// aqui.
	private String ajustarNomeClasse(String nome) {

		// Deixar tudo em caixa baixa (minúscula)
		nome = nome.toLowerCase();

		// O nome contem um underscore?
		if (nome.contains("_")) {

			// Dividi a palavra para cada underscore encontrado.
			String[] partes = nome.split("_");

			// Para cada "parte" do nome, altera a 1º letra para maiúscula.
			for (int i = 0; i < partes.length; i++) {

				// Previnir os casos que no nome tenha "__" (dois ou mais underscore em
				// sequência).
				if (partes[i].length() > 0) {

					partes[i] = String.valueOf(partes[i].charAt(0)).toUpperCase() + partes[i].substring(1);

				}

			}

			nome = "";

			// Uni as partes da palavra novamente, já sem o "_".
			for (int x = 0; x < partes.length; x++) {

				nome += partes[x];

			}

			// Se não tem "_" no nome, apenas altera a 1º letra para maiúscula.
		} else {
			nome = String.valueOf(nome.charAt(0)).toUpperCase() + nome.substring(1);
		}

		return nome;

	}

	private void encerrarAplicacao() {

		boolean conexaoFechada = true;

		try {

			// Se a conexão não foi aberta em algum momemto, encerra a aplicação sem
			// executar qualquer outro teste.
			if (dao == null) {
				return;
			}

			conexaoFechada = dao.conexaoBDEstaFechada();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(this.frmGerador, "Não foi possível verificar o estado do banco.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.ERROR_MESSAGE);

		}

		if (!conexaoFechada) {
			this.desconectar();
		}

	}

}
