package Interface;
/**
 * Author Emerson Rodrigues
 * Programa para importação e cálculo de preço de custo final dos produtos
 * O Custo Final é baseado no valor unitario do produto mais impostos e frete*/

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model_CalculoPrecoCusto.DadosXML;

public class TelaInicialInterface  extends JFrame {

	private static final long serialVersionUID = 1L;


	private JLabel lblNomeEmpresa;
	public DefaultTableModel tableModel;
	public JTable tableNota = new JTable();
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicialInterface  frame = new TelaInicialInterface ();
					frame.setExtendedState(MAXIMIZED_BOTH);
					frame.setVisible(true);

				} catch (Exception e) {
					JOptionPane.showInputDialog(null, "Erro ao Iniciar Aplicação");
				}
			}
		});
	}

	public void organizarTableNota() {
		
		//Aqui verifico se a jTable tem alguma linha se sim deleta
		while (tableModel.getRowCount() > 0) {
			int x = tableModel.getRowCount(); 
			tableModel.removeRow(x-1);
			x--;
		}
		//Aqui eu adiciono cada linha da lista na jTable
				for (int i = 0; i < DadosXML.Item.size(); i++) {
					tableModel.addRow(new Object[] {
							DadosXML.Item.get(i),
							DadosXML.CodProd.get(i),
							DadosXML.Descricao.get(i),
							"R$ " + DadosXML.vlrProd.get(i),
							"R$ " + DadosXML.vlrICMSST.get(i).toString(),
							"R$ " + DadosXML.vlrIPI.get(i).toString(),
							"R$ " + DadosXML.custoFinal.get(i).toString()
					});
				}
				
		//Limpar os dados da nota para poder importar a próxima	
		DadosXML.Item.clear();
		DadosXML.CodProd.clear();
		DadosXML.Descricao.clear();
		DadosXML.vlrProd.clear();
		DadosXML.vlrICMSST.clear();
		DadosXML.vlrIPI.clear();
		DadosXML.custoFinal.clear();

		//Redimensonamento das colunas
		tableNota.setModel(tableModel);
		tableNota.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableNota.getColumnModel().getColumn(0).setPreferredWidth(35);//Item
		tableNota.getColumnModel().getColumn(1).setPreferredWidth(100);//Codigo Produto
		tableNota.getColumnModel().getColumn(2).setPreferredWidth(530);//Descrição
		tableNota.getColumnModel().getColumn(3).setPreferredWidth(100);//Valor Produto
		tableNota.getColumnModel().getColumn(4).setPreferredWidth(70);//Valor ICMSST
		tableNota.getColumnModel().getColumn(5).setPreferredWidth(65);//Valor IPI
		tableNota.getColumnModel().getColumn(6).setPreferredWidth(95);//Custo Final

		//Alinhamento do conteúdo das células da Tabela
		DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		tableNota.getColumnModel().getColumn(0).setCellRenderer(centro);
		tableNota.getColumnModel().getColumn(1).setCellRenderer(centro);

		//Formatação do Cabeçalho		
		tableNota.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));	

		}

	public TelaInicialInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JPanel panelButton = new JPanel();
		JButton btnNewButton = new JButton("Importar Nota");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					//Importar a Nota Fiscal
					DadosXML.importarNfe();
					
					//Informar qual Empresa se refere a nota e organizar as informações da nota
					lblNomeEmpresa.setText(DadosXML.nota.getNomeEmpresa());
					organizarTableNota();

				} catch (Exception e1) {
					
					JOptionPane.showInternalMessageDialog(null, "Erro ao importar Nota Fiscal");
				}
			}	
		});

		lblNomeEmpresa = new JLabel("");
		lblNomeEmpresa.setVerticalAlignment(SwingConstants.TOP);

		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setHorizontalAlignment(SwingConstants.LEFT);

		tableModel = new DefaultTableModel(
				new Object[][] {

				}, new String[] {
						"ITEM", 
						"COD_PRODUTO", 
						"DESCRIÇÃO", 
						"VLR_PRODUTO", 
						"VLR_ICMS", 
						"VLR_IPI", 
						"CUSTO FINAL"});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(33)
							.addComponent(lblEmpresa)
							.addGap(12)
							.addComponent(lblNomeEmpresa, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
							.addGap(126))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(0)
							.addComponent(panelButton, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEmpresa)
						.addComponent(lblNomeEmpresa, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addComponent(panelButton, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addContainerGap())
		);
		tableNota = new JTable();
		scrollPane.setViewportView(tableNota);

		GroupLayout gl_panelButton = new GroupLayout(panelButton);
		gl_panelButton.setHorizontalGroup(
			gl_panelButton.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelButton.createSequentialGroup()
					.addGap(106)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
					.addGap(104))
		);
		gl_panelButton.setVerticalGroup(
			gl_panelButton.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelButton.createSequentialGroup()
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(1))
		);
		panelButton.setLayout(gl_panelButton);
		getContentPane().setLayout(groupLayout);
		
	}
}

