//
//  AskAnAnthropologistViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/14/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class AskAnAnthropologistViewController: UIViewController, UITextViewDelegate {
    @IBOutlet weak var askButton: UIButton!
    
    
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
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Ask An Anthropologist"
        
        askButton.layer.cornerRadius = 15
        

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func textView(_ textView: UITextView, shouldInteractWith URL: URL, in characterRange: NSRange, interaction: UITextItemInteraction) -> Bool {
        return true
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
