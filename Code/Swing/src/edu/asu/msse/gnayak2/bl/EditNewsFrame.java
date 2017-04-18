package edu.asu.msse.gnayak2.bl;

import java.awt.Dimension;
import java.awt.Image;
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

import edu.asu.msse.gnayak2.delegates.NewsDelegate;
import edu.asu.msse.gnayak2.models.News;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

public class EditNewsFrame extends JFrame {

	private JPanel panel;
	private JTextField tfTitle;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JTextField tfDate;
	private JLabel lblReadMore;
	private JLabel lblTitle;
	private JLabel lblDate;

	private JScrollPane scrollPane;
	private News news;
	private JButton btnSubmit;
	private JButton browseButton;
	private JTextField imageFileButton;
	private JButton addButton;
	NewsDelegate newsDelegate;
	String filename;
	String encodedImage;
	
	byte[] imageInByte;
	int flag = 0;
	/**
	 * Create the frame.
	 */
	public EditNewsFrame(News item, NewsDelegate newsDelegate) {
		news = item;
		this.newsDelegate = newsDelegate;
		setUpFrame();
		populateFileds(news);
	}
	
	public void setUpFrame() {
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		tfTitle = new JTextField();
		taDescription = new JTextArea("",20,20);
		tfDate = new JTextField("",20);
		lblReadMore = new JLabel("Link");
		lblTitle = new JLabel("Title");
		lblDate = new JLabel("Date");
	
		browseButton = new JButton("Browse");
		imageFileButton = new JTextField("",80);
		tfLink = new JTextField("",80);
		
		
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(lblTitle, "wrap");
		panel.add(tfTitle, "span,pushx,growx, wrap");
		panel.add(lblReadMore, "wrap");
		panel.add(tfLink, "wrap");
		panel.add(lblDate, "wrap");
		panel.add(tfDate,"wrap");
	
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
	
	public void populateFileds(News news) {
		tfTitle.setText(news.getTitle());
		taDescription.setText(news.getDesc());
		tfLink.setText(news.getLink());
		tfDate.setText(news.getDate());
	    imageFileButton.setText(news.getImage());
	    BufferedImage bufferedImage = convertStringToImage(news.getImage());
		ImageIcon imageIcon = null;
		 
        if (bufferedImage != null) { 
        	 imageIcon = new ImageIcon(bufferedImage);
        	 Image oldImage = imageIcon.getImage(); // transform it 
        	 Image newImage = oldImage.getScaledInstance(180, 180,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        	 imageIcon = new ImageIcon(newImage); 
        	 JLabel imageLabel = new JLabel(imageIcon);
        	 panel.add(imageLabel);
        	 
        }
        
        
        setPreferredSize(new Dimension(180,100));
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
	public EditNewsFrame(NewsDelegate newsdelegate) {
		this.newsDelegate = newsdelegate;
		setUpFrame();
	}
	
	public void setActionListenerForButton() {
		// delete old news
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
            if(flag == 1 ){
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
//     			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = new Date();
//				System.out.println(dateFormat.format(date)); 
            if(flag == 1) {
			   System.out.println("DATE-----" + tfDate.getText());
			   
				
				News newNews = new News(tfTitle.getText(), taDescription.getText(),tfLink.getText(), tfDate.getText(), encodedImage);
				newsDelegate.addNews(newNews);
            }
            else {
            	   System.out.println("DATE-----" + tfDate.getText());
   				News newNews = new News(tfTitle.getText(), taDescription.getText(),tfLink.getText(), tfDate.getText(), imageFileButton.getText());
   				newsDelegate.addNews(newNews);
            }
				if (news != null){
					newsDelegate.deleteNews(news);
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
					    flag=1;

					
				}
			});
	}
}
