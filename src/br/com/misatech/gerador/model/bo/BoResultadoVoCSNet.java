package br.com.misatech.gerador.model.bo;

import java.util.ArrayList;
import java.util.List;

import br.com.misatech.gerador.model.SessaoGerador;
import br.com.misatech.gerador.model.en.EnSGBDR;
import br.com.misatech.gerador.model.en.EnTipoDado;
import br.com.misatech.gerador.model.vo.VoColuna;
import br.com.misatech.gerador.model.vo.VoForeignKey;
import br.com.misatech.gerador.model.vo.VoTabela;

public class BoResultadoVoCSNet {

	public BoResultadoVoCSNet() {
	}

	public String formatarClasseEntidadeCSNet(VoTabela voTabela) {

		// MISAEL - 04/08/2015 - implmenta��o de vari�veis de sessao para o gerador.
		boolean gerarComentario = SessaoGerador.gerarComent�rio;

		StringBuilder codigoFonte = new StringBuilder();
		String nomeClasse = this.ajustarNomeClassePropriedadeCSNet(voTabela.getNomeTabela());

		codigoFonte.append("namespace " + SessaoGerador.pacote + "\r\n{\r\n");

		if (gerarComentario) {
			// Inserir javadoc para a classe.
			codigoFonte.append(this.identarComEspaco(1) + "/// <sumary>\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "/// Classe da entidade " + nomeClasse + ".\r\n");
			codigoFonte.append(this.identarComEspaco(1) + "/// </sumary>\r\n");
		}

		// Inserir declara��o da classe.
		codigoFonte.append(this.identarComEspaco(1) + "public class " + nomeClasse + "\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "{\r\n");

		// Inserir declara��o de atributos/campos de classe.
		for (VoColuna voColuna : voTabela.getColunas()) {

			// Flag para os casos em que o campo � uma FK
			// Ou seja, na ORM esse campo n�o � impresso com seu tipo bruto.
			// E sim com o tipo da entidade ao qual aponta.
			boolean substitui = false;

			// A tabela tem chave(s) estrangeira(s)
			if (voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for (VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela,
					// substitui pelo tipo da tabela Pai.
					if (voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {

						substitui = true;
						codigoFonte.append(this.identarComEspaco(2) + "private "
								+ this.ajustarNomeClassePropriedadeCSNet(voForeignKey.getNomeTabelaPai()) + " "
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + ";\r\n");

					}

				}

			}

			// Se o campo que � chaveFK, n�o imprime o tipo bruto.
			if (substitui) {
				continue;
			}

			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			codigoFonte.append(this.identarComEspaco(2) + "private " + this.deParaTiposBancoParaCSNet(voColuna) + " "
					+ nomeAtributo + ";\r\n");

		}

		if (gerarComentario) {

			// Inserir javadoc para o construtor vazio.
			codigoFonte.append("\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "/// <sumary>\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "/// Cria uma nova inst�ncia de " + nomeClasse + ".\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "/// </sumary>");

		}

		codigoFonte.append("\r\n");
		// Inserir construtor vazio.
		codigoFonte.append(this.identarComEspaco(2) + "public " + nomeClasse + "() {}\r\n\r\n");

		// Coment�rio para o construtor sobrecarregado.
		if (gerarComentario) {

			// Inserir javadoc para o construtor sobrecarregado.
			codigoFonte.append(this.identarComEspaco(2) + "/// <sumary>\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "/// Cria uma nova inst�ncia de " + nomeClasse + ".\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "/// </sumary>\r\n");

			for (VoColuna voColuna : voTabela.getColunas()) {

				// Flag para os casos em que o campo � uma FK
				// Ou seja, na ORM esse campo n�o � impresso com seu tipo bruto.
				// E sim com o tipo da entidade ao qual aponta.
				boolean substitui = false;

				// A tabela tem chave(s) estrangeira(s)
				if (voTabela.getVoForeignKey() != null) {
					// Varre as chaves estrangeiras
					for (VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
						// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela,
						// substitui pelo tipo da tabela Pai.
						if (voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {

							substitui = true;
							codigoFonte.append(this.identarComEspaco(2) + "/// <param name=\""
									+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + "\"></param>\r\n");

						}

					}

				}

				// Se o campo que � chaveFK, n�o imprime o tipo bruto.
				if (substitui) {
					continue;
				}

				String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
				codigoFonte.append(this.identarComEspaco(2) + "/// <param name\"" + nomeAtributo + "\"></param>\r\n");

			}

		}

		// Inserir construtor sobrecarregado.
		codigoFonte.append(this.identarComEspaco(2) + "public " + nomeClasse + "(");
		int i = 0;

		for (VoColuna voColuna : voTabela.getColunas()) {

			i++;

			boolean substitui = false;

			// A tabela tem chave(s) estrangeira(s)
			if (voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for (VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela,
					// substitui pelo tipo da tabela Pai.
					if (voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {

						substitui = true;

						// Se for o �ltimo atributo n�o insere o separador de v�rgula.
						if (i == voTabela.getColunas().size()) {
							codigoFonte.append(this.ajustarNomeClassePropriedadeCSNet(voForeignKey.getNomeTabelaPai())
									+ " " + this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()));
						} else {
							codigoFonte.append(this.ajustarNomeClassePropriedadeCSNet(voForeignKey.getNomeTabelaPai())
									+ " " + this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + ", ");
						}

					}

				}

			}

			// Se o campo que � chaveFK, n�o imprime o tipo bruto no par�metro do construtor
			// sobrecarregado.
			if (substitui) {
				continue;
			}

			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());

			// Se for o �ltimo atributo n�o insere o separador de v�rgula.
			if (i == voTabela.getColunas().size()) {
				codigoFonte.append(this.deParaTiposBancoParaCSNet(voColuna) + " " + nomeAtributo);
			} else {
				codigoFonte.append(this.deParaTiposBancoParaCSNet(voColuna) + " " + nomeAtributo + ", ");
			}
		}

		codigoFonte.append(")\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		for (VoColuna voColuna : voTabela.getColunas()) {

			boolean substitui = false;

			// A tabela tem chave(s) estrangeira(s)
			if (voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for (VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela,
					// substitui pelo tipo da tabela Pai.
					if (voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {

						substitui = true;
						codigoFonte.append(this.identarComEspaco(3) + "this."
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + " = "
								+ this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai()) + ";\r\n");

					}

				}

			}

			// Se o campo que � chaveFK, n�o imprime o tipo bruto.
			if (substitui) {
				continue;
			}

			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());
			codigoFonte.append(this.identarComEspaco(3) + "this." + nomeAtributo + " = " + nomeAtributo + ";\r\n");

		}

		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

		// Inserir Properties (Propriedades C#.NET)
		for (VoColuna voColuna : voTabela.getColunas()) {

			boolean substitui = false;

			// A tabela tem chave(s) estrangeira(s)
			if (voTabela.getVoForeignKey() != null) {
				// Varre as chaves estrangeiras
				for (VoForeignKey voForeignKey : voTabela.getVoForeignKey()) {
					// Se o nome da coluna que � FK da tabela for igual a uma das colunas da tabela,
					// substitui pelo tipo da tabela Pai.
					if (voForeignKey.getNomeColunaFK().equals(voColuna.getNomeColuna())) {

						substitui = true;

						String nomeMetodoFK = this.ajustarNomeClassePropriedadeCSNet(voForeignKey.getNomeTabelaPai());
						String nomeAtributoFK = this.ajustarNomeAtributo(voForeignKey.getNomeTabelaPai());

						// get e set
						codigoFonte.append(
								this.identarComEspaco(2) + "public " + nomeMetodoFK + " " + nomeMetodoFK + "\r\n");
						codigoFonte.append(this.identarComEspaco(2) + "{\r\n");
						codigoFonte.append(this.identarComEspaco(3) + "get { return " + nomeAtributoFK + "; }\r\n");
						codigoFonte.append(this.identarComEspaco(3) + "set { " + nomeAtributoFK + " = value; }\r\n");
						codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

					}

				}

			}

			// Se o campo que � chaveFK, n�o imprime o tipo bruto.
			if (substitui) {
				continue;
			}

			// get e set
			String nomeMetodo = this.ajustarNomeClassePropriedadeCSNet(voColuna.getNomeColuna());
			String nomeAtributo = this.ajustarNomeAtributo(voColuna.getNomeColuna());

			codigoFonte.append(this.identarComEspaco(2) + "public " + this.deParaTiposBancoParaCSNet(voColuna) + " "
					+ nomeMetodo + "\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "{\r\n");
			codigoFonte.append(this.identarComEspaco(3) + "get { return " + nomeAtributo + "; }\r\n");
			codigoFonte.append(this.identarComEspaco(3) + "set { " + nomeAtributo + " = value; }\r\n");
			codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

		}

		codigoFonte.append(this.identarComEspaco(1) + "}\r\n");

		codigoFonte.append("}");

		String codigoFormatado = this.inserirImportsCSNet(voTabela) + "\r\n" + codigoFonte.toString();

		return codigoFormatado;

	}

	public String formatarClasseNegocioCSNet(VoTabela voTabela) {

		StringBuilder codigoFonte = new StringBuilder();
		String nomeClasse = this.ajustarNomeClassePropriedadeCSNet(voTabela.getNomeTabela()) + "Business";
		String entidade = this.ajustarNomeClassePropriedadeCSNet(voTabela.getNomeTabela());

		// In�cio namespace.
		codigoFonte.append("namespace " + SessaoGerador.pacote + "\r\n");
		codigoFonte.append("{\r\n");

		// Assinatura de classe.
		codigoFonte.append(this.identarComEspaco(1) + "public class " + nomeClasse + "\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "{\r\n");

		// Campo do tipo da entidade.
		codigoFonte
				.append(this.identarComEspaco(2) + "private " + entidade + " " + entidade.toLowerCase() + ";\r\n\r\n");

		// Construtor da classe
		codigoFonte.append(this.identarComEspaco(2) + "public " + nomeClasse + "() {}\r\n\r\n");

		// 1� Sobrecarga do construtor.
		codigoFonte.append(this.identarComEspaco(2) + "public " + nomeClasse + "(" + entidade + " "
				+ entidade.toLowerCase() + ")\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");
		codigoFonte.append(
				this.identarComEspaco(3) + "this." + entidade.toLowerCase() + " = " + entidade.toLowerCase() + ";\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

		// Properties.
		codigoFonte.append(this.identarComEspaco(2) + "public " + entidade + " " + entidade + "\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "get { return " + entidade.toLowerCase() + "; }\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "set { " + entidade.toLowerCase() + " = value; }\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

		// Fim da classe.
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n");

		// Fim do namespace.
		codigoFonte.append("}");

		String codigoFormatado = this.inserirImportsCSNet(voTabela) + "\r\n" + codigoFonte.toString();

		return codigoFormatado;
	}

	public String formatarClasseDaoCSNet(VoTabela voTabela) {

		StringBuilder codigoFonte = new StringBuilder();
		String nomeClasse = this.ajustarNomeClassePropriedadeCSNet(voTabela.getNomeTabela()) + "Dao";
		String entidade = this.ajustarNomeClassePropriedadeCSNet(voTabela.getNomeTabela());

		// Using's b�sicos para a classe Dao.

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append("using MySql.Data.MySqlClient;\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append("using Npgsql;\r\n");
		}

		codigoFonte.append("using NpgsqlTypes;\r\n");
		codigoFonte.append("using System;\r\n");
		codigoFonte.append("using System.Collections.Generic;\r\n");
		codigoFonte.append("using System.Linq;\r\n");
		codigoFonte.append("using System.Text;\r\n");
		codigoFonte.append("using System.Threading.Tasks;\r\n\r\n");

		// In�cio do namespace.
		codigoFonte.append("namespace " + SessaoGerador.pacote + "\r\n");
		codigoFonte.append("{\r\n");

		// In�cio da classe.
		codigoFonte.append(this.identarComEspaco(1) + "public class " + nomeClasse + "\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "{\r\n");

		// Campo para a classe Dao gen�rica (de conex�o)
		codigoFonte.append(this.identarComEspaco(2) + "private Dao dao;\r\n\r\n");

		// Construtor padr�o.
		codigoFonte.append(this.identarComEspaco(2) + "public " + nomeClasse + "()\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "this.dao = new Dao();\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

		////////////////////////////////////////////////////////////////////////////////////////////////
		// In�cio do m�todo inserir.
		codigoFonte.append(this.identarComEspaco(2) + "public void Inserir" + "(" + entidade + " "
				+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ")\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		// In�cio try
		codigoFonte.append(this.identarComEspaco(3) + "try\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(4) + "MySqlConnection conn = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "MySqlCommand command = conn.CreateCommand();\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlConnection conn = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlCommand command = conn.CreateCommand();\r\n");
		}

		codigoFonte.append(this.identarComEspaco(4) + "StringBuilder sql = new StringBuilder();\r\n\r\n");

		// Formata o comando INSERT.
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"INSERT INTO "
				+ voTabela.getNomeTabela().toUpperCase() + "\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"(");
		int i = 0;
		// Insere as colunas no comando SQL.
		for (VoColuna voColuna : voTabela.getColunas()) {
			i++;

			// Se � a �ltima coluna n�o insere a v�rgula.
			if (i == voTabela.getColunas().size()) {
				codigoFonte.append(voColuna.getNomeColuna().toUpperCase());
			} else {
				codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + ", ");
			}
		}
		codigoFonte.append(")\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"VALUES\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"(");

		i = 0;
		// Insere os par�metros das colunas no comando SQL.
		for (VoColuna voColuna : voTabela.getColunas()) {
			i++;

			// Se � a �ltima coluna n�o insere a v�rgula.
			if (i == voTabela.getColunas().size()) {
				codigoFonte.append("@" + voColuna.getNomeColuna().toUpperCase());
			} else {
				codigoFonte.append("@" + voColuna.getNomeColuna().toUpperCase() + ", ");
			}
		}
		codigoFonte.append(")\");\r\n\r\n");

		// Insere o recebimento dos par�metros
		for (VoColuna voColuna : voTabela.getColunas()) {

			codigoFonte.append(this.identarComEspaco(4) + "command.Parameters.AddWithValue(" + "\"@"
					+ voColuna.getNomeColuna().toUpperCase() + "\", "
					+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "."
					+ this.ajustarNomeClassePropriedadeCSNet(voColuna.getNomeColuna()) + ");\r\n");

		}

		codigoFonte.append("\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "command.CommandText = sql.ToString();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "command.ExecuteNonQuery();\r\n\r\n");

		codigoFonte.append(this.identarComEspaco(4) + "this.dao.Desconectar(conn, command, null);\r\n");

		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "catch(Exception e)\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "throw e;\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");

		// Fim do m�todo inserir.
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		////////////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////////////
		// Inc�o do m�todo alterar.
		codigoFonte.append(this.identarComEspaco(2) + "public void Alterar" + "(" + entidade + " "
				+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ")\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		// In�cio try
		codigoFonte.append(this.identarComEspaco(3) + "try\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(4) + "MySqlConnection conn = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "MySqlCommand command = conn.CreateCommand();\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlConnection conn = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlCommand command = conn.CreateCommand();\r\n");
		}

		codigoFonte.append(this.identarComEspaco(4) + "StringBuilder sql = new StringBuilder();\r\n\r\n");

		// Formata o comando UPDATE.
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"UPDATE " + voTabela.getNomeTabela().toUpperCase()
				+ "\");\r\n");

		// Campos para da cl�usula SET
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" SET ");
		i = 0;
		for (VoColuna voColuna : voTabela.getColunas()) {
			i++;

			// Se for chave prim�ria n�o vai no SET do UPDATE..
			if (voColuna.isPrimaryKey()) {
				continue;
			}

			// Se � a �ltima coluna n�o insere a v�rgula.
			if (i == voTabela.getColunas().size()) {
				codigoFonte.append(
						voColuna.getNomeColuna().toUpperCase() + " = @" + voColuna.getNomeColuna().toUpperCase());
			} else {
				codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + " = @"
						+ voColuna.getNomeColuna().toUpperCase() + ", ");
			}
		}
		codigoFonte.append("\");\r\n");

		// Cl�usula WHERE
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" WHERE\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" ");

		List<String> colunasPKAlterar = new ArrayList<String>();
		i = 0;
		for (VoColuna voColuna : voTabela.getColunas()) {
			i++;
			// Existe um campo chave prim�ria? Quem �?
			if (voColuna.isPrimaryKey()) {
				// Insere os campos PK para a cl�usula WHERE.
				colunasPKAlterar.add(voColuna.getNomeColuna().toUpperCase());
			}
		}

		i = 0;
		for (String colPK : colunasPKAlterar) {

			i++;

			if (i == colunasPKAlterar.size()) {
				codigoFonte.append(colPK + " = @" + colPK);
			} else {
				codigoFonte.append(colPK + " = @" + colPK + " AND ");
			}

		}
		codigoFonte.append("\");\r\n\r\n");

		// Insere o recebimento dos par�metros
		for (VoColuna voColuna : voTabela.getColunas()) {

			codigoFonte.append(this.identarComEspaco(4) + "command.Parameters.AddWithValue(" + "\"@"
					+ voColuna.getNomeColuna().toUpperCase() + "\", "
					+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "."
					+ this.ajustarNomeClassePropriedadeCSNet(voColuna.getNomeColuna()) + ");\r\n");

		}

		codigoFonte.append("\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "command.CommandText = sql.ToString();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "command.ExecuteNonQuery();\r\n\r\n");

		codigoFonte.append(this.identarComEspaco(4) + "this.dao.Desconectar(conn, command, null);\r\n");

		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "catch(Exception e)\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "throw e;\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");

		// Fim do m�todo alterar.
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		////////////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////////////
		// In�cio do m�todo excluir.
		codigoFonte.append(this.identarComEspaco(2) + "public void Excluir" + "(" + entidade + " "
				+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ")\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		// In�cio try
		codigoFonte.append(this.identarComEspaco(3) + "try\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(4) + "MySqlConnection conn = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "MySqlCommand command = conn.CreateCommand();\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlConnection conn = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlCommand command = conn.CreateCommand();\r\n");
		}

		codigoFonte.append(this.identarComEspaco(4) + "StringBuilder sql = new StringBuilder();\r\n\r\n");

		// Formatando comando excluir.
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"DELETE FROM "
				+ voTabela.getNomeTabela().toUpperCase() + "\");\r\n");

		// Cl�usula WHERE
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" WHERE\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" ");

		List<String> colunasPKExcluir = new ArrayList<String>();
		i = 0;
		for (VoColuna voColuna : voTabela.getColunas()) {
			i++;
			// Existe um campo chave prim�ria? Quem �?
			if (voColuna.isPrimaryKey()) {
				// Insere os campos PK para a cl�usula WHERE.
				colunasPKExcluir.add(voColuna.getNomeColuna().toUpperCase());
			}
		}

		i = 0;
		for (String colPK : colunasPKExcluir) {
			i++;

			if (i == colunasPKExcluir.size()) {
				codigoFonte.append(colPK + " = @" + colPK);
			} else {
				codigoFonte.append(colPK + " = @" + colPK + " AND ");
			}

		}
		codigoFonte.append("\");\r\n\r\n");

		i = 0;
		for (String colPK : colunasPKExcluir) {
			i++;
			codigoFonte.append(this.identarComEspaco(4) + "command.Parameters.AddWithValue(" + "\"@"
					+ colPK.toUpperCase() + "\", " + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "."
					+ this.ajustarNomeClassePropriedadeCSNet(colPK) + ");\r\n");

		}
		codigoFonte.append("\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "command.CommandText = sql.ToString();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "command.ExecuteNonQuery();\r\n\r\n");

		codigoFonte.append(this.identarComEspaco(4) + "this.dao.Desconectar(conn, command, null);\r\n");

		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "catch(Exception e)\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "throw e;\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");

		// Fim do m�todo excluir.
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		////////////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////////////
		// In�cio do m�todo consultar.
		codigoFonte.append(this.identarComEspaco(2) + "public List<" + entidade + "> Consultar()\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		// In�cio try
		codigoFonte.append(this.identarComEspaco(3) + "try\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(4) + "MySqlConnection conn     = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "MySqlCommand command     = conn.CreateCommand();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "MySqlDataReader reader   = null;\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlConnection conn    = this.dao.Conectar();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlCommand command    = conn.CreateCommand();\r\n");
			codigoFonte.append(this.identarComEspaco(4) + "NpgsqlDataReader reader  = null;\r\n");
		}

		codigoFonte.append(this.identarComEspaco(4) + "List<" + entidade + "> "
				+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "s" + " = new List<" + entidade + ">();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "StringBuilder sql      = new StringBuilder();\r\n\r\n");

		// Formata��o do comando SELECT.
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\"SELECT\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" ");

		i = 0;
		for (VoColuna voColuna : voTabela.getColunas()) {
			i++;

			// � a �ltima coluna da tabela?
			if (i == voTabela.getColunas().size()) {
				codigoFonte.append(voColuna.getNomeColuna().toUpperCase());
			} else {
				codigoFonte.append(voColuna.getNomeColuna().toUpperCase() + ", ");
			}

		}
		codigoFonte.append("\");\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "sql.Append(\" FROM\");\r\n");
		codigoFonte.append(
				this.identarComEspaco(4) + "sql.Append(\" " + voTabela.getNomeTabela().toUpperCase() + "\");\r\n\r\n");

		codigoFonte.append(this.identarComEspaco(4) + "command.CommandText = sql.ToString();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "reader              = command.ExecuteReader();\r\n\r\n");

		// La�o While para alimentar a Lista com os tipos do objeto de entidade (tipos
		// complexos do usu�rios).
		codigoFonte.append(this.identarComEspaco(4) + "while (reader.Read())\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "{\r\n");

		i = 0;
		// Iterar sobre as colunas da tabela para montar o preparedStatement.
		for (VoColuna voColuna : voTabela.getColunas()) {

			i++;

			// Imprime o objeto complexo do tipo do usu�rio.
			if (i == 1) {
				codigoFonte.append(this.identarComEspaco(5) + entidade + " "
						+ this.ajustarNomeAtributo(voTabela.getNomeTabela()) + " = new " + entidade + "();\r\n\r\n");
			}

			// � a �ltima coluna da tabela?
			if (i == voTabela.getColunas().size()) {

				codigoFonte.append(this.identarComEspaco(5) + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "."
						+ this.ajustarNomeClassePropriedadeCSNet(voColuna.getNomeColuna()) + " = "
						+ this.inserirConvertCSNet(voColuna.getTipoDado(),
								"reader[\"" + voColuna.getNomeColuna().toUpperCase() + "\"].ToString()"));
				codigoFonte.append(";\r\n\r\n");

				codigoFonte.append(this.identarComEspaco(5) + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "s"
						+ ".Add(" + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + ");\r\n\r\n");

			} else {

				codigoFonte.append(this.identarComEspaco(5) + this.ajustarNomeAtributo(voTabela.getNomeTabela()) + "."
						+ this.ajustarNomeClassePropriedadeCSNet(voColuna.getNomeColuna()) + " = "
						+ this.inserirConvertCSNet(voColuna.getTipoDado(),
								"reader[\"" + voColuna.getNomeColuna().toUpperCase() + "\"].ToString()"));
				codigoFonte.append(";\r\n");
			}

		}

		codigoFonte.append(this.identarComEspaco(4) + "}\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "this.dao.Desconectar(conn, command, reader);\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "return " + this.ajustarNomeAtributo(voTabela.getNomeTabela())
				+ "s;\r\n\r\n");

		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "catch (Exception e)\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "throw e;\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");

		// Fim do m�todo consultar.
		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");
		////////////////////////////////////////////////////////////////////////////////////////////////

		// Fim da classe Dao.
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n");

		// Fim do namespace.
		codigoFonte.append("}\r\n");

		String codigoFormatado = codigoFonte.toString();
		return codigoFormatado;
	}

	public String formatarClasseDaoConexaoCSNet() {

		StringBuilder codigoFonte = new StringBuilder();

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append("using MySql.Data;\r\n");
			codigoFonte.append("using MySql.Data.MySqlClient;\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append("using Npgsql;\r\n");
		}

		codigoFonte.append("using System;\r\n");
		codigoFonte.append("using System.Collections.Generic;\r\n");
		codigoFonte.append("using System.Linq;\r\n");
		codigoFonte.append("using System.Text;\r\n");
		codigoFonte.append("using System.Threading.Tasks;\r\n\r\n");

		// In�cio do namespace.
		codigoFonte.append("namespace " + SessaoGerador.pacote + "\r\n");
		codigoFonte.append("{\r\n");

		// In�cio da classe.
		codigoFonte.append(this.identarComEspaco(1) + "public class Dao\r\n");
		codigoFonte.append(this.identarComEspaco(1) + "{\r\n");

		// Campos/atributos da classe.
		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(2)
					+ "private String stringConnection = @\"Server=localhost;Port=3306;Database=<BASE DE DADOS>;Uid=<USUARIO>;Pwd=<SENHA>;\";\r\n\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(2)
					+ "private String stringConnection = @\"Server=localhost;Port=5432;Database=<BASE DE DADOS>;Uid=<USUARIO>;Pwd=<SENHA>;\";\r\n\r\n");
		}
		// Construtor.
		codigoFonte.append(this.identarComEspaco(2) + "public Dao() {}\r\n\r\n");

		String providerAdoNet = "";
		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			providerAdoNet = "MySqlConnection";
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			providerAdoNet = "NpgsqlConnection";
		}

		// M�todo de conex�o.
		codigoFonte.append(this.identarComEspaco(2) + "public " + providerAdoNet + " Conectar()\r\n");
		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		// In�cio Try.
		codigoFonte.append(this.identarComEspaco(3) + "try\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");

		codigoFonte.append(this.identarComEspaco(4) + providerAdoNet + " conn = new " + providerAdoNet
				+ "(stringConnection);\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "conn.Open();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "return conn;\r\n");

		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "catch(Exception e)\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "throw e;\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");

		codigoFonte.append(this.identarComEspaco(2) + "}\r\n\r\n");

		// M�todo de desconex�o.

		// MySql ou PostgreSQL ?
		if (SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			codigoFonte.append(this.identarComEspaco(2)
					+ "public void Desconectar(MySqlConnection conn, MySqlCommand command, MySqlDataReader reader)\r\n");
		} else if (SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
			codigoFonte.append(this.identarComEspaco(2)
					+ "public void Desconectar(NpgsqlConnection conn, NpgsqlCommand command, NpgsqlDataReader reader)\r\n");
		}

		codigoFonte.append(this.identarComEspaco(2) + "{\r\n");

		// In�cio Try.
		codigoFonte.append(this.identarComEspaco(3) + "try\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");

		codigoFonte.append(this.identarComEspaco(4) + "if (conn != null) {\r\n");
		codigoFonte.append(this.identarComEspaco(5) + "conn.Close();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "}\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "if (command != null) {\r\n");
		codigoFonte.append(this.identarComEspaco(5) + "command.Dispose();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "}\r\n\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "if (reader != null) {\r\n");
		codigoFonte.append(this.identarComEspaco(5) + "reader.Close();\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "}\r\n");

		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "catch(Exception e)\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "{\r\n");
		codigoFonte.append(this.identarComEspaco(4) + "throw e;\r\n");
		codigoFonte.append(this.identarComEspaco(3) + "}\r\n");

		codigoFonte.append(this.identarComEspaco(2) + "}\r\n");

		// Fim da classe DaoConexao.
		codigoFonte.append(this.identarComEspaco(1) + "}\r\n");

		// Fim do namespace.
		codigoFonte.append("}");

		String codigoFormatado = codigoFonte.toString();
		return codigoFormatado;

	}

	/**
	 * Aplica identa��o (ou identa��o) para identa��o do c�digo fonte utilizando
	 * espa�os em branco (quatro).
	 * 
	 * @param vezes O valor da quantidade de vezes que ser� aplicado a tablua��o.
	 * @return Retorna uma String com 4 (quatro) espa�os vezes a quantidade
	 *         informada no par�metro.
	 */
	private String identarComEspaco(int vezes) {

		String identacao = "";

		/*
		 * Se for inserido um valor menor ou igual a zero ou um n�mero maior que 20 pois
		 * n�o devem existir 15 n�veis de aninhamento de comandos (identa��es)
		 */
		if (vezes <= 0 || vezes > 15) {
			vezes = 1;
		}

		for (; vezes > 0; vezes--) {

			// Atribui��o de valor de tabula��o composta por 4 espa�os em branco.
			identacao += "    ";

		}

		return identacao;

	}

	private String ajustarNomeClassePropriedadeCSNet(String nome) {

		// Deixar tudo em caixa baixa (min�scula)
		nome = nome.toLowerCase();

		// O nome contem um underscore?
		if (nome.contains("_")) {

			// Dividi a palavra para cada underscore encontrado.
			String[] partes = nome.split("_");

			// Para cada "parte" do nome, altera a 1� letra para mai�scula.
			for (int i = 0; i < partes.length; i++) {

				// Previnir os casos que no nome tenha "__" (exista dois ou mais underscore em
				// sequ�ncia).
				if (partes[i].length() > 0) {

					partes[i] = String.valueOf(partes[i].charAt(0)).toUpperCase() + partes[i].substring(1);

				}

			}

			nome = "";

			// Uni as partes da palavra novamente, j� sem o "_".
			for (int x = 0; x < partes.length; x++) {

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
		if (nome.contains("_")) {

			// Dividi a palavra para cada underscore encontrado.
			String[] partes = nome.split("_");

			// Para cada "parte" do nome, altera a 1� letra para mai�scula.
			for (int i = 0; i < partes.length; i++) {

				// Previnir os casos que no nome tenha "__" (dois ou mais underscore em
				// sequ�ncia).
				if (partes[i].length() > 0) {

					// Altera para caixa alta a 1� letra a partir da 2� palavra que comp�e o nome do
					// atributo (camel-case)
					if (i > 0) {

						partes[i] = String.valueOf(partes[i].charAt(0)).toUpperCase() + partes[i].substring(1);

					}
				}

			}

			nome = "";

			// Uni as partes da palavra novamente, j� sem o "_".
			for (int x = 0; x < partes.length; x++) {

				nome += partes[x];

			}

		}

		return nome;

	}

	private String inserirConvertCSNet(EnTipoDado tipoDado, String codigo) {

		String valor = "";

		switch (tipoDado) {

		case CS_STRING:
			valor = codigo;
			break;

		case CS_INT:
			valor = "Convert.ToInt32(" + codigo + ")";
			break;

		case CS_LONG:
			valor = "Convert.ToInt64(" + codigo + ")";
			break;

		case CS_DOUBLE:
			valor = "Convert.ToDouble(" + codigo + ")";
			break;

		case CS_DATETIME:
			valor = "Convert.ToDateTime(" + codigo + ")";
			break;

		case CS_BOOL:
			valor = "Convert.ToBoolean(" + codigo + ")";
			break;

		default:
			valor = codigo;
			break;

		}

		return valor;

	}

	private String inserirImportsCSNet(VoTabela voTabela) {

		StringBuilder _using = new StringBuilder();
		/*
		 * boolean impBigInteger = false; boolean impDate = false; // Flag para
		 * verificar se a importa��o j� voi feita para um determinado tipo. boolean
		 * impTime = false; boolean impTimestamp = false; boolean impBigDecimal = false;
		 * boolean impBlob = false; boolean impClob = false;
		 */

		// Using padr�es do C#.NEt
		_using.append("using NpgsqlTypes;\r\n");
		_using.append("using System;\r\n");
		_using.append("using System.Collections;\r\n");
		_using.append("using System.Collections.Generic;\r\n");
		_using.append("using System.Linq;\r\n");
		_using.append("using System.Net;\r\n");
		_using.append("using System.Net.NetworkInformation;\r\n");
		_using.append("using System.Text;\r\n");
		_using.append("using System.Threading.Tasks;\r\n");

		/*
		 * for(VoColuna voColuna : voTabela.getColunas()) {
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_BIGINTEGER && impBigInteger ==
		 * false) { _using.append("import java.math.BigInteger;\r\n"); impBigInteger =
		 * true; }
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_DATE && impDate == false) {
		 * _using.append("import java.sql.Date;\r\n"); impDate = true; }
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_TIME && impTime == false) {
		 * _using.append("import java.sql.Time;\r\n"); impTime = true; }
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_TIMESTAMP && impTimestamp ==
		 * false) { _using.append("import java.sql.Timestamp;\r\n"); impTimestamp =
		 * true; }
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_BIGDECIMAL && impBigDecimal ==
		 * false) { _using.append("import java.math.BigDecimal;\r\n"); impBigDecimal =
		 * true; }
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_BLOB && impBlob == false) {
		 * _using.append("import java.sql.Blob;\r\n"); impBlob = true; }
		 * 
		 * if(voColuna.getTipoDado() == EnTipoDado.JAVA_CLOB && impClob == false) {
		 * _using.append("import java.sql.Clob;\r\n"); impClob = true; }
		 * 
		 * }
		 * 
		 * // Caso nenhum import seja utilizado, n�o aplica espa�amento entre o import e
		 * a classe if(impBigInteger || impDate || impTimestamp || impBigDecimal ||
		 * impBlob || impClob) { _using.append("\r\n"); }
		 */

		return _using.toString();

	}

	private String deParaTiposBancoParaCSNet(VoColuna voColuna) {

		String dataType = "";

		switch (voColuna.getTipoDado()) {

		case CS_LONG:
			dataType = "long";
			break;

		case CS_ARRAY_BYTE:
			dataType = "byte[]";
			break;

		case CS_BOOL:
			dataType = "bool";
			break;

		case CS_STRING:
			dataType = "string";
			break;

		case CS_DATETIME:
			dataType = "DateTime";
			break;

		case CS_DECIMAL:
			dataType = "decimal";
			break;

		case CS_DOUBLE:
			dataType = "double";
			break;

		case CS_FLOAT:
			dataType = "float";
			break;

		case CS_INT:
			dataType = "int";
			break;

		case CS_SHORT:
			dataType = "short";
			break;

		case CS_TIMESPAN:
			dataType = "TimeSpan";
			break;

		case CS_SBYTE:
			dataType = "sbyte";
			break;

		case CS_BITARRAY:
			dataType = "BitArray"; // using System.Collections;
			break;

		case CS_GUID:
			dataType = "Guid";
			break;

		case CS_IPADDRESS:
			dataType = "IPAddress"; // using System.Net;
			break;

		case CS_PHYSICALADDRESS:
			dataType = "PhysicalAddress"; // using System.Net.NetworkInformation;
			break;

		case CS_DATETIMEOFFSET:
			dataType = "DateTimeOffset";
			break;

		// Espec�fico do PostgreSQL
		case CS_NPGSQLPOINT: // using NpgsqlTypes;
			dataType = "NpgsqlPoint";
			break;

		case CS_NPGSQLLSEG: // using NpgsqlTypes;
			dataType = "NpgsqlLSeg";
			break;

		case CS_NPGSQLPOLYGON: // using NpgsqlTypes;
			dataType = "NpgsqlPolygon";
			break;

		case CS_NPGSQLLINE: // using NpgsqlTypes;
			dataType = "NpgsqlLine";
			break;

		case CS_NPGSQLCIRCLE: // using NpgsqlTypes;
			dataType = "NpgsqlCircle";
			break;

		case CS_NPGSQLBOX: // using NpgsqlTypes;
			dataType = "NpgsqlBox";
			break;

		case CS_NPGSQLINET: // using NpgsqlTypes;
			dataType = "NpgsqlInet";
			break;

		case CS_NPGSQLTSQUERY: // using NpgsqlTypes;
			dataType = "NpgsqlTsQuery";
			break;

		case CS_NPGSQLTSVECTOR: // using NpgsqlTypes;
			dataType = "NpgsqlTsVector";
			break;

		case CS_NPGSQLRANGE: // using NpgsqlTypes;
			dataType = "NpgsqlRange";
			break;

		default:
			dataType = "<<desconhecido>>";
			break;

		}

		return dataType;

	}

}
