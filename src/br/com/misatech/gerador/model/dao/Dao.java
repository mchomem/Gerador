package br.com.misatech.gerador.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import br.com.misatech.gerador.model.SessaoGerador;
import br.com.misatech.gerador.model.en.EnSGBDR;

/**
 * Trata a conexão padrão da aplicação.
 * 
 * @author Misael C. Homem
 * 
 */
public class Dao {

	private Connection conexao;
	private String perfil = "";
	private String driver = "";
	private String url = "";
	private String usuario = "";
	private String senha = "";
	private String sgbdr = "";

	public Dao() {
	}

	public Dao(String perfil) {

		this.perfil = perfil;

	}

	public Dao(String driver, String url, String usuario, String senha, String sgbdr) {

		this.driver = driver;
		this.url = url;
		this.usuario = usuario;
		this.senha = senha;
		this.sgbdr = sgbdr;

	}

	public Connection conectarBD() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

		// Perfil de conexão foi informado?
		if (perfil.length() > 0) {

			Properties propriedades = new Properties();
			propriedades.load(new FileInputStream("config/" + perfil + ".properties"));

			this.driver = propriedades.getProperty("driver");
			this.url = propriedades.getProperty("url");
			this.usuario = propriedades.getProperty("usuario");
			this.senha = propriedades.getProperty("senha");
			this.sgbdr = propriedades.getProperty("sgbdr");

		}

		Class.forName(driver);
		conexao = DriverManager.getConnection(url, usuario, senha);

		// MISAEL - 20/09/2015 - definição do banco escolhido na sessão do gerador.
		/*
		 * if(driver.contains("mysql")) { SessaoGerador.enSGBDREscolhido =
		 * EnSGBDR.MYSQL; } else if(driver.contains("postgre")) {
		 * SessaoGerador.enSGBDREscolhido = EnSGBDR.POSTGRESQL; }
		 */

		// MISAEL - 29/08/2015 - melhoria, fazendo a leitura correta do arquivo de
		// propriedades.
		if (this.sgbdr.equals("MySql")) {
			SessaoGerador.enSGBDREscolhido = EnSGBDR.MYSQL;
		} else if (this.sgbdr.equals("PostgreSQL")) {
			SessaoGerador.enSGBDREscolhido = EnSGBDR.POSTGRESQL;
		}

		return conexao;

	}

	public void desconectarBD() throws SQLException {

		// MISAEL - 04/08/2015 - inserido teste para verificar se a conexão já é nula.
		if (conexao != null) {
			conexao.close();
			conexao = null;
		}

	}

	/**
	 * Encerra a conexão ou uma declaração preparada ou um grupo de resultados de
	 * dados.
	 * 
	 * @param conn O objeto de conexão.
	 * @param pstm O objeto de declaração preparada.
	 * @param rs   O objeto de grupo de resultados.
	 * @throws SQLException Disparado quando um erro de sql ocorrer.
	 */
	public void desconectarBD(Connection conn, PreparedStatement pstm, ResultSet rs) throws SQLException {

		if (conn != null) {
			conn.close();
		}

		if (pstm != null) {
			pstm.close();
		}

		if (rs != null) {
			rs.close();
		}

	}

	public boolean conexaoBDEstaFechada() throws SQLException {

		if (conexao == null) {
			return true;
		}

		return conexao.isClosed();

	}

}
