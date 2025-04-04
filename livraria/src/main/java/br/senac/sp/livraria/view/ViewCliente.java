package br.senac.sp.livraria.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import br.senac.sp.livraria.dao.ClienteDao;
import br.senac.sp.livraria.dao.ConnectionFactory;
import br.senac.sp.livraria.dao.InterfaceDao;
import br.senac.sp.livraria.enumeration.Escolaridade;
import br.senac.sp.livraria.enumeration.EstadoCivil;
import br.senac.sp.livraria.model.Cliente;
import br.senac.sp.livraria.tablemodel.ClienteTableModel;

public class ViewCliente extends JFrame implements ActionListener {
	JLabel lbId, lbCpf, lbNome, lbNascimento, lbTelefone, lbEmail, lbEndereco, lbEstadoCivil, lbEscolaridade;
	JTextField tfId, tfCpf, tfNome, tfTelefone, tfEmail;
	JFormattedTextField tfNascimento;
	MaskFormatter mskNascimento;
	Font fontePadrao;
	JTextArea taEndereco;
	JComboBox<Escolaridade> cbEscolaridade;
	JComboBox<EstadoCivil> cbEstadoCivil;
	JButton btSalvar;
	Cliente cliente;
	Connection conexao;
	InterfaceDao<Cliente> daoCliente;
	JScrollPane spClientes;
	JTable tbClientes;
	ClienteTableModel modelCliente;
	List<Cliente> clientes;
	JButton btExcluir;
	JButton btLimpar;

	public static void main(String[] args) {
		new ViewCliente();
	}

	public ViewCliente() {
		try {
			conexao = ConnectionFactory.getConexao();
			daoCliente = new ClienteDao(conexao);
			clientes = daoCliente.listar();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		initComponents();
		actions();
	}

	// neste método "construíremos a tela"
	private void initComponents() {
		fontePadrao = new Font("Tahoma", Font.PLAIN, 12);

		// lbId
		lbId = new JLabel("ID: ");
		lbId.setBounds(20, 20, 30, 25);
		lbId.setFont(fontePadrao);

		// tfId
		tfId = new JTextField();
		tfId.setBounds(60, 20, 40, 25);
		tfId.setEnabled(false);
		tfId.setFont(fontePadrao);
		tfId.setHorizontalAlignment(SwingConstants.CENTER);

		// lbNome
		lbNome = new JLabel("Nome: ");
		lbNome.setBounds(110, 20, 50, 25);
		lbNome.setFont(fontePadrao);

		// tfNome
		tfNome = new JTextField();
		tfNome.setBounds(170, 20, 250, 25);
		tfNome.setFont(fontePadrao);

		// lbCpf
		lbCpf = new JLabel("CPF: ");
		lbCpf.setBounds(430, 20, 40, 25);
		lbCpf.setFont(fontePadrao);

		// tfCpf
		tfCpf = new JTextField();
		tfCpf.setBounds(480, 20, 150, 25);
		tfCpf.setFont(fontePadrao);

		// lbNascimento
		lbNascimento = new JLabel("Nascimento:");
		lbNascimento.setBounds(20, 55, 70, 25);
		lbNascimento.setFont(fontePadrao);

		// mskNascimento
		try {
			mskNascimento = new MaskFormatter("##/##/####");
			mskNascimento.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		// tfNascimento
		tfNascimento = new JFormattedTextField(mskNascimento);
		tfNascimento.setBounds(100, 55, 90, 25);
		tfNascimento.setFont(fontePadrao);
		tfNascimento.setHorizontalAlignment(SwingConstants.CENTER);

		// lbTelefone:
		lbTelefone = new JLabel("Tel:");
		lbTelefone.setBounds(200, 55, 30, 25);
		lbTelefone.setFont(fontePadrao);

		// tfTelefone
		tfTelefone = new JTextField();
		tfTelefone.setBounds(230, 55, 90, 25);
		tfTelefone.setFont(fontePadrao);

		// lbEmail:
		lbEmail = new JLabel("E-mail:");
		lbEmail.setBounds(330, 55, 50, 25);
		lbEmail.setFont(fontePadrao);

		// tfEmail
		tfEmail = new JTextField();
		tfEmail.setBounds(380, 55, 250, 25);
		tfEmail.setFont(fontePadrao);

		// lbEscolaridade
		lbEscolaridade = new JLabel("Escolaridade: ");
		lbEscolaridade.setBounds(20, 90, 90, 25);
		lbEscolaridade.setFont(fontePadrao);

		// cbEscolaridade
		cbEscolaridade = new JComboBox<Escolaridade>(Escolaridade.values());
		cbEscolaridade.setBounds(100, 90, 170, 25);
		cbEscolaridade.setFont(fontePadrao);
		cbEscolaridade.setSelectedIndex(-1);

		// lbEstadoCivil
		lbEstadoCivil = new JLabel("Estado civil: ");
		lbEstadoCivil.setBounds(20, 125, 90, 25);
		lbEstadoCivil.setFont(fontePadrao);

		// cbEstadoCivil
		cbEstadoCivil = new JComboBox<EstadoCivil>(EstadoCivil.values());
		cbEstadoCivil.setBounds(100, 125, 170, 25);
		cbEstadoCivil.setFont(fontePadrao);
		cbEstadoCivil.setSelectedIndex(-1);

		// lbEndereco
		lbEndereco = new JLabel("Endereço:");
		lbEndereco.setBounds(280, 90, 60, 25);
		lbEndereco.setFont(fontePadrao);

		// taEndereco
		taEndereco = new JTextArea();
		taEndereco.setBounds(350, 90, 280, 60);
		taEndereco.setFont(fontePadrao);

		// btSalvar
		btSalvar = new JButton("Salvar");
		btSalvar.setBounds(20, 160, 80, 25);
		btSalvar.setFont(fontePadrao);
		btSalvar.setMnemonic('a');

		// btExcluir
		btExcluir = new JButton("Excluir");
		btExcluir.setBounds(110, 160, 80, 25);
		btExcluir.setFont(fontePadrao);
		
		// btLimpar
		btLimpar = new JButton("Limpar");
		btLimpar.setBounds(200,160,80,25);
		btLimpar.setFont(fontePadrao);

		// modelCliente
		modelCliente = new ClienteTableModel(clientes);

		// tbClientes
		tbClientes = new JTable(modelCliente);
		tbClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// tbClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// tbClientes.getColumnModel().getColumn(0).setPreferredWidth(40);
		// tbClientes.getColumnModel().getColumn(1).setPreferredWidth(200);
		// tbClientes.getColumnModel().getColumn(2).setPreferredWidth(100);

		// spClientes
		spClientes = new JScrollPane(tbClientes);
		spClientes.setBounds(20, 195, 610, 450);

		// adicionar componentes
		Container base = getContentPane();
		base.setLayout(null);
		base.setBackground(Color.lightGray);
		base.add(lbId);
		base.add(tfId);
		base.add(lbNome);
		base.add(tfNome);
		base.add(lbCpf);
		base.add(tfCpf);
		base.add(lbNascimento);
		base.add(tfNascimento);
		base.add(lbTelefone);
		base.add(tfTelefone);
		base.add(lbEmail);
		base.add(tfEmail);
		base.add(lbEscolaridade);
		base.add(cbEscolaridade);
		base.add(lbEstadoCivil);
		base.add(cbEstadoCivil);
		base.add(lbEndereco);
		base.add(taEndereco);
		base.add(spClientes);
		base.add(btSalvar);
		base.add(btExcluir);
		base.add(btLimpar);

		// parâmetros do frame
		setSize(670, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Cadastro de Clientes");
		setResizable(false);

	}

	// neste método definiremos os comportamentos
	private void actions() {
		// ação do botão salvar
		btSalvar.addActionListener(this);
		// ação do botão salvar com classe anônima
		btSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicou no botão 2ª vez");
			}
		});
		// ação do botão salvar usando expressão lambda
		btSalvar.addActionListener(e -> {
			// validar os campos
			if (tfNome.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Informe o nome", "Erro", JOptionPane.WARNING_MESSAGE);
				tfNome.requestFocus();
			} else if (tfCpf.getText().isEmpty() || tfCpf.getText().length() != 11) {
				JOptionPane.showMessageDialog(this, "Informe o cpf", "Erro", JOptionPane.WARNING_MESSAGE);
				tfCpf.requestFocus();
			} else if (tfNascimento.getValue() == null) {
				JOptionPane.showMessageDialog(this, "Informe a data de nascimento", "Erro",
						JOptionPane.WARNING_MESSAGE);
				tfNascimento.requestFocus();
			} else if (cbEscolaridade.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "Informe a escolaridade", "Erro", JOptionPane.WARNING_MESSAGE);
				cbEscolaridade.requestFocus();
			} else if (cbEstadoCivil.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "Informe o estado civil", "Erro", JOptionPane.WARNING_MESSAGE);
				cbEstadoCivil.requestFocus();
			} else {
				if (cliente == null) {
					cliente = new Cliente();
				}
				cliente.setNome(tfNome.getText());
				cliente.setCpf(tfCpf.getText());
				// conversão da String para data
				Calendar dataNasc = Calendar.getInstance();
				SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
				try {
					dataNasc.setTime(fmt.parse(tfNascimento.getText()));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this, "Erro ao converter a data");
				}
				cliente.setNascimento(dataNasc);
				cliente.setTelefone(tfTelefone.getText());
				cliente.setEmail(tfEmail.getText());
				cliente.setEscolaridade((Escolaridade) cbEscolaridade.getSelectedItem());
				cliente.setEstadoCivil((EstadoCivil) cbEstadoCivil.getSelectedItem());
				cliente.setEndereco(taEndereco.getText());

				// salvar no banco
				try {
					if (cliente.getId() == 0) {
						daoCliente.inserir(cliente);
					} else {
						daoCliente.alterar(cliente);
					}
					clientes = daoCliente.listar();
					modelCliente.setLista(clientes);
					modelCliente.fireTableDataChanged();
					limpar();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ViewCliente.this, "Erro ao inserir: " + e1.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});

		// permitir somente números na tfCpf
		tfCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()) || tfCpf.getText().length() == 11) {
					e.consume();
				}
			}
		});

		// evento ao selecionar item na tabela
		tbClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int linha = tbClientes.getSelectedRow();
				if (linha >= 0) {
					cliente = clientes.get(linha);
					tfId.setText(cliente.getId() + "");
					tfNome.setText(cliente.getNome());
					tfCpf.setText(cliente.getCpf());
					tfEmail.setText(cliente.getEmail());
					taEndereco.setText(cliente.getEndereco());
					cbEscolaridade.setSelectedItem(cliente.getEscolaridade());
					cbEstadoCivil.setSelectedItem(cliente.getEstadoCivil());
					tfTelefone.setText(cliente.getTelefone());
					SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
					tfNascimento.setValue(dataFormatada.format(cliente.getNascimento().getTime()));
				}
			}
		});

		// clique do botão excluir
		btExcluir.addActionListener(e -> {
			if(cliente == null) {
				JOptionPane.showMessageDialog
					(ViewCliente.this, "Selecione um cliente para excluí-lo", "Aviso",JOptionPane.WARNING_MESSAGE );
			}else {
				int resp =JOptionPane.showConfirmDialog(ViewCliente.this, 
						"Deseja excluir o cliente "+cliente.getNome()+"?", "Confirmar exclusão", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(resp == JOptionPane.YES_OPTION) {
					try {
						daoCliente.excluir(cliente.getId());
						clientes = daoCliente.listar();
						modelCliente.setLista(clientes);
						modelCliente.fireTableDataChanged();
						limpar();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(ViewCliente.this, e2.getMessage());
						e2.printStackTrace();
					}
					
				}
			}
		});
		
		// clique do botão limpar
		btLimpar.addActionListener(e -> {
			limpar();
		});
	}

	private void limpar() {
		cliente = null;
		tfId.setText(null);
		tfNome.setText(null);
		tfCpf.setText(null);
		tfNascimento.setValue(null);
		tfTelefone.setText(null);
		tfEmail.setText(null);
		cbEscolaridade.setSelectedIndex(-1);
		cbEstadoCivil.setSelectedIndex(-1);
		taEndereco.setText(null);
		tfNome.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clicou no botão 1ª vez");
	}

}
