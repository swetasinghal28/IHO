package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.delegates.GalleryDelegate;
import edu.asu.msse.gnayak2.models.GalleryModel;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EditGallery extends JFrame {

	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JScrollPane scrollPane;
	private GalleryModel gallery;
	private JButton btnSubmit;
	private JButton browseButton;
	private JTextField imageFileButton;
	private JButton addButton;
	GalleryDelegate galleryDelegate;
	String filename, encodedImage;
	byte[] imageInByte;
	
	/**
	 * Create the frame.
	 */
	public EditGallery(GalleryModel item, GalleryDelegate galleryDelegate) {
        gallery = item;
        this.galleryDelegate = galleryDelegate;
        setUpFrame();
     	populateFileds(gallery);
	}
	
	public void setUpFrame() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		tfTitle = new JTextField("", 20);
	
		browseButton = new JButton("Browse");
		imageFileButton = new JTextField("",20);
//		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		panel = new JPanel();
		panel.add(new JLabel("Image Caption"));
		panel.add(tfTitle, "span,pushx,growx, wrap");
		panel.add(browseButton, "wrap");
		panel.add(imageFileButton, "wrap");
		
		panel.add(btnSubmit);
		
		add(panel);
		pack();
		setVisible(true);
		setActionListenerForButton();
	}
	
	public void populateFileds(GalleryModel gallery) {
		tfTitle.setText(gallery.getTitle());
		imageFileButton.setText(gallery.getImage());
	}
 	
	public EditGallery(GalleryDelegate galleryDelegate) {
	    this.galleryDelegate = galleryDelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		// delete old news
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
			 	System.out.println("BYTE_________"+imageInByte.toString().length());
			 	encodedImage = Base64.getEncoder().encodeToString(imageInByte);
			 	baos.close();

			 	}catch(IOException e1){
			 		System.out.println(e1.getMessage());
			 	}
				
				GalleryModel newGallery = new GalleryModel(tfTitle.getText(), encodedImage);
			    
				
				System.out.println("BYTE_________"+imageInByte.toString());
				
				galleryDelegate.addGallery(newGallery);
				if (gallery != null){
					galleryDelegate.deleteGallery(gallery);
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

