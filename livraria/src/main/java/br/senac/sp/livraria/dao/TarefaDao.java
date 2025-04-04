package br.senac.sp.livraria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senac.sp.livraria.enumeration.Status;
import br.senac.sp.livraria.model.Tarefa;

public class TarefaDao implements InterfaceDao<Tarefa> {
    private Connection conexao;
    private String sql;
    private PreparedStatement stmt;
    
    public TarefaDao(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserir(Tarefa tarefa) throws SQLException {
        sql = "INSERT INTO tarefa(nome, descricao, status, funcionario_id) VALUES (?, ?, ?, ?)";
        stmt = conexao.prepareStatement(sql);
        stmt.setString(1, tarefa.getNome());
        stmt.setString(2, tarefa.getDescricao());
        stmt.setInt(3, tarefa.getStatus().ordinal());
        stmt.setInt(4, tarefa.getFuncionarioId());
        stmt.execute();
        stmt.close();
    }

    @Override
    public void alterar(Tarefa tarefa) throws SQLException {
        sql = "UPDATE tarefa SET nome = ?, descricao = ?, status = ?, funcionario_id = ? WHERE id = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setString(1, tarefa.getNome());
        stmt.setString(2, tarefa.getDescricao());
        stmt.setInt(3, tarefa.getStatus().ordinal());
        stmt.setInt(4, tarefa.getFuncionarioId());
        stmt.setInt(5, tarefa.getId());
        stmt.execute();
        stmt.close();
    }

    @Override
    public void excluir(int id) throws SQLException {
        sql = "DELETE FROM tarefa WHERE id = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }

    @Override
    public List<Tarefa> listar() throws SQLException {
        sql = "SELECT * FROM tarefa ORDER BY nome";
        stmt = conexao.prepareStatement(sql);
        List<Tarefa> tarefas = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Tarefa t = new Tarefa();
            t.setId(rs.getInt("id"));
            t.setNome(rs.getString("nome"));
            t.setDescricao(rs.getString("descricao"));
            t.setStatus(Status.values()[rs.getInt("status")]);
            t.setFuncionarioId(rs.getInt("funcionario_id"));
            tarefas.add(t);
        }
        
        rs.close();
        stmt.close();
        return tarefas;
    }

    @Override
    public Tarefa buscar(int id) throws SQLException {
        sql = "SELECT * FROM tarefa WHERE id = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        Tarefa tarefa = null;
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()) {
            tarefa = new Tarefa();
            tarefa.setId(rs.getInt("id"));
            tarefa.setNome(rs.getString("nome"));
            tarefa.setDescricao(rs.getString("descricao"));
            tarefa.setStatus(Status.values()[rs.getInt("status")]);
            tarefa.setFuncionarioId(rs.getInt("funcionario_id"));
        }
        
        rs.close();
        stmt.close();
        return tarefa;
    }
}