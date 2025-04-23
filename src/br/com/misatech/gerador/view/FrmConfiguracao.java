package br.com.misatech.gerador.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import br.com.misatech.gerador.model.FormUtils;
import br.com.misatech.gerador.model.MascaraCampo;

public class FrmConfiguracao extends JInternalFrame {

	private static final long serialVersionUID = -3016820243081340310L;
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel pnlConteudo;
	private JPanel pnlInformacoes;
	private JPanel pnlRegistros;
	private JPanel pnlLogoSgbdr;
	private JScrollPane scrollPane;
	private JComboBox<String> cmbSgbdr;
	private JPasswordField pswSenha;
	private JTextField txtNome;
	private JTextField txtUsuario;
	private JTextField txtUrl;
	private JTextField txtDriver;
	private JTextField txtDescricao;
	private JFormattedTextField ftfDataHora;
	private JLabel lblSgbdr;
	private JLabel lblNome;
	private JLabel lblUsurio;
	private JLabel lblSenha;
	private JLabel lblUrl;
	private JLabel lblDriver;
	private JLabel lblDescricao;
	private JLabel lblDataHora;
	private JButton btnInicializar;
	private JButton btnGravar;
	private JButton btnExcluir;
	private JButton btnConsultar;
	private JButton btnAjudaUrl;
	private JButton btnAjudaDriver;
	private JToolBar toolBar;
	private JTable table;

	/**
	 * Construtor padrão da classe.
	 */
	public FrmConfiguracao() {

		setClosable(true);
		setTitle("Configura\u00E7\u00E3o de Perfil de Conex\u00E3o");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 671, 671);
		this.setLocation((this.dimension.width - this.getWidth()) / 2, 25);

		pnlConteudo = new JPanel();
		pnlConteudo.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlConteudo);
		pnlConteudo.setLayout(null);

		pnlInformacoes = new JPanel();
		pnlInformacoes.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manuten\u00E7\u00E3o",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLUE));
		pnlInformacoes.setBounds(12, 41, 630, 288);
		pnlConteudo.add(pnlInformacoes);
		pnlInformacoes.setLayout(null);

		lblUsurio = new JLabel("Usu\u00E1rio:");
		lblUsurio.setBounds(20, 150, 60, 30);
		pnlInformacoes.add(lblUsurio);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(80, 150, 200, 30);
		pnlInformacoes.add(txtUsuario);
		txtUsuario.setColumns(10);

		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(20, 180, 60, 30);
		pnlInformacoes.add(lblSenha);

		pswSenha = new JPasswordField();
		pswSenha.setBounds(80, 180, 200, 30);
		pnlInformacoes.add(pswSenha);

		lblUrl = new JLabel("Url:");
		lblUrl.setBounds(20, 90, 60, 30);
		pnlInformacoes.add(lblUrl);

		txtUrl = new JTextField();
		txtUrl.setBounds(80, 90, 325, 30);
		pnlInformacoes.add(txtUrl);
		txtUrl.setColumns(10);

		lblDriver = new JLabel("Driver:");
		lblDriver.setBounds(20, 120, 60, 30);
		pnlInformacoes.add(lblDriver);

		txtDriver = new JTextField();
		txtDriver.setBounds(80, 120, 325, 30);
		pnlInformacoes.add(txtDriver);
		txtDriver.setColumns(10);

		lblDescricao = new JLabel("Descri\u00E7\u00E3o:");
		lblDescricao.setBounds(20, 210, 60, 30);
		pnlInformacoes.add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(80, 210, 500, 30);
		pnlInformacoes.add(txtDescricao);
		txtDescricao.setColumns(10);

		lblDataHora = new JLabel("Data/hora:");
		lblDataHora.setBounds(20, 240, 60, 30);
		pnlInformacoes.add(lblDataHora);

		ftfDataHora = new JFormattedTextField(FormUtils.formatarMascaraCampos(ftfDataHora, MascaraCampo.enDataHora));
		ftfDataHora.setBackground(Color.WHITE);
		ftfDataHora.setEditable(false);
		ftfDataHora.setBounds(80, 240, 131, 30);
		pnlInformacoes.add(ftfDataHora);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 30, 60, 30);
		pnlInformacoes.add(lblNome);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtNome.setEditable(true);
		txtNome.setBounds(80, 30, 200, 30);
		pnlInformacoes.add(txtNome);

		lblSgbdr = new JLabel("SGBDR:");
		lblSgbdr.setBounds(20, 60, 60, 30);
		pnlInformacoes.add(lblSgbdr);

		cmbSgbdr = new JComboBox<String>();
		cmbSgbdr.setBounds(80, 60, 200, 30);
		pnlInformacoes.add(cmbSgbdr);

		pnlLogoSgbdr = new JPanel();
		pnlLogoSgbdr.setBackground(Color.WHITE);
		pnlLogoSgbdr.setBounds(445, 27, 170, 120);
		pnlInformacoes.add(pnlLogoSgbdr);
		pnlLogoSgbdr.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAjudaUrl = new JButton("");
		btnAjudaUrl.setToolTipText("Exemplos de URL");
		btnAjudaUrl.setIcon(
				new ImageIcon(FrmConfiguracao.class.getResource("/br/com/misatech/gerador/view/images/help.png")));
		btnAjudaUrl.setBounds(410, 90, 30, 30);
		pnlInformacoes.add(btnAjudaUrl);

		btnAjudaDriver = new JButton("");
		btnAjudaDriver.setIcon(
				new ImageIcon(FrmConfiguracao.class.getResource("/br/com/misatech/gerador/view/images/help.png")));
		btnAjudaDriver.setToolTipText("Exmplos");
		btnAjudaDriver.setBounds(410, 120, 30, 30);
		pnlInformacoes.add(btnAjudaDriver);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 0, 655, 30);
		pnlConteudo.add(toolBar);

		btnInicializar = new JButton("");
		btnInicializar.setBorderPainted(false);
		toolBar.add(btnInicializar);
		btnInicializar.setToolTipText("Inicializar");
		btnInicializar.setIcon(new ImageIcon(
				FrmConfiguracao.class.getResource("/br/com/misatech/gerador/view/images/page_white.png")));

		btnGravar = new JButton("");
		btnGravar.setBorderPainted(false);
		toolBar.add(btnGravar);
		btnGravar.setToolTipText("Gravar");
		btnGravar.setIcon(
				new ImageIcon(FrmConfiguracao.class.getResource("/br/com/misatech/gerador/view/images/disk.png")));

		btnExcluir = new JButton("");
		btnExcluir.setBorderPainted(false);
		toolBar.add(btnExcluir);
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(
				new ImageIcon(FrmConfiguracao.class.getResource("/br/com/misatech/gerador/view/images/cross.png")));

		btnConsultar = new JButton("");
		btnConsultar.setBorderPainted(false);
		toolBar.add(btnConsultar);
		btnConsultar.setToolTipText("Consultar");
		btnConsultar.setIcon(
				new ImageIcon(FrmConfiguracao.class.getResource("/br/com/misatech/gerador/view/images/magnifier.png")));

		pnlRegistros = new JPanel();
		pnlRegistros.setBorder(
				new TitledBorder(null, "Registros", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		pnlRegistros.setBounds(12, 340, 630, 290);
		pnlConteudo.add(pnlRegistros);
		pnlRegistros.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 610, 242);
		pnlRegistros.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		setVisible(true);

	}

	public JPanel getPnlConteudo() {
		return pnlConteudo;
	}

	public void setPnlConteudo(JPanel pnlConteudo) {
		this.pnlConteudo = pnlConteudo;
	}

	public JPanel getPnlInformacoes() {
		return pnlInformacoes;
	}

	public void setPnlInformacoes(JPanel pnlInformacoes) {
		this.pnlInformacoes = pnlInformacoes;
	}

	public JPanel getPnlRegistros() {
		return pnlRegistros;
	}

	public void setPnlRegistros(JPanel pnlRegistros) {
		this.pnlRegistros = pnlRegistros;
	}

	public JPanel getPnlLogoSgbdr() {
		return pnlLogoSgbdr;
	}

	public void setPnlLogoSgbdr(JPanel pnlLogoSgbdr) {
		this.pnlLogoSgbdr = pnlLogoSgbdr;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JComboBox<String> getCmbSgbdr() {
		return cmbSgbdr;
	}

	public void setCmbSgbdr(JComboBox<String> cmbSgbdr) {
		this.cmbSgbdr = cmbSgbdr;
	}

	public JPasswordField getPswSenha() {
		return pswSenha;
	}

	public void setPswSenha(JPasswordField pswSenha) {
		this.pswSenha = pswSenha;
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

	public void setTxtNome(JTextField txtNome) {
		this.txtNome = txtNome;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public JTextField getTxtUrl() {
		return txtUrl;
	}

	public void setTxtUrl(JTextField txtUrl) {
		this.txtUrl = txtUrl;
	}

	public JTextField getTxtDriver() {
		return txtDriver;
	}

	public void setTxtDriver(JTextField txtDriver) {
		this.txtDriver = txtDriver;
	}

	public JTextField getTxtDescricao() {
		return txtDescricao;
	}

	public void setTxtDescricao(JTextField txtDescricao) {
		this.txtDescricao = txtDescricao;
	}

	public JFormattedTextField getFtfDataHora() {
		return ftfDataHora;
	}

	public void setFtfDataHora(JFormattedTextField ftfDataHora) {
		this.ftfDataHora = ftfDataHora;
	}

	public JLabel getLblSgbdr() {
		return lblSgbdr;
	}

	public void setLblSgbdr(JLabel lblSgbdr) {
		this.lblSgbdr = lblSgbdr;
	}

	public JLabel getLblNome() {
		return lblNome;
	}

	public void setLblNome(JLabel lblNome) {
		this.lblNome = lblNome;
	}

	public JLabel getLblUsurio() {
		return lblUsurio;
	}

	public void setLblUsurio(JLabel lblUsurio) {
		this.lblUsurio = lblUsurio;
	}

	public JLabel getLblSenha() {
		return lblSenha;
	}

	public void setLblSenha(JLabel lblSenha) {
		this.lblSenha = lblSenha;
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

	public JLabel getLblDescricao() {
		return lblDescricao;
	}

	public void setLblDescricao(JLabel lblDescricao) {
		this.lblDescricao = lblDescricao;
	}

	public JLabel getLblDataHora() {
		return lblDataHora;
	}

	public void setLblDataHora(JLabel lblDataHora) {
		this.lblDataHora = lblDataHora;
	}

	public JButton getBtnInicializar() {
		return btnInicializar;
	}

	public void setBtnInicializar(JButton btnInicializar) {
		this.btnInicializar = btnInicializar;
	}

	public JButton getBtnGravar() {
		return btnGravar;
	}

	public void setBtnGravar(JButton btnGravar) {
		this.btnGravar = btnGravar;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}

	public void setBtnExcluir(JButton btnExcluir) {
		this.btnExcluir = btnExcluir;
	}

	public JButton getBtnConsultar() {
		return btnConsultar;
	}

	public void setBtnConsultar(JButton btnConsultar) {
		this.btnConsultar = btnConsultar;
	}

	public JButton getBtnAjudaUrl() {
		return btnAjudaUrl;
	}

	public void setBtnAjudaUrl(JButton btnAjudaUrl) {
		this.btnAjudaUrl = btnAjudaUrl;
	}

	public JButton getBtnAjudaDriver() {
		return btnAjudaDriver;
	}

	public void setBtnAjudaDriver(JButton btnAjudaDriver) {
		this.btnAjudaDriver = btnAjudaDriver;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}
