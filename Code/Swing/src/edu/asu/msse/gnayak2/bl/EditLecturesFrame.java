package edu.asu.msse.gnayak2.bl;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.asu.msse.gnayak2.delegates.GalleryDelegate;
import edu.asu.msse.gnayak2.delegates.LecturesDelegate;
import edu.asu.msse.gnayak2.library.GalleryLibrary;
import edu.asu.msse.gnayak2.models.GalleryModel;
import edu.asu.msse.gnayak2.models.Lecture;
import net.miginfocom.swing.MigLayout;

public class EditLecturesFrame extends JFrame {

	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel galleryPanel;
	private JButton galleryBackButton;
	
	private CardLayout cardLayout;
	
	private JButton viewGalleryButton;
	private JButton deleteGalleryButton;
	private GalleryModel selectedImage;
	private JList<GalleryModel> imageList;
	private DefaultListModel<GalleryModel>  galleryModel;
	private JButton btnAddGallery;
	private GalleryLibrary galleryLibrary;
	GalleryDelegate galleryDelegate;
	
	
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
	int flag =0;
	
	byte[] imageInByte;
	private JButton galleryButton;
	
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
		/**
		 * gallery delegate not set
		 */
		
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		containerPanel = new JPanel();
		cardLayout = new CardLayout();
		containerPanel.setLayout(cardLayout);
		
		initializeMainPanel();
		initializeGallery();
		
		containerPanel.add(mainPanel, "1");
		containerPanel.add(galleryPanel, "2");
		
		cardLayout.show(containerPanel, "1");
		
		add(containerPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
	
	public void initializeMainPanel() {
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
		galleryButton = new JButton("Gallery");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout());
		mainPanel.add(tfName
				, "span,pushx,growx, wrap");
		mainPanel.add(lblReadMore);
		mainPanel.add(tfLink, "wrap");
		mainPanel.add(tfDesc, "wrap");
		mainPanel.add(tfEmail, "wrap");
		mainPanel.add(tfOrder,"wrap");
		mainPanel.add(scrollPane,"span,push,grow, wrap");	
		mainPanel.add(browseButton, "wrap");
		mainPanel.add(imageFileButton, "wrap");
		mainPanel.add(btnSubmit);
		mainPanel.add(galleryButton);
		setActionListenerForButton();
	}
	
	public void initializeGallery() {
		galleryPanel = new JPanel();
	}
	
	public void populateFileds(Lecture lecture) {
		tfName.setText(lecture.getName());
		taDescription.setText(lecture.getBio());
		tfLink.setText(lecture.getLink());
		tfDesc.setText(lecture.getTitle());
		tfEmail.setText(lecture.getEmail());
		imageFileButton.setText(lecture.getImage());
		String order_value = Integer.toString(lecture.getOrder());
		tfOrder.setText(order_value);
	}
 	
	public EditLecturesFrame(LecturesDelegate lecturedelegate) {
		this.lectureDelegate = lecturedelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		
		galleryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "2");
			}
		});
		
		// delete old lecture
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flag==1)
				{
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
				}
				 
				 if(flag==1){
					 int ord = Integer.parseInt(tfOrder.getText());
				Lecture newLecture = new Lecture(tfName.getText(), taDescription.getText(),tfLink.getText(),tfDesc.getText(),encodedImage,tfEmail.getText(),ord);
				lectureDelegate.addLecture(newLecture);
				 }
				
				 else {
					 int ord = Integer.parseInt(tfOrder.getText());
					 Lecture newLecture = new Lecture(tfName.getText(), taDescription.getText(),tfLink.getText(),tfDesc.getText(),imageFileButton.getText(),tfEmail.getText(),ord);
					 lectureDelegate.addLecture(newLecture);
				 }
				// delete old lecture
				
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
					    flag =1; 
					    imageFileButton.setText(filename);
					    

					
				}
			});
		 galleryButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					 // send the name to gallery frame and use it

					
				}
			});
	}
}
