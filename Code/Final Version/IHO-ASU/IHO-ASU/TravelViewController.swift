//
//  TravelViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//
import Foundation
import UIKit

class TravelViewControlller: UITableViewController {
    @IBOutlet var featuredNewsTableView: UITableView!
    @IBAction func readMoreLink(_ sender: Any) {
        
        newsLink = "https://iho.asu.edu/outreach/travel"
        
        let url = URL(string: newsLink!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
        
    }
    @IBOutlet weak var nTitle: UILabel!
    @IBOutlet weak var nDesc: UILabel!
    @IBOutlet weak var nImage: UIImageView!
    @IBOutlet weak var readMoreButton: UIButton!
    var newsTitle: String?
    var newsDesc: String?
    var newsId: String?
    var newsImage: String?
    var newsLink: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        self.navigationItem.title = "Travel+Learn"
        self.readMoreButton.layer.cornerRadius = 15
        
        self.nTitle.text = "Travel + Learn"
        self.nTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        self.nTitle.numberOfLines = 0
        self.nDesc.text = "IHO’s travel program is different from any other travel experience. This is not just travel—it is immersion in the span of human history, hosted by IHO and ASU scientists who add a richer understanding of your travel destination. At the same time, our travel adventures are designed for fun, excitement, and comfort and take advantage of the best accommodations and sailing vessels available in the industry. We partner with top travel providers who are specialists in exotic areas of the world. Plus, tour leaders, such as Bill Kimbel and Don Johanson, have been accompanying our travelers since the 1980s to Ethiopia, France, Galápagos, Madagascar, South Africa, and Tanzania, as well as being seasoned world travelers themselves. From years of experience, we understand the balance between a great travel experience and a rich learning program. In fact, our trips are so unique and engaging that we have many satisfied repeat travelers."
        self.nDesc.lineBreakMode = NSLineBreakMode.byWordWrapping
        self.nDesc.numberOfLines = 0
        
        if (self.newsImage != nil)
        {
            //base64 string to NSData
            let decodedData = NSData(base64Encoded: self.newsImage!, options: NSData.Base64DecodingOptions(rawValue: 0))
            
            //NSData to UIImage
            self.nImage.image = UIImage(data: decodedData! as Data)
        }
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2017"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
      
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.navigationController?.navigationBar.isHidden=false;
        self.navigationController?.setToolbarHidden(false, animated: false)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setToolbarHidden(true, animated: false)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
 
}


