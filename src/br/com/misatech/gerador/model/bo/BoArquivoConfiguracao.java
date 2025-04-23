package br.com.misatech.gerador.model.bo;

import br.com.misatech.gerador.model.vo.VoArquivoConfiguracao;

public class BoArquivoConfiguracao {

	private VoArquivoConfiguracao voArquivoConfiguracao;

	public BoArquivoConfiguracao(VoArquivoConfiguracao voArquivoConfiguracao) {

		this.voArquivoConfiguracao = voArquivoConfiguracao;

	}

	public String senhaString() {

		String senha = "";

		for (int i = 0; i < this.voArquivoConfiguracao.getSenha().length; i++) {

			senha += this.voArquivoConfiguracao.getSenha()[i];

		}

		return senha;

	}

	public char[] senhaChar(String valor) {

		char[] senha = new char[valor.length()];

		for (int i = 0; i < valor.length(); i++) {

			senha[i] = valor.charAt(i);

		}

		return senha;

	}

}
