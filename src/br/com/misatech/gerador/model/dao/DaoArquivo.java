package br.com.misatech.gerador.model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe respons�vel por gerar arquivos no disco, gravar e consultar informa��es.
 *
 * @author Misael C. Homem
 */
public class DaoArquivo {
	
	private FileReader     arquivoLeitura;
	private BufferedReader lerArq;
	private FileWriter     arquivoEscrita;
	
	/**
	 * Construtor padr�o da classe.
	 */
	public DaoArquivo() {}	
	
	// Faz a leitura de cont�udo de arquivo em disco
	public BufferedReader conectarLeitura(String nomeArquivo) throws IOException {
		
		this.arquivoLeitura = new FileReader(nomeArquivo);
		this.lerArq = new BufferedReader(arquivoLeitura);
		
		return this.lerArq;
		
	}
	
	// Faz a escrito de cont�udo em arquivo em disco.
	public FileWriter conectarEscrita(String nomeArquivo, boolean anexar) throws IOException {
		
		this.arquivoEscrita = new FileWriter(nomeArquivo, anexar);
		return this.arquivoEscrita;
		
	}
	
	public void desconectar(FileReader fr, BufferedReader br, FileWriter fw) throws IOException {
		
		if(fr != null) {
			fr.close();
		}
		
		if(br != null) {
			br.close();
		}
		
		if(fw != null) {
			fw.close();
		}
		
	}

}
