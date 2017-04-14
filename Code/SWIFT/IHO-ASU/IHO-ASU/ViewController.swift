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
        self.navigationController?.navigationBar.frame.size.height=50;
        var imageView: UIImageView?
        var ipad: Bool = (UIDevice.current.userInterfaceIdiom == .pad)
        //var htmlpath: String? = nil
//        if !ipad {
//            //imageView = UIImageView(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(self.navigationController!.navigationBar.frame.size.width / 1), height: CGFloat(self.navigationController!.navigationBar.frame.size.height-10)))
//            imageView=UIImageView(frame: (self.navigationController?.navigationBar.bounds)!);
//        }
//        else {
//            imageView = UIImageView(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(self.navigationController!.navigationBar.frame.size.width / 2.5), height: CGFloat(self.navigationController!.navigationBar.frame.size.height / 1.5)))
//        }
//        
//        imageView?.image = UIImage(named: "newLogo.jpg")
//        imageView?.contentMode=UIViewContentMode.scaleAspectFit;
//        var logoView = UIView(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat((imageView?.frame.size.width)!), height: CGFloat((imageView?.frame.size.height)!)))
//        logoView.addSubview(imageView!)
//        self.navigationItem.titleView = logoView
        
        
//        //tab bar
//        if let font = UIFont(name: "ASU IHO 2017", size: 15) {
//            iho_footer.setTitleTextAttributes([NSFontAttributeName:font], for: .normal)
//        }
        
        // skull LOGO
        htmlpath = Bundle.main.path(forResource: "skull", ofType: "html")
        var html = try? String(contentsOfFile: htmlpath!, encoding: String.Encoding.utf8)
        var baseURL = URL(fileURLWithPath: "\(Bundle.main.bundlePath)")
       // skullLogo.scalesPageToFit = false
        self.skullLogo.loadHTMLString(html!, baseURL: baseURL)
        skullLogo.scrollView.isScrollEnabled = false
        
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(200), height: CGFloat(21)))
        label.text = "ASU IHO 2017"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        
        
        let creditsButton = UIButton(frame: CGRect(x: CGFloat(-200), y: CGFloat(0), width: CGFloat(60), height: CGFloat(21)))
        //let btn: UIButton = UIButton(frame: CGRect(x: 100, y: 400, width: 100, height: 50))
        creditsButton.backgroundColor = nil
            
            UIColor(red: CGFloat((0 / 255.0)), green: CGFloat((51 / 255.0)), blue: CGFloat((102 / 255.0)), alpha: CGFloat(1))
        creditsButton.setTitle("Credits", for: .normal)
        //creditsButton.titleColor(for: <#T##UIControlState#>)
        creditsButton.addTarget(self, action: #selector(buttonAction), for: .touchUpInside)
        creditsButton.tag = 1
        self.view.addSubview(creditsButton)
        //creditsB = creditsButton
        
        let toolbarButton = UIBarButtonItem(customView: creditsButton)
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle,toolbarButton]
    }
    
    
    func buttonAction(sender: UIButton!) {
        var btnsendtag: UIButton = sender
        if btnsendtag.tag == 1 {
            //do anything here
            print("Credits button pressed")
            self.performSegue(withIdentifier: "CreditsSeque", sender: self)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        //self.navigationController?.navigationBar.frame.size.height = 500
        self.navigationController?.navigationBar.barTintColor = UIColor(red: CGFloat((255.0 / 255.0)), green: CGFloat((255.0 / 255.0)), blue: CGFloat((255.0 / 255.0)), alpha: CGFloat(1))
        self.navigationController?.setToolbarHidden(false, animated: false)
        self.navigationController?.navigationBar.isHidden=true
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

