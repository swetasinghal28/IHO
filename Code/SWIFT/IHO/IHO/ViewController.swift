//
//  ViewController.swift
//  IHO
//
//  Created by Sweta Singhal on 2/3/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var ihoLogo: UIWebView!
    @IBOutlet weak var news: UIButton!
    @IBOutlet weak var field: UIButton!
    @IBOutlet weak var connect: UIButton!
    @IBOutlet weak var donate: UIButton!
    @IBOutlet weak var gallery: UIButton!
    @IBOutlet weak var about: UIButton!
    @IBOutlet weak var menu: UIWebView!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        var ipad = (UIDevice.currentDevice().userInterfaceIdiom == .Pad)
        var htmlpath: String? = nil
        var imageView = UIImageView(frame: CGRectMake(120, 34, 150, 36))
        imageView.image = UIImage(named: "rsz_iho_h_mg.png")
        self.navigationController!.navigationBar.barTintColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
        self.navigationController!.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.whiteColor()]
        self.navigationItem.titleView = imageView
        news.layer.cornerRadius = 15
        news.backgroundColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
        about.layer.cornerRadius = 15
        about.backgroundColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
        donate.layer.cornerRadius = 15
        donate.backgroundColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
        gallery.layer.cornerRadius = 15
        gallery.backgroundColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
        connect.layer.cornerRadius = 15
        connect.backgroundColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
        field.layer.cornerRadius = 15
        // [field setBackgroundColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0]];
        self.ihoLogo.scalesPageToFit = true
        self.ihoLogo.scrollView.scrollEnabled = false
        self.ihoLogo.scrollView.bounces = false
        if !ipad {
            htmlpath = NSBundle.mainBundle().pathForResource("skull", ofType: "html")!
        }
        else {
            htmlpath = NSBundle.mainBundle().pathForResource("skulliPad", ofType: "html")!
        }
        //var html = NSString.stringWithContentsOfFile(htmlpath, encoding: NSUTF8StringEncoding, error: nil)
        //var baseURL = NSURL.fileURLWithPath("\(NSBundle.mainBundle().bundlePath)")
        ihoLogo.scalesPageToFit = true
        //self.ihoLogo.loadHTMLString(html, baseURL: baseURL)
        self.navigationController!.toolbar.barTintColor = UIColor(red: 0.22, green: 0.42, blue: 0.62, alpha: 1.0)
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

