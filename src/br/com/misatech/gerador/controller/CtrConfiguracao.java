package br.com.misatech.gerador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.misatech.gerador.model.FormUtils;
import br.com.misatech.gerador.model.InfoProjeto;
import br.com.misatech.gerador.model.Utils;
import br.com.misatech.gerador.model.bo.BoArquivoConfiguracao;
import br.com.misatech.gerador.model.dao.DaoArquivoConfiguracao;
import br.com.misatech.gerador.model.en.EnSGBDR;
import br.com.misatech.gerador.model.vo.VoArquivoConfiguracao;
import br.com.misatech.gerador.view.FrmConfiguracao;
import br.com.misatech.gerador.view.FrmMenu;
import br.com.misatech.gerador.view.table.CelTblConfiguracao;
import br.com.misatech.gerador.view.table.TblConfiguracao;
import br.com.misatech.gerador.view.uc.UCImageLoader;

public class CtrConfiguracao implements InternalFrameListener, ActionListener, ListSelectionListener, ItemListener {

	private FrmConfiguracao frmConfiguracao;
	private VoArquivoConfiguracao voArquivoConfiguracao;
	private DaoArquivoConfiguracao daoArquivoConfiguracao;
	private List<VoArquivoConfiguracao> voArquivosConfiguracoes;

	// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
	// no formuláiro de configuração de perfil de conexão : insernção de variávels.
	private final String RESOURCES_PACKAGE_PATH = "/br/com/misatech/gerador/view/images/";

	public CtrConfiguracao(FrmMenu frmMenu) {

		this.daoArquivoConfiguracao = new DaoArquivoConfiguracao();
		this.frmConfiguracao = new FrmConfiguracao();

		this.frmConfiguracao.getBtnInicializar().addActionListener(this);
		this.frmConfiguracao.getBtnGravar().addActionListener(this);
		this.frmConfiguracao.getBtnExcluir().addActionListener(this);
		this.frmConfiguracao.getBtnConsultar().addActionListener(this);
		this.frmConfiguracao.getBtnAjudaUrl().addActionListener(this);
		this.frmConfiguracao.getBtnAjudaDriver().addActionListener(this);
		this.frmConfiguracao.getTable().getSelectionModel().addListSelectionListener(this);
		this.frmConfiguracao.addInternalFrameListener(this);

		// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
		// no formuláiro de configuração de perfil de conexão : insernção de evento para
		// getCmbSgbdr.
		this.frmConfiguracao.getCmbSgbdr().addItemListener(this);

		frmMenu.getDesktopPane().add(this.frmConfiguracao);

		// MISAEL - 04/06/2015: Correção do foco para o JInternalFrame.
		frmMenu.getDesktopPane().selectFrame(true);

	}

	// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
	// no formuláiro de configuração de perfil de conexão : alimentando JComboBox
	// com chave SGBDR.
	private void popularComboSgbdr() {

		// MISAEL - 28/07/2015 - correção de bug, combo sera populada mais de uma vez,
		// utilização de DefaultComboBoxModel.
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement(EnSGBDR.MYSQL.toString());
		model.addElement(EnSGBDR.POSTGRESQL.toString());

		this.frmConfiguracao.getCmbSgbdr().setModel(model);

	}

	// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
	// no formuláiro de configuração de perfil de conexão : carregamento da imagem.
	private void carregarImagemLogoSgbdr(String nome) {

		try {

			Object arquivo = CtrConfiguracao.class.getResourceAsStream(RESOURCES_PACKAGE_PATH + nome);
			this.frmConfiguracao.getPnlLogoSgbdr().removeAll();
			this.frmConfiguracao.getPnlLogoSgbdr().add(new UCImageLoader().loadImage(arquivo));
			this.frmConfiguracao.getPnlLogoSgbdr().revalidate();
			this.frmConfiguracao.getPnlLogoSgbdr().repaint();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(this.frmConfiguracao, "Falha ao carregar a imagem. Causa: " + e.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);

		}

	}

	// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
	// no formuláiro de configuração de perfil de conexão : implementação de evento
	// ao mudarl valor na JComboBox.
	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == this.frmConfiguracao.getCmbSgbdr()) {

			if (e.getStateChange() == ItemEvent.SELECTED) {

				// Obtem o nome do objeto (array de Strings).
				String nomeBD = e.getItemSelectable().getSelectedObjects()[0].toString();

				if (nomeBD.equals(EnSGBDR.MYSQL.toString())) {

					this.carregarImagemLogoSgbdr("MySql.png");

				} else if (nomeBD.equals(EnSGBDR.POSTGRESQL.toString())) {

					this.carregarImagemLogoSgbdr("PostgreSQL.png");

				}

			}

		}

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {

		// MISAEL - 08/07/2014 - carregar os dados ao iniciar o formulário.
		this.atualizarJTable();

		// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
		// no formuláiro de configuração de perfil de conexão : chmada de métodos.
		this.popularComboSgbdr();
		this.carregarImagemLogoSgbdr("MySql.png");

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {

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
	public void valueChanged(ListSelectionEvent e) {

		int indiceLinha = this.frmConfiguracao.getTable().getSelectedRow();

		if (indiceLinha == -1) {
			return;
		}

		voArquivoConfiguracao = new TblConfiguracao(voArquivosConfiguracoes).get(indiceLinha);
		this.frmConfiguracao.getTxtNome().setText(voArquivoConfiguracao.getNome());

		// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
		// no formuláiro de configuração de perfil de conexão : implementação do campo
		// ao mudar valor na JTable.
		this.frmConfiguracao.getCmbSgbdr().setSelectedItem(this.voArquivoConfiguracao.getSgbdr());

		this.frmConfiguracao.getTxtUrl().setText(voArquivoConfiguracao.getUrl());
		this.frmConfiguracao.getTxtDriver().setText(voArquivoConfiguracao.getDriver());
		this.frmConfiguracao.getTxtUsuario().setText(voArquivoConfiguracao.getUsuario());
		this.frmConfiguracao.getPswSenha().setText(new BoArquivoConfiguracao(voArquivoConfiguracao).senhaString());
		this.frmConfiguracao.getTxtDescricao().setText(voArquivoConfiguracao.getDescricao());
		this.frmConfiguracao.getFtfDataHora().setText(voArquivoConfiguracao.getDataHora());

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.frmConfiguracao.getBtnInicializar()) {

			this.inicializar();

		} else if (e.getSource() == this.frmConfiguracao.getBtnGravar()) {

			this.gravarArquivo();

		} else if (e.getSource() == this.frmConfiguracao.getBtnExcluir()) {

			this.excluirArquivo();

		} else if (e.getSource() == this.frmConfiguracao.getBtnConsultar()) {

			this.atualizarJTable();

		} else if (e.getSource() == this.frmConfiguracao.getBtnAjudaUrl()) {

			this.informarUrl(this.frmConfiguracao.getCmbSgbdr().getSelectedItem().toString());

		} else if (e.getSource() == this.frmConfiguracao.getBtnAjudaDriver()) {

			this.informarDriver(this.frmConfiguracao.getCmbSgbdr().getSelectedItem().toString());

		}

	}

	private void inicializar() {

		// MISAEL 27/07/2015 - removido por que esta função conflita com o componente
		// UCImageLoader.
		// FormUtils.inicializarCampos(this.frmConfiguracao);

		// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
		// no formuláiro de configuração de perfil de conexão : campos tiverem que ser
		// incluidos programaticamente.
		// por conta da falha do método FormUtils.inicializarCampos.
		this.frmConfiguracao.getTxtNome().setText("");
		// MISAEL 27/07/2015 - A combo getCmbSgbdr não será incializada aqui pois não
		// contem indice sem elemento!
		this.frmConfiguracao.getTxtUsuario().setText("");
		this.frmConfiguracao.getTxtUrl().setText("");
		this.frmConfiguracao.getTxtDriver().setText("");
		this.frmConfiguracao.getPswSenha().setText("");
		this.frmConfiguracao.getTxtDescricao().setText("");
		this.frmConfiguracao.getFtfDataHora().setText("");

	}

	private void informarUrl(String sgbdr) {

		String ajudaUrl = "";

		if (sgbdr.equals(EnSGBDR.MYSQL.toString())) {
			ajudaUrl = "jdbc:mysql://nome_servidor:3306/base_de_dados";
		} else if (sgbdr.equals(EnSGBDR.POSTGRESQL.toString())) {
			ajudaUrl = "jdbc:postgresql://nome_servidor:5432/base_de_dados";
		}

		this.frmConfiguracao.getTxtUrl().setText(ajudaUrl);

	}

	private void informarDriver(String sgbdr) {

		String ajudarDriver = "";

		if (sgbdr.equals(EnSGBDR.MYSQL.toString())) {
			ajudarDriver = "com.mysql.jdbc.Driver";
		} else if (sgbdr.equals(EnSGBDR.POSTGRESQL.toString())) {
			ajudarDriver = "org.postgresql.Driver";
		}

		this.frmConfiguracao.getTxtDriver().setText(ajudarDriver);

	}

	private void gravarArquivo() {

		String perfilConexao, sgbdr, usuario, url, driver, descricao, data_hora;
		char[] senha;

		perfilConexao = this.frmConfiguracao.getTxtNome().getText();

		// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
		// no formuláiro de configuração de perfil de conexão : inclusão do campo para
		// ser gravado no arquivo properties.
		sgbdr = this.frmConfiguracao.getCmbSgbdr().getSelectedItem().toString();
		usuario = this.frmConfiguracao.getTxtUsuario().getText();
		url = this.frmConfiguracao.getTxtUrl().getText();
		driver = this.frmConfiguracao.getTxtDriver().getText();
		senha = this.frmConfiguracao.getPswSenha().getPassword();
		descricao = this.frmConfiguracao.getTxtDescricao().getText();
		data_hora = new Utils().getDataHora();

		// Foram informados os campos obrigatórios?
		if (perfilConexao.equals("") || usuario.equals("") || url.equals("") || driver.equals("")
				|| senha.length == 0) {

			JOptionPane.showMessageDialog(this.frmConfiguracao, "Informe os campos obrigatórios para gravação.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
			return;

		}

		// MISAEL 27/07/2015 - removido por que esta função conflita com o componente
		// UCImageLoader.
		/*
		 * if( FormUtils.verificarCaracterIndevido(this.frmConfiguracao) ) {
		 * 
		 * JOptionPane.showMessageDialog(this.frmConfiguracao,
		 * "Não deve ser utilizado caracteres como ' e | nos campos deste formulário.",
		 * InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE); return;
		 * 
		 * }
		 */

		// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
		// no formuláiro de configuração de perfil de conexão : verificação programática
		// pois o método
		// FormUtils.verificarCaractereIndevido falhou.
		if (perfilConexao.contains((CharSequence) "|") || perfilConexao.contains((CharSequence) "'")) {

			JOptionPane.showMessageDialog(this.frmConfiguracao,
					"Não deve ser utilizado caracteres como ' e | no campo Perfil conexão.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
			return;

		}

		// Existe ocorrência de caractere indevido no valor do texto do campo chave?
		if (FormUtils.verificarCaracterIndevido(perfilConexao)) {

			JOptionPane.showMessageDialog(this.frmConfiguracao,
					"Não deve ser utilizado caracteres como:\n\n" + "\\ / : * ? \" < > | \n\n"
							+ " no campo Perfil de conexão.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
			return;

		}

		try {

			// MISAEL - 27/07/2015 - tarefa Implementação de Imagem do Logo, e chave SGDBR
			// no formuláiro de configuração de perfil de conexão : inclusão com atributo
			// sgbdr.
			VoArquivoConfiguracao voArquivoConfiguracao = new VoArquivoConfiguracao(perfilConexao, sgbdr, usuario, url,
					driver, senha, descricao, data_hora);
			daoArquivoConfiguracao.gravar(voArquivoConfiguracao);

			this.inicializar();
			this.atualizarJTable();

			JOptionPane.showMessageDialog(this.frmConfiguracao,
					"Perfil de conexão \"" + perfilConexao + "\" gravado com sucesso.", InfoProjeto.obterNomeEVersao(),
					JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e) {

			JOptionPane.showMessageDialog(this.frmConfiguracao, "Falha ao gravar arquivo.\n\n[" + e.getMessage() + "]",
					"Mensagem", JOptionPane.ERROR_MESSAGE);

		}

	}

	private void excluirArquivo() {

		// Recuperando os dados do formulário.
		String perfilConexao = this.frmConfiguracao.getTxtNome().getText();

		// Foram informados os campos obrigatórios?
		if (perfilConexao.equals("")) {

			JOptionPane.showMessageDialog(this.frmConfiguracao, "Informe um Perfil de conexão para exclusão.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.WARNING_MESSAGE);
			return;

		}

		int resultado = JOptionPane.showConfirmDialog(this.frmConfiguracao,
				"Deseja excluir o perfil \"" + perfilConexao + "\"?", InfoProjeto.obterNomeEVersao(),
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (resultado == JOptionPane.YES_OPTION) {

			try {

				// Existe o perfil de configuração a ser excluido?
				if (this.daoArquivoConfiguracao.consultar(perfilConexao) == null) {

					JOptionPane.showMessageDialog(this.frmConfiguracao,
							"O Perfil escolhido não foi localizado para exclusão.", InfoProjeto.obterNomeEVersao(),
							JOptionPane.INFORMATION_MESSAGE);
					return;

				}

				VoArquivoConfiguracao voArquivoConfiguracao = new VoArquivoConfiguracao();
				voArquivoConfiguracao.setNome(perfilConexao);

				this.daoArquivoConfiguracao.excluir(voArquivoConfiguracao);

				JOptionPane.showMessageDialog(this.frmConfiguracao,
						"Perfil \"" + perfilConexao + "\" excluido com sucesso.", InfoProjeto.obterNomeEVersao(),
						JOptionPane.INFORMATION_MESSAGE);

				this.inicializar();
				this.atualizarJTable();

			} catch (FileNotFoundException e) {

				JOptionPane.showMessageDialog(this.frmConfiguracao, "Arquivo inexistente.",
						InfoProjeto.obterNomeEVersao(), JOptionPane.ERROR_MESSAGE);

			} catch (IOException e) {

				JOptionPane.showMessageDialog(this.frmConfiguracao, "Falha ao consultar o arquivo.",
						InfoProjeto.obterNomeEVersao(), JOptionPane.ERROR_MESSAGE);

			}

		}

	}

	private void atualizarJTable() {

		try {

			this.voArquivosConfiguracoes = this.daoArquivoConfiguracao.consultar();

			if (this.voArquivosConfiguracoes != null) {

				this.frmConfiguracao.getTable().setModel(new TblConfiguracao(voArquivosConfiguracoes));
				this.frmConfiguracao.getTable().setDefaultRenderer(Object.class, new CelTblConfiguracao());

			}

		} catch (IOException e) {

			JOptionPane.showMessageDialog(this.frmConfiguracao, "Erro ao consultar.\n\nDetalhes: " + e.getMessage(),
					InfoProjeto.obterNomeEVersao(), JOptionPane.ERROR_MESSAGE);

		}
	}

	@SuppressWarnings("unused")
	private void consultarArquivo() {

		try {

			List<VoArquivoConfiguracao> listaArquivosConfiguracoes = this.daoArquivoConfiguracao.consultar();
			String dados = "";

			for (VoArquivoConfiguracao voArquivoConfiguracao : listaArquivosConfiguracoes) {

				dados += voArquivoConfiguracao.getNome() + " " + voArquivoConfiguracao.getDataHora() + "\n";

			}

			JOptionPane.showMessageDialog(this.frmConfiguracao, dados, InfoProjeto.obterNomeEVersao(),
					JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e) {

			JOptionPane.showMessageDialog(this.frmConfiguracao, "Falha ao consultar o arquivo.",
					InfoProjeto.obterNomeEVersao(), JOptionPane.ERROR_MESSAGE);

		}

	}
}
