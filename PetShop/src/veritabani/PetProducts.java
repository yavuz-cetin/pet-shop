package veritabani;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class PetProducts extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField productID;
	private JTextField productCount;
	private JLabel lblItemID;
	private JLabel lblProductCount;
	private JButton btnBuy;
	private JButton btnAllTransaction;
	private static Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int i = 0;
				Connection dummyConn = null;
				try {
					PetProducts frame = new PetProducts(i, dummyConn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PetProducts(int userID, Connection parentConn) throws SQLException {
		conn = parentConn;
		setTitle("PetProducts");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		table.setBounds(200, 25, 450, 400);
		contentPane.add(table);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(175, 25, 450, 400);
		contentPane.add(scrollPane);

		productID = new JTextField();
		productID.setBounds(25, 80, 125, 25);
		contentPane.add(productID);
		productID.setColumns(10);

		lblItemID = new JLabel("Product ID:");
		lblItemID.setBounds(25, 50, 125, 20);
		contentPane.add(lblItemID);

		productCount = new JTextField();
		productCount.setColumns(10);
		productCount.setBounds(25, 160, 125, 25);
		contentPane.add(productCount);

		lblProductCount = new JLabel("Product Count:");
		lblProductCount.setBounds(25, 130, 125, 20);
		contentPane.add(lblProductCount);

		btnBuy = new JButton("Buy");
		btnBuy.setBounds(25, 200, 125, 25);
		contentPane.add(btnBuy);

		btnAllTransaction = new JButton("All Transaction");
		btnAllTransaction.setBounds(25, 300, 125, 25);
		contentPane.add(btnAllTransaction);

		displayItemsTable();

		// Buy butonuna eylem ekle
		btnBuy.setFocusable(false);
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp1 = productID.getText();
				String temp2 = productCount.getText();
				int check = -1;
				if(!temp1.equals("") && !temp2.equals("")) {
					try {
						int itemID = Integer.parseInt(temp1);
						int itemCount = Integer.parseInt(temp2);
						String query = "SELECT id FROM items WHERE id = ?;";
						PreparedStatement statement = conn.prepareStatement(query);
						statement.setInt(1, itemID);
						ResultSet result = statement.executeQuery();
						while (result.next()) {
							check = result.getInt(1);
						}
						if (check != -1) {
							performPurchase(userID, itemID, itemCount);
						} else {
							JOptionPane.showMessageDialog(null, "Invalid item id. Please enter valid numbers.");
							productID.setText("");
							productCount.setText("");
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
					} catch (SQLException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error performing purchase.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Input(s) can not be empty. Please enter valid numbers.");
				}
			}
		});
		btnAllTransaction.setFocusable(false);
		btnAllTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AllTransaction at = new AllTransaction(userID, conn);
					at.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}

	private void displayItemsTable() {
		try {
			// Tablo adını Items olarak alıp modeli güncelle
			DefaultTableModel model = new DefaultTableModel();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, "items", null);

			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				model.addColumn(columnName);
			}

			// Verileri al ve tabloya ekle
			String query = "SELECT * FROM items";
			try (PreparedStatement statement = conn.prepareStatement(query);
					ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					Object[] row = new Object[model.getColumnCount()];
					for (int i = 0; i < model.getColumnCount(); i++) {
						row[i] = result.getObject(i + 1); // Sütunlar 1'den başlar
					}
					model.addRow(row);
				}
			}

			table.setModel(model); // Tabloyu güncelle
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void performPurchase(int userID, int itemID, int itemCount) throws SQLException {
		// Veritabanında ürünün fiyatını al
		int itemPrice = getItemPrice(itemID);

		// Alışverişin toplam fiyatını hesapla
		int totalPrice = itemPrice * itemCount;

		// Alışveriş tarihini al
		java.sql.Date transactionDate = new java.sql.Date(System.currentTimeMillis());

		// transactions tablosuna yeni değeri ekle
		String insertQuery = "INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
			// Kullanıcı ID'sini ve alışveriş bilgilerini set et
			insertStatement.setInt(1, userID); // Bu metodunuzu eklemeniz gerekecek, Kullanıcının ID'sini döndürmelidir.
			insertStatement.setInt(2, itemID);
			insertStatement.setInt(3, itemCount);
			insertStatement.setInt(4, totalPrice);
			insertStatement.setDate(5, transactionDate);

			// Yeni alışveriş işlemini ekle
			insertStatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "Purchase completed successfully!");
		}
	}

	private int getItemPrice(int itemID) throws SQLException {
		String query = "SELECT price FROM items WHERE id = ?";
		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, itemID);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("price");
				} else {
					throw new SQLException("Item not found.");
				}
			}
		}
	}
}