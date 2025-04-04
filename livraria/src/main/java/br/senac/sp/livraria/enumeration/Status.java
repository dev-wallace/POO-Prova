package br.senac.sp.livraria.enumeration;

public enum Status {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída");

    private String rotulo;

    private Status(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public String toString() {
        return this.rotulo;
    }
}