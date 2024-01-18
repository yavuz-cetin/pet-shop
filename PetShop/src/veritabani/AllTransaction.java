package veritabani;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AllTransaction extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTable table;
	private Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int i = 0;
					Connection dummyConn = null;
					AllTransaction frame = new AllTransaction(i, dummyConn);
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
	public AllTransaction(int userID, Connection parentConn) {
		conn = parentConn;
		setTitle("All Transaction");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

		// Veritaban�ndan verileri çek ve tabloya ekle
		displayTransactions(userID);
	}

	private void displayTransactions(int userID) {
		try {
			String query = "SELECT * FROM transactions WHERE user_id = ?";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, userID);

				try (ResultSet resultSet = statement.executeQuery()) {
					// ResultSet'ten JTable için model oluştur
					DefaultTableModel tableModel = new DefaultTableModel();
					tableModel.addColumn("User ID");
					tableModel.addColumn("Item ID");
					tableModel.addColumn("Count");
					tableModel.addColumn("Total Price");
					tableModel.addColumn("Transaction Date");

					while (resultSet.next()) {
						Object[] row = { resultSet.getInt("user_id"), resultSet.getInt("item_id"),
								resultSet.getInt("count"), resultSet.getInt("total_price"),
								resultSet.getDate("ts_date") };
						tableModel.addRow(row);
					}

					// JTable'a modeli ata
					table.setModel(tableModel);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}