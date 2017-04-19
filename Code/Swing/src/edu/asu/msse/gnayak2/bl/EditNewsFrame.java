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
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private JLabel lbldesc;
	private JScrollPane scrollPane;
	private News news;
	private JButton btnSubmit;
	private JButton browseButton;
	private JTextField imageFileButton;
	private JButton addButton;
	NewsDelegate newsDelegate;
	News newNews;
	String filename;
	String encodedImage;
	
	private Pattern pattern;
	private Matcher matcher;
	
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
		tfTitle = new JTextField("",180);
		taDescription = new JTextArea("",180,180);
		tfDate = new JTextField("",180);
		lblReadMore = new JLabel("Link");
		lblTitle = new JLabel("Title");
		lblDate = new JLabel("Date");
	    lbldesc = new JLabel("Description");
		browseButton = new JButton("Browse");
		imageFileButton = new JTextField("",180);
		tfLink = new JTextField("",180);
		
		
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(lblTitle);
		panel.add(tfTitle, "span,pushx,growx,wrap");
		panel.add(lblReadMore);
		panel.add(tfLink, "wrap");
		panel.add(lblDate);
		panel.add(tfDate,"wrap");
	    panel.add(lbldesc);
		panel.add(scrollPane,"wrap");	
		panel.add(browseButton);
		panel.add(imageFileButton, "wrap");
		panel.add(btnSubmit,"wrap");

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
        	 Image newImage = oldImage.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
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
	
	public boolean validate(final String date){
		 String DATE_PATTERN =
		          "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
		  pattern = Pattern.compile(DATE_PATTERN);
   matcher = pattern.matcher(date);

   if(matcher.matches()){
   System.out.println("Matches");
	 matcher.reset();

	 return true;
   }else{
	  JOptionPane.showMessageDialog(tfDate, "Date format is invalid");   
	  return false;
   }
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
		 	System.out.println("BYTE ARRAY"+imageInByte);
		 	baos.close();
           	}catch(IOException e1){
		 		System.out.println(e1.getMessage());
			 	}
            }
            
            boolean isValidDate = validate(tfDate.getText());
//            if(!isValidDate)
//            	System.out.println("Invalid");
//            else 
//            	System.out.println("Valid");
//     			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = new Date();
//				System.out.println(dateFormat.format(date)); 
            if(isValidDate)
            {   if(flag == 1) {
        	   
			   System.out.println("DATE-----" + tfDate.getText());
			   
			   System.out.println(tfTitle.getText()); 
				 newNews = new News(tfTitle.getText(), taDescription.getText(),tfLink.getText(), tfDate.getText(), encodedImage);
        	   }
            
            else {
            	 
 			   System.out.println(taDescription.getText()); 
            	   System.out.println("DATE" + tfDate.getText());
            	   
   				 newNews = new News(tfTitle.getText(), taDescription.getText(),tfLink.getText(), tfDate.getText(), imageFileButton.getText());
   				
            }
           
            newsDelegate.addNews(newNews);
				if (news != null){
					newsDelegate.deleteNews(news);
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
					    if(f!=null){
					    filename = f.getAbsolutePath();
					    imageFileButton.setText(filename);
					    

					    }
					    flag=1;
				}
			});
	}
}
