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

public class EvaluationPage {

	private JFrame EvaluationPage;
	private Connection conn;
	private JTable printApplicantsTable;
	private String query;
	private DefaultTableModel model;
	private JTextField inputApplicentID;
	private JButton btnReject;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int i = 0;
				Connection dummyConn = null;
				try {
					EvaluationPage window = new EvaluationPage(i, i, dummyConn);
					window.EvaluationPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EvaluationPage(int adNumber, int user, Connection parent_conn) {
		int adNo = adNumber;
		int user_id = user;
		conn = parent_conn;
		initialize(adNo, user_id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int adNo, int user_id) {
		EvaluationPage = new JFrame();
		EvaluationPage.setTitle("EvaluationPage");
		EvaluationPage.setBounds(100, 100, 950, 550);
		EvaluationPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		EvaluationPage.getContentPane().setLayout(null);

		printApplicantsTable = new JTable();
		printApplicantsTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printApplicantsTable.setBounds(350, 40, 550, 450);
		EvaluationPage.getContentPane().add(printApplicantsTable);
		model = new DefaultTableModel();
		model.addColumn("Ad No");
		model.addColumn("Applicant ID");
		model.addColumn("Applicant Name");
		model.addColumn("Phone Number");
		model.addColumn("Address");
		query = "SELECT applications.ad_no,users.id as applicant_id, users.user_name as applicent_name, users.phone_number, users.address "
				+ "FROM applications,users " + "WHERE ad_no = ? AND applications.applicent_id = users.id;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, adNo);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1);
				}
				model.addRow(row);
			}
			printApplicantsTable.setModel(model);

			JLabel lblApplicentID = new JLabel("Applicent ID:");
			lblApplicentID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblApplicentID.setBounds(100, 85, 125, 30);
			EvaluationPage.getContentPane().add(lblApplicentID);

			inputApplicentID = new JTextField();
			inputApplicentID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			inputApplicentID.setBounds(100, 125, 125, 35);
			EvaluationPage.getContentPane().add(inputApplicentID);
			inputApplicentID.setColumns(10);

			JButton btnApprove = new JButton("Approve");
			btnApprove.setFocusable(false);
			btnApprove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String temp = inputApplicentID.getText();
					int applicent_id;
					int animal_id = 0;
					int check = -1;
					if(!temp.equals("")) {
						query = "SELECT users.id FROM applications,users WHERE ad_no = ? AND users.id = ? LIMIT 1;";
						applicent_id = Integer.parseInt(temp);
						try {
							PreparedStatement statement = conn.prepareStatement(query);
							statement.setInt(1, adNo);
							statement.setInt(2, applicent_id);
							ResultSet result = statement.executeQuery();
							while (result.next()) {
								check = result.getInt(1);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (check != -1) {
							query = "SELECT animal_id FROM ads WHERE ad_no = ?;";
							PreparedStatement statement;
							try {
								statement = conn.prepareStatement(query);
								statement.setInt(1, adNo);
								ResultSet result = statement.executeQuery();
								while (result.next()) {
									animal_id = result.getInt(1);
								}
								query = "DELETE FROM applications WHERE ad_no = ?;";
								statement = conn.prepareStatement(query);
								statement.setInt(1, adNo);
								statement.execute();
								query = "DELETE FROM ads WHERE ad_no = ?;";
								statement = conn.prepareStatement(query);
								statement.setInt(1, adNo);
								statement.execute();
								query = "DELETE FROM owns WHERE animal_id = ?;";
								statement = conn.prepareStatement(query);
								statement.setInt(1, animal_id);
								statement.execute();
								query = "INSERT INTO owns VALUES(?,?);";
								statement = conn.prepareStatement(query);
								statement.setInt(1, applicent_id);
								statement.setInt(2, animal_id);
								statement.execute();
								JOptionPane.showMessageDialog(null, "Applicent Approved");
								inputApplicentID.setText("");
								updatePrintApplicantsTable(adNo);
								EvaluationPage.dispose();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid applicent id, please try again.");
							inputApplicentID.setText("");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Applicant id can not be empty, please try again.");
					}
				}
			});
			btnApprove.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnApprove.setBounds(25, 175, 125, 35);
			EvaluationPage.getContentPane().add(btnApprove);

			btnReject = new JButton("Reject");
			btnReject.setFocusable(false);
			btnReject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String temp = inputApplicentID.getText();
					int applicentID;
					int check = -1;
					if(!temp.equals("")) {
						query = "SELECT users.id FROM applications,users WHERE ad_no = ? AND users.id = ? LIMIT 1;";
						applicentID = Integer.parseInt(temp);
						try {
							PreparedStatement statement = conn.prepareStatement(query);
							statement.setInt(1, adNo);
							statement.setInt(2, applicentID);
							ResultSet result = statement.executeQuery();
							while (result.next()) {
								check = result.getInt(1);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (check != -1) {
							query = "DELETE FROM applications WHERE applicent_id = ?";
							PreparedStatement statement;
							try {
								statement = conn.prepareStatement(query);
								statement.setInt(1, applicentID);
								statement.execute();
								JOptionPane.showMessageDialog(null, "Applicent Rejected");
								updatePrintApplicantsTable(adNo);
								inputApplicentID.setText("");
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid applicent id, please try again.");
							inputApplicentID.setText("");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Applicent id can not be empty, please try again.");
					}
					
				}

			});

			btnReject.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnReject.setBounds(175, 175, 125, 35);
			EvaluationPage.getContentPane().add(btnReject);

			JLabel lblNewLabel = new JLabel("Ad No");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel.setBounds(350, 15, 110, 15);
			EvaluationPage.getContentPane().add(lblNewLabel);

			JLabel lblAppId = new JLabel("App. ID");
			lblAppId.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblAppId.setBounds(460, 15, 110, 15);
			EvaluationPage.getContentPane().add(lblAppId);

			JLabel lblNewLabel_1_1 = new JLabel("App. Name");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_1_1.setBounds(570, 15, 110, 15);
			EvaluationPage.getContentPane().add(lblNewLabel_1_1);

			JLabel lblNewLabel_1_1_1 = new JLabel("App. Phone");
			lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_1_1_1.setBounds(680, 15, 110, 15);
			EvaluationPage.getContentPane().add(lblNewLabel_1_1_1);

			JLabel lblNewLabel_1_1_1_1 = new JLabel("App. Address");
			lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_1_1_1_1.setBounds(790, 15, 110, 15);
			EvaluationPage.getContentPane().add(lblNewLabel_1_1_1_1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void updatePrintApplicantsTable(int adNo) throws SQLException {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		model.addColumn("Ad No");
		model.addColumn("Applicant ID");
		model.addColumn("Applicant Name");
		model.addColumn("Phone Number");
		model.addColumn("Address");
		query = "SELECT applications.ad_no,users.id as applicent_id, users.user_name as applicent_name, users.phone_number, users.address "
				+ "FROM applications,users " + "WHERE ad_no = ? AND applications.applicent_id = users.id;";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setInt(1, adNo);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			Object[] row = new Object[model.getColumnCount()];
			for (int i = 0; i < model.getColumnCount(); i++) {
				row[i] = result.getObject(i + 1);
			}
			model.addRow(row);
		}
		printApplicantsTable.setModel(model);
	}

	public void showFrame() {
		EvaluationPage.setVisible(true);
	}
}