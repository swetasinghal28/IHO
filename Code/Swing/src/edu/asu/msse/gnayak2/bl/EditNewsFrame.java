package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.News;
import edu.asu.msse.gnayak2.models.NewsDelegate;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;

public class EditNewsFrame extends JFrame {

	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JScrollPane scrollPane;
	private News news;
	private JButton btnSubmit;
	private JButton addButton;
	NewsDelegate newsDelegate;
	
	/**
	 * Create the frame.
	 */
	public EditNewsFrame(News item, NewsDelegate newsdelegate) {
		newsDelegate = newsdelegate;
		news = item;
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		tfTitle = new JTextField(news.getTitle());
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
				News news = new News(tfTitle.getText(), taDescription.getText());
				newsDelegate.addNewsToList(news);
				dispose();
			}
		});
	}
	
	

}
