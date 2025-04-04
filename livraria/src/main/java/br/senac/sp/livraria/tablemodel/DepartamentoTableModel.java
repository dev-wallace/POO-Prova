package br.senac.sp.livraria.tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import br.senac.sp.livraria.model.Departamento;

public class DepartamentoTableModel extends AbstractTableModel {
    private List<Departamento> lista;
    private String[] colunas = {"ID", "Nome"};
    
    public DepartamentoTableModel(List<Departamento> lista) {
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
        Departamento departamento = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return departamento.getId();
            case 1: return departamento.getNome();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    public void setLista(List<Departamento> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
    
    public Departamento getDepartamento(int row) {
        return lista.get(row);
    }
}