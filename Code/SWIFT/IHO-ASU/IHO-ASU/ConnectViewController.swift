//
//  ConnectViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/9/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class ConnectViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Connect"
        
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
}
