package interf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FormCadAgenda extends JDialog {

	private JLabel labelNome = new JLabel("Nome:");;
	private JLabel labelTelefone = new JLabel("Telefone:");;
	private JLabel labelEmail = new JLabel("Email:");
	private JLabel labelEndereco = new JLabel("Endereco:");

	private JTextField textNome = new JTextField();
	private JTextField textTelefone = new JTextField();
	private JTextField textEmail = new JTextField();
	private JTextField textEndereco = new JTextField();

	private JButton buttonGravar;
	private JButton buttonFechar;

	private InterfaceAgenda pai;

	private String codigo = "";

	private boolean edicao = false;

	public FormCadAgenda(InterfaceAgenda newPai) {
		this.pai = newPai;
	}

	public FormCadAgenda(InterfaceAgenda newPai, String codigo, String nome, String telefone, String email,
			String endereco) {
		this(newPai);
		this.edicao = true;
		this.codigo = codigo;
		this.textNome.setText(nome);
		this.textTelefone.setText(telefone);
		this.textEmail.setText(email);
		this.textEndereco.setText(endereco);
	}

	void inicia(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		buttonGravar = new JButton("Gravar");
		buttonFechar = new JButton("Fechar");

		Container pane = this.getContentPane(); //cria Pane (container);

		pane.setLayout(null);	
		labelNome.setBounds(10, 10, 50, 20);
		labelTelefone.setBounds(10, 41, 80, 20);
		labelEmail.setBounds(10, 72, 50, 20);
		labelEndereco.setBounds(10, 102, 80, 20);
		
		textNome.setBounds(70, 10, 150, 20);
		textTelefone.setBounds(70, 41, 70, 20);
		textEmail.setBounds(70, 72, 150, 20);
		textEndereco.setBounds(70, 102, 150, 20);
		
		buttonGravar.setBounds(68, 140, 80, 22);
		buttonFechar.setBounds(150, 140, 80, 22);
		
		buttonGravar.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			buttonGravarActionPerformed(e);
			}
		});
		
		buttonFechar.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			buttonFecharActionPerformed(e);
				}
			});
		
		pane.add(labelNome);
		pane.add(labelTelefone);
		pane.add(labelEmail);
		pane.add(labelEndereco);

		pane.add(textNome);
		pane.add(textTelefone);
		pane.add(textEmail);
		pane.add(textEndereco);

		pane.add(buttonGravar);
		pane.add(buttonFechar);
		
		this.setBounds(40, 60, 250, 210); 
		this.setContentPane(pane);
		this.setVisible(true);
	}

	private void buttonGravarActionPerformed(ActionEvent e){
		int resposta;
		
		if (edicao == false){
		resposta = JOptionPane.showConfirmDialog(this,JOptionPane.OK_CANCEL_OPTION,"Incluir Registro?",
		JOptionPane.QUESTION_MESSAGE);
		if (resposta == JOptionPane.OK_OPTION){
		
		Pessoa p = new Pessoa(textNome.getText(),
		textTelefone.getText(),
		textEmail.getText(),
		textEndereco.getText());
		Main.minhaAgenda.add(p);
		
		JOptionPane.showMessageDialog(this,
		"Inclusão efetuada!!!",
		"Inclusão",
		JOptionPane.INFORMATION_MESSAGE);
		pai.addRow(new String[]{p.getCodigo(),
				p.getNome(),
				p.getTelefone(),
				p.getEmail(),
				p.getEndereco()});

				this.dispose();
			}
		
		}
		else{ //se edicao = true indica que é uma alteração.
			resposta = JOptionPane.showConfirmDialog(this,
			"Salvar Alterações",
			"Edição",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE);
			
			if (resposta == JOptionPane.OK_OPTION){
				Pessoa p = Main.minhaAgenda.getPessoa(codigo);

				p.setValores(new String[]{textNome.getText(),
				textTelefone.getText(),
				textEmail.getText(),
				textEndereco.getText()});

				JOptionPane.showMessageDialog(this,
				"Edição efetuada!!!",
				"Edição",
				JOptionPane.INFORMATION_MESSAGE);

				pai.atualizaTabela(new String[]{p.getCodigo(),
				p.getNome(),
				p.getTelefone(),
				p.getEmail(),
				p.getEndereco()});

				this.dispose();
		}
			
		}
	}
	private void buttonFecharActionPerformed(ActionEvent e){
		this.dispose();
		}


		}