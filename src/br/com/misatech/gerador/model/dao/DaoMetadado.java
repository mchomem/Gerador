package br.com.misatech.gerador.model.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.misatech.gerador.model.SessaoGerador;
import br.com.misatech.gerador.model.en.EnSGBDR;
import br.com.misatech.gerador.model.en.EnTecnologia;
import br.com.misatech.gerador.model.en.EnTipoDado;
import br.com.misatech.gerador.model.vo.VoColuna;
import br.com.misatech.gerador.model.vo.VoForeignKey;
import br.com.misatech.gerador.model.vo.VoMetadado;
import br.com.misatech.gerador.model.vo.VoTabela;

/**
 * Consulta os dados de metadados da base de dados.
 * 
 * @author Misael C. Homem
 *
 */
public class DaoMetadado {
	
	private VoMetadado voMetadado; 
	
	public DaoMetadado() {
		
		this.voMetadado = new VoMetadado();
		
	}
	
	/**
	 * Obtém os metadados da base de dados para atribuir a um tipo VoMetadado.
	 * 
	 * @return Retorna um objeto do tipo VoMetadado.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public VoMetadado obterMetaDados(Connection conexao) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		
		this.voMetadado = new VoMetadado();
		this.voMetadado.setNomeBanco(conexao.getCatalog());
		this.voMetadado.setNomeSGBD(conexao.getMetaData().getDatabaseProductName());
		this.voMetadado.setTabelas(this.obterMTTabela(conexao));
		
		return voMetadado;
		
	}
	
	/**
	 * Obtém os metadados para tabela a partir da conexão informada.
	 * 
	 * @param conexao - A conexão informada para recuperar dados para VoMetadado.
	 * @return Retorna uma lista de VoTabela.
	 * @throws SQLException
	 */
	private List<VoTabela> obterMTTabela(Connection conexao) throws SQLException {
				
		ResultSet rsTabela     = conexao.getMetaData().getTables(conexao.getCatalog(), null, "", null);
		List<VoTabela> tabelas = new ArrayList<VoTabela>();
		
		while(rsTabela.next()) {
			
			System.out.println(rsTabela.getString("TABLE_TYPE") + ": " + rsTabela.getString("TABLE_NAME"));
			
			// Correção: detectado que objetos "views" de banco estavam sendo inclusas na geração de código fonte.
			// 27/01/2015 - Detectado que o resultSet estava obtendo além das tabelas as views do banco.
			// Solução: implementar filtro para descartar objetos de banco que não sejam tabelas.
			
			// TODO implementação futura, para gerar relatórios um objeto de banco view pode ser útil.
			
			// Se não for uma tabela (se for uma view por exemplo ou tabela global ou do sistema)
			String tipoObjetoBanco = rsTabela.getString("TABLE_TYPE");
			
			// TODO esse teste é apenas para testar o postgre, remover depois caso haja a separação (MySql e PostgreSQL) na obtenção de datatype.
			if(tipoObjetoBanco == null) {
				// teste rápido por conta que o postgre não identifica esse objeto null: pg_toast_11708.
				return tabelas;
			}
			
			if(!tipoObjetoBanco.equals("TABLE")) {
				continue;
			}
						
			String nomeTabela = rsTabela.getString("TABLE_NAME");
			
			VoTabela voTabela = new VoTabela();
			voTabela.setNomeTabela(nomeTabela);
			voTabela.setColunas(this.obterMTColuna(conexao, nomeTabela));
			voTabela.setVoForeignKey(this.obterChavesImportadas(conexao, nomeTabela));
			voTabela.setGerar(true);
			
			tabelas.add(voTabela);
			
		}
		
		return tabelas;
		
	}
	
	/**
	 * Consulta uma coluna para averificar se esta coluna é chave primária na tabela ou não.
	 * @param conexao - a conexão de banco.
	 * @param nomeTabela - o nome da tabela a ser pesquisado.
	 * @param nomeColuna - a coluna para averifiguação.
	 * @return true se a coluna averiguada é chave primária na tabela.
	 * @throws SQLException
	 */
	private boolean confirmaChavePrimaria(Connection conexao, String nomeTabela, String nomeColuna) throws SQLException {
		
		String colunaPK       = "";
		DatabaseMetaData dbmd = conexao.getMetaData();
		ResultSet rsPK        = dbmd.getPrimaryKeys(conexao.getCatalog(), null, nomeTabela);
		boolean pk            = false;
		
		while(rsPK.next()) {
			
			colunaPK = rsPK.getString("COLUMN_NAME");
			
			// A coluna obtida na consulta aos metadados é igual a coluna inspecionada?
			if(colunaPK.equals(nomeColuna)) {
				System.out.println(nomeColuna + " é PRIMARY KEY da tabela " + nomeTabela);
				pk = true;
				break;
			}
			
		}
		
		return pk;
	}
	
	/**
	 * Obtém os metadados de coluna a partir da conexão atual e tabela informada.
	 * 
	 * @param conexao - A conexão informada para recuperar dados para VoMetadado.
	 * @param nomeTabela - O nome da tabela para se obter os metadados das colunas.
	 * @return Retorna uma lista de VoColuna.
	 * @throws SQLException
	 */
	private List<VoColuna> obterMTColuna(Connection conexao, String nomeTabela) throws SQLException {
		
		ResultSet rsColuna     = conexao.getMetaData().getColumns(conexao.getCatalog(), null, nomeTabela, "");
		List<VoColuna> colunas = new ArrayList<VoColuna>();
		
		while(rsColuna.next()) {
			
			int dataType = rsColuna.getInt("DATA_TYPE");
			
			System.out.println(rsColuna.getString("COLUMN_NAME") + " - DATA_TYPE(código: " + dataType + " - " + rsColuna.getString("TYPE_NAME") +")");
			
			VoColuna voColuna = new VoColuna();
			voColuna.setNomeColuna(rsColuna.getString("COLUMN_NAME"));
			
			// MISAEL - 20/08/2015 - CONDIÇÃO PARA BANCO MYSQL E POSTGRE.
			if(SessaoGerador.enSGBDREscolhido == EnSGBDR.MYSQL) {
			
				if(SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {
					voColuna.setTipoDado(this.fazerDeParaJavaMySql(rsColuna.getString("TYPE_NAME")));
				} else if(SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {
					voColuna.setTipoDado(this.fazerDeParaCSNetMySql(rsColuna.getString("TYPE_NAME")));
				}
				
			} else if(SessaoGerador.enSGBDREscolhido == EnSGBDR.POSTGRESQL) {
				
				if(SessaoGerador.enTecnologiaEscolhida == EnTecnologia.JAVA) {
					voColuna.setTipoDado(this.fazerDeParaJavaPostrgre(rsColuna.getString("TYPE_NAME")));
				} else if(SessaoGerador.enTecnologiaEscolhida == EnTecnologia.CSNET) {
					voColuna.setTipoDado(this.fazerDeParaCSNetPostgre(rsColuna.getString("TYPE_NAME")));
				}
				
			}
			
			voColuna.setPrimaryKey( this.confirmaChavePrimaria(conexao, nomeTabela, rsColuna.getString("COLUMN_NAME")) );
			
			// 06/10/2015
			// TODO chamar o método getImportedKeys aqui, e alimentar uma estrutura de dados (Classe ForeignKey)
			// essa estrutura irá substituir os campos FK na classe.
			voColuna.setForeignKey(false);
			
			// Para NULLABLE, "0" identifica que a coluna não é nula.
			voColuna.setNotNull((rsColuna.getInt("NULLABLE") == 0 ? true : false));
			// Para IS_NULLABLE "NO" identifica que a coluna não é nula.
			// voColuna.setNotNull((rsColuna.getString("IS_NULLABLE").equals("NO") ? true : false));
			voColuna.setTamanho(rsColuna.getInt("COLUMN_SIZE"));
			
			colunas.add(voColuna);
			
		}
		
		System.out.println("\n");
		
		return colunas;
		
	}
	
	// MISAEL - 07/10/2015 - imlpementação do método para buscar os metadados para se montar a associação na ORM.
	private List<VoForeignKey> obterChavesImportadas(Connection conexao, String nomeTabela) throws SQLException {
		
		DatabaseMetaData dbmd   = conexao.getMetaData();
		ResultSet rsFk          = dbmd.getImportedKeys(conexao.getCatalog(), conexao.getSchema(), nomeTabela);
		
		List<VoForeignKey> voForeignKeys = new ArrayList<VoForeignKey>();
		
		while(rsFk.next()) {
			
			VoForeignKey voForeignKey = new VoForeignKey();
			voForeignKey.setNomeTabelaPai(rsFk.getString("PKTABLE_NAME")); // Nome da tabela pai
			voForeignKey.setNomeColunaPK(rsFk.getString("PKCOLUMN_NAME"));  // A coluna que a FK aponta na tabela pai.
			voForeignKey.setNomeColunaFK(rsFk.getString("FKCOLUMN_NAME"));  // A coluna que é FK na tabela atual.
			voForeignKey.setNomeFK(rsFk.getString("FK_NAME"));             // O nome da FK na tabela atual.
			
			voForeignKeys.add(voForeignKey);
			
		}
		
		return voForeignKeys;
		
	}
		
	// MISAEL - 02/02/2015 - nova implementação de data_types.
	// MISAEL - 03/02/2015 - revisão dos tipos de dados implementados conforme o link e a tabela do livro
	// ver http://dev.mysql.com/doc/connector-j/en/connector-j-reference-type-conversions.html
	// Correspondência de tipos de banco no java
	// http://docs.oracle.com/javase/8/docs/api/java/sql/Types.html
	private EnTipoDado fazerDeParaJavaMySql(String tipoDadoBanco) {
		
		EnTipoDado enTipoDado = null;
		
		switch(tipoDadoBanco) {
		
		case "BIGINT":                       // OK
		case "INTEGER UNSIGNED":             // OK
			enTipoDado = EnTipoDado.JAVA_LONG;
			break;
			
		case "BIGINT UNSIGNED":              // OK
			enTipoDado = EnTipoDado.JAVA_BIGINTEGER;
			break;
		
		case "BIT":                          // OK
			enTipoDado = EnTipoDado.JAVA_BOOLEAN;
			break;
			
		case "CHAR":                         // OK
		case "VARCHAR":                      // OK
		case "TEXT":                         // OK
		case "LONGVARCHAR":                  // OK
		case "ENUM":                         // OK
		case "SET":                          // OK
		case "TINYTEXT":                     // somente no link
		case "MEDIUMTEXT":                   // somente no link
		case "LONGTEXT":                     // somente no link
			enTipoDado = EnTipoDado.JAVA_STRING;
			break;
			
		case "DATE":                         // OK
			enTipoDado = EnTipoDado.JAVA_DATE;
			break;
			
		case "TIME":                         // OK
			enTipoDado = EnTipoDado.JAVA_TIME;
			break;
			
		case "TIMESTAMP":                    // OK
		case "DATETIME":                     // OK
			enTipoDado = EnTipoDado.JAVA_TIMESTAMP;
			break;
			
		case "DECIMAL":                      // OK
		case "NUMERIC":                      // OK
			enTipoDado = EnTipoDado.JAVA_BIGDECIMAL;
			break;
			
		case "DOUBLE":                       // OK
		case "FLOAT":                        // OK
			enTipoDado = EnTipoDado.JAVA_DOUBLE;
			break;
		
		case "TINYINT UNSIGNED":             // não tem definição no livro e nem no site, ficou definido como "int" conforme consta no banco MySql.
		case "INT":                          // OK
		case "INTEGER":                      // OK
		case "MEDIUMINT":                    // OK
		case "MEDIUMINT UNSIGNED":           // OK
			enTipoDado = EnTipoDado.JAVA_INT;
			break;
			
		case "REAL":                         // OK
			enTipoDado = EnTipoDado.JAVA_FLOAT;
			break;
			
		case "SMALLINT":                     // OK
		case "SMALLINT UNSIGNED":            // OK
		case "YEAR":                         // no caso do YEAR do Mysql, conforme configuração do banco, pode ser um short ou um date(com o dia e mês configurado para 01).
			enTipoDado = EnTipoDado.JAVA_SHORT;
			break;
			
		case "TINYINT":                      // na lista do site pode ser um boolean ou um int do java, no livro é um byte.
			enTipoDado = EnTipoDado.JAVA_BYTE;
			break;
			
		case "BINARY":                       // OK
		case "VARBINARY":                    // OK
		case "LONGVARBINARY":                // OK
		case "TINYBLOB":                     // somente no link
		case "MEDIUMBLOB":                   // somente no link
			enTipoDado = EnTipoDado.JAVA_ARRAY_BYTE;
			break;
			
		case "BLOB":                        // na lista é um byte[]
		case "LONGBLOB":                    // Adicionado em 19/04/2015 - Correção para Northwind.
			enTipoDado = EnTipoDado.JAVA_BLOB;
			break;
			
		case "CLOB":                        // na lista é um byte[]
			enTipoDado = EnTipoDado.JAVA_CLOB;
			break;
			
		default:
			// TODO verificar, aqui muito certo pode ser usado o Object (pois no javax.sql o Object pode ser utilizado para tudo!)
			enTipoDado = EnTipoDado.DESCONHECIDO;  
			break;
			
		}
		
		return enTipoDado;
	}
	
	private EnTipoDado fazerDeParaJavaPostrgre(String tipoDadoBanco) {
		
		EnTipoDado enTipoDado = null;
		
		switch(tipoDadoBanco) {
		
		case "int8":
		case "bigserial":
		case "pg_lsn":
		case "txid_snapshot":
			enTipoDado =  EnTipoDado.JAVA_LONG;
			break;
			
		case "bit":
		case "bool":
			enTipoDado = EnTipoDado.JAVA_BOOLEAN;
			break;
			
		case "varbit":
		case "bytea":
		case "box":
		case "circle":
		case "json":
		case "jsonb":
		case "line":
		case "lseg":
		case "path":
		case "point":
		case "polygon":
		case "uuid":
			enTipoDado = EnTipoDado.JAVA_ARRAY_BYTE;
			break;
		
		case "bpchar":
		case "varchar":
		case "text":
		case "cidr":
		case "inet":
		case "macaddr":
		case "tsquery":
		case "tsvector":
		case "xml":
			enTipoDado = EnTipoDado.JAVA_STRING;
			break;
		
		case "date":
			enTipoDado = EnTipoDado.JAVA_DATE;
			break;			
		
		case "interval":
		case "time":
		case "timestamp":
			enTipoDado = EnTipoDado.JAVA_TIMESTAMP;
			break;
		
		case "float8":
		case "money":
			enTipoDado = EnTipoDado.JAVA_DOUBLE;
			break;
		
		case "int4":
		case "serial":
			enTipoDado = EnTipoDado.JAVA_INT;
			break;
			
		case "numeric":
			enTipoDado = EnTipoDado.JAVA_BIGDECIMAL;
			break;
		
		case "float4":
			enTipoDado = EnTipoDado.JAVA_FLOAT;
			break;
			
		case "int2":
			enTipoDado = EnTipoDado.JAVA_SHORT;
			break;
			
		default:
			// TODO verificar, aqui muito certo pode ser usado o Object (pois no java.sql o Object pode ser utilizado para tudo!)
			enTipoDado = EnTipoDado.DESCONHECIDO;  
			break;
			
		}
		
		return enTipoDado;
		
	}
	
	private EnTipoDado fazerDeParaCSNetMySql(String tipoDadoBanco) {
	
		EnTipoDado enTipoDado = null;
		
		switch(tipoDadoBanco) {
		
		case "BIGINT":                       // 
		case "INTEGER UNSIGNED":             // 
			enTipoDado = EnTipoDado.CS_LONG;
			break;
			
		case "BIGINT UNSIGNED":              //
			enTipoDado = EnTipoDado.CS_LONG;
			break;
		
		case "BIT":                          //
			enTipoDado = EnTipoDado.CS_BOOL;
			break;
			
		case "CHAR":                         // 
		case "VARCHAR":                      // 
		case "TEXT":                         // 
		case "LONGVARCHAR":                  // 
		case "ENUM":                         // 
		case "SET":                          // 
		case "TINYTEXT":                     // 
		case "MEDIUMTEXT":                   // 
		case "LONGTEXT":                     // 
			enTipoDado = EnTipoDado.CS_STRING;
			break;
			
		case "DATE":                         //
			enTipoDado = EnTipoDado.CS_DATETIME;
			break;
			
		case "TIME":                         //
			enTipoDado = EnTipoDado.CS_TIMESPAN;
			break;
			
		case "TIMESTAMP":                    //
		case "DATETIME":                     //
			enTipoDado = EnTipoDado.CS_DATETIME;
			break;
			
		case "DECIMAL":                      //
		case "NUMERIC":                      //
			enTipoDado = EnTipoDado.CS_DECIMAL;
			break;
			
		case "DOUBLE":                       //
			enTipoDado = EnTipoDado.CS_DOUBLE;
			break;
			
		case "FLOAT":                        //
			enTipoDado = EnTipoDado.CS_FLOAT;
			break;
		
		case "TINYINT UNSIGNED":             //
		case "INT":                          //
		case "INTEGER":                      //
		case "MEDIUMINT":                    //
		case "MEDIUMINT UNSIGNED":           //
			enTipoDado = EnTipoDado.CS_INT;
			break;
			
		case "REAL":                         //
			enTipoDado = EnTipoDado.CS_DOUBLE;
			break;
			
		case "SMALLINT":                     //
		case "SMALLINT UNSIGNED":            //
		case "YEAR":                         //
			enTipoDado = EnTipoDado.CS_SHORT;
			break;
			
		case "TINYINT":                      //
			enTipoDado = EnTipoDado.CS_SBYTE;
			break;
			
		case "BINARY":                       //
		case "VARBINARY":                    //
		case "LONGVARBINARY":                //
		case "TINYBLOB":                     //
		case "MEDIUMBLOB":                   //
		case "BLOB":                         // 
		case "LONGBLOB":                     //
		case "CLOB":                         //
			enTipoDado = EnTipoDado.CS_ARRAY_BYTE;
			break;
						
		default:
			enTipoDado = EnTipoDado.DESCONHECIDO;  
			break;
			
		}
		
		return enTipoDado;
		
	}
	
	private EnTipoDado fazerDeParaCSNetPostgre(String tipoDadoBanco) {
		
		EnTipoDado enTipoDado = null;
		
		switch(tipoDadoBanco) {
		
		case "int8":           // OK
		case "bigserial":      // OK
		case "pg_lsn":         // *
		case "txid_snapshot":  // *
			enTipoDado =  EnTipoDado.CS_LONG;
			break;
			
		case "bit":   // OK
		case "bool":  // OK
			enTipoDado = EnTipoDado.CS_BOOL;
			break;
			
		case "varbit": // ok
			enTipoDado = EnTipoDado.CS_BITARRAY;
			break;
			
		case "box": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLBOX;
			break;
			
		case "circle": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLCIRCLE;
			break;
			
		case "line": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLINET;
			break;
		
		case "point": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLPOINT;
			break;
			
		case "polygon": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLPOLYGON;
			break;
			
		case "uuid": // OK
			enTipoDado = EnTipoDado.CS_GUID;
			break;
			
		case "bytea": // OK
			enTipoDado = EnTipoDado.CS_ARRAY_BYTE;
			break;
			
		case "lseg": // OK
		case "path": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLLSEG;
			break;
		
		case "bpchar":     // OK
		case "varchar":    // OK
		case "text":       // OK
		case "xml":        // OK
		case "json":       // OK
		case "jsonb":      // OK
			enTipoDado = EnTipoDado.CS_STRING;
			break;
			
		case "cidr": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLINET;
			break;
			
		case "inet": // OK
			enTipoDado = EnTipoDado.CS_IPADDRESS;
			break;
			
		case "macaddr": // OK
			enTipoDado = EnTipoDado.CS_PHYSICALADDRESS;
			break;
			
		case "tsquery": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLTSQUERY;
			break;
			
		case "tsvector": // OK
			enTipoDado = EnTipoDado.CS_NPGSQLTSVECTOR;
			break;
		
		case "date":      // OK
		case "timestamp": // OK
			enTipoDado = EnTipoDado.CS_DATETIME;
			break;			
		
		case "interval":   // OK
		case "time":       // OK
			enTipoDado = EnTipoDado.CS_TIMESPAN;
			break;
		
		case "float8": // OK
			enTipoDado = EnTipoDado.CS_DOUBLE;
			break;
			
		case "money":   // OK
		case "numeric": // OK
			enTipoDado = EnTipoDado.CS_DECIMAL;
			break;
		
		case "int4":    // OK
		case "serial":  // OK
			enTipoDado = EnTipoDado.CS_INT;
			break;
		
		case "float4": // OK
			enTipoDado = EnTipoDado.CS_FLOAT;
			break;
			
		case "int2": // OK
			enTipoDado = EnTipoDado.CS_SHORT;
			break;
			
		default:
			enTipoDado = EnTipoDado.DESCONHECIDO;  
			break;
			
		}
		
		return enTipoDado;
	}
	
}
