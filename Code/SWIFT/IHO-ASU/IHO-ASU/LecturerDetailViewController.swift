//
//  LecturerDetailViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation
import MessageUI
import UIKit


class LecturerDetailViewController: UITableViewController, MFMailComposeViewControllerDelegate {
    
    
    @IBAction func linkReadMore(_ sender: Any) {
        let url = URL(string: newsLink!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }

    }
    @IBAction func lEmail(_ sender: Any) {
        
        
        
        
        if MFMailComposeViewController.canSendMail() {
            print("Can send mail \n \n")
            let mail = MFMailComposeViewController()
            mail.mailComposeDelegate = self
            mail.setToRecipients([newsEmail!])
            mail.setSubject("Ask a question")
            mail.setMessageBody("<p>Enter your question here</p>" ,isHTML: true)
            
            present(mail, animated: true)
        } else {
            // show failure alert
        }
      
        

    }
    
    @IBAction func lGallery(_ sender: Any) {
    }
    @IBOutlet weak var nImage: UIImageView!
    @IBOutlet weak var buttonReadMore: UIButton!
    @IBOutlet weak var nTitle: UILabel!
    @IBOutlet weak var lecEmail: UIButton!
    
    @IBOutlet weak var gallery: UIButton!
    @IBOutlet weak var nDesc: UILabel!
    @IBOutlet weak var lecTitle: UILabel!
    var newsTitle: String?
    var newsBio: String?
    var newsId: String?
    var newsImage: String?
    var newsLink: String?
    var newsName: String?
    var newsEmail: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        //
        //        tableView.rowHeight = UITableViewAutomaticDimension
        //        tableView.estimatedRowHeight = 40
        
        //self.navigationItem.title = "News Details"
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        //tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
        buttonReadMore.layer.cornerRadius = 15
        gallery.layer.cornerRadius = 15
        lecEmail.layer.cornerRadius = 15
        
        print("Inside Lec detail view controller")
        print("Lec Title", newsTitle ?? "no value")
        print("Lec Title", newsName ?? "no value")
        print("Lec Id",newsId ?? "no value")
        print("Lec Bio",newsBio ?? "no value")
        //print("Lec Image", newsImage ?? "no value")
        print("Lec Link", newsLink ?? "no value")
        
        nTitle.text = newsName
        nTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        nTitle.numberOfLines = 0
        nDesc.text = newsBio
        nDesc.lineBreakMode = NSLineBreakMode.byWordWrapping
        nDesc.numberOfLines = 0
        lecTitle.text = newsTitle
        lecTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        lecTitle.numberOfLines = 0
        
        if (newsImage != nil)
        {
            //base64 string to NSData
            let decodedData = NSData(base64Encoded: newsImage!, options: NSData.Base64DecodingOptions(rawValue: 0))
            
            //NSData to UIImage
            nImage.image = UIImage(data: decodedData! as Data)
        }
        
        
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
        
        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
        
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2017"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
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
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "LecturerGallery" {
            let viewController:LecturerGalleryViewController = segue.destination as! LecturerGalleryViewController
            //let indexPath = self.tableView.indexPathForSelectedRow!
            
            //let moviedata = self.tableView.indexPathForSelectedRow
            
            // let aMovie = movieLib.movies[movieLib.names[indexPath.row]]! as MovieDescription
            //let title = self.names[(indexPath.row)]
            
            
            //print( "Trying to print selected news object ", newsList[title]?.desc ?? "No value" , title)
            
            
            viewController.lecEmail  = self.newsEmail!
        }
    }

    
    // MARK: - Table view data source
    
    //    override func numberOfSections(in tableView: UITableView) -> Int {
    //        // #warning Incomplete implementation, return the number of sections
    //        return 3
    //    }
    //
    //    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    //        // #warning Incomplete implementation, return the number of rows
    //        return 0
    //    }
    
    
    
    /*
     override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
     let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)
     
     // Configure the cell...
     
     return cell
     }
     */
    
    /*
     // Override to support conditional editing of the table view.
     override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the specified item to be editable.
     return true
     }
     */
    
    /*
     // Override to support editing the table view.
     override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
     if editingStyle == .delete {
     // Delete the row from the data source
     tableView.deleteRows(at: [indexPath], with: .fade)
     } else if editingStyle == .insert {
     // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
     }
     }
     */
    
    /*
     // Override to support rearranging the table view.
     override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
     
     }
     */
    
    /*
     // Override to support conditional rearranging of the table view.
     override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the item to be re-orderable.
     return true
     }
     */
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}


