package br.senac.sp.livraria.tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import br.senac.sp.livraria.model.Funcionario;


public class FuncionarioTableModel extends AbstractTableModel {
    private List<Funcionario> lista;
    private String[] colunas = {"ID", "Nome", "Salário", "Nível", "Departamento"};
    
    public FuncionarioTableModel(List<Funcionario> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Funcionario funcionario = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return funcionario.getId();
            case 1: return funcionario.getNome();
            case 2: return String.format("R$ %.2f", funcionario.getSalario());
            case 3: return funcionario.getNivel().toString();
            case 4: return funcionario.getDepartamentoId(); // Você pode melhorar para mostrar o nome do departamento
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    public void setLista(List<Funcionario> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
    
    public Funcionario getFuncionario(int row) {
        return lista.get(row);
    }
}