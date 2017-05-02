//
//  ConnectInfoViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/14/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class ConnectInfoViewController: UITableViewController {
    @IBOutlet weak var locationButton: UIButton!
    @IBOutlet weak var contactButton: UIButton!
    @IBOutlet weak var officialButton: UIButton!
    @IBAction func officialWebsite(_ sender: Any) {
        let url = URL(string: "https://iho.asu.edu/")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
        
    }

    @IBAction func ihoLocation(_ sender: Any) {
        let url = URL(string: "https://iho.asu.edu/contact-us")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    @IBAction func ihoContact(_ sender: Any) {
        let url = URL(string: "https://iho.asu.edu/contact-us")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    @IBOutlet weak var textContact: UITextView!
    @IBOutlet weak var textLocation: UITextView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Connect Info"
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
        contactButton.layer.cornerRadius = 15
        officialButton.layer.cornerRadius = 15
        locationButton.layer.cornerRadius = 15
        
        //Button style
        
        
        self.textLocation.isSelectable = true
        textLocation.font = UIFont(name: "Arial", size: CGFloat(14))
        textLocation.text = "Social Sciences Building,Room 103\n951 South Cady Mall\nTempe, AZ 85287-4101"
        
        self.textContact.isSelectable = true
        textContact.font = UIFont(name: "Arial", size: CGFloat(14))
        textContact.text = "Phone:480.727.6580\nFax:480.727.6570\nEmail:iho@asu.edu"

        
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
}
