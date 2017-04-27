package edu.asu.msse.gnayak2.bl;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import edu.asu.msse.gnayak2.delegates.LecturesDelegate;
import edu.asu.msse.gnayak2.delegates.NewSciencesDelegate;
import edu.asu.msse.gnayak2.library.LecturesLibrary;
import edu.asu.msse.gnayak2.library.NewSciencesLibrary;
import edu.asu.msse.gnayak2.models.Identifier;
import edu.asu.msse.gnayak2.models.Lecture;
import edu.asu.msse.gnayak2.models.NewScience;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;

public class FieldNotes extends JFrame implements LecturesDelegate, NewSciencesDelegate {
		
//	HashMap<String, Lecture> map = new HashMap<String, Lecture>();
//	ArrayList<String> lectureArray = new ArrayList<String>();
	
	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel lecturePanel;
	private JPanel newSciencesPanel;
	private JPanel travelPanel;
	private JButton lectureBackButton;
	private JButton travelBackButton;
	private JButton newSciencesBackButton;
	private JButton lectureButton;
	private JButton newSciencesButton;
	private JButton travelButton;
	private CardLayout cardLayout;
	
	private JButton viewLectureButton;
	private JButton deleteLectureButton;
	private Lecture selectedLecture;
	private JList<Lecture> lectureList;
	private DefaultListModel<Lecture>  lectureModel;
	private JButton btnAddLecture;
	private LecturesLibrary lectureLibrary;
	LecturesDelegate lectureDelegate;
	
	private JButton viewNewSciencesButton;
	private JButton deleteNewSciencesButton;
	private NewScience selectedNewScience;
	private JList<NewScience> newSciencesList;
	private DefaultListModel<NewScience>  newSciencesModel;
	private JButton btnAddNewScience;
	private NewSciencesLibrary newSciencesLibrary;
	NewSciencesDelegate newSciencesDelegate;
	
//	private JButton viewNewSciencesButton;
//	private JButton deleteNewSciencesButton;
//	private Lecture selectedNewScience;
//	private JList<Lecture> lectureList;
//	private DefaultListModel<Lecture>  lectureModel;
	
	
	// List components
	
	
	/**
	 * Create the frame.
	 */
	public FieldNotes() {
		lectureDelegate = this;
		newSciencesDelegate = this;
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		
		containerPanel = new JPanel();
		cardLayout = new CardLayout();
		containerPanel.setLayout(cardLayout);
		
		initializeMainPanel();
		initializeLecture();
		initializeNewSciences();
		initializeTravel();
		
		// add panels to container panels and give them id'scomp
		containerPanel.add(mainPanel, "1");
		containerPanel.add(lecturePanel, "2");
		containerPanel.add(newSciencesPanel, "3");
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
		newSciencesPanel = new JPanel();
		travelPanel = new JPanel();
		lecturePanel = new JPanel();

		newSciencesButton = new JButton("NewSciences");
		lectureButton = new JButton("Scientists");
		travelButton = new JButton("Travel");
		
		mainPanel.add(lectureButton);
		mainPanel.add(newSciencesButton);
		mainPanel.add(travelButton);		
	}
	
	// intitalize lecture
	public void initializeLecture() {
		lectureBackButton = new JButton("Back");
		viewLectureButton = new JButton("View");
		deleteLectureButton = new JButton("Delete");
		btnAddLecture = new JButton("Add");

		lecturePanel.setLayout(new MigLayout());
		lecturePanel.add(lectureBackButton);
		lecturePanel.add(viewLectureButton);
		lecturePanel.add(deleteLectureButton);
		lecturePanel.add(btnAddLecture, "wrap");
		lectureList =  new JList<>();
		lecturePanel.add(new JScrollPane(lectureList),"span,push,grow, wrap");
		
		lectureModel = new DefaultListModel<>();
		lectureList.setModel(lectureModel);
		lectureLibrary = LecturesLibrary.getInstance();
		Set<String> lectureIds = lectureLibrary.getKeySet();
		
		for(String id: lectureIds) {
			lectureModel.addElement(lectureLibrary.getLecture(id));
		}
		
		lectureBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});

		lectureList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedLecture = lectureList.getSelectedValue();
				//System.out.println(selectedLecture.getDesc());
			}
		});
		
		viewLectureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedLecture != null) {
					EditLecturesFrame editFrame = new EditLecturesFrame(selectedLecture, lectureDelegate);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteLectureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedLecture != null) {
					HTTPConnectionHelper helper = new HTTPConnectionHelper();
					try {
						helper.delete("lectureobjects/" + selectedLecture.getId());
						helper.delete("lectureid/" + selectedLecture.getId());
						helper.delete("lectureimages/" + selectedLecture.getEmail());
						lectureLibrary.deleteLecture(selectedLecture.getId());
						
						lectureModel.removeElement(selectedLecture);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public void initializeNewSciences() {
		newSciencesBackButton = new JButton("Back");
		viewNewSciencesButton = new JButton("View");
		deleteNewSciencesButton = new JButton("Delete");
		btnAddNewScience = new JButton("Add");
		
		newSciencesPanel.setLayout(new MigLayout());
		newSciencesPanel.add(newSciencesBackButton);
		newSciencesPanel.add(viewNewSciencesButton);
		newSciencesPanel.add(deleteNewSciencesButton);
		newSciencesPanel.add(btnAddNewScience, "wrap");
		newSciencesList =  new JList<>();
		newSciencesPanel.add(new JScrollPane(newSciencesList),"span,push,grow, wrap");
		
		newSciencesModel = new DefaultListModel<>();
		newSciencesList.setModel(newSciencesModel);
		newSciencesLibrary = NewSciencesLibrary.getInstance();
		Set<String> eventIds = newSciencesLibrary.getKeySet();
		
		for(String id: eventIds) {
			newSciencesModel.addElement(newSciencesLibrary.getNewScience(id));
		}
		
		newSciencesBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
		
		
		
		newSciencesBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});

		newSciencesList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedNewScience = newSciencesList.getSelectedValue();
//				System.out.println(selectedNewScience.getDesc());
			}
		});
		
		viewNewSciencesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedNewScience != null) {
					EditNewScienceFrame editFrame = new EditNewScienceFrame(selectedNewScience, newSciencesDelegate);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteNewSciencesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedNewScience != null) {
					HTTPConnectionHelper helper = new HTTPConnectionHelper();
					try {
						helper.delete("newscienceobjects/" + selectedNewScience.getId());
						helper.delete("newscienceid/" + selectedNewScience.getId());
						newSciencesLibrary.deleteNewScience(selectedNewScience.getId());
						newSciencesModel.removeElement(selectedNewScience);
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
		btnAddLecture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditLecturesFrame lectureFrame = new EditLecturesFrame(lectureDelegate);
				lectureFrame.setVisible(true);
			}
		});

		btnAddNewScience.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditNewScienceFrame newSciencesFrame = new EditNewScienceFrame(newSciencesDelegate);
				newSciencesFrame.setVisible(true);
			}
		});
		
		// back button action listener
		
		
		
		
		
		// individual page action listener
		
		
		lectureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "2");
			}
		});
		
		newSciencesButton.addActionListener(new ActionListener() {

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
	
	public void deleteLecture(Lecture lecture) {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.delete("lectureobjects/" + lecture.getId());
			helper.delete("lectureid/" + lecture.getId());
			lectureLibrary.deleteLecture(lecture.getId());
			lectureModel.removeElement(lecture);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void addLecture(Lecture lecture) {
		
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("lectureobjects", new JSONObject(lecture));
			helper.post("lectureid", new JSONObject(new Identifier(lecture.getId())));
			lectureModel.addElement(lecture);
			lectureLibrary.getInstance().addToLibrary(lecture);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addNewScience(NewScience newScience) {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("newscienceobjects", new JSONObject(newScience));
			helper.post("newscienceid", new JSONObject(new Identifier(newScience.getId())));
			newSciencesModel.addElement(newScience);
			newSciencesLibrary.getInstance().addToLibrary(newScience);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteNewScience(NewScience newScience) {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.delete("newscienceobjects/" + selectedNewScience.getId());
			helper.delete("newscienceid/" + selectedNewScience.getId());
			newSciencesLibrary.deleteNewScience(selectedNewScience.getId());
			newSciencesModel.removeElement(selectedNewScience);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
}
