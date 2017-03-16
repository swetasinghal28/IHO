package edu.asu.msse.gnayak2.bl;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Connect extends JFrame {

	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel IHOpanel;
	private JPanel becomingHumanPanel; 
	private JPanel signupPanel;
	private JButton btnIHO; 
	private JButton btnBecomingHuman;
	private JButton btnNews;
	private JButton IHOBackBtn;
	private JButton b1;
	private JButton linkBtn;
	private JButton b2;
	private CardLayout cardLayout;
	
    
	/**
	 * Create the frame.
	 */
	public Connect() {
		
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		
		containerPanel = new JPanel();
		cardLayout = new CardLayout();
		containerPanel.setLayout(cardLayout);
		initializeMainPanel();
	    initializeIHOPanel();
		initializeBecomingHumanPanel();
		initializeSignUpPanel();
		//initializeTravel();
		
		// add panels to container panels and give them id'scomp
		containerPanel.add(mainPanel, "1");
		containerPanel.add(IHOpanel, "2");
		containerPanel.add(becomingHumanPanel, "3");
		containerPanel.add(signupPanel, "4");
		
		
		//default show
		cardLayout.show(containerPanel, "1");
		
		//set action listeners on buttons
		setButtonActionListeners();
				
		add(containerPanel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void initializeMainPanel() {
		mainPanel = new JPanel();
		IHOpanel = new JPanel();
		becomingHumanPanel = new JPanel();
		signupPanel = new JPanel();

		btnIHO = new JButton("Institute of Human Origins");
		btnBecomingHuman = new JButton("Becoming Human");
		btnNews = new JButton("Sign up for E-news");
		
		mainPanel.add(btnIHO);
		mainPanel.add(btnBecomingHuman);
		mainPanel.add(btnNews);		
	}
	public void initializeIHOPanel() {
		linkBtn = new JButton("Official Website");
		IHOpanel.add(linkBtn);
		

		
		linkBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//cardLayout.show(containerPanel, "1");
			}
		});
	}
	
	public void initializeBecomingHumanPanel() {
		b1 = new JButton("Back");
		becomingHumanPanel.add(b1);
		
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
	}
	
	public void initializeSignUpPanel() {
		b2 = new JButton("Back");
		signupPanel.add(b2);
		
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
	}
	
	public void setButtonActionListeners(){
		btnIHO.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "2");
			}
		});
		btnBecomingHuman.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "3");
			}
		});
		btnNews.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "4");
			}
		});
	}

}