package br.senac.sp.livraria.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import br.senac.sp.livraria.dao.*;
import br.senac.sp.livraria.enumeration.*;
import br.senac.sp.livraria.model.*;
import br.senac.sp.livraria.tablemodel.*;

public class ViewFuncionario extends JFrame {
    // Componentes para Funcionário
    private JTextField tfId, tfNome, tfSalario;
    private JComboBox<Nivel> cbNivel;
    private JComboBox<Departamento> cbDepartamento;
    private JButton btSalvar, btExcluir, btLimpar;
    private JTable tbFuncionarios;
    private FuncionarioTableModel modelFuncionario;
    
    // Componentes para Tarefa
    private JTextField tfTarefaId, tfTarefaNome;
    private JTextArea taTarefaDescricao;
    private JComboBox<Status> cbTarefaStatus;
    private JComboBox<Funcionario> cbTarefaFuncionario;
    private JButton btTarefaSalvar, btTarefaExcluir, btTarefaLimpar;
    private JTable tbTarefas;
    private TarefaTableModel modelTarefa;
    
    // Componentes para Departamento
    private JTextField tfDepartamentoId, tfDepartamentoNome;
    private JButton btDepartamentoSalvar, btDepartamentoExcluir, btDepartamentoLimpar;
    private JTable tbDepartamentos;
    private DepartamentoTableModel modelDepartamento;
    
    private Connection conexao;
    private FuncionarioDao daoFuncionario;
    private TarefaDao daoTarefa;
    private DepartamentoDao daoDepartamento;
    private List<Funcionario> funcionarios;
    private List<Tarefa> tarefas;
    private List<Departamento> departamentos;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ViewFuncionario().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar a aplicação:\n" + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
    
    public ViewFuncionario() throws SQLException {
        super("Sistema de Gestão de Funcionários");
        initializeDatabaseConnection();
        initComponents();
        actions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initializeDatabaseConnection() {
        try {
            conexao = ConnectionFactory.getConexao();
            daoFuncionario = new FuncionarioDao(conexao);
            daoTarefa = new TarefaDao(conexao);
            daoDepartamento = new DepartamentoDao(conexao);
            
            carregarDadosIniciais();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao conectar ao banco de dados:\n" + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void carregarDadosIniciais() throws SQLException {
        funcionarios = daoFuncionario.listar();
        departamentos = daoDepartamento.listar();
        tarefas = daoTarefa.listar();
        
        if (funcionarios == null) funcionarios = new ArrayList<>();
        if (departamentos == null) departamentos = new ArrayList<>();
        if (tarefas == null) tarefas = new ArrayList<>();
    }
    
    private void initComponents() throws SQLException {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Aba de Funcionários
        tabbedPane.addTab("Funcionários", criarPainelFuncionarios());
        
        // Aba de Tarefas
        tabbedPane.addTab("Tarefas", criarPainelTarefas());
        
        // Aba de Departamentos
        tabbedPane.addTab("Departamentos", criarPainelDepartamentos());
        
        add(tabbedPane);
    }
    
    private JPanel criarPainelFuncionarios() throws SQLException {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        
        // Componentes
        tfId = new JTextField();
        tfId.setEditable(false);
        tfNome = new JTextField();
        tfSalario = new JTextField();
        cbNivel = new JComboBox<>(Nivel.values());
        cbDepartamento = new JComboBox<>();
        atualizarComboDepartamentos();
        
        // Adicionando componentes ao formulário
        formPanel.add(new JLabel("ID:"));
        formPanel.add(tfId);
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfNome);
        formPanel.add(new JLabel("Salário:"));
        formPanel.add(tfSalario);
        formPanel.add(new JLabel("Nível:"));
        formPanel.add(cbNivel);
        formPanel.add(new JLabel("Departamento:"));
        formPanel.add(cbDepartamento);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btSalvar = new JButton("Salvar");
        btExcluir = new JButton("Excluir");
        btLimpar = new JButton("Limpar");
        buttonPanel.add(btSalvar);
        buttonPanel.add(btExcluir);
        buttonPanel.add(btLimpar);
        
        // Tabela
        modelFuncionario = new FuncionarioTableModel(funcionarios);
        tbFuncionarios = new JTable(modelFuncionario);
        tbFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tbFuncionarios);
        
        // Adicionando tudo ao painel principal
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelTarefas() throws SQLException {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        
        // Componentes
        tfTarefaId = new JTextField();
        tfTarefaId.setEditable(false);
        tfTarefaNome = new JTextField();
        taTarefaDescricao = new JTextArea(3, 20);
        cbTarefaStatus = new JComboBox<>(Status.values());
        cbTarefaFuncionario = new JComboBox<>();
        atualizarCombosFuncionarios();
        
        // Adicionando componentes ao formulário
        formPanel.add(new JLabel("ID:"));
        formPanel.add(tfTarefaId);
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfTarefaNome);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(new JScrollPane(taTarefaDescricao));
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cbTarefaStatus);
        formPanel.add(new JLabel("Funcionário:"));
        formPanel.add(cbTarefaFuncionario);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btTarefaSalvar = new JButton("Salvar");
        btTarefaExcluir = new JButton("Excluir");
        btTarefaLimpar = new JButton("Limpar");
        buttonPanel.add(btTarefaSalvar);
        buttonPanel.add(btTarefaExcluir);
        buttonPanel.add(btTarefaLimpar);
        
        // Tabela
        modelTarefa = new TarefaTableModel(tarefas);
        tbTarefas = new JTable(modelTarefa);
        tbTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tbTarefas);
        
        // Adicionando tudo ao painel principal
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelDepartamentos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        
        // Componentes
        tfDepartamentoId = new JTextField();
        tfDepartamentoId.setEditable(false);
        tfDepartamentoNome = new JTextField();
        
        // Adicionando componentes ao formulário
        formPanel.add(new JLabel("ID:"));
        formPanel.add(tfDepartamentoId);
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfDepartamentoNome);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btDepartamentoSalvar = new JButton("Salvar");
        btDepartamentoExcluir = new JButton("Excluir");
        btDepartamentoLimpar = new JButton("Limpar");
        buttonPanel.add(btDepartamentoSalvar);
        buttonPanel.add(btDepartamentoExcluir);
        buttonPanel.add(btDepartamentoLimpar);
        
        // Tabela
        modelDepartamento = new DepartamentoTableModel(departamentos);
        tbDepartamentos = new JTable(modelDepartamento);
        tbDepartamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tbDepartamentos);
        
        // Adicionando tudo ao painel principal
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void actions() {
        // Ações para Funcionários
        btSalvar.addActionListener(e -> salvarFuncionario());
        btExcluir.addActionListener(e -> excluirFuncionario());
        btLimpar.addActionListener(e -> limparFuncionario());
        
        tbFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tbFuncionarios.getSelectedRow();
                if (linha >= 0) {
                    Funcionario funcionario = funcionarios.get(linha);
                    carregarFuncionarioNoFormulario(funcionario);
                }
            }
        });
        
        // Ações para Tarefas
        btTarefaSalvar.addActionListener(e -> salvarTarefa());
        btTarefaExcluir.addActionListener(e -> excluirTarefa());
        btTarefaLimpar.addActionListener(e -> limparTarefa());
        
        tbTarefas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tbTarefas.getSelectedRow();
                if (linha >= 0) {
                    Tarefa tarefa = tarefas.get(linha);
                    carregarTarefaNoFormulario(tarefa);
                }
            }
        });
        
        // Ações para Departamentos
        btDepartamentoSalvar.addActionListener(e -> salvarDepartamento());
        btDepartamentoExcluir.addActionListener(e -> excluirDepartamento());
        btDepartamentoLimpar.addActionListener(e -> limparDepartamento());
        
        tbDepartamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tbDepartamentos.getSelectedRow();
                if (linha >= 0) {
                    Departamento departamento = departamentos.get(linha);
                    carregarDepartamentoNoFormulario(departamento);
                }
            }
        });
    }
    
    private void carregarFuncionarioNoFormulario(Funcionario funcionario) {
        tfId.setText(String.valueOf(funcionario.getId()));
        tfNome.setText(funcionario.getNome());
        tfSalario.setText(String.valueOf(funcionario.getSalario()));
        cbNivel.setSelectedItem(funcionario.getNivel());
        
        for (int i = 0; i < cbDepartamento.getItemCount(); i++) {
            Departamento dep = cbDepartamento.getItemAt(i);
            if (dep != null && dep.getId() == funcionario.getDepartamentoId()) {
                cbDepartamento.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void carregarTarefaNoFormulario(Tarefa tarefa) {
        tfTarefaId.setText(String.valueOf(tarefa.getId()));
        tfTarefaNome.setText(tarefa.getNome());
        taTarefaDescricao.setText(tarefa.getDescricao());
        cbTarefaStatus.setSelectedItem(tarefa.getStatus());
        
        for (int i = 0; i < cbTarefaFuncionario.getItemCount(); i++) {
            Funcionario func = cbTarefaFuncionario.getItemAt(i);
            if (func != null && func.getId() == tarefa.getFuncionarioId()) {
                cbTarefaFuncionario.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void carregarDepartamentoNoFormulario(Departamento departamento) {
        tfDepartamentoId.setText(String.valueOf(departamento.getId()));
        tfDepartamentoNome.setText(departamento.getNome());
    }
    
    private void salvarFuncionario() {
        try {
            if (validarCamposFuncionario()) {
                Funcionario funcionario = new Funcionario();
                if (!tfId.getText().isEmpty()) {
                    funcionario.setId(Integer.parseInt(tfId.getText()));
                }
                
                funcionario.setNome(tfNome.getText());
                funcionario.setSalario(Double.parseDouble(tfSalario.getText()));
                funcionario.setNivel((Nivel) cbNivel.getSelectedItem());
                
                Departamento depSelecionado = (Departamento) cbDepartamento.getSelectedItem();
                if (depSelecionado != null) {
                    funcionario.setDepartamentoId(depSelecionado.getId());
                }
                
                if (funcionario.getId() == 0) {
                    daoFuncionario.inserir(funcionario);
                } else {
                    daoFuncionario.alterar(funcionario);
                }
                
                atualizarListaFuncionarios();
                limparFuncionario();
                JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salário deve ser um valor numérico válido", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean validarCamposFuncionario() {
        if (tfNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do funcionário", "Erro", JOptionPane.WARNING_MESSAGE);
            tfNome.requestFocus();
            return false;
        }
        
        if (tfSalario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o salário", "Erro", JOptionPane.WARNING_MESSAGE);
            tfSalario.requestFocus();
            return false;
        }
        
        try {
            Double.parseDouble(tfSalario.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salário deve ser um valor numérico", "Erro", JOptionPane.WARNING_MESSAGE);
            tfSalario.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void excluirFuncionario() {
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este funcionário?", "Confirmação", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(tfId.getText());
                daoFuncionario.excluir(id);
                atualizarListaFuncionarios();
                limparFuncionario();
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void limparFuncionario() {
        tfId.setText("");
        tfNome.setText("");
        tfSalario.setText("");
        cbNivel.setSelectedIndex(0);
        cbDepartamento.setSelectedIndex(-1);
        tbFuncionarios.clearSelection();
        tfNome.requestFocus();
    }
    
    private void salvarTarefa() {
        try {
            if (validarCamposTarefa()) {
                Tarefa tarefa = new Tarefa();
                if (!tfTarefaId.getText().isEmpty()) {
                    tarefa.setId(Integer.parseInt(tfTarefaId.getText()));
                }
                
                tarefa.setNome(tfTarefaNome.getText());
                tarefa.setDescricao(taTarefaDescricao.getText());
                tarefa.setStatus((Status) cbTarefaStatus.getSelectedItem());
                tarefa.setFuncionarioId(((Funcionario) cbTarefaFuncionario.getSelectedItem()).getId());
                
                if (tarefa.getId() == 0) {
                    daoTarefa.inserir(tarefa);
                } else {
                    daoTarefa.alterar(tarefa);
                }
                
                atualizarListaTarefas();
                limparTarefa();
                JOptionPane.showMessageDialog(this, "Tarefa salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean validarCamposTarefa() {
        if (tfTarefaNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da tarefa", "Erro", JOptionPane.WARNING_MESSAGE);
            tfTarefaNome.requestFocus();
            return false;
        }
        
        if (cbTarefaFuncionario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário", "Erro", JOptionPane.WARNING_MESSAGE);
            cbTarefaFuncionario.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void excluirTarefa() {
        if (tfTarefaId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir esta tarefa?", "Confirmação", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(tfTarefaId.getText());
                daoTarefa.excluir(id);
                atualizarListaTarefas();
                limparTarefa();
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void limparTarefa() {
        tfTarefaId.setText("");
        tfTarefaNome.setText("");
        taTarefaDescricao.setText("");
        cbTarefaStatus.setSelectedIndex(0);
        cbTarefaFuncionario.setSelectedIndex(-1);
        tbTarefas.clearSelection();
        tfTarefaNome.requestFocus();
    }
    
    private void salvarDepartamento() {
        try {
            if (validarCamposDepartamento()) {
                Departamento departamento = new Departamento();
                if (!tfDepartamentoId.getText().isEmpty()) {
                    departamento.setId(Integer.parseInt(tfDepartamentoId.getText()));
                }
                
                departamento.setNome(tfDepartamentoNome.getText());
                
                if (departamento.getId() == 0) {
                    daoDepartamento.inserir(departamento);
                } else {
                    daoDepartamento.alterar(departamento);
                }
                
                atualizarListaDepartamentos();
                limparDepartamento();
                JOptionPane.showMessageDialog(this, "Departamento salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar departamento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean validarCamposDepartamento() {
        if (tfDepartamentoNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do departamento", "Erro", JOptionPane.WARNING_MESSAGE);
            tfDepartamentoNome.requestFocus();
            return false;
        }
        return true;
    }
    
    private void excluirDepartamento() {
        if (tfDepartamentoId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um departamento para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este departamento?", "Confirmação", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(tfDepartamentoId.getText());
                daoDepartamento.excluir(id);
                atualizarListaDepartamentos();
                limparDepartamento();
                JOptionPane.showMessageDialog(this, "Departamento excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir departamento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void limparDepartamento() {
        tfDepartamentoId.setText("");
        tfDepartamentoNome.setText("");
        tbDepartamentos.clearSelection();
        tfDepartamentoNome.requestFocus();
    }
    
    private void atualizarListaFuncionarios() throws SQLException {
        funcionarios = daoFuncionario.listar();
        modelFuncionario.setLista(funcionarios);
        atualizarCombosFuncionarios();
    }
    
    private void atualizarListaTarefas() throws SQLException {
        tarefas = daoTarefa.listar();
        modelTarefa.setLista(tarefas);
    }
    
    private void atualizarListaDepartamentos() throws SQLException {
        departamentos = daoDepartamento.listar();
        modelDepartamento.setLista(departamentos);
        atualizarComboDepartamentos();
    }
    
    private void atualizarCombosFuncionarios() throws SQLException {
        DefaultComboBoxModel<Funcionario> model = new DefaultComboBoxModel<>();
        for (Funcionario f : funcionarios) {
            model.addElement(f);
        }
        cbTarefaFuncionario.setModel(model);
    }
    
    private void atualizarComboDepartamentos() throws SQLException {
        DefaultComboBoxModel<Departamento> model = new DefaultComboBoxModel<>();
        model.addElement(null); // Permite selecionar nenhum departamento
        for (Departamento d : departamentos) {
            model.addElement(d);
        }
        cbDepartamento.setModel(model);
    }
}