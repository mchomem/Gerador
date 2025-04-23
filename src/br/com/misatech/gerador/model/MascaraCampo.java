package br.com.misatech.gerador.model;

/**
 * Enumera��o para os tipos de m�scaras de campos.
 * 
 * @author Misael
 * 
 */
public enum MascaraCampo {

	enData, enHora, enDataHora, enCPF, enCNPJ, enRG;

	public String getValor() {

		switch (this) {

		case enData:
			return "##/##/####";

		case enHora:
			return "##:##:##";

		case enDataHora:
			return "##/##/#### ##:##:##";

		case enCPF:
			return "##.###.###-##";

		case enCNPJ:
			return "##.###.###/####-##";

		default:
			return "";

		}
	}

}
