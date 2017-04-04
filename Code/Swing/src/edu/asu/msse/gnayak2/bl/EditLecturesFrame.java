package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	private JTextField tfName;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JTextField tfDesc;
	private JTextField tfEmail;
	private JTextField tfOrder;
	private JScrollPane scrollPane;
	private Lecture lecture;
	private JButton btnSubmit;
	private JButton addButton;
	private JButton browseButton;
	private JTextField imageFileButton;
	LecturesDelegate lectureDelegate;
	String filename;
	String encodedImage;
	
	byte[] imageInByte;
	
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
		tfName = new JTextField();
		taDescription = new JTextArea("",20,20);
		lblReadMore = new JLabel("Read More: ");
		tfLink = new JTextField("http://");
		browseButton = new JButton("Browse");
		imageFileButton = new JTextField("",20);
		tfDesc = new JTextField("Title",20);
		tfEmail = new JTextField("Email",20);
		tfOrder = new JTextField("Order",20);
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(tfName
				, "span,pushx,growx, wrap");
		panel.add(lblReadMore);
		panel.add(tfLink, "wrap");
		panel.add(tfDesc, "wrap");
		panel.add(tfEmail, "wrap");
		panel.add(tfOrder,"wrap");
		panel.add(scrollPane,"span,push,grow, wrap");	
		panel.add(browseButton, "wrap");
		panel.add(imageFileButton, "wrap");
		panel.add(btnSubmit);

		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
		setActionListenerForButton();
	}
	
	public void populateFileds(Lecture lecture) {
		tfName.setText(lecture.getName());
		taDescription.setText(lecture.getBio());
		tfLink.setText(lecture.getLink());
		tfDesc.setText(lecture.getTitle());
		tfEmail.setText(lecture.getEmail());
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
				 try{

					 	BufferedImage originalImage =
					                               ImageIO.read(new File(filename));
			      	 	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  		 	ImageIO.write( originalImage, "jpg", baos );
					 	baos.flush();
					 	imageInByte = baos.toByteArray();
				

			            encodedImage = Base64.getEncoder().encodeToString(imageInByte);
					 	System.out.println("BYTE ARRAY_________"+imageInByte);
					 	baos.close();
			           	}catch(IOException e1){
					 		System.out.println(e1.getMessage());
						 	}
				 
				 int ord = Integer.parseInt(tfOrder.getText());
				Lecture newLecture = new Lecture(tfName.getText(), taDescription.getText(),tfLink.getText(),tfDesc.getText(),encodedImage,tfEmail.getText(),ord);
				// delete old lecture
				lectureDelegate.addLecture(newLecture);
				if (lecture != null){
					lectureDelegate.deleteLecture(lecture);
				}
				dispose();
			}
		});
		 browseButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					  JFileChooser chooser = new JFileChooser();
					    chooser.showOpenDialog(null);
					    File f = chooser.getSelectedFile();
					    filename = f.getAbsolutePath();
					    imageFileButton.setText(filename);
					    

					
				}
			});
	}
}
