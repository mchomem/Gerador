package br.com.misatech.gerador.model.en;

public enum EnTecnologia {

	CSNET, JAVA;

	@Override
	public String toString() {

		switch (this) {
		case CSNET:
			return "C#.Net";

		case JAVA:
			return "Java";

		default:
			return "";
		}

	}

}
