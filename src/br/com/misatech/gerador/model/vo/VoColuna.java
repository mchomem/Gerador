package br.com.misatech.gerador.model.vo;

import br.com.misatech.gerador.model.en.EnTipoDado;

public class VoColuna {

	private String nomeColuna;
	private EnTipoDado tipoDado;
	private boolean primaryKey; // Esse campo pode continar sendo um boolean
	// TODO analisar a criação de um tipo ForeignKey contendo nome do campo e nome
	// da tabela apontada (tabela pai).
	private boolean foreignKey; // Será necessário o nome da tabela Pai ao qual esse campo se referencia.
	private boolean notNull;
	private float tamanho;

	public VoColuna() {
	}

	public VoColuna(String nomeColuna, EnTipoDado tipoDado, boolean primaryKey, boolean foreignKey, boolean notNull,
			float tamanho) {

		this.nomeColuna = nomeColuna;
		this.tipoDado = tipoDado;
		this.primaryKey = primaryKey;
		this.foreignKey = foreignKey;
		this.notNull = notNull;
		this.tamanho = tamanho;

	}

	public String getNomeColuna() {
		return nomeColuna;
	}

	public void setNomeColuna(String nomeColuna) {
		this.nomeColuna = nomeColuna;
	}

	public EnTipoDado getTipoDado() {
		return tipoDado;
	}

	public void setTipoDado(EnTipoDado tipoDado) {
		this.tipoDado = tipoDado;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public float getTamanho() {
		return tamanho;
	}

	public void setTamanho(float tamanho) {
		this.tamanho = tamanho;
	}

}
