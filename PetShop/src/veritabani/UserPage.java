package veritabani;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UserPage {

	private JFrame frame;
	private JTextField userIdLabel;
	private JTextField usernameLabel;
	private JTextField phoneLabel;
	private JTextField addressLabel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JTextField textField_4;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	Connection conn;
	private static int buttonCounter;
	private static int buttonCounter_1;
	private static int buttonCounter_2;
	private static int buttonCounter_3;
	private int user;
	private JTextField passwordLabel;
	private JButton btnNewButton_8;
	private JButton btnNewButton_10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPage window = new UserPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserPage() throws SQLException {
		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
		user = 1001;
		initialize();
	}

	public UserPage(int userId, Connection conn_parent) throws SQLException {
		user = userId;
		conn = conn_parent;
		initialize();

		String query = "SELECT id, user_name, phone_number, password, address FROM users WHERE id = ? ;";
		PreparedStatement p = conn.prepareStatement(query);
		p.clearParameters();
		p.setInt(1, userId);

		ResultSet r = p.executeQuery();
		if (r.next()) {
			userIdLabel.setText("" + r.getInt(1));
			usernameLabel.setText(r.getString(2));
			phoneLabel.setText(r.getString(3));
			addressLabel.setText(r.getString(5));
			passwordLabel.setText(r.getString(4));
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("User Page");
		frame.setBounds(100, 100, 700, 536);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("User Id");
		lblNewLabel.setBounds(30, 14, 76, 21);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(30, 92, 76, 21);
		frame.getContentPane().add(lblUsername);

		JLabel lblNewLabel_1_1 = new JLabel("Phone");
		lblNewLabel_1_1.setBounds(30, 178, 76, 21);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Address");
		lblNewLabel_1_1_1.setBounds(30, 263, 76, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1);

		userIdLabel = new JTextField();
		userIdLabel.setEditable(false);
		userIdLabel.setBounds(10, 46, 86, 21);
		frame.getContentPane().add(userIdLabel);
		userIdLabel.setColumns(10);

		usernameLabel = new JTextField();
		usernameLabel.setEditable(false);
		usernameLabel.setColumns(10);
		usernameLabel.setBounds(10, 131, 86, 21);
		frame.getContentPane().add(usernameLabel);

		phoneLabel = new JTextField();
		phoneLabel.setEditable(false);
		phoneLabel.setColumns(10);
		phoneLabel.setBounds(10, 204, 86, 21);
		frame.getContentPane().add(phoneLabel);

		addressLabel = new JTextField();
		addressLabel.setEditable(false);
		addressLabel.setColumns(10);
		addressLabel.setBounds(10, 295, 86, 21);
		frame.getContentPane().add(addressLabel);

		buttonCounter = 0;
		buttonCounter_1 = 0;
		buttonCounter_2 = 0;
		buttonCounter_3 = 0;

		btnNewButton = new JButton("Change");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonCounter % 2 == 0) {
					textField_4.setVisible(true);
					btnNewButton_4.setVisible(true);
				} else {
					textField_4.setVisible(false);
					btnNewButton_4.setVisible(false);
				}
				buttonCounter++;
			}
		});
		btnNewButton.setBounds(106, 366, 89, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setFocusable(false);

		btnNewButton_1 = new JButton("Change");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonCounter_1 % 2 == 0) {
					textField_1.setVisible(true);
					btnNewButton_5.setVisible(true);
				} else {
					textField_1.setVisible(false);
					btnNewButton_5.setVisible(false);
				}
				buttonCounter_1++;

			}
		});
		btnNewButton_1.setBounds(106, 130, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.setFocusable(false);

		btnNewButton_2 = new JButton("Change");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonCounter_2 % 2 == 0) {
					textField_2.setVisible(true);
					btnNewButton_6.setVisible(true);
				} else {
					textField_2.setVisible(false);
					btnNewButton_6.setVisible(false);
				}
				buttonCounter_2++;

			}
		});
		btnNewButton_2.setBounds(106, 203, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.setFocusable(false);

		btnNewButton_3 = new JButton("Change");
		btnNewButton_3.setBounds(106, 294, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
		btnNewButton_3.setFocusable(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonCounter_3 % 2 == 0) {
					textField_3.setVisible(true);
					btnNewButton_7.setVisible(true);
				} else {
					textField_3.setVisible(false);
					btnNewButton_7.setVisible(false);
				}
				buttonCounter_3++;

			}
		});

		textField_1 = new JTextField();
		textField_1.setBounds(205, 131, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setVisible(false);

		textField_2 = new JTextField();
		textField_2.setBounds(205, 204, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setVisible(false);

		textField_3 = new JTextField();
		textField_3.setBounds(205, 295, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		textField_3.setVisible(false);

		textField_4 = new JTextField();
		textField_4.setBounds(205, 367, 86, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		textField_4.setVisible(false);

		btnNewButton_4 = new JButton("Confirm");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = textField_4.getText();
				if (password != null && password.length() >= 3) {
					try {
						changePassword(password, user);
						passwordLabel.setText(textField_4.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Must be at least 3 chars in length", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		btnNewButton_4.setBounds(301, 366, 89, 23);
		frame.getContentPane().add(btnNewButton_4);
		btnNewButton_4.setFocusable(false);
		btnNewButton_4.setVisible(false);

		btnNewButton_5 = new JButton("Confirm");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newUserName = textField_1.getText();
				if (newUserName != null && newUserName.length() >= 3) {
					try {
						changeUserName(newUserName, user);
						usernameLabel.setText(textField_1.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Must be at least 3 chars in length", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton_5.setBounds(301, 130, 89, 23);
		frame.getContentPane().add(btnNewButton_5);
		btnNewButton_5.setFocusable(false);
		btnNewButton_5.setVisible(false);

		btnNewButton_6 = new JButton("Confirm");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String phoneNumber = textField_2.getText();
				if (phoneNumber != null && phoneNumber.length() >= 3) {
					try {
						changePhone(textField_2.getText(), user);
						phoneLabel.setText(textField_2.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Must be at least 3 chars in length", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton_6.setBounds(301, 203, 89, 23);
		frame.getContentPane().add(btnNewButton_6);
		btnNewButton_6.setFocusable(false);
		btnNewButton_6.setVisible(false);

		btnNewButton_7 = new JButton("Confirm");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = textField_3.getText();
				if (address != null && address.length() >= 3) {
					try {
						changeAddress(address, user);
						addressLabel.setText(textField_3.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Must be at least 3 chars in length", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton_7.setBounds(301, 294, 89, 23);
		frame.getContentPane().add(btnNewButton_7);
		btnNewButton_7.setFocusable(false);

		passwordLabel = new JTextField();
		passwordLabel.setText("******");
		passwordLabel.setEditable(false);
		passwordLabel.setColumns(10);
		passwordLabel.setBounds(10, 367, 86, 21);
		frame.getContentPane().add(passwordLabel);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Password");
		lblNewLabel_1_1_1_1.setBounds(30, 338, 76, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);

		btnNewButton_2 = new JButton("Log Out");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage startPage = new StartPage(conn);
				startPage.showFrame();
				frame.setVisible(false);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_2.setBounds(40, 420, 125, 35);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.setFocusable(false);

		btnNewButton_8 = new JButton("Shop");
		btnNewButton_8.setFocusable(false);
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShopModule shop;
				try {

					shop = new ShopModule(user, conn);
					shop.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 29));
		btnNewButton_8.setBounds(260, 15, 125, 50);
		frame.getContentPane().add(btnNewButton_8);

		JButton btnNewButton_8_1 = new JButton("My Applications");
		btnNewButton_8_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MyApps app = new MyApps(user, conn);
					app.showFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		btnNewButton_8_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_8_1.setFocusable(false);
		btnNewButton_8_1.setBounds(450, 100, 175, 50);
		frame.getContentPane().add(btnNewButton_8_1);

		JButton btnNewButton_8_1_1 = new JButton("Create Ad");
		btnNewButton_8_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateAd ad;
				try {
					ad = new CreateAd(user, conn);
					ad.showFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnNewButton_8_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_8_1_1.setFocusable(false);
		btnNewButton_8_1_1.setBounds(450, 250, 175, 50);
		frame.getContentPane().add(btnNewButton_8_1_1);

		JButton btnNewButton_8_1_1_1 = new JButton("New Animal");
		btnNewButton_8_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kind, name, age, sex;
				int animal_id = 0;
				kind = JOptionPane.showInputDialog("Enter animal kind:");
				if (kind != null && kind.length() > 0) {
					name = JOptionPane.showInputDialog("Enter animal name:");
					if (name != null && name.length() > 0) {
						age = JOptionPane.showInputDialog("Enter animal age:");
						if (age != null && age.length() > 0) {
							sex = JOptionPane.showInputDialog("Enter animal sex:");
							String query = "INSERT INTO animals (kind, name, age, sex) VALUES (?, ?, ?, ?)";
							if (sex != null && sex.length() > 0) {
								try (PreparedStatement statement = conn.prepareStatement(query)) {
									statement.setString(1, kind);
									statement.setString(2, name);
									statement.setInt(3, Integer.parseInt(age));
									statement.setString(4, sex);

									String query_2 = "SELECT id FROM animals ORDER BY id DESC LIMIT 1;";
									PreparedStatement statement_2 = conn.prepareStatement(query_2);
									ResultSet result = statement_2.executeQuery();
									while (result.next()) {
										animal_id = result.getInt(1) + 1;
									}
									String query_3 = "INSERT INTO owns(user_id,animal_id) VALUES(?, ?);";
									PreparedStatement statement_3 = conn.prepareStatement(query_3);

									statement.execute();
									statement_3.setInt(1, user);
									statement_3.setInt(2, animal_id);
									statement_3.execute();
									JOptionPane.showMessageDialog(null,
											"Fill date field trigger triggered! Owns table updated successfully!");
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, e1.getMessage());
								}
							} else {
								JOptionPane.showMessageDialog(null, "Input can not be empty.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Input can not be empty.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Input can not be empty.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Input can not be empty.");
				}
			}
		});
		btnNewButton_8_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_8_1_1_1.setFocusable(false);
		btnNewButton_8_1_1_1.setBounds(450, 175, 175, 50);
		frame.getContentPane().add(btnNewButton_8_1_1_1);

		JButton btnNewButton_9 = new JButton("My Ads");
		btnNewButton_9.setFocusable(false);
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyAds frame_2;
				try {
					frame_2 = new MyAds(user, conn);
					frame_2.showFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnNewButton_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_9.setBounds(450, 325, 175, 50);
		frame.getContentPane().add(btnNewButton_9);

		JButton btnNewButton_8_1_1_2 = new JButton("Update Price");
		btnNewButton_8_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ChangeAnimalPrice chn = new ChangeAnimalPrice(user, conn);
					chn.showFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		btnNewButton_8_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_8_1_1_2.setFocusable(false);
		btnNewButton_8_1_1_2.setBounds(450, 400, 175, 50);
		frame.getContentPane().add(btnNewButton_8_1_1_2);

		btnNewButton_10 = new JButton("Pet Products");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PetProducts petprd = new PetProducts(user, conn);
					petprd.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		btnNewButton_10.setFont(new Font("Tahoma", Font.BOLD, 29));
		btnNewButton_10.setFocusable(false);
		btnNewButton_10.setBounds(400, 15, 225, 50);
		frame.getContentPane().add(btnNewButton_10);
		btnNewButton_7.setVisible(false);
	}

	public void showFrame() {
		frame.setVisible(true);
	}

	public void changeUserName(String newString, int idOfUser) throws SQLException {
		String query = "UPDATE users SET user_name = ? WHERE id = ?;";

		PreparedStatement p = conn.prepareStatement(query);
		p.clearParameters();
		// p.setString(1, toBeChanged);
		p.setString(1, newString);
		p.setInt(2, idOfUser);
		p.execute();

	}

	public void changePhone(String newString, int idOfUser) throws SQLException {
		String query = "UPDATE users SET phone_number = ? WHERE id = ?;";

		PreparedStatement p = conn.prepareStatement(query);
		p.clearParameters();
		// p.setString(1, toBeChanged);
		p.setString(1, newString);
		p.setInt(2, idOfUser);
		p.execute();

	}

	public void changeAddress(String newString, int idOfUser) throws SQLException {
		String query = "UPDATE users SET address = ? WHERE id = ?;";

		PreparedStatement p = conn.prepareStatement(query);
		p.clearParameters();
		// p.setString(1, toBeChanged);
		p.setString(1, newString);
		p.setInt(2, idOfUser);
		p.execute();

	}

	public void changePassword(String newString, int idOfUser) throws SQLException {
		String query = "UPDATE users SET password = ? WHERE id = ?;";

		PreparedStatement p = conn.prepareStatement(query);
		p.clearParameters();
		// p.setString(1, toBeChanged);
		p.setString(1, newString);
		p.setInt(2, idOfUser);
		p.execute();

	}
}