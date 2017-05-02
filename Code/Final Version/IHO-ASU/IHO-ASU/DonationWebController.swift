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
    }
    


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
