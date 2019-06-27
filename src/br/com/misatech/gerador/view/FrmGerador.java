package br.com.misatech.gerador.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import br.com.misatech.gerador.view.uc.FileListCellRenderer;

public class FrmGerador extends JInternalFrame {

	private static final long serialVersionUID = 5690482638329633666L;
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel pnlConteudo;
	private JPanel pnlConexao;
	private JPanel pnlDados;
	private JPanel pnlLog;
	private JPanel pnlBancoModelo;
	private JPanel pnlTipoComponente;
	private JPanel pnlTabelas;
	private JPanel pnlTecnologias;
	
	private JTabbedPane tabsGerador;
	
	private JScrollPane spnlLog;
	private JScrollPane spnlTabDisponivel;
	private JScrollPane spnlTabSelecionadas;
	
	private ButtonGroup bgTipoDados;
	private ButtonGroup bgTecnologias;
	
	private JTextField txtUrl;
	private JTextField txtUsuario;
	private JTextField txtDriver;
	private JTextField txtPacote;
	private JTextField txtDestinoFontes;
	
	private JPasswordField pwsSenha;
	
	private JLabel lblFormaConexao;
	private JLabel lblPerfil;
	private JLabel lblSgbdr;
	private JLabel lblUrl;
	private JLabel lblDriver;
	private JLabel lblUsuario;
	private JLabel lblSenha;
	private JLabel lblPacote;
	private JLabel lblDestinoFontes;
	private JLabel lblMvc;
	
	private JComboBox<String> cmbPerfil;
	private JComboBox<String> cmbSgbdr;
	
	private JButton btnTestar;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JButton btnGerar;
	private JButton btnDestinoFontes;
	private JButton btnLimparLog;
	private JButton btnTodosDireita;
	private JButton btnSelDireita;
	private JButton btnSelEsquerda;
	private JButton btnTodosEsquerda;
	private JButton btnAjudaPacote;
	private JButton btnConfigPerfil;
	
	private JRadioButton rdbtnInformado;
	private JRadioButton rdbtnPerfil;
	
	private JCheckBox chckbxClassesDeNegocio;
	private JCheckBox chckbxClassesDao;
	private JCheckBox chkMvc;
	
	private ButtonGroup group;
	private JRadioButton rdbtnJava;
	private JRadioButton rdbtnCSnet;
	
	private JList<String> listLog;
	
	private DefaultListModel<String> dlModelo;
	private JList<String> listaTabDisponivel;
	private JList<String> listaTabSelecionadas;
	
	private JCheckBox chckbxGerarComentario;

	/**
	 * Create the frame.
	 */
	public FrmGerador() {
		
		this.setTitle("Gerador");
		this.setClosable(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 814, 609);
		this.setLocation((this.dimension.width - this.getWidth()) / 2, 25);
		
		pnlConteudo = new JPanel();
		pnlConteudo.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(pnlConteudo);
		pnlConteudo.setLayout(null);
		
		tabsGerador = new JTabbedPane(JTabbedPane.TOP);
		tabsGerador.setBounds(0, 10, 788, 557);
		pnlConteudo.add(tabsGerador);
		
		pnlConexao = new JPanel();
		tabsGerador.addTab("Conexão", null, pnlConexao, null);
		pnlConexao.setLayout(null);
		
		pnlDados = new JPanel();
		pnlDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		pnlDados.setBounds(10, 30, 763, 252);
		pnlConexao.add(pnlDados);
		pnlDados.setLayout(null);
		
		rdbtnInformado = new JRadioButton("Informado");
		rdbtnInformado.setSelected(true);
		rdbtnInformado.setBounds(140, 30, 109, 30);
		pnlDados.add(rdbtnInformado);
		
		rdbtnPerfil = new JRadioButton("Perfil");
		rdbtnPerfil.setBounds(280, 30, 109, 30);
		pnlDados.add(rdbtnPerfil);
		
		bgTipoDados = new ButtonGroup();
		bgTipoDados.add(rdbtnInformado);
		bgTipoDados.add(rdbtnPerfil);
		
		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setBounds(20, 60, 100, 30);
		pnlDados.add(lblPerfil);
		
		cmbPerfil = new JComboBox<String>();
		cmbPerfil.setBounds(120, 60, 200, 30);
		pnlDados.add(cmbPerfil);
		
		lblUrl = new JLabel("Url:");
		lblUrl.setBounds(20, 120, 100, 30);
		pnlDados.add(lblUrl);
		
		txtUrl = new JTextField();
		txtUrl.setColumns(10);
		txtUrl.setBounds(120, 120, 300, 30);
		pnlDados.add(txtUrl);
		
		lblDriver = new JLabel("Driver:");
		lblDriver.setBounds(20, 150, 100, 30);
		pnlDados.add(lblDriver);
		
		txtDriver = new JTextField();
		txtDriver.setEditable(true);
		txtDriver.setBounds(120, 150, 300, 30);
		pnlDados.add(txtDriver);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(20, 180, 100, 30);
		pnlDados.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(120, 180, 200, 30);
		pnlDados.add(txtUsuario);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(20, 210, 100, 30);
		pnlDados.add(lblSenha);
		
		pwsSenha = new JPasswordField();
		pwsSenha.setBounds(120, 210, 200, 30);
		pnlDados.add(pwsSenha);
		
		btnTestar = new JButton("Testar");
		btnTestar.setMargin(new Insets(0, 0, 0, 0));
		btnTestar.setBounds(430, 150, 100, 30);
		pnlDados.add(btnTestar);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConectar.setForeground(new Color(255, 255, 255));
		btnConectar.setBackground(new Color(0, 128, 0));
		btnConectar.setMargin(new Insets(0, 0, 0, 0));
		btnConectar.setBounds(430, 90, 100, 30);
		pnlDados.add(btnConectar);
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setBackground(Color.RED);
		btnDesconectar.setForeground(Color.WHITE);
		btnDesconectar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDesconectar.setMargin(new Insets(0, 0, 0, 0));
		btnDesconectar.setBounds(430, 120, 100, 30);
		pnlDados.add(btnDesconectar);
		
		lblFormaConexao = new JLabel("Forma de conex\u00E3o:");
		lblFormaConexao.setBounds(20, 30, 110, 30);
		pnlDados.add(lblFormaConexao);
		
		lblSgbdr = new JLabel("SGBDR:");
		lblSgbdr.setBounds(20, 90, 100, 30);
		pnlDados.add(lblSgbdr);
		
		cmbSgbdr = new JComboBox<String>();
		cmbSgbdr.setBounds(120, 90, 200, 30);
		pnlDados.add(cmbSgbdr);
		
		btnConfigPerfil = new JButton("");
		btnConfigPerfil.setToolTipText("Configurar Perfil");
		btnConfigPerfil.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/cog.png")));
		btnConfigPerfil.setBounds(330, 60, 30, 30);
		pnlDados.add(btnConfigPerfil);
		
		pnlTecnologias = new JPanel();
		pnlTecnologias.setLayout(null);
		pnlTecnologias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tecnologia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		pnlTecnologias.setBounds(554, 50, 199, 160);
		pnlDados.add(pnlTecnologias);
		
		rdbtnJava = new JRadioButton("Java");
		rdbtnJava.setSelected(true);
		rdbtnJava.setBounds(20, 29, 170, 23);
		pnlTecnologias.add(rdbtnJava);
		
		rdbtnCSnet = new JRadioButton("C#.NET");
		rdbtnCSnet.setBounds(20, 60, 170, 23);
		pnlTecnologias.add(rdbtnCSnet);
		
		group = new ButtonGroup();
		group.add(rdbtnJava);
		group.add(rdbtnCSnet);
		
		pnlLog = new JPanel();
		pnlLog.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		pnlLog.setBounds(10, 293, 763, 225);
		pnlConexao.add(pnlLog);
		pnlLog.setLayout(null);
		
		spnlLog = new JScrollPane();
		spnlLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spnlLog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spnlLog.setBounds(10, 30, 708, 180);
		pnlLog.add(spnlLog);
		
		dlModelo = new DefaultListModel<String>();
		
		listLog = new JList<String>(dlModelo);
		listLog.setForeground(Color.WHITE);
		listLog.setBackground(Color.BLACK);
		spnlLog.setViewportView(listLog);
		
		btnLimparLog = new JButton("");
		btnLimparLog.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/cross.png")));
		btnLimparLog.setToolTipText("Limpar log");
		btnLimparLog.setBounds(728, 30, 25, 23);
		pnlLog.add(btnLimparLog);
		
		pnlBancoModelo = new JPanel();
		tabsGerador.addTab("Banco para Modelo", null, pnlBancoModelo, null);
		pnlBancoModelo.setLayout(null);
		
		btnGerar = new JButton("Gerar");
		btnGerar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGerar.setForeground(new Color(255, 255, 255));
		btnGerar.setBackground(new Color(0, 128, 0));
		btnGerar.setBounds(339, 490, 90, 30);
		pnlBancoModelo.add(btnGerar);
		
		pnlTipoComponente = new JPanel();
		pnlTipoComponente.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tipo de Componente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		pnlTipoComponente.setBounds(544, 30, 204, 160);
		pnlBancoModelo.add(pnlTipoComponente);
		pnlTipoComponente.setLayout(null);
			
		chckbxClassesDeNegocio = new JCheckBox("Classes de neg\u00F3cio.");
		chckbxClassesDeNegocio.setBounds(10, 30, 180, 30);
		pnlTipoComponente.add(chckbxClassesDeNegocio);
		
		chckbxClassesDao = new JCheckBox("Classes Dao.");
		chckbxClassesDao.setBounds(10, 60, 180, 30);
		pnlTipoComponente.add(chckbxClassesDao);
		
		pnlTabelas = new JPanel();
		pnlTabelas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tabelas do banco: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		pnlTabelas.setBounds(10, 30, 509, 350);
		pnlBancoModelo.add(pnlTabelas);
		pnlTabelas.setLayout(null);
		
		spnlTabDisponivel = new JScrollPane();
		spnlTabDisponivel.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dispon\u00EDveis", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		spnlTabDisponivel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spnlTabDisponivel.setBounds(10, 20, 220, 320);
		pnlTabelas.add(spnlTabDisponivel);
		
		listaTabDisponivel = new JList<String>();
		listaTabDisponivel.setCellRenderer(new FileListCellRenderer());
		spnlTabDisponivel.setViewportView(listaTabDisponivel);
		
		spnlTabSelecionadas = new JScrollPane();
		spnlTabSelecionadas.setViewportBorder(new TitledBorder(null, "Selecionadas", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		spnlTabSelecionadas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spnlTabSelecionadas.setBounds(277, 20, 220, 320);
		pnlTabelas.add(spnlTabSelecionadas);
		
		listaTabSelecionadas = new JList<String>();
		listaTabSelecionadas.setCellRenderer(new FileListCellRenderer());
		spnlTabSelecionadas.setViewportView(listaTabSelecionadas);
		
		btnTodosDireita = new JButton("");
		btnTodosDireita.setToolTipText("Adicionar todos");
		btnTodosDireita.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/fastforward.png")));
		btnTodosDireita.setBounds(242, 79, 25, 25);
		pnlTabelas.add(btnTodosDireita);
		
		btnSelDireita = new JButton("");
		btnSelDireita.setToolTipText("Adicionar");
		btnSelDireita.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/right.png")));
		btnSelDireita.setBounds(242, 115, 25, 25);
		pnlTabelas.add(btnSelDireita);
		
		btnSelEsquerda = new JButton("");
		btnSelEsquerda.setToolTipText("Remover");
		btnSelEsquerda.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/left.png")));
		btnSelEsquerda.setBounds(242, 151, 25, 25);
		pnlTabelas.add(btnSelEsquerda);
		
		btnTodosEsquerda = new JButton("");
		btnTodosEsquerda.setToolTipText("Remover todos");
		btnTodosEsquerda.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/rewind.png")));
		btnTodosEsquerda.setBounds(242, 187, 25, 25);
		pnlTabelas.add(btnTodosEsquerda);
		
		bgTecnologias = new ButtonGroup();
		
		lblDestinoFontes = new JLabel("Destino Fontes:");
		lblDestinoFontes.setBounds(22, 460, 100, 30);
		pnlBancoModelo.add(lblDestinoFontes);
		
		txtDestinoFontes = new JTextField();
		txtDestinoFontes.setBounds(122, 460, 590, 30);
		pnlBancoModelo.add(txtDestinoFontes);
		txtDestinoFontes.setColumns(10);
		
		btnDestinoFontes = new JButton("...");
		btnDestinoFontes.setBounds(720, 460, 30, 30);
		pnlBancoModelo.add(btnDestinoFontes);
		
		lblPacote = new JLabel("Package:");
		lblPacote.setBounds(22, 430, 100, 30);
		pnlBancoModelo.add(lblPacote);
		
		txtPacote = new JTextField();
		txtPacote.setBounds(122, 430, 590, 30);
		pnlBancoModelo.add(txtPacote);
		txtPacote.setColumns(10);
		
		btnAjudaPacote = new JButton("");
		btnAjudaPacote.setToolTipText("Exemplo de Package");
		btnAjudaPacote.setIcon(new ImageIcon(FrmGerador.class.getResource("/br/com/misatech/gerador/view/images/help.png")));
		btnAjudaPacote.setBounds(720, 430, 30, 30);
		pnlBancoModelo.add(btnAjudaPacote);
		
		lblMvc = new JLabel("MVC?");
		lblMvc.setBounds(22, 400, 100, 30);
		pnlBancoModelo.add(lblMvc);
		
		chkMvc = new JCheckBox("");
		chkMvc.setBounds(122, 400, 30, 30);
		pnlBancoModelo.add(chkMvc);
		
		chckbxGerarComentario = new JCheckBox("Gerar coment\u00E1rio padr\u00E3o.");
		chckbxGerarComentario.setMargin(new Insets(0, 0, 0, 0));
		chckbxGerarComentario.setBounds(553, 214, 170, 23);
		pnlBancoModelo.add(chckbxGerarComentario);
		
		setVisible(true);
		
	}

	public JPanel getPnlConteudo() {
		return pnlConteudo;
	}

	public void setPnlConteudo(JPanel pnlConteudo) {
		this.pnlConteudo = pnlConteudo;
	}

	public JPanel getPnlConexao() {
		return pnlConexao;
	}

	public void setPnlConexao(JPanel pnlConexao) {
		this.pnlConexao = pnlConexao;
	}

	public JPanel getPnlDados() {
		return pnlDados;
	}

	public void setPnlDados(JPanel pnlDados) {
		this.pnlDados = pnlDados;
	}

	public JPanel getPnlLog() {
		return pnlLog;
	}

	public void setPnlLog(JPanel pnlLog) {
		this.pnlLog = pnlLog;
	}

	public JPanel getPnlBancoModelo() {
		return pnlBancoModelo;
	}

	public void setPnlBancoModelo(JPanel pnlBancoModelo) {
		this.pnlBancoModelo = pnlBancoModelo;
	}

	public JPanel getPnlTipoComponente() {
		return pnlTipoComponente;
	}

	public void setPnlTipoComponente(JPanel pnlTipoComponente) {
		this.pnlTipoComponente = pnlTipoComponente;
	}

	public JPanel getPnlTabelas() {
		return pnlTabelas;
	}

	public void setPnlTabelas(JPanel pnlTabelas) {
		this.pnlTabelas = pnlTabelas;
	}

	public JPanel getPnlTecnologias() {
		return pnlTecnologias;
	}

	public void setPnlTecnologias(JPanel pnlTecnologias) {
		this.pnlTecnologias = pnlTecnologias;
	}

	public JTabbedPane getTabsGerador() {
		return tabsGerador;
	}

	public void setTabsGerador(JTabbedPane tabsGerador) {
		this.tabsGerador = tabsGerador;
	}

	public JScrollPane getSpnlLog() {
		return spnlLog;
	}

	public void setSpnlLog(JScrollPane spnlLog) {
		this.spnlLog = spnlLog;
	}

	public JScrollPane getSpnlTabDisponivel() {
		return spnlTabDisponivel;
	}

	public void setSpnlTabDisponivel(JScrollPane spnlTabDisponivel) {
		this.spnlTabDisponivel = spnlTabDisponivel;
	}

	public JScrollPane getSpnlTabSelecionadas() {
		return spnlTabSelecionadas;
	}

	public void setSpnlTabSelecionadas(JScrollPane spnlTabSelecionadas) {
		this.spnlTabSelecionadas = spnlTabSelecionadas;
	}

	public ButtonGroup getBgTipoDados() {
		return bgTipoDados;
	}

	public void setBgTipoDados(ButtonGroup bgTipoDados) {
		this.bgTipoDados = bgTipoDados;
	}

	public ButtonGroup getBgTecnologias() {
		return bgTecnologias;
	}

	public void setBgTecnologias(ButtonGroup bgTecnologias) {
		this.bgTecnologias = bgTecnologias;
	}

	public JTextField getTxtUrl() {
		return txtUrl;
	}

	public void setTxtUrl(JTextField txtUrl) {
		this.txtUrl = txtUrl;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public JTextField getTxtDriver() {
		return txtDriver;
	}

	public void setTxtDriver(JTextField txtDriver) {
		this.txtDriver = txtDriver;
	}

	public JTextField getTxtPacote() {
		return txtPacote;
	}

	public void setTxtPacote(JTextField txtPacote) {
		this.txtPacote = txtPacote;
	}

	public JTextField getTxtDestinoFontes() {
		return txtDestinoFontes;
	}

	public void setTxtDestinoFontes(JTextField txtDestinoFontes) {
		this.txtDestinoFontes = txtDestinoFontes;
	}

	public JPasswordField getPwsSenha() {
		return pwsSenha;
	}

	public void setPwsSenha(JPasswordField pwsSenha) {
		this.pwsSenha = pwsSenha;
	}

	public JLabel getLblFormaConexao() {
		return lblFormaConexao;
	}

	public void setLblFormaConexao(JLabel lblFormaConexao) {
		this.lblFormaConexao = lblFormaConexao;
	}

	public JLabel getLblPerfil() {
		return lblPerfil;
	}

	public void setLblPerfil(JLabel lblPerfil) {
		this.lblPerfil = lblPerfil;
	}

	public JLabel getLblSgbdr() {
		return lblSgbdr;
	}

	public void setLblSgbdr(JLabel lblSgbdr) {
		this.lblSgbdr = lblSgbdr;
	}

	public JLabel getLblUrl() {
		return lblUrl;
	}

	public void setLblUrl(JLabel lblUrl) {
		this.lblUrl = lblUrl;
	}

	public JLabel getLblDriver() {
		return lblDriver;
	}

	public void setLblDriver(JLabel lblDriver) {
		this.lblDriver = lblDriver;
	}

	public JLabel getLblUsuario() {
		return lblUsuario;
	}

	public void setLblUsuario(JLabel lblUsuario) {
		this.lblUsuario = lblUsuario;
	}

	public JLabel getLblSenha() {
		return lblSenha;
	}

	public void setLblSenha(JLabel lblSenha) {
		this.lblSenha = lblSenha;
	}

	public JLabel getLblPacote() {
		return lblPacote;
	}

	public void setLblPacote(JLabel lblPacote) {
		this.lblPacote = lblPacote;
	}

	public JLabel getLblDestinoFontes() {
		return lblDestinoFontes;
	}

	public void setLblDestinoFontes(JLabel lblDestinoFontes) {
		this.lblDestinoFontes = lblDestinoFontes;
	}

	public JLabel getLblMvc() {
		return lblMvc;
	}

	public void setLblMvc(JLabel lblMvc) {
		this.lblMvc = lblMvc;
	}

	public JComboBox<String> getCmbPerfil() {
		return cmbPerfil;
	}

	public void setCmbPerfil(JComboBox<String> cmbPerfil) {
		this.cmbPerfil = cmbPerfil;
	}

	public JComboBox<String> getCmbSgbdr() {
		return cmbSgbdr;
	}

	public void setCmbSgbdr(JComboBox<String> cmbSgbdr) {
		this.cmbSgbdr = cmbSgbdr;
	}

	public JButton getBtnTestar() {
		return btnTestar;
	}

	public void setBtnTestar(JButton btnTestar) {
		this.btnTestar = btnTestar;
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}

	public void setBtnConectar(JButton btnConectar) {
		this.btnConectar = btnConectar;
	}

	public JButton getBtnDesconectar() {
		return btnDesconectar;
	}

	public void setBtnDesconectar(JButton btnDesconectar) {
		this.btnDesconectar = btnDesconectar;
	}

	public JButton getBtnGerar() {
		return btnGerar;
	}

	public void setBtnGerar(JButton btnGerar) {
		this.btnGerar = btnGerar;
	}

	public JButton getBtnDestinoFontes() {
		return btnDestinoFontes;
	}

	public void setBtnDestinoFontes(JButton btnDestinoFontes) {
		this.btnDestinoFontes = btnDestinoFontes;
	}

	public JButton getBtnLimparLog() {
		return btnLimparLog;
	}

	public void setBtnLimparLog(JButton btnLimparLog) {
		this.btnLimparLog = btnLimparLog;
	}

	public JButton getBtnTodosDireita() {
		return btnTodosDireita;
	}

	public void setBtnTodosDireita(JButton btnTodosDireita) {
		this.btnTodosDireita = btnTodosDireita;
	}

	public JButton getBtnSelDireita() {
		return btnSelDireita;
	}

	public void setBtnSelDireita(JButton btnSelDireita) {
		this.btnSelDireita = btnSelDireita;
	}

	public JButton getBtnSelEsquerda() {
		return btnSelEsquerda;
	}

	public void setBtnSelEsquerda(JButton btnSelEsquerda) {
		this.btnSelEsquerda = btnSelEsquerda;
	}

	public JButton getBtnTodosEsquerda() {
		return btnTodosEsquerda;
	}

	public void setBtnTodosEsquerda(JButton btnTodosEsquerda) {
		this.btnTodosEsquerda = btnTodosEsquerda;
	}

	public JButton getBtnAjudaPacote() {
		return btnAjudaPacote;
	}

	public void setBtnAjudaPacote(JButton btnAjudaPacote) {
		this.btnAjudaPacote = btnAjudaPacote;
	}

	public JButton getBtnConfigPerfil() {
		return btnConfigPerfil;
	}

	public void setBtnConfigPerfil(JButton btnConfigPerfil) {
		this.btnConfigPerfil = btnConfigPerfil;
	}

	public JRadioButton getRdbtnInformado() {
		return rdbtnInformado;
	}

	public void setRdbtnInformado(JRadioButton rdbtnInformado) {
		this.rdbtnInformado = rdbtnInformado;
	}

	public JRadioButton getRdbtnPerfil() {
		return rdbtnPerfil;
	}

	public void setRdbtnPerfil(JRadioButton rdbtnPerfil) {
		this.rdbtnPerfil = rdbtnPerfil;
	}

	public JRadioButton getRdbtnJava() {
		return rdbtnJava;
	}

	public void setRdbtnJava(JRadioButton rdbtnJava) {
		this.rdbtnJava = rdbtnJava;
	}

	public JRadioButton getRdbtnCSnet() {
		return rdbtnCSnet;
	}

	public void setRdbtnCSnet(JRadioButton rdbtnCSnet) {
		this.rdbtnCSnet = rdbtnCSnet;
	}

	public JCheckBox getChckbxClassesDeNegocio() {
		return chckbxClassesDeNegocio;
	}

	public void setChckbxClassesDeNegocio(JCheckBox chckbxClassesDeNegocio) {
		this.chckbxClassesDeNegocio = chckbxClassesDeNegocio;
	}

	public JCheckBox getChckbxClassesDao() {
		return chckbxClassesDao;
	}

	public void setChckbxClassesDao(JCheckBox chckbxClassesDao) {
		this.chckbxClassesDao = chckbxClassesDao;
	}

	public JCheckBox getChckbxGerarComentario() {
		return chckbxGerarComentario;
	}

	public void setChckbxGerarComentario(JCheckBox chckbxGerarComentario) {
		this.chckbxGerarComentario = chckbxGerarComentario;
	}

	public JCheckBox getChkMvc() {
		return chkMvc;
	}

	public void setChkMvc(JCheckBox chkMvc) {
		this.chkMvc = chkMvc;
	}

	public JList<String> getListLog() {
		return listLog;
	}

	public void setListLog(JList<String> listLog) {
		this.listLog = listLog;
	}

	public DefaultListModel<String> getDlModelo() {
		return dlModelo;
	}

	public void setDlModelo(DefaultListModel<String> dlModelo) {
		this.dlModelo = dlModelo;
	}

	public JList<String> getListaTabDisponivel() {
		return listaTabDisponivel;
	}

	public void setListaTabDisponivel(JList<String> listaTabDisponivel) {
		this.listaTabDisponivel = listaTabDisponivel;
	}

	public JList<String> getListaTabSelecionadas() {
		return listaTabSelecionadas;
	}

	public void setListaTabSelecionadas(JList<String> listaTabSelecionadas) {
		this.listaTabSelecionadas = listaTabSelecionadas;
	}
}
