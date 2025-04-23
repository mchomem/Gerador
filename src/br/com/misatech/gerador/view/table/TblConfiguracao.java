package br.com.misatech.gerador.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.misatech.gerador.model.vo.VoArquivoConfiguracao;

public class TblConfiguracao extends AbstractTableModel {

	private static final long serialVersionUID = 1520433551742287343L;
	private static final int COL_PERFIL_CONEXAO = 0;
	private static final int COL_SGBDR = 1;
	private static final int COL_DESCRICAO = 2;
	private static final int COL_DATA_HORA = 3;

	private List<VoArquivoConfiguracao> registros;

	public TblConfiguracao(List<VoArquivoConfiguracao> registros) {

		this.registros = registros;

	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return this.registros.size();
	}

	@Override
	public Object getValueAt(int indiceLinha, int indiceColuna) {

		VoArquivoConfiguracao voArquivoConfiguracao = registros.get(indiceLinha);

		if (indiceColuna == COL_PERFIL_CONEXAO) {

			return voArquivoConfiguracao.getNome();

		} else if (indiceColuna == COL_SGBDR) {

			return voArquivoConfiguracao.getSgbdr();

		} else if (indiceColuna == COL_DESCRICAO) {

			return voArquivoConfiguracao.getDescricao();

		} else if (indiceColuna == COL_DATA_HORA) {

			return voArquivoConfiguracao.getDataHora();

		}

		return null;
	}

	@Override
	public String getColumnName(int column) {

		String coluna = "";

		switch (column) {

		case COL_PERFIL_CONEXAO:

			coluna = "Nome";
			break;

		case COL_SGBDR:

			coluna = "SGBDR";
			break;

		case COL_DESCRICAO:

			coluna = "Descrição";
			break;

		case COL_DATA_HORA:

			coluna = "Data/Hora";
			break;

		default:

			throw new IllegalArgumentException("Coluna inválida");

		}

		return coluna;

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		if (columnIndex == COL_PERFIL_CONEXAO) {

			return String.class;

		} else if (columnIndex == COL_SGBDR) {

			return String.class;

		} else if (columnIndex == COL_DESCRICAO) {

			return String.class;

		} else if (columnIndex == COL_DATA_HORA) {

			return String.class;

		}

		return null;

	}

	public VoArquivoConfiguracao get(int linha) {

		return this.registros.get(linha);

	}

}
