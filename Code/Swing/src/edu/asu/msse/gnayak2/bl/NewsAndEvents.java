package edu.asu.msse.gnayak2.bl;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.library.NewsLibrary;
import edu.asu.msse.gnayak2.models.Event;
import edu.asu.msse.gnayak2.models.Identifier;
import edu.asu.msse.gnayak2.models.News;
import edu.asu.msse.gnayak2.models.NewsDelegate;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class NewsAndEvents extends JFrame implements NewsDelegate {
		
//	HashMap<String, News> map = new HashMap<String, News>();
//	ArrayList<String> newsArray = new ArrayList<String>();
	NewsDelegate newsDelegate;
	
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
	
	private JButton viewNewsButton;
	private JButton deleteNewsButton;
	private News selectedNews;
	private JList<News> newsList;
	private DefaultListModel<News>  newsModel;
	
	private JButton viewEventsButton;
	private JButton deleteEventsButton;
	private Event selectedEvent;
	private JList<Event> eventsList;
	private DefaultListModel<Event>  eventsModel;
	
	private JButton btnAddNews;
	private NewsLibrary newsLibrary;
	
//	private JButton viewEventsButton;
//	private JButton deleteEventsButton;
//	private News selectedEvent;
//	private JList<News> newsList;
//	private DefaultListModel<News>  newsModel;
	
	
	// List components
	
	
	/**
	 * Create the frame.
	 */
	public NewsAndEvents() {
		newsDelegate = this;
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		
		containerPanel = new JPanel();
		cardLayout = new CardLayout();
		containerPanel.setLayout(cardLayout);
		
		initializeMainPanel();
		initializeNews();
		initializeEvents();
		initializeTravel();
		
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
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//set everythign for list
		
	}
	
	public void initializeMainPanel() {
		mainPanel = new JPanel();
		eventsPanel = new JPanel();
		travelPanel = new JPanel();
		newsPanel = new JPanel();
		btnAddNews = new JButton("Add");
		
		eventsButton = new JButton("Events");
		newsButton = new JButton("News");
		travelButton = new JButton("Travel");
		
		mainPanel.add(newsButton);
		mainPanel.add(eventsButton);
		mainPanel.add(travelButton);		
	}
	
	// intitalize news
	public void initializeNews() {
		newsBackButton = new JButton("Back");
		viewNewsButton = new JButton("View");
		deleteNewsButton = new JButton("Delete");

		newsPanel.add(newsBackButton);
		newsPanel.add(viewNewsButton);
		newsPanel.add(deleteNewsButton);
		newsPanel.add(btnAddNews);
		
		newsList =  new JList<>();
		newsModel = new DefaultListModel<>();
		newsPanel.add(new JScrollPane(newsList));
		
		newsList.setModel(newsModel);
		newsLibrary = NewsLibrary.getInstance();
		Set<String> newsIds = newsLibrary.getKeySet();
		
		for(String id: newsIds) {
			newsModel.addElement(newsLibrary.getNews(id));
		}
		
		newsBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});

		newsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedNews = newsList.getSelectedValue();
				//System.out.println(selectedNews.getDesc());
			}
		});
		
		viewNewsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedNews != null) {
					EditNewsFrame editFrame = new EditNewsFrame(selectedNews, newsDelegate);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteNewsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedNews != null) {
					HTTPConnectionHelper helper = new HTTPConnectionHelper();
					try {
						helper.delete("newsobjects/" + selectedNews.getId());
						helper.delete("newsid/" + selectedNews.getId());
						newsLibrary.deleteNews(selectedNews.getId());
						newsModel.removeElement(selectedNews);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public void initializeEvents() {
		eventsBackButton = new JButton("Back");
		viewEventsButton = new JButton("View");
		deleteEventsButton = new JButton("Delete");
		
		eventsPanel.add(eventsBackButton);
		eventsPanel.add(viewEventsButton);
		eventsPanel.add(deleteEventsButton);
		
		eventsList =  new JList<>();
		eventsModel = new DefaultListModel<>();
		eventsPanel.add(new JScrollPane(eventsList));
		
		eventsList.setModel(eventsModel);
		eventsModel.addElement(new Event("Sansa Stark","Is an amazing lady"));
		eventsModel.addElement(new Event("Tyrion Lannister","Is the hand of the king"));
		eventsModel.addElement(new Event("Daenerys Targerian","Owns 3 dragons"));
		
		eventsBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});

		eventsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedEvent = eventsList.getSelectedValue();
				System.out.println(selectedEvent.getDesc());
			}
		});
		
		viewEventsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedEvent != null) {
					EditEventsFrame editFrame = new EditEventsFrame(selectedEvent);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteEventsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedEvent != null) {
					eventsModel.removeElement(selectedEvent);
				}
			}
		});
	}
	
	public void initializeTravel() {
		travelBackButton = new JButton("Back");
		travelPanel.add(travelBackButton);
		
		travelBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
	}

	public void setButtonActionListeners() {
		btnAddNews.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditNewsFrame newsFrame = new EditNewsFrame(newsDelegate);
				newsFrame.setVisible(true);
			}
		});

		
		// back button action listener
		
		
		
		
		
		// individual page action listener
		
		
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
	
	public void deleteNews(News news) {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.delete("newsobjects/" + news.getId());
			helper.delete("newsid/" + news.getId());
			newsLibrary.deleteNews(news.getId());
			newsModel.removeElement(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void addNews(News news) {
		
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("newsobjects", new JSONObject(news));
			helper.post("newsid", new JSONObject(new Identifier(news.getId())));
			newsModel.addElement(news);
			newsLibrary.getInstance().addToLibrary(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
