package veritabani;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyApps {
	private JTable printAppsTable;
	private JFrame frame;
	private static int id;
	private static Connection conn;
	private JTextField inputAdNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection dummyConn = null;
				int i = 1;
				try {
					MyApps window = new MyApps(i, dummyConn);
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
	public MyApps() throws SQLException {
		initialize();
	}

	public MyApps(int userId, Connection parent_conn) throws SQLException {
		id = userId;
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("My Applications");
		frame.setBounds(100, 100, 825, 475);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		printAppsTable = new JTable();
		printAppsTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printAppsTable.setBounds(25, 30, 765, 300);
		frame.getContentPane().add(printAppsTable);

		JLabel lblNewLabel = new JLabel("Ad No");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(25, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblOwnerId = new JLabel("Owner Id");
		lblOwnerId.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOwnerId.setBounds(110, 10, 85, 15);
		frame.getContentPane().add(lblOwnerId);

		JLabel lblNewLabel_1_1 = new JLabel("Animal Id");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(195, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Kind");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1.setBounds(280, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1_1.setBounds(365, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Address");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1_1_1.setBounds(450, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Ad Date");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1_1_1_1.setBounds(535, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Name");
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1_1_1_1_1.setBounds(620, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("Age");
		lblNewLabel_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1_1_1_1_1_1.setBounds(705, 10, 85, 15);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1_1);

		JLabel lblNewLabel_1 = new JLabel("Ad No:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(205, 370, 85, 25);
		frame.getContentPane().add(lblNewLabel_1);

		inputAdNo = new JTextField();
		inputAdNo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputAdNo.setBounds(290, 370, 85, 25);
		frame.getContentPane().add(inputAdNo);
		inputAdNo.setColumns(10);

		JButton btnDeleteApplication = new JButton("Delete");
		btnDeleteApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = inputAdNo.getText();
				int check = -1;
				int adNo;
				if (!temp.equals("")) {
					adNo = Integer.parseInt(temp);
					String query = "SELECT ad_no FROM applications WHERE ad_no = ? AND applicent_id = ?";
					PreparedStatement statement;
					try {
						statement = conn.prepareStatement(query);
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
						query = "DELETE FROM applications WHERE ad_no = ? AND applicent_id = ?;";
						try {
							statement = conn.prepareStatement(query);
							statement.setInt(1, adNo);
							statement.setInt(2, id);
							statement.execute();
							JOptionPane.showMessageDialog(null, "Your application has been deleted");
							updateList();
							inputAdNo.setText("");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Invalid ad no. Please try again");
						inputAdNo.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ad no can not be empty. Please try again");
				}
			}
		});
		btnDeleteApplication.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDeleteApplication.setBounds(400, 340, 135, 35);
		frame.getContentPane().add(btnDeleteApplication);
		
		JButton btnShowApplicants = new JButton("Check Applicants");
		btnShowApplicants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = inputAdNo.getText();
				int adNo;
				int check = -1;
				if(!temp.equals("")) {
					String query = "SELECT ads.ad_no FROM ads,applications WHERE ads.ad_no = ? AND applications.ad_no = ads.ad_no AND applicent_id = ?;";
					adNo = Integer.parseInt(temp);
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
						ApplicantWindow w = null;
						try {
							w = new ApplicantWindow(adNo, conn);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						w.showFrame();
					} else {
						JOptionPane.showMessageDialog(null, "Invalid ad no, please try again.");
					}
					inputAdNo.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "Ad no can not be empty, please try again.");
				}
			}
		});
		btnShowApplicants.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnShowApplicants.setBounds(399, 385, 136, 35);
		frame.getContentPane().add(btnShowApplicants);
		printAppsTable.setVisible(true);
		updateList();
	}

	public void updateList() {
		DefaultTableModel model = new DefaultTableModel();

		String columnName = "Ad No";
		model.addColumn(columnName);
		columnName = "Owner Id";
		model.addColumn(columnName);
		columnName = "Animal Id";
		model.addColumn(columnName);
		columnName = "Kind";
		model.addColumn(columnName);
		columnName = "Price";
		model.addColumn(columnName);
		columnName = "Address";
		model.addColumn(columnName);
		columnName = "Ad Date";
		model.addColumn(columnName);
		columnName = "Name";
		model.addColumn(columnName);
		columnName = "Age";
		model.addColumn(columnName);

		String query = "Select a.ad_no,a.owner_id,a.animal_id,a.kind,a.price,a.address,a.ad_date,b.name,b.age "
				+ "FROM animals b,ads a,applications c "
				+ "WHERE c.ad_no = a.ad_no AND c.applicent_id = ? AND b.id = a.animal_id;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1);
				}
				model.addRow(row);
			}
			printAppsTable.setModel(model);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		frame.getContentPane().setVisible(true);
	}
	public void showFrame() {
		frame.setVisible(true);
	}
}