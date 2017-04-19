package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class EditGallery extends JFrame {

	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JTextField tfOrder;
	private JLabel lblReadMore;
	private JLabel lblOrder;
	private JLabel lblTitle;
	private JScrollPane scrollPane;
	
	private GalleryModel gallery;
	private JButton btnSubmit;
	private JButton browseButton;
	private JTextField imageFileButton;
	private JButton addButton;
	GalleryDelegate galleryDelegate;
	String filename, encodedImage;
	byte[] imageInByte;
	byte[] decoded;
	int flag = 0;
	private Pattern pattern;
	private Matcher matcher;
	/**
	 * Create the frame.
	 */
	public EditGallery(GalleryModel item, GalleryDelegate galleryDelegate) {
        gallery = item;
        this.galleryDelegate = galleryDelegate;
        setUpFrame();
     	populateFileds(gallery);
	}
	
	public EditGallery(GalleryDelegate galleryDelegate) {
	    this.galleryDelegate = galleryDelegate;
	    
		setUpFrame();
	}
	
	public void setUpFrame() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		
		tfTitle = new JTextField("", 20);
		tfOrder = new JTextField("",10);
	
		browseButton = new JButton("Browse");
		imageFileButton = new JTextField("",25);
//		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		lblOrder = new JLabel("Order");
		
		panel = new JPanel();
		panel.add(new JLabel("Image Caption"));
		panel.add(tfTitle, "span,pushx,growx, wrap");
		panel.add(browseButton, "wrap");
		panel.add(imageFileButton, "wrap");
		panel.add(lblOrder);
		panel.add(tfOrder);
		
		panel.add(btnSubmit,"wrap");
		
		
		add(panel);
		pack();
		setVisible(true);
		setActionListenerForButton();
	}
	
	public void populateFileds(GalleryModel gallery) {
		
		tfTitle.setText(gallery.getTitle());

		String order = Integer.toString(gallery.getOrder());
		tfOrder.setText(order);
		imageFileButton.setText(gallery.getImage());

		BufferedImage bufferedImage = convertStringToImage(gallery.getImage());
		ImageIcon imageIcon = null;
		 
        if (bufferedImage != null) { 
        	 imageIcon = new ImageIcon(bufferedImage);
        	 Image oldImage = imageIcon.getImage(); // transform it 
        	 Image newImage = oldImage.getScaledInstance(300, 250,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        	 imageIcon = new ImageIcon(newImage); 
        	 JLabel imageLabel = new JLabel(imageIcon);
        	 panel.add(imageLabel);
        	 
        }
        
        
        setPreferredSize(new Dimension(180,100));


	}
	public boolean validate(final String order){
		 String ORDER_PATTERN =
				 "^\\d+$";
		  pattern = Pattern.compile(ORDER_PATTERN);
 matcher = pattern.matcher(order);

 if(matcher.matches()){
 System.out.println("Matches");
	 matcher.reset();

	 return true;
 }else{
	  JOptionPane.showMessageDialog(tfOrder, "Please enter an integer value for order");   
	  return false;
 }
}
	
	private BufferedImage convertStringToImage(String base64String) {
    	BufferedImage image = null;
    	byte[] imageByte;
    	imageByte = Base64.getDecoder().decode(base64String);
    	ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
    	try {
			image = ImageIO.read(bis);
			bis.close();
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}     	

    }
	
	public void setActionListenerForButton() {
		// delete old news
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
			 	System.out.println("BYTE_________"+imageInByte.toString().length());
			 	encodedImage = Base64.getEncoder().encodeToString(imageInByte);
			 	baos.close();

			 	}catch(IOException e1){
			 		System.out.println(e1.getMessage());
			 	}
			}
            boolean isValid = validate(tfOrder.getText());
            if(isValid)
            {
            if(flag == 1){
			    int ord = Integer.parseInt(tfOrder.getText());
				
				GalleryModel newGallery = new GalleryModel(tfTitle.getText(), encodedImage,ord);

				galleryDelegate.addGallery(newGallery);
            }
            else{
                int ord = Integer.parseInt(tfOrder.getText());
				
				GalleryModel newGallery = new GalleryModel(tfTitle.getText(), imageFileButton.getText(),ord);
				galleryDelegate.addGallery(newGallery);
            }

				if (gallery != null){
					galleryDelegate.deleteGallery(gallery);
					gallery = null;
				}


				
				dispose();
			}
			}
		});
		
		 browseButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					  JFileChooser chooser = new JFileChooser();
					    chooser.showOpenDialog(null);
					    File f = chooser.getSelectedFile();
					    if(f!=null)
					    {
					    filename = f.getAbsolutePath();
					   
					    imageFileButton.setText(filename);
					    }
					    flag=1;

					
				}
			});
	}
}