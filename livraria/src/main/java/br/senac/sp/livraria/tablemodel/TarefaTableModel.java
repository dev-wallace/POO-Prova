package br.senac.sp.livraria.tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import br.senac.sp.livraria.model.Tarefa;


public class TarefaTableModel extends AbstractTableModel {
    private List<Tarefa> lista;
    private String[] colunas = {"ID", "Nome", "Descrição", "Status", "Funcionário"};
    
    public TarefaTableModel(List<Tarefa> lista) {
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
        Tarefa tarefa = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return tarefa.getId();
            case 1: return tarefa.getNome();
            case 2: return tarefa.getDescricao();
            case 3: return tarefa.getStatus().toString();
            case 4: return tarefa.getNome() + " " + tarefa.getId();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    public void setLista(List<Tarefa> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
    
    public Tarefa getTarefa(int row) {
        return lista.get(row);
    }
}