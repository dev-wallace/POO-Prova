package br.senac.sp.livraria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senac.sp.livraria.model.Departamento;

public class DepartamentoDao implements InterfaceDao<Departamento> {
    private Connection conexao;
    private String sql;
    private PreparedStatement stmt;
    
    public DepartamentoDao(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserir(Departamento departamento) throws SQLException {
        sql = "INSERT INTO departamento(nome) VALUES (?)";
        stmt = conexao.prepareStatement(sql);
        stmt.setString(1, departamento.getNome());
        stmt.execute();
        stmt.close();
    }

    @Override
    public void alterar(Departamento departamento) throws SQLException {
        sql = "UPDATE departamento SET nome = ? WHERE id = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setString(1, departamento.getNome());
        stmt.setInt(2, departamento.getId());
        stmt.execute();
        stmt.close();
    }

    @Override
    public void excluir(int id) throws SQLException {
        sql = "DELETE FROM departamento WHERE id = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }

    @Override
    public List<Departamento> listar() throws SQLException {
        sql = "SELECT * FROM departamento ORDER BY nome";
        stmt = conexao.prepareStatement(sql);
        List<Departamento> departamentos = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Departamento d = new Departamento();
            d.setId(rs.getInt("id"));
            d.setNome(rs.getString("nome"));
            departamentos.add(d);
        }
        
        rs.close();
        stmt.close();
        return departamentos;
    }

    @Override
    public Departamento buscar(int id) throws SQLException {
        sql = "SELECT * FROM departamento WHERE id = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        Departamento departamento = null;
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()) {
            departamento = new Departamento();
            departamento.setId(rs.getInt("id"));
            departamento.setNome(rs.getString("nome"));
        }
        
        rs.close();
        stmt.close();
        return departamento;
    }
}