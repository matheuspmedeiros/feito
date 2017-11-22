package interf;
	import java.awt.*;
	import java.awt.event.*;
	import java.util.*;
	import javax.swing.event.*;
	import javax.swing.table.DefaultTableModel;
	import javax.swing.*;

public class InterfaceAgenda extends JInternalFrame{
	private JTable tabela;
	private JButton buttonNovo = new JButton("Novo");
	private JButton buttonEditar = new JButton("Editar");
	private JButton buttonRemover = new JButton("Remover");
	private JButton buttonFechar = new JButton("Fechar");
	
	private FormCadAgenda formCadAgenda;
	private static InterfaceAgenda INSTANCE = null;
	
	private DefaultTableModel modelo = new DefaultTableModel();
	
	public InterfaceAgenda(){
		super("Agenda", true, true , false, true);
		Container pane = this.getContentPane();
		pane.setLayout(null);
		
		buttonNovo.setBounds(5, 10, 90, 70);
		buttonNovo.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonNovo.setVerticalAlignment(SwingConstants.TOP);
		buttonNovo.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonNovo.setIcon(new ImageIcon(getClass().getResource("imagens/agenda.png")));
		
		buttonNovo.addActionListener(new ActionListener(){ 
		public void actionPerformed(ActionEvent e){
		buttonNovoActionPerformed(e); 
			}
		});
		
		buttonEditar.setBounds(95, 10, 90, 70);
		buttonEditar.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonEditar.setVerticalAlignment(SwingConstants.TOP);
		buttonEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonEditar.setIcon(new ImageIcon(getClass().getResource("imagens/editar.png")));
		buttonEditar.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
		buttonEditarActionPerformed(e); 
			}
		});
		buttonRemover.setBounds(185, 10, 90, 70);
		buttonRemover.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonRemover.setVerticalAlignment(SwingConstants.TOP);
		buttonRemover.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonRemover.setIcon(new ImageIcon(getClass().getResource("imagens/remov.png")));
		buttonRemover.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
		buttonRemoverActionPerformed(e);
			}
		});
		buttonFechar.setBounds(280, 10, 90, 70);
		buttonFechar.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonFechar.setVerticalAlignment(SwingConstants.TOP);
		buttonFechar.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonFechar.setIcon(new ImageIcon(getClass().getResource("imagens/fecharr.png")));
		buttonFechar.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
		buttonFecharActionPerformed(e); //chama esse método
			}
		});
		
		this.addInternalFrameListener(new InternalFrameAdapter(){
		public void internalFrameClosing(InternalFrameEvent e){
			interfaceAgendaInternalFrameClosing(e);
			}
		});
		tabela = new JTable(modelo);
		
		modelo.addColumn("Codigo");
		modelo.addColumn("Nome");
		modelo.addColumn("Telefone");
		modelo.addColumn("E-mail");
		modelo.addColumn("Endereço");
		
		JScrollPane scrollpane = new JScrollPane(tabela);
		scrollpane.setBounds(5, 95, 680, 390);
		
		pane.add(buttonNovo);
		pane.add(buttonEditar);
		pane.add(buttonRemover);
		pane.add(buttonFechar);
		pane.add(scrollpane);

		preencheTabela();

		this.setResizable(false);
		this.setSize(700, 550);
		}
		
		private void buttonNovoActionPerformed(ActionEvent e){
			formCadAgenda = new FormCadAgenda(this);
			formCadAgenda.setModal(true);
			formCadAgenda.inicia();
		}
		private void buttonEditarActionPerformed(ActionEvent e){
			int linha = tabela.getSelectedRow();

			if (linha < 0) 
			return;
			else{
			
			String codigo = tabela.getValueAt(linha, 0).toString();
			String nome = tabela.getValueAt(linha,1).toString();
			String telefone = tabela.getValueAt(linha, 2).toString();
			String email = tabela.getValueAt(linha, 3).toString();
			String endereco = tabela.getValueAt(linha, 4).toString();

			formCadAgenda= new FormCadAgenda(this, codigo, nome, telefone, email, endereco);
			formCadAgenda.setModal(true);
			formCadAgenda.inicia();
			}
		}
		private void buttonRemoverActionPerformed(ActionEvent e){
			int resposta = JOptionPane.showConfirmDialog(this,
					"Deseja remover registro?",
					"Remoção",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
					if (resposta == JOptionPane.OK_OPTION){
					
					int linha = tabela.getSelectedRow();
					int coluna = 0;

					if (linha < 0)
					return;
					else{
					String codigo = tabela.getValueAt(linha, coluna).toString();
					Main.minhaAgenda.remove(codigo); //remove o contato
					modelo.removeRow(linha); //remove a linha

					JOptionPane.showMessageDialog(this,
					"Registro excluído com sucesso!!!",
					"Remover", JOptionPane.INFORMATION_MESSAGE);

			}
		}			
	}
		private void buttonFecharActionPerformed(ActionEvent e){
			int ret = JOptionPane.showConfirmDialog(this,
					"Deseja Fechar?",
					"Fechar",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
					//fecha a janela da agenda caso clique em OK
					if (ret == JOptionPane.OK_OPTION){
					this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					this.destroyInstance();
			}else
				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
		private void interfaceAgendaInternalFrameClosing(InternalFrameEvent e){
			
			int ret = JOptionPane.showConfirmDialog(this,
			"Deseja Fechar?",
			"Fechar",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE);
			
			if (ret == JOptionPane.OK_OPTION){
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.destroyInstance();
		}else
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

		public static InterfaceAgenda getInstance(){
			if (InterfaceAgenda.INSTANCE == null){
			return INSTANCE = new InterfaceAgenda();
			}
			return INSTANCE;
		}
		public void preencheTabela(){

			ArrayList lista = Main.minhaAgenda.getList();
			
			Collections.sort(lista, Pessoa.ORDEM_CODIGO);
			Iterator it = lista.iterator();

			while (it.hasNext()){
			Pessoa p = (Pessoa)it.next();

			modelo.addRow(new String[]{
			p.getCodigo(),
			p.getNome(),
			p.getTelefone(),
			p.getEmail(),
			p.getEndereco()
			});
		}
	}
		public void atualizaTabela(String[] dados){

			
			int linha = tabela.getSelectedRow();

			tabela.setValueAt(dados[0], linha, 0);
			tabela.setValueAt(dados[1], linha, 1);
			tabela.setValueAt(dados[2], linha, 2);
			tabela.setValueAt(dados[3], linha, 3);
			tabela.setValueAt(dados[4], linha, 4);
		}
		
		public InterfaceAgenda destroyInstance(){
			this.dispose();
				return INSTANCE = null;
		}

		public static boolean isInstance(){
			if (INSTANCE == null) return false;

				return true;
		}
		public void addRow(Object[] o){
			modelo.addRow(o);
		}

}



