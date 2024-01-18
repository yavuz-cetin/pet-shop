package veritabani;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdvancedSearch {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private int ids;
	private Connection conn;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable printAddsTable;
	private JLabel lblOwnerId;
	private JLabel lblAnimalId;
	private JLabel lblKind;
	private JLabel lblPrice;
	private JLabel lblAddDate;
	private JLabel lblAddress;
	private JLabel lblAge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection dummyConn = null;
				try {
					AdvancedSearch window = new AdvancedSearch(1001, dummyConn);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdvancedSearch() throws SQLException {
		initialize();
	}

	public AdvancedSearch(int id, Connection parent_conn) throws SQLException {
		ids = id;
		conn = parent_conn;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Advanced Search");
		frame.setBounds(100, 100, 1050, 550);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 40, 80, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 100, 80, 20);
		frame.getContentPane().add(textField_1);

		JLabel lblNewLabel = new JLabel("1. Animal Type");
		lblNewLabel.setBounds(10, 20, 100, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("1. Animal Min Age");
		lblNewLabel_1.setBounds(10, 80, 110, 15);
		frame.getContentPane().add(lblNewLabel_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(10, 170, 80, 20);
		frame.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(11, 235, 80, 20);
		frame.getContentPane().add(textField_3);

		JLabel lblAnimalType = new JLabel("2. Animal Type");
		lblAnimalType.setBounds(10, 150, 100, 15);
		frame.getContentPane().add(lblAnimalType);

		JLabel lblNewLabel_1_1 = new JLabel("2. Animal Min Age");
		lblNewLabel_1_1.setBounds(10, 210, 110, 15);
		frame.getContentPane().add(lblNewLabel_1_1);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp1 = textField.getText();
				String temp2 = textField_2.getText();
				String temp3 = textField_1.getText();
				String temp4 = textField_4.getText();
				String temp5 = textField_3.getText();
				String temp6 = textField_5.getText();
				if(!temp1.equals("") && !temp2.equals("") && !temp3.equals("") && !temp4.equals("") && !temp5.equals("") && !temp6.equals("")) {
					try {
			            DefaultTableModel model = new DefaultTableModel();
			            DatabaseMetaData metaData = conn.getMetaData();
			            ResultSet resultSet = metaData.getColumns(null, null, "ads", null);

			            while (resultSet.next()) {
			                String columnName = resultSet.getString("COLUMN_NAME");
			                model.addColumn(columnName);
			            }
			            model.addColumn("Age");

			            String query = "SELECT ad_no,owner_id,animal_id,d.kind,d.price,address,ad_date,a.age "
			            		+ "FROM ads d,animals a WHERE d.kind = ? AND d.owner_id != ? "
			            		+ "AND a.age >= ? AND a.age <= ?  AND a.id = d.animal_id UNION ";
			            String query_2 = "SELECT ad_no,owner_id,animal_id,c.kind,c.price,address,ad_date,b.age "
			            		+ "FROM ads c,animals b WHERE c.kind = ? AND c.owner_id != ? "
			            		+ "AND b.age >= ? AND b.age <= ? AND b.id = c.animal_id  ORDER BY ad_no";
			            String finalQuery = query + query_2;
			            PreparedStatement p = conn.prepareStatement(finalQuery);
			            p.setString(1, temp1);
			            p.setInt(2,ids);
			            p.setInt(3,Integer.parseInt(temp3) ); 
			            p.setInt(4, Integer.parseInt(temp4) );
			            
			            p.setString(5, temp2);
			            p.setInt(6,ids);
			            p.setInt(7, Integer.parseInt(temp5) );
			            p.setInt(8, Integer.parseInt(temp6) );
			            
			            try {
			            	ResultSet result = p.executeQuery();
			                while (result.next()) {
			                     Object[] row = new Object[model.getColumnCount()];
			                     for (int i = 0; i < model.getColumnCount(); i++) {
			                         row[i] = result.getObject(i + 1);
			                     }
			                     model.addRow(row);
			                }
			                printAddsTable.setModel(model);
			            }
			            catch (SQLException ex) {
			            	ex.printStackTrace();
			            }
			            
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			        }
				}
				else {
					JOptionPane.showMessageDialog(null, "Input(s) can not be empty. Please try again.");
				}
				
			}
		});
		btnNewButton.setBounds(50, 275, 100, 35);
		frame.getContentPane().add(btnNewButton);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(125, 100, 80, 20);
		frame.getContentPane().add(textField_4);

		JLabel lblNewLabel_1_2 = new JLabel("1. Animal Max Age");
		lblNewLabel_1_2.setBounds(125, 80, 110, 15);
		frame.getContentPane().add(lblNewLabel_1_2);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(125, 235, 80, 20);
		frame.getContentPane().add(textField_5);

		JLabel lblNewLabel_1_3 = new JLabel("2. Animal Max Age");
		lblNewLabel_1_3.setBounds(125, 210, 110, 15);
		frame.getContentPane().add(lblNewLabel_1_3);

		printAddsTable = new JTable();
		printAddsTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printAddsTable.setBounds(250, 80, 760, 400);
		frame.getContentPane().add(printAddsTable);

		lblNewLabel = new JLabel("Ad No");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(250, 50, 95, 25);
		frame.getContentPane().add(lblNewLabel);

		lblOwnerId = new JLabel("Owner ID");
		lblOwnerId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOwnerId.setBounds(345, 50, 95, 25);
		frame.getContentPane().add(lblOwnerId);

		lblAnimalId = new JLabel("Animal ID");
		lblAnimalId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAnimalId.setBounds(440, 50, 95, 25);
		frame.getContentPane().add(lblAnimalId);

		lblKind = new JLabel("Kind");
		lblKind.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKind.setBounds(535, 50, 95, 25);
		frame.getContentPane().add(lblKind);

		lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(630, 50, 95, 25);
		frame.getContentPane().add(lblPrice);

		lblAddDate = new JLabel("Ad Date");
		lblAddDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddDate.setBounds(820, 50, 95, 25);
		frame.getContentPane().add(lblAddDate);

		lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddress.setBounds(725, 50, 95, 25);
		frame.getContentPane().add(lblAddress);

		lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAge.setBounds(915, 50, 95, 25);
		frame.getContentPane().add(lblAge);
	}

	public void showFrame() {
		frame.setVisible(true);
	}
}