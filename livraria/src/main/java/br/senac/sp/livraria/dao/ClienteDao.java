package br.senac.sp.livraria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.senac.sp.livraria.enumeration.Escolaridade;
import br.senac.sp.livraria.enumeration.EstadoCivil;
import br.senac.sp.livraria.model.Cliente;

public class ClienteDao implements InterfaceDao<Cliente> {
	private Connection conexao;
	private String sql;	
	private PreparedStatement stmt;
	
	public ClienteDao(Connection conexao) {
		this.conexao = conexao;
	}	

	@Override
	public void inserir(Cliente objeto) throws SQLException {
		sql = "insert into cliente(nome, cpf, nascimento, telefone, email, endereco, escolaridade, estadocivil)"
				+ " values(?,?,?,?,?,?,?,?)";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, objeto.getNome());
		stmt.setString(2, objeto.getCpf());		
		stmt.setDate(3, new Date(objeto.getNascimento().getTimeInMillis()));
		stmt.setString(4, objeto.getTelefone());
		stmt.setString(5, objeto.getEmail());
		stmt.setString(6, objeto.getEndereco());
		stmt.setInt(7, objeto.getEscolaridade().ordinal());
		stmt.setInt(8, objeto.getEstadoCivil().ordinal());
		stmt.execute();
		stmt.close();
	}

	@Override
	public void alterar(Cliente objeto) throws SQLException {
		sql = "update cliente set nome = ?, cpf = ?, nascimento = ?, telefone = ?, email = ?, "
				+ "endereco = ?, escolaridade = ?, estadocivil = ? where id = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, objeto.getNome());
		stmt.setString(2, objeto.getCpf());		
		stmt.setDate(3, new Date(objeto.getNascimento().getTimeInMillis()));
		stmt.setString(4, objeto.getTelefone());
		stmt.setString(5, objeto.getEmail());
		stmt.setString(6, objeto.getEndereco());
		stmt.setInt(7, objeto.getEscolaridade().ordinal());
		stmt.setInt(8, objeto.getEstadoCivil().ordinal());
		stmt.setInt(9, objeto.getId());
		stmt.execute();
		stmt.close();
	}

	@Override
	public void excluir(int id) throws SQLException {
		sql = "delete from cliente where id = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.execute();
		stmt.close();
	}

	@Override
	public List<Cliente> listar() throws SQLException {
		sql = "select * from cliente order by nome";
		stmt = conexao.prepareStatement(sql);
		List<Cliente> clientes = new ArrayList<Cliente>();
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Cliente c = new Cliente();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setCpf(rs.getString("cpf"));
			c.setEmail(rs.getString("email"));
			c.setTelefone(rs.getString("telefone"));
			c.setEndereco(rs.getString("endereco"));
			c.setEscolaridade
				(Escolaridade.values()[rs.getInt("escolaridade")]);
			c.setEstadoCivil
				(EstadoCivil.values()[rs.getInt("estadocivil")]);
			Calendar nasc = Calendar.getInstance();
			nasc.setTimeInMillis(rs.getDate("nascimento").getTime());
			c.setNascimento(nasc);
			clientes.add(c);
		}
		rs.close();
		stmt.close();
		return clientes;
	}
	
	@Override
	public Cliente buscar(int id) throws SQLException {
		sql = "select * from cliente where id = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setInt(1, id);
		Cliente cliente = null;
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			cliente = new Cliente();
			cliente.setId(rs.getInt("id"));
			cliente.setNome(rs.getString("nome"));
			cliente.setCpf(rs.getString("cpf"));
			cliente.setEmail(rs.getString("email"));
			cliente.setTelefone(rs.getString("telefone"));
			cliente.setEndereco(rs.getString("endereco"));
			cliente.setEscolaridade
				(Escolaridade.values()[rs.getInt("escolaridade")]);
			cliente.setEstadoCivil
				(EstadoCivil.values()[rs.getInt("estadocivil")]);
			Calendar nasc = Calendar.getInstance();
			nasc.setTimeInMillis(rs.getDate("nascimento").getTime());
			cliente.setNascimento(nasc);			
		}
		rs.close();
		stmt.close();
		return cliente;
	}



}
