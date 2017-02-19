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

import edu.asu.msse.gnayak2.delegates.LecturesDelegate;
import edu.asu.msse.gnayak2.models.Lecture;
import net.miginfocom.swing.MigLayout;

public class EditLecturesFrame extends JFrame {

	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JScrollPane scrollPane;
	private Lecture lecture;
	private JButton btnSubmit;
	private JButton addButton;
	LecturesDelegate lectureDelegate;
	
	/**
	 * Create the frame.
	 */
	public EditLecturesFrame(Lecture item, LecturesDelegate lectureDelegate) {
		lecture = item;
		this.lectureDelegate = lectureDelegate;
		setUpFrame();
		populateFileds(lecture);
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
	
	public void populateFileds(Lecture lecture) {
		tfTitle.setText(lecture.getTitle());
		taDescription.setText(lecture.getDesc());
		tfLink.setText(lecture.getLink());
	}
 	
	public EditLecturesFrame(LecturesDelegate lecturedelegate) {
		this.lectureDelegate = lecturedelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		// delete old lecture
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Lecture newLecture = new Lecture(tfTitle.getText(), taDescription.getText(),tfLink.getText());
				// delete old lecture
				lectureDelegate.addLecture(newLecture);
				if (lecture != null){
					lectureDelegate.deleteLecture(lecture);
				}
				dispose();
			}
		});
	}
}
