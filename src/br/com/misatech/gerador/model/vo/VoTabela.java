package br.com.misatech.gerador.model.vo;

import java.util.List;

public class VoTabela {
	
	private String             nomeTabela;
	private List<VoColuna>     colunas;
	private List<VoForeignKey> voForeignKey;
	private boolean            gerar;
	
	public VoTabela() {}
	
	public VoTabela(String nomeTabela, List<VoColuna> colunas, List<VoForeignKey> voForeignKey, boolean gerar) {
		
		this.nomeTabela   = nomeTabela;
		this.colunas      = colunas;
		this.voForeignKey = voForeignKey;
		this.gerar        = gerar;
		
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public List<VoColuna> getColunas() {
		return colunas;
	}

	public void setColunas(List<VoColuna> colunas) {
		this.colunas = colunas;
	}
	
	public List<VoForeignKey> getVoForeignKey() {
		return voForeignKey;
	}
	
	public void setVoForeignKey(List<VoForeignKey> voForeignKey) {
		this.voForeignKey = voForeignKey;
	}

	public boolean isGerar() {
		return gerar;
	}

	public void setGerar(boolean gerar) {
		this.gerar = gerar;
	}

}
