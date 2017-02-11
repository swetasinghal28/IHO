package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JFrame {

	private JPanel panel;

	/**
	 * Create the frame.
	 */
	public About() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		panel = new JPanel();
		panel.add(new JLabel("About"));
		add(panel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
