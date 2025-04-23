package br.com.misatech.gerador.model.vo;

// MISAEL - 07/10/2015 - implementação de um tipo para abrigar os dados de foreign key para montar associação na ORM.
public class VoForeignKey {

	private String nomeTabelaPai;
	private String nomeColunaPK;
	private String nomeColunaFK;
	private String nomeFK;

	public VoForeignKey() {
	}

	public VoForeignKey(String nomeTabelaPai, String nomeColunaPK, String nomeColunaFK, String nomeFK) {

		this.nomeTabelaPai = nomeTabelaPai;
		this.nomeColunaPK = nomeColunaPK;
		this.nomeColunaFK = nomeColunaFK;
		this.nomeFK = nomeFK;

	}

	public String getNomeTabelaPai() {
		return nomeTabelaPai;
	}

	public void setNomeTabelaPai(String nomeTabelaPai) {
		this.nomeTabelaPai = nomeTabelaPai;
	}

	public String getNomeColunaPK() {
		return nomeColunaPK;
	}

	public void setNomeColunaPK(String nomeColunaPK) {
		this.nomeColunaPK = nomeColunaPK;
	}

	public String getNomeColunaFK() {
		return nomeColunaFK;
	}

	public void setNomeColunaFK(String nomeColunaFK) {
		this.nomeColunaFK = nomeColunaFK;
	}

	public String getNomeFK() {
		return nomeFK;
	}

	public void setNomeFK(String nomeFK) {
		this.nomeFK = nomeFK;
	}
}
