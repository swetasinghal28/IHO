package edu.asu.msse.gnayak2;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class NewsAndEvents extends JFrame {
		
	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel newsPanel;
	private JPanel eventsPanel;
	private JPanel travelPanel;
	private JButton newsBackButton;
	private JButton travelBackButton;
	private JButton eventsBackButton;
	private JButton newsButton;
	private JButton eventsButton;
	private JButton travelButton;
	private CardLayout cardLayout;
	private JButton viewButton;
	private JButton deleteButton;
	private News selectedNews;
	
	// List components
	JList<News> jlist;
	DefaultListModel<News>  model;
	
	/**
	 * Create the frame.
	 */
	public NewsAndEvents() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		containerPanel = new JPanel();
		mainPanel = new JPanel();
		eventsPanel = new JPanel();
		travelPanel = new JPanel();
		newsPanel = new JPanel();
		newsBackButton = new JButton("Back");
		eventsBackButton = new JButton("Back");
		travelBackButton = new JButton("Back");
		eventsButton = new JButton("Events");
		newsButton = new JButton("News");
		travelButton = new JButton("Travel");
		cardLayout = new CardLayout();
		viewButton = new JButton("View");
		deleteButton = new JButton("Delete");
		
		jlist =  new JList<>();
		model = new DefaultListModel<>();
		
		containerPanel.setLayout(cardLayout);
		
		// add main buttons to main layout
		mainPanel.add(newsButton);
		mainPanel.add(eventsButton);
		mainPanel.add(travelButton);
		
		// Add back button to child layouts
		newsPanel.add(newsBackButton);
		eventsPanel.add(eventsBackButton);
		travelPanel.add(travelBackButton);
		
		// add panels to container panels and give them id'scomp
		containerPanel.add(mainPanel, "1");
		containerPanel.add(newsPanel, "2");
		containerPanel.add(eventsPanel, "3");
		containerPanel.add(travelPanel, "4");
		
		//default show
		cardLayout.show(containerPanel, "1");
		
		//set action listeners on buttons
		setButtonActionListeners();
				
		add(containerPanel);
		//add(mainPanel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//set everythign for list
		initializeList();
	}
	
	public void initializeList() {
		jlist.setModel(model);
		model.addElement(new News("Sansa Stark","Is an amazing lady"));
		model.addElement(new News("Tyrion Lannister","Is the hand of the king"));
		model.addElement(new News("Daenerys Targerian","Owns 3 dragons"));
		
		JScrollPane jscrollPane = new JScrollPane(jlist);
		newsPanel.add(jscrollPane);
		newsPanel.add(viewButton);
		newsPanel.add(deleteButton);
		
		// add a list listener
		jlist.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedNews = jlist.getSelectedValue();
				System.out.println(selectedNews.getDesc());
			}
		});
	}
	
	public void setButtonActionListeners() {
		
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedNews != null) {
					EditNewsFrame editFrame = new EditNewsFrame(selectedNews);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedNews != null) {
					model.removeElement(selectedNews);
				}
			}
		});
		
		// back button action listener
		newsBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
		
		eventsBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
		
		
		// individual page action listener
		travelBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
		
		newsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "2");
			}
		});
		
		eventsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "3");
			}
		});

		travelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "4");
			}
		});
	}
}
