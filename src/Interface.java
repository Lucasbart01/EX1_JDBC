import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Interface extends JFrame {
	private JLabel lbfiltro,lbtabela, lbcor, lbtamanho, lbtipo, lbpreco, label1, label2, label3, label4;
	private JButton btBuscar, btProx, btAnt;
	private JScrollPane scrollTable;
	private JComboBox cbCor, cbTamanho, cbTipo, cbPreco;
	private JTextField tfCamisa, tfCor, tfTamanho, tfPreco;
	private JTable table;
	private BD bd;
	private PreparedStatement st;
	private ResultSet rs;

	String sql = "select camisa, cor, tamanho, preco from itens where tamanho=? or cor=? or camisa=? or preco=?" + "order by preco";//variável sql serve para armazenar o código de sql para buscar no banco de dados;
	public Interface() {
		inicializarComponentes();
		definirEventos();
	}

	public void inicializarComponentes() {
		String [] cbt = {"" ,"PP","P", "M", "G", "GG"};
		String [] cbc = {"", "branca", "preta", "azul", "rosa", "branco"};
		String [] cbtipo = {"", "meia manga", "manga longa", "regata", "moletom"};
		String [] cbp = {" ", "40", "50", "55", "60", "80", "100", "115"};
		
		setLayout(null);
	
		setTitle("Loja de camisas");
		setBounds(200, 20, 700, 400);
		setResizable(true);
		getContentPane().setBackground(new Color(176,196,222));
		
		//Definindo labels
		label2 = new JLabel("Tipo");
		label2.setBounds(80, 250, 70, 20);
		label3 = new JLabel("Cor");
		label3.setBounds(200, 250, 70, 20);
		label4 = new JLabel("Tamanho");
		label4.setBounds(290, 250, 70, 20);
		label1 = new JLabel("Preço");
		label1.setBounds(396, 250, 70, 20);
		lbfiltro = new JLabel("Filtrar:");
		lbfiltro.setBounds(580, 10, 70, 20);
		lbtabela = new JLabel("Tabela: ");
		lbtabela.setBounds(200, 10, 70, 20);
		lbtamanho = new JLabel("Tamanhos:");
		lbtamanho.setBounds(550, 60, 70, 20);
		lbcor = new JLabel("Cores:");
		lbcor.setBounds(550, 100, 70, 20);
		lbtipo = new JLabel("Tipos:");
		lbtipo.setBounds(550, 140, 70, 20);
		lbpreco = new JLabel("Preços: ");
		lbpreco.setBounds(550, 180, 70, 20);
		
		//Definindo botoes
		btBuscar = new JButton("Buscar");
		btBuscar.setBounds(580, 220, 80, 20);
		btProx = new JButton("Próximo");
		btProx.setBounds(250, 310, 120, 20);
		btAnt = new JButton("Anterior");
		btAnt.setBounds(100, 310, 120, 20);
		
		scrollTable = new JScrollPane();
		scrollTable.setBounds(10, 40, 500, 200);
		
		//Definindo ComboBox
		cbTipo = new JComboBox(cbtipo);
		cbTipo.setBounds(590, 142, 80, 20);
		cbTamanho = new JComboBox(cbt);
        cbTamanho.setBounds(620, 61, 50, 20);
		cbCor = new JComboBox(cbc);
		cbCor.setBounds(595, 102, 70, 20);
		cbPreco = new JComboBox(cbp);
		cbPreco.setBounds(599, 182, 70, 20);
		
		//Definindo TF
		tfCamisa = new JTextField();
		tfCamisa.setBounds(40, 270, 100, 20);
		tfCor = new JTextField();
		tfCor.setBounds(170, 270, 80, 20);
		tfTamanho = new JTextField();
		tfTamanho.setBounds(288, 270, 60, 20);
		tfPreco = new JTextField();
		tfPreco.setBounds(380, 270, 70, 20);
	
		//add itens
		add(scrollTable);
		add(label1);
		add(label2);
		add(label4);
		add(label3);
		add(tfCamisa);
		add(tfCor);
		add(tfTamanho);
		add(tfPreco);
		
		add(lbfiltro);
		add(lbtabela);
		add(lbcor);
		add(lbtamanho);
		add(lbtipo);
		add(lbpreco);
		add(btBuscar);
		add(btProx);
		add(btAnt);
		add(cbTipo);
		add(cbTamanho);
		add(cbCor);
		add(cbPreco);
		
		
		//Cores e estilos
		btBuscar.setBackground(Color.white);
		btProx.setBackground(Color.white);
		btAnt.setBackground(Color.white);
		
		lbfiltro.setFont(new Font("font", Font.BOLD, 16));
		lbtabela.setFont(new Font("font", Font.BOLD, 16));
		lbtipo.setFont(new Font("font", Font.BOLD, 13));
		lbpreco.setFont(new Font("font", Font.BOLD, 13));
		lbcor.setFont(new Font("font", Font.BOLD, 13));
		lbtamanho.setFont(new Font("font", Font.BOLD, 13));
		
		
		label1.setFont(new Font("font", Font.BOLD, 12));
		label2.setFont(new Font("font", Font.BOLD, 12));
		label3.setFont(new Font("font", Font.BOLD, 12));
		label4.setFont(new Font("font", Font.BOLD, 12));
		
		
		label1.setForeground(new Color 	(105,105,105));
		label2.setForeground(new Color 	(105,105,105));
		label3.setForeground(new Color 	(105,105,105));
		label4.setForeground(new Color 	(105,105,105));
		
		bd = new BD();
		
		if(!bd.getConnection()){
			JOptionPane.showMessageDialog(null,"Falha na conexão!");
			System.exit(0);
		}
	}
	
	public void definirEventos() {
		
		btBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				String t = cbTamanho.getSelectedItem().toString();
				String c = cbCor.getSelectedItem().toString();
				String tipo = cbTipo.getSelectedItem().toString();
				String p = cbPreco.getSelectedItem().toString();
				try{
					if(!bd.getConnection()){
						JOptionPane.showMessageDialog(null,"Falha na conexão!");
						System.exit(0);
					}
					st = bd.c.prepareStatement(sql);
					st.setString(1, t); //é o valor que irá substituir as interrogações do código sql;
					st.setString(2, c);
					st.setString(3, tipo);
					st.setString(4, p);
					rs = st.executeQuery();
					DefaultTableModel tableModel = new DefaultTableModel(
							new String[]{"Camisa" ,"Cor", "Tamanho", "Preço"},0){
					};
					int qtdeColunas = rs.getMetaData().getColumnCount();
					
					table = new JTable(tableModel);
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					
					while(rs.next()){
						try{
							String[] dados = new String[qtdeColunas];
							for(int i = 1; i<=qtdeColunas; i++){
								dados[i-1] = rs.getString(i);
								
							}
							dtm.addRow(dados);
							System.out.println();
						}catch (SQLException erro){
							
						}
						scrollTable.setViewportView(table);
					}
					//rs.close();
					//st.close();
					//bd.close();
				}catch (Exception erro){
					JOptionPane.showMessageDialog(null,"Comando Inválido"+erro.toString());
				}		
				
				try{
					rs.first();
					atualizarCampos();
				}catch(SQLException erro){
				}
				
			}
			});
		
		btProx.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					rs.next();
					atualizarCampos();
				}catch(SQLException erro){
			}
			}
		});
		
		btAnt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					rs.previous();
					atualizarCampos();
					
				}catch(SQLException erro){
			}
			}
		});
		
		cbTamanho.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
				if(cbTamanho.getSelectedIndex() > 0) {
					
					cbCor.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);	
					cbPreco.setSelectedIndex(0);
				}
			}
		});
		
		
		cbCor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
				if(cbCor.getSelectedIndex() > 0) {
					
					cbTamanho.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
					cbPreco.setSelectedIndex(0);
				}
			}
		});
			
	    cbTipo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
				if(cbTipo.getSelectedIndex() > 0) {
							
					cbCor.setSelectedIndex(0);
					cbTamanho.setSelectedIndex(0);
					cbPreco.setSelectedIndex(0);
				}
			}
		});
	    
	    cbPreco.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
				if(cbPreco.getSelectedIndex() > 0) {
							
					cbCor.setSelectedIndex(0);
					cbTamanho.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
				}
			}
		});
		
}
	
	public void atualizarCampos(){
		try{
			if(rs.isAfterLast()){
				rs.last();
			}
			if(rs.isBeforeFirst()){
				rs.first();
			}
			tfCamisa.setText(rs.getString("camisa"));
			tfCor.setText(rs.getString("cor"));
			tfTamanho.setText(rs.getString("tamanho"));
			tfPreco.setText(rs.getString("preco"));
		} catch(SQLException erro){
			
		}
		
	}
	
	public static void main(String args[]) {
		JFrame frame = new Interface();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}