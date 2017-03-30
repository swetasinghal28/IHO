//
//  EventsDetailViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/29/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation
import UIKit

class EventsDetailViewController: UITableViewController {
    
    @IBAction func registerLink(_ sender: Any) {
        
        let url = URL(string: eventRegURL!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }

        
    }
    @IBOutlet weak var descDetail: UILabel!
    @IBAction func locationDetail(_ sender: Any) {
    }
    @IBOutlet weak var whereDetail: UILabel!
    @IBOutlet weak var whenDetail: UILabel!
    @IBOutlet weak var eventTitle: UILabel!
    
    var eTitle: String?
    var eventDesc: String?
    var eventId: String?
    var eventLocation: String?
    var eventRegURL: String?
    var eventDate: String?
    var eventPlace: String?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        //
        //        tableView.rowHeight = UITableViewAutomaticDimension
        //        tableView.estimatedRowHeight = 40
        
        //self.navigationItem.title = "News Details"
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        //tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
        //registerLink.layer.cornerRadius = 15
        //locationDetail.layer.cornerRadius = 15
        
        print("Inside News detail view controller")
        print("News Title", eTitle ?? "no value")
        print("News Id",eventId ?? "no value")
        print("News Desc",eventDesc ?? "no value")
        print("News Image", eventLocation ?? "no value")
        print("News Link", eventRegURL ?? "no value")
        
        eventTitle.text = eTitle
        eventTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        eventTitle.numberOfLines = 0
        descDetail.text = eventDesc
        descDetail.lineBreakMode = NSLineBreakMode.byWordWrapping
        descDetail.numberOfLines = 0
        
        whereDetail.text = eventPlace
        whereDetail.lineBreakMode = NSLineBreakMode.byWordWrapping
        whereDetail.numberOfLines = 0
        
        whenDetail.text = eventDate
        whenDetail.lineBreakMode = NSLineBreakMode.byWordWrapping
        whenDetail.numberOfLines = 0
        
    
        
        
        
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

