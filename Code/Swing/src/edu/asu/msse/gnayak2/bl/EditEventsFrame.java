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
import edu.asu.msse.gnayak2.models.NewsDelegate;
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
	
	
	
	/**
	 * Create the frame.
	 */
	public EditEventsFrame(Event item) {
		event = item;
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		tfTitle = new JTextField(event.getId());
		taDescription = new JTextArea("helo world",20,20);
		lblReadMore = new JLabel("Read More: ");
		tfLink = new JTextField("http:// google.com");
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
//		panel.add();
		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
		setActionListenerForButton();
	}
	
	public void setActionListenerForButton() {
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				event.setId(tfTitle.getText());
				event.setDesc(taDescription.getText());
//				dispose();
			}
		});
	}
	
	

}
