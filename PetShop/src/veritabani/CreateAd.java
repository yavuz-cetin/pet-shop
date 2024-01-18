package veritabani;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.sql.SQLException;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class CreateAd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable printAnimals;
	private JTextField inputAnimalID;
	private JTextField inputPrice;
	private int animal_id;
	private String query;
	private String address;
	private String kind;
	private int price;
	private int userId;
	PreparedStatement statement;
	ResultSet result;
	ResultSet holdOwnedIds;
	private Connection conn;
	private static JFrame CreateAd;
	private JLabel lblId;
	private JLabel lblKind;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_1_1_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int i = 1001;
				Connection dummyConn = null;
				try {
					CreateAd = new CreateAd(i, dummyConn);
					CreateAd.setVisible(true);
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
	public CreateAd(int id, Connection parent_conn) throws SQLException {
		userId = id;
		conn = parent_conn;
		CreateAd = new JFrame();
		setTitle("Create Ad");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAnimalID = new JLabel("Animal ID:");
		lblAnimalID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAnimalID.setBounds(10, 75, 100, 25);
		contentPane.add(lblAnimalID);

		inputAnimalID = new JTextField();
		inputAnimalID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inputAnimalID.setBounds(100, 75, 100, 25);
		contentPane.add(inputAnimalID);
		inputAnimalID.setColumns(10);

		inputPrice = new JTextField();
		inputPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inputPrice.setBounds(100, 125, 100, 25);
		contentPane.add(inputPrice);
		inputPrice.setColumns(10);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrice.setBounds(10, 125, 100, 25);
		contentPane.add(lblPrice);

		JButton btnCreateAd = new JButton("Create Ad");
		btnCreateAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp1 = inputAnimalID.getText();

				String temp2 = inputPrice.getText();
				if (!temp1.equals("") && !temp2.equals("")) {
					animal_id = Integer.parseInt(temp1);
					price = Integer.parseInt(temp2);
					int check = -1;
					query = "SELECT id FROM animals, owns WHERE animals.id = ? AND owns.user_id = ? AND owns.animal_id = animals.id;";
					PreparedStatement s;
					try {
						s = conn.prepareStatement(query);
						s.setInt(1, animal_id);
						s.setInt(2, userId);
						ResultSet r = s.executeQuery();
						while (r.next()) {
							check = r.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (check != -1) {
						try {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							LocalDateTime now = LocalDateTime.now();
							String temp = dtf.format(now);
							java.sql.Date date = new java.sql.Date(
									new SimpleDateFormat("yyyy-MM-dd").parse(temp).getTime());

							query = "SELECT address FROM users WHERE id = ?;";
							try (PreparedStatement statement = conn.prepareStatement(query)) {
								statement.setInt(1, userId);
								try (ResultSet result = statement.executeQuery()) {
									while (result.next()) {
										address = result.getString(1);
									}
								}
							}
							query = "SELECT kind FROM animals WHERE id = ?;";
							try (PreparedStatement statement_2 = conn.prepareStatement(query)) {
								statement_2.setInt(1, animal_id);
								try (ResultSet result_1 = statement_2.executeQuery()) {
									while (result_1.next()) {
										kind = result_1.getString(1);
									}
								}
							}

							query = "INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (?, ?, ?, ?, ?, ?);";
							statement = conn.prepareStatement(query);
							statement.setInt(1, userId);
							statement.setInt(2, animal_id);
							statement.setString(3, kind);
							statement.setInt(4, price);
							statement.setString(5, address);
							statement.setDate(6, date);
							statement.execute();
							JOptionPane.showMessageDialog(null, "Your ad has been listed.");
							inputAnimalID.setText("");
							inputPrice.setText("");
							updateList();
						} catch (SQLException ex) {

								 JOptionPane.showMessageDialog(null, ex.getMessage());
					             
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Invalid animal id. Please try again.");
						inputAnimalID.setText("");
						inputPrice.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Input(s) can not be empty. Please try again.");
				}

			}
		});
		btnCreateAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateAd.setBounds(100, 175, 125, 35);
		btnCreateAd.setFocusable(false);
		contentPane.add(btnCreateAd);

		printAnimals = new JTable();
		printAnimals.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printAnimals.setBounds(250, 40, 450, 350);
		contentPane.add(printAnimals);
		
		lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblId.setBounds(250, 20, 90, 15);
		contentPane.add(lblId);
		
		lblKind = new JLabel("Kind");
		lblKind.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblKind.setBounds(340, 20, 90, 15);
		contentPane.add(lblKind);
		
		lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(430, 20, 90, 15);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_1_1 = new JLabel("Age");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(520, 20, 90, 15);
		contentPane.add(lblNewLabel_1_1);
		
		lblNewLabel_1_1_1 = new JLabel("Sex");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(610, 20, 90, 15);
		contentPane.add(lblNewLabel_1_1_1);

		updateList();

	}

	public void updateList() {
		DefaultTableModel model = new DefaultTableModel();

		model.addColumn("ID");
		model.addColumn("kind");
		model.addColumn("name");
		model.addColumn("age");
		model.addColumn("sex");
		String query = "SELECT a.* FROM animals a,owns o WHERE o.user_id=? AND o.animal_id=a.id AND a.id NOT IN (Select animal_id from ads where animal_id=a.id) GROUP BY a.id;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);

			statement.setInt(1, userId);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Object[] row = new Object[model.getColumnCount()];
				for (int i = 0; i < model.getColumnCount(); i++) {
					row[i] = result.getObject(i + 1);
				}
				model.addRow(row);
			}
			printAnimals.setModel(model);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void showFrame() {
		setVisible(true);
	}
}