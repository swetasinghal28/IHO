package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.asu.msse.gnayak2.delegates.EventsDelegate;
import edu.asu.msse.gnayak2.models.Event;
import net.miginfocom.swing.MigLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EditEventsFrame extends JFrame {
	
	private JPanel panel;
	private JTextField tfTitle;
	private JTextField tfLocation;
	private JTextArea taDescription;
	private JTextField tfRegURL;
	private JTextField tfDate;
	private JLabel lblReadMore;
	private JLabel lblTitle;
	private JLabel lblDate;
	private JLabel lblDesc;
	private JLabel lblRegistrationURL;
	private JScrollPane scrollPane;
	private Event event;
	private JButton btnSubmit;
	private JButton addButton;
	EventsDelegate eventDelegate;
	String locationURL;
	private Pattern pattern;
	private Matcher matcher;
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
		taDescription = new JTextArea("",120,120);
		lblReadMore = new JLabel("Read More:");
		lblDate = new JLabel("Date:");
		lblRegistrationURL = new JLabel("Registration URL:");
		lblTitle = new JLabel("Event Name:");
		lblDesc = new JLabel("Description");
//		tfPlace = new JTextField("");
		tfRegURL = new JTextField("",120);
		tfLocation = new JTextField("",120);
		tfDate = new JTextField("",120);
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(lblTitle);
		panel.add(tfTitle, "span,pushx,growx, wrap");
		panel.add(lblReadMore);
		//panel.add(tfPlace, "wrap");
		panel.add(tfLocation,"wrap");
		panel.add(lblDate);
		panel.add(tfDate,"wrap");
		panel.add(lblRegistrationURL);
		panel.add(tfRegURL, "wrap");
		panel.add(lblDesc);
		panel.add(scrollPane,"wrap");	
		panel.add(btnSubmit,"wrap");

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
		
		tfLocation.setText(event.getPlace());
		tfRegURL.setText(event.getRegURL());
		tfDate.setText(event.getDate());
	}
 	
	public EditEventsFrame(EventsDelegate eventDelegate) {
		this.eventDelegate = eventDelegate;
		setUpFrame();
	}
	public boolean validate(final String date){
	    String format="((?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Tues|Thur|Thurs|Sun|Mon|Tue|Wed|Thu|Fri|Sat))(,)(\\s+)((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?))(\\s+)((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])(\\s+)((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])(,)(\\s+)((1[0-2]|0?[1-9]):([0-5][0-9]) ([AP][M]))";

		  pattern = Pattern.compile(format);
  matcher = pattern.matcher(date);

  if(matcher.matches()){
  System.out.println("Matches");
	 matcher.reset();

	 return true;
  }else{
	  JOptionPane.showMessageDialog(tfDate, "Please enter date in the format - Tuesday, April 30 2018, 05:40 PM");   
	  return false;
  }
}
	public void setActionListenerForButton() {
		// delete old event
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String eventLocation = tfLocation.getText();
				locationURL = "http://maps.google.com/?q=" + eventLocation;
				boolean isValidDate = validate(tfDate.getText());
				if(isValidDate){
				Event newEvent = new Event(tfTitle.getText(), taDescription.getText(),tfLocation.getText(), locationURL, tfDate.getText(), tfRegURL.getText());
				// delete old event
				
				eventDelegate.addEvent(newEvent);
				if (event != null){
					eventDelegate.deleteEvent(event);
				}
				dispose();
				
			}
			}
		});
	}
	
	

}