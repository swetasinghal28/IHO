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

import edu.asu.msse.gnayak2.delegates.NewSciencesDelegate;
import edu.asu.msse.gnayak2.models.NewScience;
import net.miginfocom.swing.MigLayout;

public class EditNewScienceFrame extends JFrame {

	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JLabel lblTitle;
	private JLabel lblDesc;
	private JScrollPane scrollPane;
	private NewScience newScience;
	private JButton btnSubmit;
	private JButton addButton;
	NewSciencesDelegate newScienceDelegate;
	
	/**
	 * Create the frame.
	 */
	public EditNewScienceFrame(NewScience item, NewSciencesDelegate newScienceDelegate) {
		newScience = item;
		this.newScienceDelegate = newScienceDelegate;
		setUpFrame();
		populateFileds(newScience);
	}
	
	public void setUpFrame() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		lblTitle = new JLabel("Title");
		tfTitle = new JTextField();
		lblDesc = new JLabel("Description");
		taDescription = new JTextArea("",120,120);
		lblReadMore = new JLabel("Read More: ");
		tfLink = new JTextField("",120);
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(lblTitle);
		panel.add(tfTitle, " wrap");
		panel.add(lblReadMore);
		panel.add(tfLink, "wrap");
		panel.add(lblDesc);
		panel.add(scrollPane,"wrap");	
		panel.add(btnSubmit,"wrap");

		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
		setActionListenerForButton();
	}
	
	public void populateFileds(NewScience newScience) {
		tfTitle.setText(newScience.getTitle());
		taDescription.setText(newScience.getDesc());
		tfLink.setText(newScience.getLink());
	}
 	
	public EditNewScienceFrame(NewSciencesDelegate newSciencedelegate) {
		this.newScienceDelegate = newSciencedelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		// delete old newScience
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewScience newNewScience = new NewScience(tfTitle.getText(), taDescription.getText(),tfLink.getText());
				// delete old newScience
				newScienceDelegate.addNewScience(newNewScience);
				if (newScience != null){
					newScienceDelegate.deleteNewScience(newScience);
				}
				dispose();
			}
		});
	}
}
