//
//  CreditsViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 4/13/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//


import UIKit

class CreditsViewController: UIViewController {
    
    @IBOutlet weak var creditsView: UIWebView!
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Credits"
        creditsView.loadRequest(URLRequest(url: URL(fileURLWithPath: Bundle.main.path(forResource: "CreditDetails", ofType: "html")!)))
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.barTintColor = UIColor(red: CGFloat((3 / 255.0)), green: CGFloat((36 / 255.0)), blue: CGFloat((83 / 255.0)), alpha: CGFloat(1))
        
        self.navigationController?.navigationBar.tintColor = UIColor.white
        
        self.navigationController!.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName : UIColor.white]
        self.navigationController?.setToolbarHidden(false, animated: false)
    }
    
    
}
