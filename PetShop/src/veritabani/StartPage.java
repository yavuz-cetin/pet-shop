package veritabani;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class StartPage {
	private JFrame frame;
	private static Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection dummyConn = null;
				try {
					StartPage window = new StartPage(dummyConn);
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
	public StartPage(Connection parent_conn) {
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Start Page");
		frame.setBounds(100, 100, 373, 293);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login;
				try {
					frame.setVisible(false);
					login = new LoginPage(conn);
					login.showFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(35, 100, 125, 35);
		frame.getContentPane().add(btnNewButton);

		JButton btnRegister = new JButton("Register");
		btnRegister.setFocusable(false);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.setVisible(false);
					RegisterPage register = new RegisterPage(conn);
					register.showFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRegister.setBounds(180, 100, 125, 35);
		frame.getContentPane().add(btnRegister);

		JLabel lblNewLabel = new JLabel("Do you want to login or register?");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 40, 325, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnLogOut = new JButton("Quit");
		btnLogOut.setFocusable(false);
		btnLogOut.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (conn != null && !conn.isClosed()) {
		                conn.close();
		            }
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		        frame.dispose();
		        System.exit(0); // Ensure the application exits
		    }
		});
		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogOut.setFocusable(false);
		btnLogOut.setBounds(95, 176, 156, 35);
		frame.getContentPane().add(btnLogOut);
	}

	public void showFrame() {
		frame.setVisible(true);
	}
}