//
//  ConnectViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/9/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit
import MessageUI

class ConnectViewController: UITableViewController, MFMailComposeViewControllerDelegate {
    @IBAction func twitter(_ sender: Any) {
        
        let url = URL(string: "https://twitter.com/HumanOriginsASU")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    @IBAction func instagram(_ sender: Any) {
        
        let url = URL(string: "https://www.instagram.com/human_origins_asu/")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    
    @IBAction func facebook(_ sender: Any) {
        
        let url = URL(string: "https://www.facebook.com/pages/Lucy-and-ASU-Institute-of-Human-Origins/146317035387367")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Connect"
        
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.tableView.deselectRow(at: self.tableView.indexPathForSelectedRow!, animated: true)
        if indexPath.section == 3 {
            //            var picker = mailComposeController()
            //            picker.mailComposeDelegate = self
            //            picker.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
            //            var emailTitle: String = "ENews Subscription"
            //            // Email Content
            //            var messageBody: String = "Sign me up for E News!"
            //            // To address
            //            var toRecipents: [Any] = ["iho@asu.edu"]
            //            picker.setSubject(emailTitle)
            //            picker.setMessageBody(messageBody, isHTML: false)
            //            picker.setToRecipients(toRecipents)
            //            self.present(picker, animated: true, completion: { _ in })
            
            //print("Going inside \n \n")
            
            if MFMailComposeViewController.canSendMail() {
                print("Can send mail \n \n")
                let mail = MFMailComposeViewController()
                mail.mailComposeDelegate = self
                mail.setToRecipients(["iho@asu.edu"])
                mail.setSubject("ENews Subscription")
                mail.setMessageBody("<p>Sign me up for E News!</p>", isHTML: true)
                
                present(mail, animated: true)
            } else {
                // show failure alert
            }
            
            
        }
    }
    
    
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        controller.dismiss(animated: true)
    }
}


