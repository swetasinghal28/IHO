package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.asu.msse.gnayak2.delegates.EventsDelegate;
import edu.asu.msse.gnayak2.models.Event;
import net.miginfocom.swing.MigLayout;

public class EditEventsFrame extends JFrame {
	
	private JPanel panel;
	private JTextField tfTitle;
	private JTextField tfLocation;
	private JTextArea taDescription;
	private JTextField tfRegURL;
	private JTextField tfDate;
	private JLabel lblReadMore;
	private JScrollPane scrollPane;
	private Event event;
	private JButton btnSubmit;
	private JButton addButton;
	EventsDelegate eventDelegate;
	String locationURL;
	
	/**
	 * Create the frame.
	 */
	public EditEventsFrame(Event item, EventsDelegate eventdelegate) {
		event = item;
		this.eventDelegate = eventdelegate;
		setUpFrame();
		populateFileds(event);
	}
	
	public void setUpFrame() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		tfTitle = new JTextField();
		taDescription = new JTextArea("",20,20);
		lblReadMore = new JLabel("Read More: ");
//		tfPlace = new JTextField("");
		tfRegURL = new JTextField("Registration URL",20);
		tfLocation = new JTextField("Location",20);
		tfDate = new JTextField("Date",20);
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(tfTitle, "span,pushx,growx, wrap");
		panel.add(lblReadMore);
		//panel.add(tfPlace, "wrap");
		panel.add(tfLocation,"wrap");
		panel.add(tfDate,"wrap");
		panel.add(tfRegURL, "wrap");
		panel.add(scrollPane,"span,push,grow, wrap");	
		panel.add(btnSubmit);

		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
		setActionListenerForButton();
	}
	
	public void populateFileds(Event event) {
		tfTitle.setText(event.getTitle());
		taDescription.setText(event.getDesc());
		//tfPlace.setText(event.getPlace());
		tfLocation.setText(event.getLocation());
		tfRegURL.setText(event.getRegURL());
		tfDate.setText(event.getDate());
	}
 	
	public EditEventsFrame(EventsDelegate eventDelegate) {
		this.eventDelegate = eventDelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		// delete old event
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String eventLocation = tfLocation.getText();
				locationURL = "http://maps.google.com/?q=" + eventLocation;
				
				Event newEvent = new Event(tfTitle.getText(), taDescription.getText(),tfLocation.getText(), locationURL, tfDate.getText(), tfRegURL.getText());
				// delete old event
				eventDelegate.addEvent(newEvent);
				if (event != null){
					eventDelegate.deleteEvent(event);
				}
				dispose();
			}
		});
	}
	
	

}