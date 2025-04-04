package br.senac.sp.livraria.enumeration;

public enum Escolaridade {
	FUNDAMENTAL("Fundamental"), 
	MEDIO("Médio"), 
	GRADUACAO("Graduação"), 
	POS("Pós-graduação");

	private Escolaridade(String rotulo) {
		this.rotulo = rotulo;
	}

	private String rotulo;

	@Override
	public String toString() {
		return this.rotulo;
	}
}
