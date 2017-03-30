//
//  DonationWebController.swift
//  IHO-ASU
//
//  Created by Arpit Jaiswal on 2/8/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit
import SafariServices

class DonationWebController: UIViewController {
    

    @IBOutlet weak var openWeb: UIWebView!
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let url = NSURL(string : "https://iho.asu.edu/support-iho")!
        let request = NSURLRequest(url : url as URL)
        openWeb.loadRequest(request as URLRequest)
        
       
        
        // Do any additional setup after loading the view.
    }
    


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
