package br.com.misatech.gerador.model.vo;

import java.util.List;

/**
 * É o molde do objeto do banco.	
 * 
 * @author Misael C. Homem
 *
 */
public class VoMetadado {
	
	private String nomeSGBD;
	private String nomeBanco;
	private List<VoTabela> tabelas;
	
	public VoMetadado() {}
	
	public VoMetadado(String nomeSGBD, String nomeBanco, List<VoTabela> tabelas) {
		
		this.nomeSGBD  = nomeSGBD;
		this.nomeBanco = nomeBanco;
		this.tabelas   = tabelas;
		
	}
	
	public String getNomeSGBD() {
		return nomeSGBD;
	}

	public void setNomeSGBD(String nomeSGBD) {
		this.nomeSGBD = nomeSGBD;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public List<VoTabela> getTabelas() {
		return tabelas;
	}

	public void setTabelas(List<VoTabela> tabelas) {
		this.tabelas = tabelas;
	}
		
}
