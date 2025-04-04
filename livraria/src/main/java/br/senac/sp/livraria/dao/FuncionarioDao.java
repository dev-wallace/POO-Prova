package br.senac.sp.livraria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senac.sp.livraria.enumeration.Nivel;
import br.senac.sp.livraria.model.Funcionario;

public class FuncionarioDao implements InterfaceDao<Funcionario> {
    private Connection conexao;
    
    public FuncionarioDao(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserir(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionario(nome, salario, nivel, departamento_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setDouble(2, funcionario.getSalario());
            stmt.setInt(3, funcionario.getNivel().ordinal());
            stmt.setInt(4, funcionario.getDepartamentoId());
            stmt.execute();
        }
    }

    @Override
    public void alterar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionario SET nome = ?, salario = ?, nivel = ?, departamento_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setDouble(2, funcionario.getSalario());
            stmt.setInt(3, funcionario.getNivel().ordinal());
            stmt.setInt(4, funcionario.getDepartamentoId());
            stmt.setInt(5, funcionario.getId());
            stmt.execute();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public List<Funcionario> listar() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY nome";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setSalario(rs.getDouble("salario"));
                f.setNivel(Nivel.values()[rs.getInt("nivel")]);
                f.setDepartamentoId(rs.getInt("departamento_id"));
                funcionarios.add(f);
            }
        }
        return funcionarios;
    }

    @Override
    public Funcionario buscar(int id) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        Funcionario funcionario = null;
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("id"));
                    funcionario.setNome(rs.getString("nome"));
                    funcionario.setSalario(rs.getDouble("salario"));
                    funcionario.setNivel(Nivel.values()[rs.getInt("nivel")]);
                    funcionario.setDepartamentoId(rs.getInt("departamento_id"));
                }
            }
        }
        return funcionario;
    }
}