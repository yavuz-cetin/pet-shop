package veritabani;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JLabel;

public class ChangeAnimalPrice {

	private JFrame frame;
	Connection conn;
	private JTable table;
	private static int id;
	private String query;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int i = 0;
				Connection dummyConn = null;
				try {
					ChangeAnimalPrice window = new ChangeAnimalPrice(i, dummyConn);
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
	public ChangeAnimalPrice() throws SQLException {
		initialize();
	}

	public ChangeAnimalPrice(int userId, Connection conn_parent) throws SQLException {
		conn = conn_parent;
		id = userId;
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Update Price");
		frame.setBounds(100, 100, 875, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		table = new JTable();
		table.setBounds(300, 40, 525, 400);
		frame.getContentPane().add(table);
		updateList();
		// Yeni butonun eylemi
		JButton btnUpdatePrice = new JButton("Update Price by Percentage");
		btnUpdatePrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdatePrice.setBounds(25, 200, 250, 35);
		frame.getContentPane().add(btnUpdatePrice);
		
		JLabel lblNewLabel = new JLabel("Ad No");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(300, 15, 75, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUserId.setBounds(375, 15, 75, 15);
		frame.getContentPane().add(lblUserId);
		
		JLabel lblNewLabel_1_1 = new JLabel("Animal ID");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(450, 15, 75, 15);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1.setBounds(600, 15, 75, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Address");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1.setBounds(675, 15, 75, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Date");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1_1.setBounds(750, 15, 75, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("Kind");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(525, 15, 75, 15);
		frame.getContentPane().add(lblNewLabel_1);
		btnUpdatePrice.setFocusable(false);
		btnUpdatePrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp1 = JOptionPane.showInputDialog("Enter ad_no:");
				String temp2 = JOptionPane.showInputDialog("Enter percentage:");
				if (temp1 != null && temp2 != null && !temp1.equals("") && !temp2.equals("")) {
					int adNo = Integer.parseInt(temp1);
					int check = -1;
					query = "SELECT ad_no FROM ads WHERE ad_no = ? AND owner_id = ? ";
					try {
						PreparedStatement statement = conn.prepareStatement(query);
						statement.setInt(1, adNo);
						statement.setInt(2, id);
						ResultSet result = statement.executeQuery();
						while (result.next()) {
							check = result.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (check != -1) {
						try {
							// Kullanıcıdan ad_no ve yüzdelik değerleri al
							int percentage = Integer.parseInt(temp2);

							query = "SELECT update_price_by_percentage(?, ?) as new_price";
							try (PreparedStatement statement = conn.prepareStatement(query)) {
								statement.setInt(1, adNo);
								statement.setInt(2, percentage);

								try (ResultSet resultSet = statement.executeQuery()) {
									if (resultSet.next()) {
										int newPrice = resultSet.getInt("new_price");
										JOptionPane.showMessageDialog(null,
												"Price updated successfully! New price: " + newPrice);
										updateList();
									}
								}
							}
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							JOptionPane.showMessageDialog(null, " 50<= price <= 10000");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Wrong ad no. Please enter correct ad no.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Input(s) cannot be empty. Please enter valid numbers.");
				}

			}
		});

	}

	public void updateList() {
		try {
			// Tablo adını ads olarak alıp modeli güncelle
			DefaultTableModel model = new DefaultTableModel();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, "ads", null);

			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				model.addColumn(columnName);
			}

			// Verileri al ve tabloya ekle
			String query = "SELECT * FROM ads WHERE owner_id = ? ORDER BY ad_no";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1); // Sütunlar 1'den başlar
				}
				model.addRow(row);
			}

			table.setModel(model); // Tabloyu güncelle
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void showFrame() {
		frame.setVisible(true);
	}
}