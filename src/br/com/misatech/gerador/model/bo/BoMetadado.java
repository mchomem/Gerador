package br.com.misatech.gerador.model.bo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.misatech.gerador.model.dao.DaoMetadado;
import br.com.misatech.gerador.model.vo.VoMetadado;
import br.com.misatech.gerador.model.vo.VoTabela;

/**
 * Contém a chamada a DaoMetadado para carregar um objeto do tipo VoMetadado.
 * 
 * @author Misael C. Homem
 *
 */
public class BoMetadado {

	private DaoMetadado daoMetadado;

	public BoMetadado() {

		this.daoMetadado = new DaoMetadado();

	}

	public VoMetadado lerMetadado(Connection conn)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		return this.daoMetadado.obterMetaDados(conn);

	}

	/// Java

	public String gerarStringClasseEntidadeJava(VoTabela voTabela) {

		BoResultadoVoJava boResultadoVo = new BoResultadoVoJava();
		return boResultadoVo.formatarClasseEntidadeJava(voTabela);

	}

	public String gerarStringClasseNegocioJava(VoTabela voTabela) {

		BoResultadoVoJava boResultadoVo = new BoResultadoVoJava();
		return boResultadoVo.formatarClasseNegocioJava(voTabela);

	}

	public String gerarStringClasseDaoJava(VoTabela voTabela) {

		BoResultadoVoJava boResultadoVo = new BoResultadoVoJava();
		return boResultadoVo.formatarClasseDaoJava(voTabela);

	}

	public String gerarStringClasseDaoConexaoJava() {

		BoResultadoVoJava boResultadoVo = new BoResultadoVoJava();
		return boResultadoVo.formatarClasseDaoConexaoJava();

	}

	/// C#.NET

	public String gerarStringClasseEntidadeCSNet(VoTabela voTabela) {

		BoResultadoVoCSNet boResultadoVo = new BoResultadoVoCSNet();
		return boResultadoVo.formatarClasseEntidadeCSNet(voTabela);

	}

	public String gerarStringClasseNegocioCSNet(VoTabela voTabela) {

		BoResultadoVoCSNet boResultadoVo = new BoResultadoVoCSNet();
		return boResultadoVo.formatarClasseNegocioCSNet(voTabela);

	}

	public String gerarStringClasseDaoCSNet(VoTabela voTabela) {

		BoResultadoVoCSNet boResultadoVo = new BoResultadoVoCSNet();
		return boResultadoVo.formatarClasseDaoCSNet(voTabela);

	}

	public String gerarStringClasseDaoConexaoCSNet() {

		BoResultadoVoCSNet boResultadoVo = new BoResultadoVoCSNet();
		return boResultadoVo.formatarClasseDaoConexaoCSNet();

	}

}
