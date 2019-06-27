package br.com.misatech.gerador.model;

import br.com.misatech.gerador.model.en.EnSGBDR;
import br.com.misatech.gerador.model.en.EnTecnologia;

/**
 * Classe responsável por manter estado de algumas variáveis.
 * @author Misael C. Homem
 *
 */
public class SessaoGerador {

	public static boolean      gerarComentário;
	public static EnTecnologia enTecnologiaEscolhida;
	public static EnSGBDR      enSGBDREscolhido;
	public static String       pacote;
	public static String       nomeProjeto;
	
}
