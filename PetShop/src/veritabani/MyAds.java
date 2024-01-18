package veritabani;

import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class MyAds {

	private JFrame MyAds;
	private JTable printAds;
	private JTextField inputAdNo;
	private String query;
	private static Connection conn;
	private static int user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			int id = 1;
			Connection dummyConn = null;

			public void run() {
				try {
					MyAds frame = new MyAds(id, dummyConn);
					frame.showFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyAds(int id, Connection parent_conn) throws SQLException {
		conn = parent_conn;
		initialize(id);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize(int id) throws SQLException {
		user = id;
		MyAds = new JFrame();
		MyAds.setTitle("MyAds");
		MyAds.setBounds(100, 100, 956, 512);
		MyAds.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		MyAds.getContentPane().setLayout(null);

		JLabel lblAdNo = new JLabel("Ad No:");
		lblAdNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdNo.setBounds(125, 60, 100, 20);
		MyAds.getContentPane().add(lblAdNo);

		inputAdNo = new JTextField();
		inputAdNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inputAdNo.setBounds(125, 90, 100, 30);
		MyAds.getContentPane().add(inputAdNo);
		inputAdNo.setColumns(10);

		JButton btnShowApplicants = new JButton("Show Applicants");
		btnShowApplicants.setFocusable(false);
		btnShowApplicants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = inputAdNo.getText();
				int adNo;;
				int check = -1;
				if(!temp.equals("")) {
					query = "SELECT ad_no FROM ads WHERE ad_no = ? AND owner_id = ? LIMIT 1;";
					adNo = Integer.parseInt(temp);
					try {
						PreparedStatement statement = conn.prepareStatement(query);
						statement.setInt(1, adNo);
						statement.setInt(2, user);
						ResultSet result = statement.executeQuery();
						while (result.next()) {
							check = result.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (check != -1) {
						EvaluationPage frame = new EvaluationPage(adNo, user, conn);
						frame.showFrame();
						inputAdNo.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Invalid ad no, please try again.");
						inputAdNo.setText("");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Ad no can not be empty, please try again.");
				}
			}
		});
		btnShowApplicants.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShowApplicants.setBounds(20, 150, 150, 50);
		MyAds.getContentPane().add(btnShowApplicants);
		updateList();

	}

	public void showFrame() {
		MyAds.setVisible(true);
	}

	public void updateList() {
		printAds = new JTable();
		printAds.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printAds.setBounds(375, 30, 540, 425);
		MyAds.getContentPane().add(printAds);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Ad No");
		model.addColumn("Applicent Count");
		model.addColumn("Animal Name");
		model.addColumn("Kind");
		model.addColumn("Price");
		model.addColumn("Ad Date");
		query = "SELECT ads.ad_no, COUNT(applications.applicent_id) AS applicent_count, animals.name AS animal_name, ads.kind, ads.price, ads.ad_date "
				+ "FROM ads JOIN animals ON ads.animal_id = animals.id LEFT JOIN applications ON ads.ad_no = applications.ad_no "
				+ "WHERE ads.owner_id = ? GROUP BY ads.ad_no, animals.name, ads.kind, ads.price, ads.ad_date;";
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(query);
			statement.setInt(1, user);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1);
				}
				model.addRow(row);

			}
			printAds.setModel(model);

			JButton btnDeleteAd = new JButton("DeleteAd");
			btnDeleteAd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String temp = inputAdNo.getText();
					int adNo;
					int check = -1;
					if(!temp.equals("")) {
						query = "SELECT ad_no FROM ads WHERE ad_no = ? AND owner_id = ? LIMIT 1;";
						adNo = Integer.parseInt(temp);
						try {
							PreparedStatement statement = conn.prepareStatement(query);
							statement.setInt(1, adNo);
							statement.setInt(2, user);
							ResultSet result = statement.executeQuery();
							while (result.next()) {
								check = result.getInt(1);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (check != -1) {
							query = "DELETE FROM applications WHERE ad_no = ?";
							PreparedStatement statement;
							try {
								statement = conn.prepareStatement(query);
								statement.setInt(1, adNo);
								statement.execute();
								query = "DELETE FROM ads WHERE owner_id = ? AND ad_no = ?";
								statement = conn.prepareStatement(query);
								statement.setInt(1, user);
								statement.setInt(2, adNo);
								statement.execute();
								JOptionPane.showMessageDialog(null, "Your ad has been deleted");
								inputAdNo.setText("");
								updatePrintAdsTable();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid ad no, please try again.");
							inputAdNo.setText("");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Ad no can not be empty, please try again.");
					}
				}

				private void updatePrintAdsTable() throws SQLException {
					// TODO Auto-generated method stub
					DefaultTableModel model = new DefaultTableModel();
					model.addColumn("Ad No");
					model.addColumn("App. Count");
					model.addColumn("Animal Name");
					model.addColumn("Kind");
					model.addColumn("Price");
					model.addColumn("Ad Date");
					query = "SELECT ads.ad_no, COUNT(applications.applicent_id) AS applicent_count, animals.name AS animal_name, ads.kind, ads.price, ads.ad_date "
							+ "FROM ads JOIN animals ON ads.animal_id = animals.id LEFT JOIN applications ON ads.ad_no = applications.ad_no "
							+ "WHERE ads.owner_id = ? GROUP BY ads.ad_no, animals.name, ads.kind, ads.price, ads.ad_date;";
					PreparedStatement statement;
					statement = conn.prepareStatement(query);
					statement.setInt(1, user);
					ResultSet result = statement.executeQuery();
					while (result.next()) {
						Object[] row = new Object[model.getColumnCount()];
						for (int i = 0; i < model.getColumnCount(); i++) {
							row[i] = result.getObject(i + 1);
						}
						model.addRow(row);

					}
					printAds.setModel(model);
				}
			});
			btnDeleteAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnDeleteAd.setBounds(190, 150, 150, 50);
			MyAds.getContentPane().add(btnDeleteAd);

			JLabel lblNewLabel = new JLabel("Ad No");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel.setBounds(375, 10, 90, 15);
			MyAds.getContentPane().add(lblNewLabel);

			JLabel lblApplicantId = new JLabel("App. Count");
			lblApplicantId.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblApplicantId.setBounds(465, 10, 90, 15);
			MyAds.getContentPane().add(lblApplicantId);

			JLabel lblNewLabel_1_1 = new JLabel("Name");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_1_1.setBounds(555, 10, 90, 15);
			MyAds.getContentPane().add(lblNewLabel_1_1);

			JLabel lblNewLabel_1_1_1 = new JLabel("Kind");
			lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_1_1_1.setBounds(645, 10, 90, 15);
			MyAds.getContentPane().add(lblNewLabel_1_1_1);

			JLabel lblNewLabel_1_1_1_1 = new JLabel("Price");
			lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_1_1_1_1.setBounds(735, 10, 90, 15);
			MyAds.getContentPane().add(lblNewLabel_1_1_1_1);

			JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Ad Date");
			lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_1_1_1_1_1.setBounds(825, 10, 90, 15);
			MyAds.getContentPane().add(lblNewLabel_1_1_1_1_1);
			
			JButton btnRefresh = new JButton("Refresh");
			btnRefresh.setFocusable(false);
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateList();
					inputAdNo.setText("");
				}
			});
			btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnRefresh.setBounds(20, 325, 125, 35);
			MyAds.getContentPane().add(btnRefresh);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}