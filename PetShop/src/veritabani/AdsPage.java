package veritabani;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class AdsPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputAdNo;
	private JButton btnApply;
	private String query;
	private Connection conn;
	private JTable printAdsTable;
	private JLabel lblNewLabel;
	private DefaultTableModel model;
	private JLabel lblAdNo_1;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int id = 1001;
				String str = "Kus";
				Connection dummyConn = null;
				try {
					AdsPage frame = new AdsPage(str, id, dummyConn);
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
	public AdsPage(String kind, int id, Connection parent_conn) throws SQLException {
		conn = parent_conn;
		setTitle("Ads");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1025, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAdNo = new JLabel("Ad No:");
		lblAdNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAdNo.setBounds(10, 175, 75, 20);
		contentPane.add(lblAdNo);

		inputAdNo = new JTextField();
		inputAdNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputAdNo.setBounds(75, 175, 75, 20);
		contentPane.add(inputAdNo);
		inputAdNo.setColumns(10);

		btnApply = new JButton("Apply");
		btnApply.setFocusable(false);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = inputAdNo.getText();
				int adNo;
				int check = -1;
				if(!temp.equals("")) {
					query = "SELECT ad_no FROM ads WHERE kind = ? AND owner_id != ? AND ad_no = ?"
							+ " AND ad_no NOT IN (SELECT ad_no FROM applications WHERE applicent_id = ?);";
					adNo = Integer.parseInt(temp);
					try {
						PreparedStatement statement = conn.prepareStatement(query);
						statement.setString(1, kind);
						statement.setInt(2, id);
						statement.setInt(3, adNo);
						statement.setInt(4, id);
						ResultSet result = statement.executeQuery();
						while (result.next()) {
							check = result.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (check != -1) {
						query = "INSERT INTO applications(applicent_id,ad_no) VALUES(?,?);";
						try {
							PreparedStatement p = conn.prepareStatement(query);
							p.clearParameters();
							p.setInt(1, id);
							p.setInt(2, adNo);
							p.execute();
							JOptionPane.showMessageDialog(null, "Your application has been received");
						} catch (SQLException e1) {
							
								 JOptionPane.showMessageDialog(null, e1.getMessage());
					            
						}
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
		btnApply.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnApply.setBounds(75, 200, 100, 30);
		contentPane.add(btnApply);

		printAdsTable = new JTable();
		printAdsTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printAdsTable.setBounds(225, 75, 765, 300);
		contentPane.add(printAdsTable);
		model = new DefaultTableModel();
		model.addColumn("Ad No");
		model.addColumn("Owner ID");
		model.addColumn("Kind");
		model.addColumn("Price");
		model.addColumn("Address");
		model.addColumn("Ad Date");
		model.addColumn("Name");
		model.addColumn("Age");
		model.addColumn("Sex");
		String query = "CREATE OR REPLACE VIEW list_Ad_View AS "
				+ "SELECT ads.ad_no, ads.owner_id, ads.kind,ads.price,ads.address,ads.ad_date, animals.name, animals.age, animals.sex"
				+ " FROM ads, animals " + "WHERE ads.kind = '" + kind
				+ "' AND ads.animal_id = animals.id AND owner_id !=" + id + " AND ads.ad_no NOT IN (SELECT ad_no FROM applications WHERE applicent_id = " + id
				+ ") ORDER BY ad_no;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.execute();
			query = "SELECT * FROM list_Ad_View";
			statement = conn.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1);
				}
				model.addRow(row);
			}
			printAdsTable.setModel(model);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		lblNewLabel = new JLabel("Ad No");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(225, 50, 85, 20);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("List Cheap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model_1 = new DefaultTableModel();
				model = model_1;
				model.addColumn("Ad No");
				model.addColumn("Owner ID");
				model.addColumn("Kind");
				model.addColumn("Price");
				model.addColumn("Address");
				model.addColumn("Ad Date");
				model.addColumn("Name");
				model.addColumn("Age");
				model.addColumn("Sex");
				String subquery = "SELECT avg(price) FROM ads where kind = ? group by kind";

				String query = "SELECT ads.ad_no, ads.owner_id, ads.kind, ads.price,ads.address,ads.ad_date,animals.name, animals.age, animals.sex "
						+ "FROM ads,animals " + "WHERE ads.kind = ? AND ads.animal_id = animals.id AND owner_id != ? "
						+ "GROUP BY ads.ad_no, ads.owner_id, ads.kind, ads.price,ads.address,ads.ad_date,animals.name , animals.age, animals.sex "
						+ "HAVING ads.price<=?  order by ad_no;";
				try {
					PreparedStatement substt = conn.prepareStatement(subquery);
					substt.setString(1, kind);
					ResultSet subResult = substt.executeQuery();

					PreparedStatement stt = conn.prepareStatement(query);
					stt.setInt(2, id);
					stt.setString(1, kind);
					subResult.next();
					stt.setFloat(3, subResult.getFloat(1));
					ResultSet result = stt.executeQuery();
					while (result.next()) {
						Object[] row = new Object[model.getColumnCount()];
						for (int i = 0; i < model.getColumnCount(); i++) {
							row[i] = result.getObject(i + 1);
						}
						model.addRow(row);
					}
					printAdsTable.setModel(model);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 50, 150, 30);
		contentPane.add(btnNewButton);

		JButton btnListExpensive = new JButton("List Expensive");
		btnListExpensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model_1 = new DefaultTableModel();
				model = model_1;
				model.addColumn("Ad No");
				model.addColumn("Owner ID");
				model.addColumn("Kind");
				model.addColumn("Price");
				model.addColumn("Address");
				model.addColumn("Ad Date");
				model.addColumn("Name");
				model.addColumn("Age");
				model.addColumn("Sex");
				String subquery = "SELECT avg(price) FROM ads where kind = ? group by kind";

				String query = "SELECT ads.ad_no, ads.owner_id, ads.kind, ads.price,ads.address,ads.ad_date,animals.name, animals.age, animals.sex "
						+ "FROM ads,animals " + "WHERE ads.kind = ? AND ads.animal_id = animals.id AND owner_id != ? "
						+ "GROUP BY ads.ad_no, ads.owner_id, ads.kind, ads.price,ads.address,ads.ad_date,animals.name , animals.age, animals.sex "
						+ "HAVING ads.price>=?  order by ad_no;";

				try {
					PreparedStatement substt = conn.prepareStatement(subquery);
					substt.setString(1, kind);
					ResultSet subResult = substt.executeQuery();

					PreparedStatement stt = conn.prepareStatement(query);
					stt.setInt(2, id);
					stt.setString(1, kind);
					subResult.next();
					stt.setInt(3, subResult.getInt(1));
					ResultSet result = stt.executeQuery();
					while (result.next()) {
						Object[] row = new Object[model.getColumnCount()];
						for (int i = 0; i < model.getColumnCount(); i++) {
							row[i] = result.getObject(i + 1);
						}
						model.addRow(row);
					}
					printAdsTable.setModel(model);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnListExpensive.setFocusable(false);
		btnListExpensive.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnListExpensive.setBounds(10, 100, 150, 30);
		contentPane.add(btnListExpensive);

		lblAdNo_1 = new JLabel("Ad No:");
		lblAdNo_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAdNo_1.setBounds(10, 300, 75, 20);
		contentPane.add(lblAdNo_1);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(75, 300, 75, 20);
		contentPane.add(textField);

		JButton btnCheckApplicants = new JButton("Check Applicants");
		btnCheckApplicants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = textField.getText();
				int adNo;
				int check = -1;
				if(!temp.equals("")) {
					String query = "SELECT ad_no FROM ads WHERE kind = ? AND ad_no = ?;";
					adNo = Integer.parseInt(temp);
					try {
						PreparedStatement statement = conn.prepareStatement(query);
						statement.setString(1, kind);
						statement.setInt(2, adNo);
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
					textField.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "Ad no can not be empty, please try again.");
				}
			}
		});
		btnCheckApplicants.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCheckApplicants.setFocusable(false);
		btnCheckApplicants.setBounds(10, 325, 175, 30);
		contentPane.add(btnCheckApplicants);

		JLabel lblOwnerId = new JLabel("Owner ID");
		lblOwnerId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOwnerId.setBounds(310, 50, 85, 20);
		contentPane.add(lblOwnerId);

		JLabel lblKind = new JLabel("Kind");
		lblKind.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKind.setBounds(395, 50, 85, 20);
		contentPane.add(lblKind);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(480, 50, 85, 20);
		contentPane.add(lblPrice);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddress.setBounds(565, 50, 85, 20);
		contentPane.add(lblAddress);

		JLabel lblAdDate = new JLabel("Ad Date");
		lblAdDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAdDate.setBounds(650, 50, 85, 20);
		contentPane.add(lblAdDate);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblName.setBounds(735, 50, 85, 20);
		contentPane.add(lblName);

		JLabel lblSex = new JLabel("Sex");
		lblSex.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSex.setBounds(905, 50, 85, 20);
		contentPane.add(lblSex);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAge.setBounds(820, 50, 85, 20);
		contentPane.add(lblAge);

	}
}