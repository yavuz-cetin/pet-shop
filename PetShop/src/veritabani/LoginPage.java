package veritabani;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

public class LoginPage {

	private JFrame frame;
	private JTextField userIdField;
	private JPasswordField passwordField;
	private Connection conn;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection dummyConn = null;
				try {
					LoginPage window = new LoginPage(dummyConn);
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
	public LoginPage(Connection parent_conn) throws SQLException {
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login Page");
		frame.setBounds(100, 100, 373, 293);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		userIdField = new JTextField();
		userIdField.setBounds(20, 60, 100, 25);
		frame.getContentPane().add(userIdField);
		userIdField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(20, 120, 100, 25);
		frame.getContentPane().add(passwordField);

		JLabel userIdLabel = new JLabel("User Id:");
		userIdLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		userIdLabel.setBounds(20, 40, 80, 15);
		frame.getContentPane().add(userIdLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		passwordLabel.setBounds(20, 100, 100, 15);
		frame.getContentPane().add(passwordLabel);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String query = "SELECT login_user(?,?);";
				try {
					PreparedStatement statement = conn.prepareStatement(query);
					statement.setInt(1, Integer.parseInt(userIdField.getText()));
					statement.setString(2, passwordField.getText());

					ResultSet r = statement.executeQuery();
					r.next();
					if (r.getBoolean(1)) {
						UserPage userpg = new UserPage(Integer.parseInt(userIdField.getText()), conn);
						userpg.showFrame();
						frame.setVisible(false);
					} else {
						lblNewLabel.setVisible(true);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(20, 165, 100, 30);
		frame.getContentPane().add(btnNewButton);

		JButton returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage startPage = new StartPage(conn);
				startPage.showFrame();
				frame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		returnButton.setBounds(144, 165, 100, 30);
		frame.getContentPane().add(returnButton);
		returnButton.setFocusable(false);

		lblNewLabel = new JLabel("Wrong id or password please try again.");
		lblNewLabel.setVisible(false);
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(20, 199, 325, 44);
		frame.getContentPane().add(lblNewLabel);
		
		
	}

	public void showFrame() {
		frame.setVisible(true);
	}
}