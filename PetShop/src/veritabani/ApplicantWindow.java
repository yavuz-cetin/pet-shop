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

public class ApplicantWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTable printAppsTable;
	private int adNo;
	private Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicantWindow window = new ApplicantWindow();
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
	public ApplicantWindow() throws SQLException {
		initialize();
	}

	public ApplicantWindow(int ad_No, Connection parent_conn) throws SQLException {
		adNo = ad_No;
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Applicant Window");
		frame.setBounds(100, 100, 375, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		printAppsTable = new JTable();
		printAppsTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printAppsTable.setBounds(25, 30, 300, 300);
		frame.getContentPane().add(printAppsTable);
		DefaultTableModel model = new DefaultTableModel();

		String columnName = "Applicant Id";
		model.addColumn(columnName);
		columnName = "Username";
		model.addColumn(columnName);
		columnName = "City";
		model.addColumn(columnName);

		String query = "SELECT a.applicent_id,u.user_name,u.address FROM users u,applications a,ads d WHERE u.id = a.applicent_id AND a.ad_no =d.ad_no AND d.ad_no = ?";
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
			printAppsTable.setModel(model);
			
			JLabel lblUserId = new JLabel("User ID");
			lblUserId.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblUserId.setBounds(25, 5, 100, 20);
			frame.getContentPane().add(lblUserId);
			
			JLabel lblName = new JLabel("Name");
			lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblName.setBounds(125, 5, 100, 20);
			frame.getContentPane().add(lblName);
			
			JLabel lblNewLabel_1_1 = new JLabel("Address");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_1_1.setBounds(225, 5, 100, 20);
			frame.getContentPane().add(lblNewLabel_1_1);
			printAppsTable.setVisible(true);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		frame.getContentPane().setVisible(true);
	}

	public void showFrame() {
		frame.setVisible(true);
	}
}