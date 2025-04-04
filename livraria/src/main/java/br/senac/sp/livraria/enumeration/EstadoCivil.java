package br.senac.sp.livraria.enumeration;

public enum EstadoCivil {
	CASADO("Casado"), 
	DIVORCIADO("Divorciado"), 
	SOLTEIRO("Solteiro"), 
	UNIAO_ESTAVEL("União estável"), 
	VIUVO("Viúvo");
	
	private EstadoCivil(String rotulo) {
		this.rotulo = rotulo;
	}
	
	private String rotulo;
	
	@Override
	public String toString() {	
		return this.rotulo;
	}
}
