//
//  ViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/7/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var skullLogo: UIWebView!
   
    @IBOutlet weak var donate: UIButton!
    @IBOutlet weak var about: UIButton!
    @IBOutlet weak var gallery: UIButton!
    @IBOutlet weak var connect: UIButton!
    @IBOutlet weak var field: UIButton!
    @IBOutlet weak var news: UIButton!
    var htmlpath: String? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
//        self.toolbarItems = [flexible,toolbarTitle]
        
        UINavigationBar.appearance().barTintColor = UIColor(red: CGFloat((255.0 / 255.0)), green: CGFloat((255.0 / 255.0)), blue: CGFloat((255.0 / 255.0)), alpha: CGFloat(1))
        
        // button sytle
        news.layer.cornerRadius = 15
        about.layer.cornerRadius = 15
        donate.layer.cornerRadius = 15
        gallery.layer.cornerRadius = 15
        connect.layer.cornerRadius = 15
        field.layer.cornerRadius = 15
        
        // ASU LOGO at navigation bar
        var imageView: UIImageView?
        var ipad: Bool = (UIDevice.current.userInterfaceIdiom == .pad)
        //var htmlpath: String? = nil
        if !ipad {
            imageView = UIImageView(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(self.navigationController!.navigationBar.frame.size.width / 1.6), height: CGFloat(self.navigationController!.navigationBar.frame.size.height / 1)))
        }
        else {
            imageView = UIImageView(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(self.navigationController!.navigationBar.frame.size.width / 2.5), height: CGFloat(self.navigationController!.navigationBar.frame.size.height / 1.5)))
        }
        
        imageView?.image = UIImage(named: "newLogo.jpg")
        var logoView = UIView(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat((imageView?.frame.size.width)!), height: CGFloat((imageView?.frame.size.height)!)))
        logoView.addSubview(imageView!)
        self.navigationItem.titleView = logoView
        
        
//        //tab bar
//        if let font = UIFont(name: "ASU IHO 2017", size: 15) {
//            iho_footer.setTitleTextAttributes([NSFontAttributeName:font], for: .normal)
//        }
        
        // skull LOGO
        htmlpath = Bundle.main.path(forResource: "skull", ofType: "html")
        var html = try? String(contentsOfFile: htmlpath!, encoding: String.Encoding.utf8)
        var baseURL = URL(fileURLWithPath: "\(Bundle.main.bundlePath)")
        skullLogo.scalesPageToFit = false
        self.skullLogo.loadHTMLString(html!, baseURL: baseURL)
        skullLogo.scrollView.isScrollEnabled = false
        
        
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
        self.navigationController?.navigationBar.barTintColor = UIColor(red: CGFloat((255.0 / 255.0)), green: CGFloat((255.0 / 255.0)), blue: CGFloat((255.0 / 255.0)), alpha: CGFloat(1))
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

