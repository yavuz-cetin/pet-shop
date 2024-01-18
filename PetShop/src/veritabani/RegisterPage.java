package veritabani;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterPage {
	private Connection conn;
	private JFrame frame;
	private JTextField passwordField;
	private JTextField phoneField;
	private JLabel phoneLabel;
	private JTextField addressField;
	private JLabel addressLabel;
	private JTextField usernameField;
	private JLabel usernameLabel;
	private JLabel tipLabel;
	private int newId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection dummyConn = null;
				try {
					RegisterPage window = new RegisterPage(dummyConn);
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
	public RegisterPage(Connection parent_conn) throws SQLException {
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Register Page");
		frame.setBounds(100, 100, 431, 411);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(30, 250, 125, 25);
		frame.getContentPane().add(passwordField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		passwordLabel.setBounds(30, 230, 104, 14);
		frame.getContentPane().add(passwordLabel);

		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "SELECT register_user(?,?,?,?);";
				PreparedStatement statement;
				if(usernameField.getText().length() > 20 || addressField.getText().length() > 20
						|| phoneField.getText().length() > 10 || passwordField.getText().length() > 6) {
					JOptionPane.showMessageDialog(null, "Max length : user name(20), address(20), phone number(10), password(6)");
				}
				else {
					try {
						if (usernameField.getText().length() >= 3 && addressField.getText().length() >= 3
								&& phoneField.getText().length() >= 3 && passwordField.getText().length() >= 3) {
							statement = conn.prepareStatement(query);
							statement.setString(1, usernameField.getText());
							statement.setString(2, addressField.getText());
							statement.setString(3, phoneField.getText());
							statement.setString(4, passwordField.getText());
							ResultSet r = statement.executeQuery();
							if (r.next()) {
								JOptionPane.showMessageDialog(null,
										"Register trigger triggered! Your new id is : " + r.getInt(1));
								newId = r.getInt(1);
								UserPage pg = new UserPage(newId, conn);
								pg.showFrame();
								frame.setVisible(false);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Information(s) can not be empty.Please try again.");

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		registerButton.setBounds(30, 300, 125, 35);
		frame.getContentPane().add(registerButton);
		registerButton.setFocusable(false);

		JButton returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage startPage = new StartPage(conn);
				startPage.showFrame();
				frame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		returnButton.setBounds(175, 300, 125, 35);
		frame.getContentPane().add(returnButton);
		returnButton.setFocusable(false);

		phoneField = new JTextField();
		phoneField.setColumns(10);
		phoneField.setBounds(30, 190, 125, 25);
		frame.getContentPane().add(phoneField);

		phoneLabel = new JLabel("Phone:");
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		phoneLabel.setBounds(30, 170, 104, 14);
		frame.getContentPane().add(phoneLabel);

		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(30, 130, 125, 25);
		frame.getContentPane().add(addressField);

		addressLabel = new JLabel("Address:");
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		addressLabel.setBounds(30, 110, 100, 15);
		frame.getContentPane().add(addressLabel);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(30, 70, 125, 25);
		frame.getContentPane().add(usernameField);

		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		usernameLabel.setBounds(30, 50, 100, 15);
		frame.getContentPane().add(usernameLabel);

		tipLabel = new JLabel("Note: User name, address, phone and password must be at least length of 3");
		tipLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		tipLabel.setBounds(20, 10, 375, 30);
		frame.getContentPane().add(tipLabel);
	}

	public void showFrame() {
		frame.setVisible(true);
	}
}