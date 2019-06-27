package br.com.misatech.gerador.model.bo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.com.misatech.gerador.model.dao.DaoArquivo;

public class BoArquivo {

	DaoArquivo daoArquivo;
	
	public BoArquivo() {
		
		this.daoArquivo = new DaoArquivo();
		
	}
	
	/**
	 * Escreve o contéudo dos arquivos no caminho informado (já existente).
	 * @param nomeArquivo
	 * @param conteudo
	 * @param caminho
	 * @throws IOException
	 */
	public void gravar(String nomeArquivo, String conteudo, String caminho) throws IOException {
		
		FileWriter fw = daoArquivo.conectarEscrita(caminho + "/" + nomeArquivo, false);
		PrintWriter gravaArq = new PrintWriter(fw);
		gravaArq.println(conteudo);
		daoArquivo.desconectar(null, null, fw);
		
	}
	
}
