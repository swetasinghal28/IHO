package edu.asu.msse.gnayak2.bl;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import edu.asu.msse.gnayak2.delegates.GalleryDelegate;
import edu.asu.msse.gnayak2.library.GalleryLibrary;
import edu.asu.msse.gnayak2.models.GalleryModel;
import edu.asu.msse.gnayak2.models.Identifier;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;

public class Gallery extends JFrame implements GalleryDelegate {
		
//	HashMap<String, News> map = new HashMap<String, News>();
//	ArrayList<String> newsArray = new ArrayList<String>();
	
	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel galleryPanel;
	
	private JButton galleryBackButton;
	
	private JButton galleryButton;
	
	private CardLayout cardLayout;
	
	private JButton viewGalleryButton;
	private JButton deleteGalleryButton;
	private GalleryModel selectedImage;
	private JList<GalleryModel> imageList;
	private DefaultListModel<GalleryModel>  galleryModel;
	private JButton btnAddGallery;
	private GalleryLibrary galleryLibrary;
	GalleryDelegate galleryDelegate;
	
	


	
	
//	private JButton viewEventsButton;
//	private JButton deleteEventsButton;
//	private News selectedEvent;
//	private JList<News> newsList;
//	private DefaultListModel<News>  newsModel;
	
	
	// List components
	
	
	/**
	 * Create the frame.
	 */
	public Gallery() {
	    galleryDelegate = this;
	
		setResizable(false);
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
//	    setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		containerPanel = new JPanel();
		cardLayout = new CardLayout();
		containerPanel.setLayout(cardLayout);
		
		initializeMainPanel();
		initializeGallery();
		
		
		// add panels to container panels and give them id'scomp
		containerPanel.add(mainPanel, "1");
		containerPanel.add(galleryPanel, "2");
	
		
		//default show
		cardLayout.show(containerPanel, "1");
		
		//set action listeners on buttons
		setButtonActionListeners();
				
		add(containerPanel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//set everythign for list
		
	}
	
	public void initializeMainPanel() {
		mainPanel = new JPanel();
		galleryPanel = new JPanel();
		galleryButton = new JButton("Gallery");
		mainPanel.add(galleryButton);			
	}
	
	// intitalize news
	public void initializeGallery() {
		galleryBackButton = new JButton("Back");
		viewGalleryButton = new JButton("View");
		deleteGalleryButton = new JButton("Delete");
		
		btnAddGallery = new JButton("Add");
		

		galleryPanel.setLayout(new MigLayout());
		galleryPanel.add(galleryBackButton);
		galleryPanel.add(viewGalleryButton);
		galleryPanel.add(deleteGalleryButton);
		
		galleryPanel.add(btnAddGallery, "wrap");
		imageList =  new JList<>();
		galleryPanel.add(new JScrollPane(imageList),"span,push,grow, wrap");
		
		galleryModel = new DefaultListModel<>();
		imageList.setModel(galleryModel);
		
		
		galleryLibrary = galleryLibrary.getInstance();
		Set<String> galleryid = galleryLibrary.getKeySet();
		
		for(String id: galleryid) {
			galleryModel.addElement(galleryLibrary.getGallery(id));
		}
		imageList.setCellRenderer(new ImageListRenderer());
		galleryBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});
		
       
	    imageList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedImage = imageList.getSelectedValue();
				//System.out.println(selectedNews.getDesc());
			}
		});
		
		viewGalleryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedImage != null) {
					EditGallery editFrame = new EditGallery(selectedImage, galleryDelegate);
					editFrame.setVisible(true);
				}
			}
		});
		
		deleteGalleryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedImage != null) {
					HTTPConnectionHelper helper = new HTTPConnectionHelper();
					try {
						helper.delete("galleryobjects/" + selectedImage.getId());
						helper.delete("galleryid/" + selectedImage.getId());
						galleryLibrary.deleteGallery(selectedImage.getId());
						galleryModel.removeElement(selectedImage);
						selectedImage = null;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	

	


	public void setButtonActionListeners() {
		btnAddGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditGallery galleryFrame = new EditGallery(galleryDelegate);
				galleryFrame.setVisible(true);
			}
		});

	
		
		// back button action listener
		
		
		
		
		
		// individual page action listener
		
		
		galleryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "2");
			}
		});
		
		
	}	
	

	public void addGallery(GalleryModel gallery) {
		
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.post("galleryobjects", new JSONObject(gallery));
			helper.post("galleryid", new JSONObject(new Identifier(gallery.getId())));
			galleryModel.addElement(gallery);
			galleryLibrary.getInstance().addToLibrary(gallery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteGallery(GalleryModel gallery) {
		// TODO Auto-generated method stub
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			helper.delete("galleryobjects/" + gallery.getId());
			helper.delete("galleryid/" + gallery.getId());
			galleryLibrary.deleteGallery(gallery.getId());
			galleryModel.removeElement(gallery);
			selectedImage = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public class ImageListRenderer extends JLabel implements ListCellRenderer<GalleryModel> {
		 
	    public ImageListRenderer() {
	        setOpaque(true);
	    }
	 
	    @Override
	    public Component getListCellRendererComponent(JList<? extends GalleryModel> list, GalleryModel galleryModel, int index,
	            boolean isSelected, boolean cellHasFocus) {
	    	
	        String code = galleryModel.getTitle();
	        BufferedImage bufferedImage = convertStringToImage(galleryModel.getImage());
	        ImageIcon imageIcon = null;
	        if (bufferedImage != null) {
	        	 imageIcon = new ImageIcon(bufferedImage);
	        	 imageIcon = new ImageIcon(bufferedImage);
	        	 Image oldImage = imageIcon.getImage(); // transform it 
	        	 Image newImage = oldImage.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	        	 imageIcon = new ImageIcon(newImage); 
	        }
	        
	        setIcon(imageIcon);
	        setPreferredSize(new Dimension(180,100));
	        setText(code);
	        setToolTipText(code);

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
	 
	        return this;
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
	} 



	
}