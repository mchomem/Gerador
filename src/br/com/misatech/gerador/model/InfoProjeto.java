package br.com.misatech.gerador.model;


/**
 * Classe cujo objetivo � disponibilizar informa��es b�sicas
 * como nome e versionamento do projeto.
 * 
 * Pesquisas em:
 * http://www.hardware.com.br/artigos/sistemas-versoes/
 * http://blog.luiscoms.com.br/controle-de-versao/semantic-versioning-uma-forma-de-padronizacao-de-versionamento-de-software.lcs
 * 
 * @author Misael C. Homem
 *
 */
public class InfoProjeto {
	
	private static final String NOME_PROJETO = "Gerador";
	private static final String NOME_PRODUTO = "Jelly Class";
	private static final String VERSAO_MAIOR = "1";   // Grande volume de modifica��es.
	private static final String VERSAO_MENOR = "01";  // Pequeno volume de mofica��es.
	private static final String CORRECAO     = "000"; // Corre��es de bug's do sistema.
	private static final boolean RC          = false; // Define se a vers�o � a release candidate ou n�o.
	
	/**
	 * Construtor padr�o da classe.
	 */
	public InfoProjeto() {}
	
	/**
	 * Disponibiliza o nome e o versionamento do projeto.
	 * @return Retorna uma String com as informa��es de nome e vers�o do projeto.
	 */
	public static String obterNomeEVersao() {
		
		return NOME_PRODUTO
				+ " - " + VERSAO_MAIOR
				+ "." + VERSAO_MENOR
				+ "." + CORRECAO
				+ (RC ? " RC" : "");
		
	}
	
	public static String obterNomeProjeto() {
		
		return NOME_PROJETO;
		
	}
	
	public static String obterNomeProduto() {
		
		return NOME_PRODUTO;
		
	}
	
	public static String obterVersao() {
		
		return VERSAO_MAIOR
				+ "." + VERSAO_MENOR
				+ "." + CORRECAO;
		
	}

}
