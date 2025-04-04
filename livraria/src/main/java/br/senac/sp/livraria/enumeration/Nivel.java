package br.senac.sp.livraria.enumeration;

public enum Nivel {
    JUNIOR("Júnior"), 
    PLENO("Pleno"), 
    SENIOR("Sênior");

    private String rotulo;

    private Nivel(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public String toString() {
        return this.rotulo;
    }
}