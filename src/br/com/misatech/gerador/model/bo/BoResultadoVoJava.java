package br.com.misatech.gerador.model.bo;

import java.util.ArrayList;
import java.util.List;

import br.com.misatech.gerador.model.SessaoGerador;
import br.com.misatech.gerador.model.en.EnSGBDR;
import br.com.misatech.gerador.model.en.EnTipoDado;
import br.com.misatech.gerador.model.vo.VoColuna;
import br.com.misatech.gerador.model.vo.VoForeignKey;
import br.com.misatech.gerador.model.vo.VoTabela;

// TODO refatorar o nome da classe ou dividir corretamente o met�do formatarClasseNegocioJava pode pertencer a uma BoResultadoBo.
public class BoResultadoVoJava {
	
	public BoResultadoVoJava() {}
	
	// MISAEL - 04/08/2015 - corre��o de bug, inser��o do \r\n em cada quebra para leitura dos fontes no Notepad do Windows.
	
	public String formatarClasseEntidadeJava(VoTabela voTabela) {
		
		// MISAEL - 04/08/2015 - implmenta��o de vari�veis de sessao para o gerador.
		boolean gerarComentario   = SessaoGerador.gerarComent�rio;
		
		StringBuilder codigoFonte = new StringBuilder();
		String        nomeClasse  = this.ajustarNomeClassePropriedadeJava(voTabela.getNomeTabela());
		
		// MISAEL - 12/08/2015 - removido para deixar o fonte mais limpo.
		// codigoFonte.append("/* C�digo gerado automaticamente pelo Jelly Class. */\r\n\r\n");
		
		if(gerarComentario) {
			// Inserir javadoc para a classe.
			codigoFonte.append("/**\r\n");
			codigoFonte.append(" * Classe da entidade " + nomeClasse + ".\r\n");
			codigoFonte.append(" *\r\n");
			codigoFonte.append(" * @author Jelly Class\r\n");
			codigoFonte.append(" *\r\n");
			codigoFonte.append(" */\r\n");
		}
		
		// Inserir declara��o da classe.
		codigoFonte.append("public class " + nomeClasse  + " {\r\n\r\n");
		
		// Inserir declara��o de atributos/campos de classe.
		for(VoColuna voColuna : voTabela.getColunas()) {
			
			// Flag para os casos em que o campo � uma FK
			// Ou seja, na ORM esse campo n�o � impresso com seu tipo bruto.
			// E sim com o tipo da entidade ao qual aponta.
			boolean substitui = false;
			
			// A tabela tem chave(s) estrangeira(s)
			if(voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for(VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela, substitui pelo tipo da tabela Pai.
					if(voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {
						
						substitui = true;
						codigoFonte.append(this.identarComEspaco(1)
								+ "private "
								+ this.ajustarNomeClassePropriedadeJava(voForeignKey.getNomeTabelaPai())
								+ " "
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + ";\r\n");
						
					}
					
				}
				
			}
			
			// Se o campo que � chaveFK, n�o imprime o tipo bruto.
			if(substitui) {
				continue;
			}
			
			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			codigoFonte.append(this.identarComEspaco(1) + "private " + this.deParaTiposBancoParaJava(voColuna) + " " + nomeAtributo + ";\r\n");
			
		}
		
		if(gerarComentario) {
			
			// Inserir javadoc para o construtor vazio.
			codigoFonte.append("\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "/**\r\n");
			codigoFonte.append(this.identarComEspaco(1) + " * Cria uma nova inst�ncia de " + nomeClasse + ".\r\n");
			codigoFonte.append(this.identarComEspaco(1) + " */");
			
		}
		
		codigoFonte.append("\r\n");
		// Inserir construtor vazio.
		codigoFonte.append(this.identarComEspaco(1) + "public " + nomeClasse + "() {}\r\n\r\n");
		
		// Coment�rio para o construtor sobrecarregado.
		if(gerarComentario) {
			
			// Inserir javadoc para o construtor sobrecarregado.
			codigoFonte.append(this.identarComEspaco(1) + "/**\r\n");
			codigoFonte.append(this.identarComEspaco(1) + " * Cria uma nova inst�ncia de " + nomeClasse + ".\r\n");
			codigoFonte.append(this.identarComEspaco(1) + " *\r\n");
			
			for(VoColuna voColuna : voTabela.getColunas()) {
				
				// Flag para os casos em que o campo � uma FK
				// Ou seja, na ORM esse campo n�o � impresso com seu tipo bruto.
				// E sim com o tipo da entidade ao qual aponta.
				boolean substitui = false;
				
				// A tabela tem chave(s) estrangeira(s)
				if(voTabela.getVoForeignKey() != null) {
					// Varre as chaves estrangeiras
					for(VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
						// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela, substitui pelo tipo da tabela Pai.
						if(voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {
							
							substitui = true;
							codigoFonte.append(this.identarComEspaco(1)	+ " * @param " + this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + "\r\n");
							
						}
						
					}
					
				}
				
				// Se o campo que � chaveFK, n�o imprime o tipo bruto.
				if(substitui) {
					continue;
				}
				
				String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
				codigoFonte.append(this.identarComEspaco(1) + " * @param " + nomeAtributo + "\r\n");
				
			}
			
			codigoFonte.append(this.identarComEspaco(1) + " */\r\n");
			
		}
		
		// Inserir construtor sobrecarregado.
		codigoFonte.append(this.identarComEspaco(1) + "public " + nomeClasse + "(");
		int i = 0;
		
		for(VoColuna voColuna : voTabela.getColunas()) {
			
			i++;
			
			boolean substitui = false;
			
			// A tabela tem chave(s) estrangeira(s)
			if(voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for(VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela, substitui pelo tipo da tabela Pai.
					if(voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {
						
						substitui = true;
						
						// Se for o �ltimo atributo n�o insere o separador de v�rgula.
						if(i == voTabela.getColunas().size()) {
							codigoFonte.append(this.ajustarNomeClassePropriedadeJava(voForeignKey.getNomeTabelaPai())
								+ " "
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()));
						} else {
							codigoFonte.append(this.ajustarNomeClassePropriedadeJava(voForeignKey.getNomeTabelaPai())
								+ " "
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + ", ");
						}
						
					}
					
				}
				
			}
			
			// Se o campo que � chaveFK, n�o imprime o tipo bruto no par�metro do construtor sobrecarregado.
			if(substitui) {
				continue;
			}
			
			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			
			// Se for o �ltimo atributo n�o insere o separador de v�rgula.
			if(i == voTabela.getColunas().size()) {
				codigoFonte.append(this.deParaTiposBancoParaJava(voColuna) + " "  + nomeAtributo);
			} else {
				codigoFonte.append(this.deParaTiposBancoParaJava(voColuna) + " " + nomeAtributo + ", ");
			}
		}
		
		codigoFonte.append(") {\r\n");
		
		for(VoColuna voColuna : voTabela.getColunas()) {
			
			boolean substitui = false;
			
			// A tabela tem chave(s) estrangeira(s)
			if(voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for(VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela, substitui pelo tipo da tabela Pai.
					if(voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {
						
						substitui = true;
						codigoFonte.append(this.identarComEspaco(2)
								+ "this."
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai())
								+ " = "
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + ";\r\n");
						
					}
					
				}
				
			}
			
			// Se o campo que � chaveFK, n�o imprime o tipo bruto.
			if(substitui) {
				continue;
			}
			
			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			codigoFonte.append(this.identarComEspaco(2) + "this." + nomeAtributo + " = " + nomeAtributo + ";\r\n");
			
		}
		
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
		
		
		if(gerarComentario) {
			// Inserir coment�rio simples para getters e setters.
			codigoFonte.append(this.identarComEspaco(1) + "/* Encapsulamento dos campos da classe (Getters e Setters). */\r\n");
		}
		
		
		// Inserir Getters e setters
		for(VoColuna voColuna : voTabela.getColunas()) {
			
			boolean substitui = false;
			
			// A tabela tem chave(s) estrangeira(s)
			if(voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for(VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela, substitui pelo tipo da tabela Pai.
					if(voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {
						
						substitui = true;
						
						String nomeMetodoFK            = this.ajustarNomeClassePropriedadeJava(voForeignKey.getNomeTabelaPai());
						String nomeAtributoFK          = this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai());
						String nomeAtributoParametroFK = this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai());
						
						// get
						codigoFonte.append(this.identarComEspaco(1) + "public " + nomeMetodoFK + " get" + nomeMetodoFK + "() {\r\n");
						codigoFonte.append(this.identarComEspaco(2) + "return " + nomeAtributoFK + ";\r\n");
						codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
						
						// set
						codigoFonte.append(this.identarComEspaco(1) + "public void set" + nomeMetodoFK + "(" + nomeMetodoFK + " " + nomeAtributoParametroFK + ") {\r\n");
						codigoFonte.append(this.identarComEspaco(2) + "this." + nomeMetodoFK.toLowerCase() + " = " + nomeAtributoParametroFK + ";\r\n");
						codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
						
					}
					
				}
				
			}
			
			// Se o campo que � chaveFK, n�o imprime o tipo bruto.
			if(substitui) {
				continue;
			}
			
			
			// get
			String nomeMetodo            = this.ajustarNomeClassePropriedadeJava(voColuna.getNomeColuna());
			String nomeAtributo          = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			String nomeVariavelParametro = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			
			String prefixo = (voColuna.getTipoDado() ==  EnTipoDado.JAVA_BOOLEAN) ? "is" : "get";
			
			codigoFonte.append(this.identarComEspaco(1) + "public " + this.deParaTiposBancoParaJava(voColuna) + " " + prefixo + nomeMetodo + "() {\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "return " + nomeAtributo + ";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
			
			// set
			codigoFonte.append(this.identarComEspaco(1) + "public void set" + nomeMetodo + "(" + this.deParaTiposBancoParaJava(voColuna) + " " + nomeVariavelParametro + ") {\r\n" );
			codigoFonte.append(this.identarComEspaco(2) + "this." + nomeAtributo + " = " + nomeVariavelParametro + ";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
			
		}
		
		codigoFonte.append("}");
		
		String pacote = "";
		
		// MISAEL - 12/08/2015 - adicionado o caminho para instru��o package do fonte;
		if(SessaoGerador.pacote.length() > 0) {
			pacote = "package " + SessaoGerador.pacote + ";\r\n\r\n";
		}
		 
		String codigoFormatado = pacote + this.inserirImportsJava(voTabela) + codigoFonte.toString();
		
		return codigoFormatado;
		
	}
	
	
	// MISAEL - 17/08/2015 - m�todo para formatar o c�digo fonte para as classes de neg�cio.
	public String formatarClasseNegocioJava(VoTabela voTabela) {
		
		StringBuilder codigoFonte = new StringBuilder();
		String        nomeClasse  = this.ajustarNomeClassePropriedadeJava(voTabela.getNomeTabela()) + "Business";
		String        entidade    = this.ajustarNomeClassePropriedadeJava(voTabela.getNomeTabela());
		
		// Assinatura de classe.
		codigoFonte.append("public class " + nomeClasse + " {\r\n\r\n");
		
		// Campo do tipo da entidade.
		codigoFonte.append(this.identarComEspaco(1) + "private " + entidade + " " + entidade.toLowerCase() + ";\r\n\r\n");
	
		// Construtor da classe
		codigoFonte.append(this.identarComEspaco(1) + "public " + nomeClasse + "() {}\r\n\r\n");
	
		// 1� Sobrecarga do construtor.
		codigoFonte.append(this.identarComEspaco(1) + "public " + nomeClasse + "(" + entidade + " " + entidade.toLowerCase() + ") {\r\n" );
		codigoFonte.append(this.identarComEspaco(2) + "this." + entidade.toLowerCase() + " = " + entidade.toLowerCase() + ";\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
	
		// get.
		codigoFonte.append(this.identarComEspaco(1) + "public " + entidade + " get" + entidade + "() {\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "return " + entidade.toLowerCase() + ";\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
	
		// set.
		codigoFonte.append(this.identarComEspaco(1) + "public void set" + entidade + "(" + entidade + " " + entidade.toLowerCase() + ") {\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "this." + entidade.toLowerCase() + " = " + entidade.toLowerCase() + ";\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
		
		// Fim da classe.
		codigoFonte.append("}");
		
		String pacote = "";
		
		if(SessaoGerador.pacote.length() > 0) {
			pacote = "package " + SessaoGerador.pacote + ";\r\n\r\n";
		}
		
		String codigoFormatado = pacote + codigoFonte.toString(); 
		
		return codigoFormatado;
	}
	
	// MISAEL - 08/10/2015 - m�todo para formatar o c�digo fonte para as classes DAO. 
	public String formatarClasseDaoJava(VoTabela voTabela) {
		
		StringBuilder codigoFonte = new StringBuilder();
		String        nomeClasse  = this.ajustarNomeClassePropriedadeJava(voTabela.getNomeTabela()) + "Dao";
		String        entidade    = this.ajustarNomeClassePropriedadeJava(voTabela.getNomeTabela());
		
		// Imports b�sicos para a classe Dao.
		codigoFonte.append("import java.sql.Connection;\r\n");
		codigoFonte.append("import java.sql.PreparedStatement;\r\n");
		codigoFonte.append("import java.sql.ResultSet;\r\n");
		codigoFonte.append("import java.sql.SQLException;\r\n");
		codigoFonte.append("import java.util.ArrayList;\r\n");
		codigoFonte.append("import java.util.List;\r\n\r\n");
		
		// Assinatura de classe.
		codigoFonte.append("public class " + nomeClasse + " {\r\n\r\n");
		
		// Campo para a classe Dao gen�rica (de conex�o)
		codigoFonte.append(this.identarComEspaco(1) + "private Dao dao;\r\n\r\n");
		
		// Construtor padr�o.
		codigoFonte.append(this.identarComEspaco(1) + "public " + nomeClasse + "() {\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "this.dao = new Dao();\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
		
		
			////////////////////////////////////////////////////////////////////////////////////////////////
			// In�cio do m�todo inserir.
			codigoFonte.append(this.identarComEspaco(1)
					+ "public void inserir"
					+ "(" + entidade + " " + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ")"
					+ " throws ClassNotFoundException, SQLException {\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "Connection conn        = this.dao.conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "PreparedStatement pstm = null;\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "StringBuilder sql      = new StringBuilder();\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"INSERT INTO " + voTabela.getNomeTabela().toUpperCase() + "\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"(");
			
			int i = 0;
			
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				
				// Se � a �ltima coluna n�o insere a v�rgula.
				if(i == voTabela.getColunas().size()) {
					codigoFonte.append(voColuna.getNomeColuna().toUpperCase());
				} else {
					codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + ", ");
				}
			}
			
			codigoFonte.append(")\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"VALUES\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"(");
			
			// Inicia o valor do contador para reaproveitamento na itera��o a seguir.
			i = 0;
			
			while(i < voTabela.getColunas().size()) {
				i++;
				// Se � a �ltima coluna, n�o insere a v�rgula.
				if(i == voTabela.getColunas().size()) {
					codigoFonte.append("?");
				} else {
					codigoFonte.append("?" + ", ");
				}
			}
			
			codigoFonte.append(")\");\r\n\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "pstm = conn.prepareStatement(sql.toString());\r\n\r\n");
			
			i = 0;
			for(VoColuna voColuna : voTabela.getColunas()) {
				
				i++;
				String prefixo = (voColuna.getTipoDado() == EnTipoDado.JAVA_BOOLEAN) ? "is" : "get";
				
				codigoFonte.append(this.identarComEspaco(2) + "pstm.setObject(" + i + ", "
						+ this.ajustarNomeAtributo(voTabela.getNomeTabela())
						+ "." + prefixo + this.ajustarNomeClassePropriedadeJava(voColuna.getNomeColuna())
						+ "());\r\n");
				
			}
			
			codigoFonte.append("\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "pstm.executeUpdate();\r\n\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "this.dao.desconectar(conn, pstm, null);\r\n\r\n");
			
			// Fim do m�todo inserir.
			codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
			////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			////////////////////////////////////////////////////////////////////////////////////////////////
			// Inc�o do m�todo alterar.
			codigoFonte.append(this.identarComEspaco(1)
					+ "public void alterar"
					+ "(" + entidade + " " + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ")"
					+ " throws ClassNotFoundException, SQLException {\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "Connection conn        = this.dao.conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "PreparedStatement pstm = null;\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "StringBuilder sql      = new StringBuilder();\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"UPDATE " + voTabela.getNomeTabela().toUpperCase()  + "\");\r\n");
			
			// Campos para da cl�usula SET
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" SET ");
			i = 0;
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				
				// Se for chave prim�ria n�o vai no SET do UPDATE..
				if(voColuna.isPrimaryKey()) {
					continue;
				}
				
				// Se � a �ltima coluna n�o insere a v�rgula.
				if(i == voTabela.getColunas().size()) {
					codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + " = ?");
				} else {
					codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + " = ?, ");
				}
			}
			codigoFonte.append("\");\r\n");
			
			// Cl�usula WHERE
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" WHERE\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" ");
			
			List<String> colunasPKAlterar = new ArrayList<String>();
 			i = 0;
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				// Existe um campo chave prim�ria? Quem �?
				if(voColuna.isPrimaryKey()) {
					// Insere os campos PK para a cl�usula WHERE.
					colunasPKAlterar.add(voColuna.getNomeColuna().toUpperCase());
				}
			}
			
			i = 0;
			for(String colPK : colunasPKAlterar ) {
				i++;
				
				if(i == colunasPKAlterar.size()) {
					codigoFonte.append(colPK + " = ?");
				} else {
					codigoFonte.append(colPK + " = ? AND ");
				}
				
			}
			codigoFonte.append("\");\r\n\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "pstm = conn.prepareStatement(sql.toString());\r\n\r\n");
			
			i = 0;
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				
				// Se for chave prim�ria n�o vai no SET do UPDATE..
				if(voColuna.isPrimaryKey()) {
					i--; // descontar de i, caso a PK n�o esteja em sequ�ncia.
					continue;
				}
				
				String prefixo = (voColuna.getTipoDado() == EnTipoDado.JAVA_BOOLEAN) ? "is" : "get";
				
				codigoFonte.append(this.identarComEspaco(2) + "pstm.setObject(" + i + ", "
						+ this.ajustarNomeAtributo(voTabela.getNomeTabela())
						+ "." + prefixo + this.ajustarNomeClassePropriedadeJava(voColuna.getNomeColuna())
						+ "());\r\n");				
				
			}
			
			// N�o ZERAR a vari�vel "i" aqui neste ponto, manter o seu valor para manter a sequ�ncia dos par�mentros do PreparedStatement
			for(String colPK : colunasPKAlterar ) {
				i++;
				codigoFonte.append(this.identarComEspaco(2) + "pstm.setObject(" + i + ", "
						+ this.ajustarNomeAtributo(voTabela.getNomeTabela())
						+ ".get" + this.ajustarNomeClassePropriedadeJava(colPK)
						+ "());\r\n");
				
			}
			
			codigoFonte.append("\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "pstm.executeUpdate();\r\n\r\n");			
			codigoFonte.append(this.identarComEspaco(2) + "this.dao.desconectar(conn, pstm, null);\r\n\r\n");
			
			// Fim do m�todo alterar.
			codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
			////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			////////////////////////////////////////////////////////////////////////////////////////////////
			// In�cio do m�todo excluir.
			codigoFonte.append(this.identarComEspaco(1)
					+ "public void excluir"
					+ "(" + entidade + " " + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ")"
					+ " throws ClassNotFoundException, SQLException {\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "Connection conn        = this.dao.conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "PreparedStatement pstm = null;\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "StringBuilder sql      = new StringBuilder();\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"DELETE FROM " + voTabela.getNomeTabela().toUpperCase()  + "\");\r\n");
			
			// Cl�usula WHERE
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" WHERE\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" ");
			
			List<String> colunasPKExcluir = new ArrayList<String>();
 			i = 0;
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				// Existe um campo chave prim�ria? Quem �?
				if(voColuna.isPrimaryKey()) {
					// Insere os campos PK para a cl�usula WHERE.
					colunasPKExcluir.add(voColuna.getNomeColuna().toUpperCase());
				}
			}
			
			i = 0;
			for(String colPK : colunasPKExcluir ) {
				i++;
				
				if(i == colunasPKExcluir.size()) {
					codigoFonte.append(colPK + " = ?");
				} else {
					codigoFonte.append(colPK + " = ? AND ");
				}
				
			}
			codigoFonte.append("\");\r\n\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "pstm = conn.prepareStatement(sql.toString());\r\n\r\n");
			
			i = 0;
			for(String colPK : colunasPKExcluir ) {
				i++;
				codigoFonte.append(this.identarComEspaco(2) + "pstm.setObject(" + i + ", "
						+ this.ajustarNomeAtributo(voTabela.getNomeTabela())
						+ ".get" + this.ajustarNomeClassePropriedadeJava(colPK)
						+ "());\r\n");
				
			}
			codigoFonte.append("\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "pstm.executeUpdate();\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "this.dao.desconectar(conn, pstm, null);\r\n\r\n");		
			
			// Fim do m�todo excluir.
			codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
			////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			
			////////////////////////////////////////////////////////////////////////////////////////////////
			// In�cio do m�todo consultar.
			codigoFonte.append(this.identarComEspaco(1)
					+ "public List<" + entidade + "> consultar()"
					+ " throws ClassNotFoundException, SQLException {\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "Connection conn        = this.dao.conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "PreparedStatement pstm = null;\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "List<" + entidade + "> "
					+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "s"
					+ " = new ArrayList<" + entidade + ">();\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "ResultSet rs           = null;\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "StringBuilder sql      = new StringBuilder();\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\"SELECT\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" ");
			
			i = 0;
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				
				// � a �ltima coluna da tabela?
				if(i == voTabela.getColunas().size()) {
					codigoFonte.append(voColuna.getNomeColuna().toUpperCase());
				} else  {
					codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + ", ");
				}
				
			}
			codigoFonte.append("\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" FROM\");\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "sql.append(\" " + voTabela.getNomeTabela().toUpperCase() + "\");\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "pstm = conn.prepareStatement(sql.toString());\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "rs   = pstm.executeQuery();\r\n\r\n");
			
			// La�o While para alimentar a Lista com os tipos do objeto de entidade (tipos complexos do usu�rios).
			codigoFonte.append(this.identarComEspaco(2) + "while(rs.next()) {\r\n\r\n");
			
			i = 0;
			// Iterar sobre as colunas da tabela para montar o preparedStatement.
			for(VoColuna voColuna : voTabela.getColunas()) {
				i++;
				
				// Imprime o objeto complexo do tipo do usu�rio.
				if(i == 1) {
					codigoFonte.append(this.identarComEspaco(3)
							+ entidade + " " + this.ajustarNomeAtributo(voTabela.getNomeTabela())
							+ " = new " + entidade + "();\r\n\r\n");
				}
				
				// � a �ltima coluna da tabela?
				if(i == voTabela.getColunas().size()) {
					codigoFonte.append(this.identarComEspaco(3) + this.ajustarNomeAtributo(voTabela.getNomeTabela())
							+ ".set" + this.ajustarNomeClassePropriedadeJava(voColuna.getNomeColuna())
							+ "(rs.get" + this.ajustarNomeClassePropriedadeJava(this.deParaTiposBancoParaJava(voColuna))
							+ "(\"" + voColuna.getNomeColuna().toUpperCase() + "\"));\r\n\r\n");

					codigoFonte.append(this.identarComEspaco(3) + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "s"
							+ ".add(" + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ");\r\n\r\n");
				} else {
					codigoFonte.append(this.identarComEspaco(3) + this.ajustarNomeAtributo(voTabela.getNomeTabela())
							+ ".set" + this.ajustarNomeClassePropriedadeJava(voColuna.getNomeColuna())
							+ "(rs.get" + this.ajustarNomeClassePropriedadeJava(this.deParaTiposBancoParaJava(voColuna))
							+ "(\"" + voColuna.getNomeColuna().toUpperCase() + "\"));\r\n");
				}
				
			}
			
			codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "this.dao.desconectar(conn, pstm, rs);\r\n\r\n");
			
			codigoFonte.append(this.identarComEspaco(2) + "return " + this.ajustarNomeAtributo(voTabela.getNomeTabela())  + "s;\r\n\r\n");
			
			// Fim do m�todo consultar.
			codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
			////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			
		// Fim da classe Dao.
		codigoFonte.append("}");
		
		String pacote = "";
		
		if(SessaoGerador.pacote.length() > 0) {
			pacote = "package " + SessaoGerador.pacote + ";\r\n\r\n";
		}
		
		String codigoFormatado = pacote + codigoFonte.toString();
		return codigoFormatado;
	}
	
	public String formatarClasseDaoConexaoJava() {
		
		StringBuilder codigoFonte = new StringBuilder();
		
		codigoFonte.append("import java.sql.Connection;\r\n");
		codigoFonte.append("import java.sql.DriverManager;\r\n");
		codigoFonte.append("import java.sql.PreparedStatement;\r\n");
		codigoFonte.append("import java.sql.ResultSet;\r\n");
		codigoFonte.append("import java.sql.SQLException;\r\n\r\n");
		
		// Assinatura da classe.
		codigoFonte.append("public class Dao {\r\n\r\n");
		
		// Campos/atributos da classe.
		// MySql ou PostreSQL ?
		if(SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(1) + "private static final String URL          = \"jdbc:mysql://localhost:3306/<BASE DE DADOS>\";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "private static final String DRIVER_CLASS = \"com.mysql.jdbc.Driver\";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "private static final String USER         = \"<USU�RIO>\";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "private static final String PASS         = \"<SENHA>\";\r\n\r\n");
		} else if(SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(1) + "private static final String URL          = \"jdbc:postgresql://localhost:5432/<BASE DE DADOS>\";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "private static final String DRIVER_CLASS = \"org.postgresql.Driver\";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "private static final String USER         = \"<USU�RIO>\";\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "private static final String PASS         = \"<SENHA>\";\r\n\r\n");
		}
		
		// Construtor.
		codigoFonte.append(this.identarComEspaco(1) + "public Dao() {}\r\n\r\n");
		
		// M�todo de conex�o.
		codigoFonte.append(this.identarComEspaco(1) + "public Connection conectar() throws ClassNotFoundException, SQLException {\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "Class.forName(DRIVER_CLASS);\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "Connection conn = DriverManager.getConnection(URL, USER, PASS);\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "return conn;\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
		
		// M�todo de desconex�o.
		codigoFonte.append(this.identarComEspaco(1) + "public void desconectar(Connection conn, PreparedStatement pstm, ResultSet rs) throws SQLException {\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "if (conn != null) {\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "conn.close();\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "if (pstm != null) {\r\n");
		codigoFonte.append(this.identarComEspaco(3) +  "pstm.close();\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "if (rs != null) {\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "rs.close();\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n\r\n");
		
		// Fim da classe DaoConexao.
		codigoFonte.append("}");
		
		String pacote = "";
		
		if(SessaoGerador.pacote.length() > 0) {
			pacote = "package " + SessaoGerador.pacote + ";\r\n\r\n";
		}
		
		String codigoFormatado = pacote + codigoFonte.toString();
		return codigoFormatado;
		
	}
	
	/**
	 * Aplica identa��o (ou identa��o) para identa��o do c�digo fonte utilizando espa�os em branco (quatro).
	 * 
	 * @param vezes O valor da quantidade de vezes que ser� aplicado a tablua��o.
	 * @return Retorna uma String com 4 (quatro) espa�os vezes a quantidade informada no par�metro.
	 */
	private String identarComEspaco(int vezes) {
		
		String identacao = "";
		
		/* Se for inserido um valor menor ou igual a zero ou um n�mero maior que 20
		 pois n�o devem existir 15 n�veis de aninhamento de comandos (identa��es) */
		if(vezes <= 0 || vezes > 15) {
			vezes = 1;
		}
		
		for( ;vezes > 0 ; vezes--) {
			
			// Atribui��o de valor de tabula��o composta por 4 espa�os em branco.
			identacao += "    ";
			
		}
		
		return identacao;
		
	}
	
	// MISAEL - 28/07/2015 - REFATORA��O, AJUSTE DE NOME DE ajustarNomeClassePropriedade PARA ajustarNomeClassePropriedadeJava.
	private String ajustarNomeClassePropriedadeJava(String nome) {
		
		// Deixar tudo em caixa baixa (min�scula)
		nome = nome.toLowerCase();
		
		// O nome contem um underscore?
		if(nome.contains("_")) {
			
			// Dividi a palavra para cada underscore encontrado.
			String[] partes = nome.split("_");
			
			// Para cada "parte" do nome, altera a 1� letra para mai�scula.
			for(int i = 0; i < partes.length; i++) {
				
				// Previnir os casos que no nome tenha "__" (exista dois ou mais underscore em sequ�ncia).
				if(partes[i].length() > 0) {
					
					partes[i] = String.valueOf(partes[i].charAt(0)).toUpperCase() + partes[i].substring(1);
					
				}
				
			}
			
			nome = "";
			
			// Uni as partes da palavra novamente, j� sem o "_".
			for(int x = 0; x < partes.length; x++) {
				
				nome += partes[x];
				
			}
			
		// Se n�o tem "_" no nome, apenas altera a 1� letra para mai�scula.
		} else {
			nome = String.valueOf(nome.charAt(0)).toUpperCase() + nome.substring(1);
		}
		
		return nome;
		
	}
	
	private String ajustarNomeAtributo(String nome) {
		
		// Deixar tudo em caixa baixa (min�scula)
		nome = nome.toLowerCase();
		
		// O nome contem um underscore?
		if(nome.contains("_")) {
			
			// Dividi a palavra para cada underscore encontrado.
			String[] partes = nome.split("_");
			
			// Para cada "parte" do nome, altera a 1� letra para mai�scula.
			for(int i = 0; i < partes.length; i++) {
				
				// Previnir os casos que no nome tenha "__" (dois ou mais underscore em sequ�ncia).
				if(partes[i].length() > 0) {
					
					// Altera para caixa alta a 1� letra a partir da 2� palavra que comp�e o nome do atributo (camel-case)
					if(i > 0) {
						
						partes[i] = String.valueOf(partes[i].charAt(0)).toUpperCase() + partes[i].substring(1);
						
					}
				}
				
			}
			
			nome = "";
			
			// Uni as partes da palavra novamente, j� sem o "_".
			for(int x = 0; x < partes.length; x++) {
				
				nome += partes[x];
				
			}
			
		}
		
		return nome;
		
	}
	
	// MISAEL - 04/02/2015 - NOVA IMPLEMENTA��O - INSERIR IMPORTS JAVA.
	// MISAEL - 26/02/2015 - CORRE��O, INCLUIDO IMPORT PARA TIME.
	// MISAEL - 28/07/2015 - REFATORA��O, NOME DO M�TODO DE inserirImports PARA inserirImportsJava.
	private String inserirImportsJava(VoTabela voTabela) {
		
		StringBuilder _import = new StringBuilder();
		boolean impBigInteger = false;
		boolean impDate       = false; // Flag para verificar se a importa��o j� voi feita para um determinado tipo.
		boolean impTime       = false;
		boolean impTimestamp  = false;
		boolean impBigDecimal = false;
		boolean impBlob       = false;
		boolean impClob       = false;
		
		for(VoColuna voColuna : voTabela.getColunas()) {
		
			if(voColuna.getTipoDado() ==  EnTipoDado.JAVA_BIGINTEGER && impBigInteger == false) {
				_import.append("import java.math.BigInteger;\r\n");
				impBigInteger = true;
			}
			
			if(voColuna.getTipoDado() ==  EnTipoDado.JAVA_DATE && impDate == false) {
				_import.append("import java.sql.Date;\r\n");
				impDate = true;
			}
			
			if(voColuna.getTipoDado() == EnTipoDado.JAVA_TIME && impTime == false) {
				_import.append("import java.sql.Time;\r\n");
				impTime = true;
			}
			
			if(voColuna.getTipoDado() ==  EnTipoDado.JAVA_TIMESTAMP && impTimestamp == false) {
				_import.append("import java.sql.Timestamp;\r\n");
				impTimestamp = true;			
			}
			
			if(voColuna.getTipoDado() ==  EnTipoDado.JAVA_BIGDECIMAL && impBigDecimal == false) {
				_import.append("import java.math.BigDecimal;\r\n");
				impBigDecimal = true;
			}
			
			if(voColuna.getTipoDado() ==  EnTipoDado.JAVA_BLOB && impBlob == false) {
				_import.append("import java.sql.Blob;\r\n");
				impBlob = true;
			}
			
			if(voColuna.getTipoDado() ==  EnTipoDado.JAVA_CLOB && impClob == false) {
				_import.append("import java.sql.Clob;\r\n");
				impClob = true;
			}
			
		}
		
		// Caso nenhum import seja utilizado, n�o aplica espa�amento entre o import e a classe
		if(impBigInteger || impDate ||  impTimestamp || impBigDecimal || impBlob || impClob) {
			_import.append("\r\n");
		}
		
		return _import.toString();
		
	}
 
	// MISAEL - 28/07/2015 - REFATORA��O, RENOMEADO NOME DO M�TODO DE trazudirMySqlParaJava para deParaTiposMySqlParaJava.
	private String deParaTiposBancoParaJava(VoColuna voColuna) {
		
		// MISAEL - 18/01/2014: Implementa��o de datatypes.
		// MISAEL - 27/01/2015: Inclus�o de short e byte e refatora��o.
		// MISAEL - 26/02/2015: corre��o, faltou incluir os datatypes Time, double e float.
		
		String dataType = "";
		
		// TODO detalhe, no caso da VoColuna no getForeignKey() trouxer o nome da tabela ao qual se referencia, ent�o imprime como
		// datatype o nome da tabela, obviamente j� tratando o Camel Case dela. 		
		
		switch(voColuna.getTipoDado()) {
		
		case JAVA_LONG:
			dataType = "long";
			break;
			
		case JAVA_BIGINTEGER:
			dataType =  "BigInteger"; // import java.math.BigInteger;
			break;
			
		case JAVA_BOOLEAN:
			dataType = "boolean";
			break;
			
		case JAVA_STRING:
			dataType = "String";
			break;
			
		case JAVA_DATE:
			dataType = "Date"; // import java.sql.Date;
			break;
			
		case JAVA_TIME:
			dataType = "Time"; // import java.sql.Time;
			break;
			
		case JAVA_TIMESTAMP:
			dataType = "Timestamp"; // import java.sql.Timestamp;
			break;
		
		case JAVA_BIGDECIMAL:
			dataType = "BigDecimal"; //import java.math.BigDecimal;
			break;
		
		case JAVA_DOUBLE:
			dataType = "double";
			break;
			
		case JAVA_INT:
			dataType = "int";
			break;
			
		case JAVA_FLOAT:
			dataType = "float";
			break;
			
		case JAVA_SHORT:
			dataType = "short";
			break;
			
		case JAVA_BYTE:
			dataType = "byte";
			break;
			
		case JAVA_ARRAY_BYTE:
			dataType = "byte[]";
			break;
			
		case JAVA_BLOB:
			dataType = "Blob"; // import java.sql.Blob;
			break;
			
		case JAVA_CLOB:
			dataType = "Clob"; //import java.sql.Clob;
			break;
			
		default:
			dataType = "<<desconhecido>>";
			break;
		
		}
		
		return dataType;
		
	}
	
}
