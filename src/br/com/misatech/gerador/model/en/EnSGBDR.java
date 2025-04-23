package br.com.misatech.gerador.model.en;

public enum EnSGBDR {

	MYSQL, POSTGRESQL;

	@Override
	public String toString() {

		switch (this) {
		case MYSQL:
			return "MySql";

		case POSTGRESQL:
			return "PostgreSQL";

		default:
			return "";
		}

	}

}
