//
//  AskAnAnthropologistViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/14/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class AskAnAnthropologistViewController: UIViewController, UITextViewDelegate, UIWebViewDelegate {
    @IBOutlet weak var askButton: UIButton!
    
    @IBOutlet weak var anthro: UIWebView!
    
    @IBAction func visitAnthropologist(_ sender: Any) {
        
        
        let url = URL(string: "https://askananthropologist.asu.edu/")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        anthro.scrollView.isScrollEnabled = false
        self.anthro.delegate = self
        anthro.loadRequest(URLRequest(url: URL(fileURLWithPath: Bundle.main.path(forResource: "askAnthro", ofType: "html")!)))
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        self.navigationItem.title = "Ask An Anthropologist"
        
        askButton.layer.cornerRadius = 15
        

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
    
    @objc(webView:shouldStartLoadWithRequest:navigationType:) func webView(_ anthro: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        
        if navigationType == UIWebViewNavigationType.linkClicked {
            UIApplication.shared.openURL(request.url!)
            return false
        }
        
        return true
        
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
    
    func textView(_ textView: UITextView, shouldInteractWith URL: URL, in characterRange: NSRange, interaction: UITextItemInteraction) -> Bool {
        return true
    }
}
