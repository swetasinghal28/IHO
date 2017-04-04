package edu.asu.msse.gnayak2.bl;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import edu.asu.msse.gnayak2.delegates.EventsDelegate;
import edu.asu.msse.gnayak2.delegates.FeaturedDelegate;
import edu.asu.msse.gnayak2.delegates.NewsDelegate;
import edu.asu.msse.gnayak2.library.EventsLibrary;

import edu.asu.msse.gnayak2.library.FeaturedNewsLibrary;
import edu.asu.msse.gnayak2.library.NewsLibrary;
import edu.asu.msse.gnayak2.models.Event;
import edu.asu.msse.gnayak2.models.Identifier;
import edu.asu.msse.gnayak2.models.FeaturedNews;
import edu.asu.msse.gnayak2.models.News;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;

public class NewsAndEvents extends JFrame implements NewsDelegate, EventsDelegate, FeaturedDelegate {
		
//	HashMap<String, News> map = new HashMap<String, News>();
//	ArrayList<String> newsArray = new ArrayList<String>();
	
	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel newsPanel;
	private JPanel eventsPanel;
	private JPanel travelPanel;
	private JPanel featuredPanel;
	private JButton newsBackButton;
	private JButton travelBackButton;
	private JButton eventsBackButton;
	private JButton featuredBackButton;
	private JButton newsButton;
	private JButton featuredButton;
	private JButton eventsButton;
	private JButton travelButton;
	private CardLayout cardLayout;
	
	private JButton viewFeaturedButton;
	private JButton deleteFeaturedButton;
	private FeaturedNews selectedFNews;
	private JList<FeaturedNews> featureList;
	private DefaultListModel<FeaturedNews>  featureModel;
	private JButton btnAddFNews;
	private FeaturedNewsLibrary featuredLibrary;
	FeaturedDelegate featuredDelegate;
	
	private JButton viewNewsButton;
	private JButton deleteNewsButton;
	private News selectedNews;
	private JList<News> newsList;
	private DefaultListModel<News>  newsModel;
	private JButton btnAddNews;
	private NewsLibrary newsLibrary;
	NewsDelegate newsDelegate;
	
	private JButton viewEventsButton;
	private JButton deleteEventsButton;
	private Event selectedEvent;
	private JList<Event> eventsList;
	private DefaultListModel<Event>  eventsModel;
	private JButton btnAddEvent;
	private EventsLibrary eventsLibrary;
	EventsDelegate eventsDelegate;
	
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
		eventsDelegate = this;
		featuredDelegate = this;
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		
		containerPanel = new JPanel();
		cardLayout = new CardLayout();
		containerPanel.setLayout(cardLayout);
		
		initializeMainPanel();
		initializeNews();
		initializeEvents();
		initializeTravel();
		initializeFeaturedNews();
		
		// add panels to container panels and give them id'scomp
		containerPanel.add(mainPanel, "1");
		containerPanel.add(newsPanel, "2");
		containerPanel.add(eventsPanel, "3");
		containerPanel.add(travelPanel, "4");
		containerPanel.add(featuredPanel,"5");
		
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
		eventsPanel = new JPanel();
		travelPanel = new JPanel();
		newsPanel = new JPanel();
		featuredPanel = new JPanel();

		eventsButton = new JButton("Events");
		newsButton = new JButton("News");
		travelButton = new JButton("Travel");
		featuredButton = new JButton("Featured News");
		
		
		mainPanel.add(newsButton);
		mainPanel.add(eventsButton);
		mainPanel.add(travelButton);	
		mainPanel.add(featuredButton);
	}
	
	// intitalize news
	public void initializeNews() {
		newsBackButton = new JButton("Back");
		viewNewsButton = new JButton("View");
		deleteNewsButton = new JButton("Delete");
		
		btnAddNews = new JButton("Add");
		

		newsPanel.setLayout(new MigLayout());
		newsPanel.add(newsBackButton);
		newsPanel.add(viewNewsButton);
		newsPanel.add(deleteNewsButton);
		
		newsPanel.add(btnAddNews, "wrap");
		newsList =  new JList<>();
		newsPanel.add(new JScrollPane(newsList),"span,push,grow, wrap");
		
		newsModel = new DefaultListModel<>();
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
						selectedNews = null;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	public void initializeFeaturedNews() {
		featuredBackButton = new JButton("Back");
		viewFeaturedButton = new JButton("View");
		deleteFeaturedButton = new JButton("Delete");
		
		btnAddFNews = new JButton("Add");
		

		featuredPanel.setLayout(new MigLayout());
		featuredPanel.add(featuredBackButton);
		featuredPanel.add(viewFeaturedButton);
		featuredPanel.add(deleteFeaturedButton);
		
		featuredPanel.add(btnAddFNews, "wrap");
		featureList =  new JList<>();
		featuredPanel.add(new JScrollPane(featureList),"span,push,grow, wrap");
		
		featureModel = new DefaultListModel<>();
		featureList.setModel(featureModel);
		featuredLibrary = FeaturedNewsLibrary.getInstance();
		Set<String> featureIDs = featuredLibrary.getKeySet();
		
		for(String id: featureIDs) {
			featureModel.addElement(featuredLibrary.getNews(id));
		}
		
		featuredBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
		
       
		featureList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedFNews = featureList.getSelectedValue();
				//System.out.println(selectedNews.getDesc());
			}
		});
		
		viewFeaturedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFNews != null) {
					EditFeaturedNewsFrame editFrame = new EditFeaturedNewsFrame(selectedFNews, featuredDelegate);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteFeaturedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFNews != null) {
					HTTPConnectionHelper helper = new HTTPConnectionHelper();
					try {
						helper.delete("featureobjects/" + selectedFNews.getId());
						helper.delete("featureid/" + selectedFNews.getId());
						featuredLibrary.deleteNews(selectedFNews.getId());
						featureModel.removeElement(selectedFNews);
						selectedFNews = null;
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
		btnAddEvent = new JButton("Add");
		
		eventsPanel.setLayout(new MigLayout());
		eventsPanel.add(eventsBackButton);
		eventsPanel.add(viewEventsButton);
		eventsPanel.add(deleteEventsButton);
		eventsPanel.add(btnAddEvent, "wrap");
		eventsList =  new JList<>();
		eventsPanel.add(new JScrollPane(eventsList),"span,push,grow, wrap");
		
		eventsModel = new DefaultListModel<>();
		eventsList.setModel(eventsModel);
		eventsLibrary = EventsLibrary.getInstance();
		Set<String> eventIds = eventsLibrary.getKeySet();
		
		for(String id: eventIds) {
			eventsModel.addElement(eventsLibrary.getEvent(id));
		}
		
		eventsBackButton.addActionListener(new ActionListener() {

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

		eventsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedEvent = eventsList.getSelectedValue();
//				System.out.println(selectedEvent.getDesc());
			}
		});
		
		viewEventsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedEvent != null) {
					EditEventsFrame editFrame = new EditEventsFrame(selectedEvent, eventsDelegate);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteEventsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedEvent != null) {
					HTTPConnectionHelper helper = new HTTPConnectionHelper();
					try {
						helper.delete("eventobjects/" + selectedEvent.getId());
						helper.delete("eventid/" + selectedEvent.getId());
						eventsLibrary.deleteEvent(selectedEvent.getId());
						eventsModel.removeElement(selectedEvent);
						selectedEvent = null;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
       
		btnAddFNews.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditFeaturedNewsFrame newsFrame = new EditFeaturedNewsFrame(featuredDelegate);
				newsFrame.setVisible(true);
			}
		});

		
		btnAddEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditEventsFrame eventsFrame = new EditEventsFrame(eventsDelegate);
				eventsFrame.setVisible(true);
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
		featuredButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "5");
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
			selectedNews = null;
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

	@Override
	public void addEvent(Event event) {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("eventobjects", new JSONObject(event));
			helper.post("eventid", new JSONObject(new Identifier(event.getId())));
			eventsModel.addElement(event);
			eventsLibrary.getInstance().addToLibrary(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEvent(Event event) {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.delete("eventobjects/" + selectedEvent.getId());
			helper.delete("eventid/" + selectedEvent.getId());
			eventsLibrary.deleteEvent(selectedEvent.getId());
			eventsModel.removeElement(selectedEvent);
			selectedEvent = null;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

	@Override
	public void addNews(FeaturedNews news) {
		// TODO Auto-generated method stub
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("featureobjects", new JSONObject(news));
			helper.post("featureid", new JSONObject(new Identifier(news.getId())));
			featureModel.addElement(news);
			featuredLibrary.getInstance().addToLibrary(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteNews(FeaturedNews news) {
		// TODO Auto-generated method stub
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("featureobjects", new JSONObject(news));
			helper.post("featureid", new JSONObject(new Identifier(news.getId())));
			featureModel.addElement(news);
			featuredLibrary.getInstance().addToLibrary(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
