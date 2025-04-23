package br.com.misatech.gerador.model.vo;

public class VoArquivoConfiguracao {

	private String nome;
	private String sgbdr;
	private String usuario;
	private String url;
	private String driver;
	private char[] senha;
	private String descricao;
	private String dataHora;

	public VoArquivoConfiguracao() {
	}

	public VoArquivoConfiguracao(String nome, String sgbdr, String usuario, String url, String driver, char[] senha,
			String descricao, String dataHora) {

		this.nome = nome;
		this.sgbdr = sgbdr;
		this.usuario = usuario;
		this.url = url;
		this.driver = driver;
		this.senha = senha;
		this.descricao = descricao;
		this.dataHora = dataHora;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSgbdr() {
		return sgbdr;
	}

	public void setSgbdr(String sgbdr) {
		this.sgbdr = sgbdr;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public char[] getSenha() {
		return senha;
	}

	public void setSenha(char[] senha) {
		this.senha = senha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	@Override
	public String toString() {
		return nome;
	}

}
