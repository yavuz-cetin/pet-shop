package veritabani;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShopModule extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable printKindsTable;
	private JTextField inputKind;
	private String query;
	private Connection conn;
	private int userId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			int id = 1;
			Connection dummyConn = null;

			public void run() {
				try {
					ShopModule frame = new ShopModule(id, dummyConn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public ShopModule(int id, Connection conn_parent) throws SQLException {
		userId = id;
		conn = conn_parent;
		setTitle("Shop Module");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblKind = new JLabel("Kind:");
		lblKind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblKind.setBounds(30, 30, 100, 15);
		contentPane.add(lblKind);

		inputKind = new JTextField();
		inputKind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inputKind.setBounds(30, 50, 100, 25);
		contentPane.add(inputKind);
		inputKind.setColumns(10);

		JButton btnShow = new JButton("Show");
		btnShow.setFocusable(false);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kind;
				int check = -1;
				kind = inputKind.getText();
				if (!kind.equals("")) {
					query = "SELECT animal_id FROM ads"
							+ " WHERE kind = ? AND owner_id != ? AND ad_no NOT IN (SELECT ad_no FROM applications WHERE applicent_id = ?) LIMIT 1;";
					try {
						PreparedStatement statement = conn.prepareStatement(query);
						statement.setString(1, kind);
						statement.setInt(2, userId);
						statement.setInt(3, userId);
						ResultSet result = statement.executeQuery();
						while (result.next()) {
							check = result.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (check != -1) {
						AdsPage frame = null;
						try {
							frame = new AdsPage(kind, id, conn);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						frame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid kind, please try again.");
						inputKind.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Kind can not be empty, please try again.");
				}
			}
		});
		btnShow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShow.setBounds(30, 150, 100, 35);
		contentPane.add(btnShow);

		printKindsTable = new JTable();
		printKindsTable.setBounds(250, 35, 250, 375);
		contentPane.add(printKindsTable);
		try {
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Kind");
			model.addColumn("Ad Count");
			query = "SELECT kind,count(kind) FROM ads"
					+ " WHERE owner_id != ? AND ad_no NOT IN (SELECT ad_no FROM applications WHERE applicent_id = ?)"
					+ " GROUP BY kind HAVING count(kind) >= 1;";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, userId);
			statement.setInt(2, userId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1);
				}
				model.addRow(row);
			}

			printKindsTable.setModel(model);

			JLabel lblNewLabel = new JLabel("Kind");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblNewLabel.setBounds(250, 15, 125, 15);
			contentPane.add(lblNewLabel);

			JLabel lblAdCount = new JLabel("Ad Count");
			lblAdCount.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblAdCount.setBounds(375, 15, 125, 15);
			contentPane.add(lblAdCount);

			JButton btnAdvancedSearch = new JButton("Advanced Search");
			btnAdvancedSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdvancedSearch adv;
					try {
						adv = new AdvancedSearch(userId, conn);
						adv.showFrame();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
			btnAdvancedSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnAdvancedSearch.setFocusable(false);
			btnAdvancedSearch.setBounds(30, 300, 175, 35);
			contentPane.add(btnAdvancedSearch);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}