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
    @IBOutlet weak var registerButton: UIButton!
    
    @IBOutlet weak var mapItButton: UIButton!
    @IBAction func mapIt(_ sender: Any) {
        print("Event Location", eventLocation!)
        
        
        eventLocation = eventLocation!.replacingOccurrences(of: " ", with: "%20")
        
        let url = URL(string: eventLocation!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
        
    }
    @IBAction func registerLink(_ sender: Any) {
        let url = URL(string: eventRegURL!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
        
    }
    
    @IBOutlet weak var descDetail: UILabel!
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
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
        mapItButton.layer.cornerRadius = 15
        registerButton.layer.cornerRadius = 15
        
        if(eTitle != nil){
            eventTitle.text = eTitle
            eventTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
            eventTitle.numberOfLines = 0
        }
        if(eventDesc != nil){
            descDetail.text = eventDesc
            descDetail.lineBreakMode = NSLineBreakMode.byWordWrapping
            descDetail.numberOfLines = 0
        }
        
        if(eventPlace != nil){
            eventPlace = eventPlace!.replacingOccurrences(of: "\\n", with: "\n")
            whereDetail.text = eventPlace
            whereDetail.lineBreakMode = NSLineBreakMode.byWordWrapping
            whereDetail.numberOfLines = 0
        }
        
        if(eventDate != nil){
            whenDetail.text = eventDate
            whenDetail.lineBreakMode = NSLineBreakMode.byWordWrapping
            whenDetail.numberOfLines = 0
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
    }
}

