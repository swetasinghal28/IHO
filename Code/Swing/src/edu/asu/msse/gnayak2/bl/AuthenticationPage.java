package edu.asu.msse.gnayak2.bl;

/**
 * Copyright 2016 Gowtham Ganesh Nayak,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * SER518 Software Factory
 *
 * Purpose:The purpose of this project is to all the administrator to post 
 * the contents to centralized server so that mobile devices can pull the
 * update information. This page authenticates the user so that only authorized 
 * users can post the information to the server.
 *
 * @author Gowtham Ganesh Nayak mailto:gnayak2@asu.edu
 * @version January 15 2016
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AuthenticationPage {

	private JFrame frame;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private JButton btnLogin;
	private JButton btnRegister;
	private JPanel panel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthenticationPage window = new AuthenticationPage();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AuthenticationPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		tfUsername = new JTextField(20);
		tfPassword = new JPasswordField(20);
		btnRegister = new JButton("Register");
		btnLogin = new JButton("Login");
		panel = new JPanel();
		
		panel.setLayout(new MigLayout());
		panel.add(lblUsername);
		panel.add(tfUsername,"wrap");
		panel.add(lblPassword);
		panel.add(tfPassword,"wrap");
		panel.add(btnLogin,"skip,split2");
		panel.add(btnRegister);
		
		tfPassword.setEchoChar('*');
		
		//frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		setButtonActionListeners();
	}
	
	public void setButtonActionListeners() {
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				char[] typedPass = tfPassword.getPassword();
				boolean checkPass = checkPassword(typedPass);
				
//				if(checkPass) {
					frame.dispose();
//					frame.setVisible(false);
					MainPage mainPage =  new MainPage();
					mainPage.setVisible(true);
//				} else {
//					JOptionPane.showMessageDialog(null, "Password is incorrect");
//				}
			}
			
		});
	}
	
	public boolean checkPassword(char[] typedPassword) {
		boolean isCorrect = false;
		char[] correctPass = {'d','e','f'};
		System.out.println(String.valueOf(typedPassword));
		if (typedPassword.length == correctPass.length){
			isCorrect = Arrays.equals(typedPassword, correctPass);
		}
		Arrays.fill(correctPass, '0');
		return isCorrect;
	}

}
