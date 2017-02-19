package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.asu.msse.gnayak2.models.Event;
import edu.asu.msse.gnayak2.models.News;
import edu.asu.msse.gnayak2.models.EventDelegate;
import net.miginfocom.swing.MigLayout;

public class EditEventsFrame extends JFrame {
	
	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JScrollPane scrollPane;
	private Event event;
	private JButton btnSubmit;
	private JButton addButton;
	EventDelegate eventDelegate;
	
	/**
	 * Create the frame.
	 */
	public EditEventsFrame(Event item, EventDelegate eventdelegate) {
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
		tfLink = new JTextField("http://");
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(tfTitle, "span,pushx,growx, wrap");
		panel.add(lblReadMore);
		panel.add(tfLink, "wrap");
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
		tfLink.setText(event.getLink());
	}
 	
	public EditEventsFrame(EventDelegate eventDelegate) {
		this.eventDelegate = eventDelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		// delete old event
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Event newEvent = new Event(tfTitle.getText(), taDescription.getText(),tfLink.getText());
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
