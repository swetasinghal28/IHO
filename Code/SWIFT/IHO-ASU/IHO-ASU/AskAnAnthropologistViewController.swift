//
//  AskAnAnthropologistViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/14/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class AskAnAnthropologistViewController: UIViewController, UITextViewDelegate {
    @IBAction func anthropologistLink(_ sender: Any) {
        let url = URL(string: "https://askananthropologist.asu.edu/")!
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }

        
    }
    
    @IBOutlet weak var textView: UITextView!
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Ask An Anthropologist"
        
        
        let linkAttributes = [
            NSLinkAttributeName: NSURL(string: "https://askabiologist.asu.edu")!,
            NSForegroundColorAttributeName: UIColor.blue
            ] as [String : Any]
        
        let attributedString = NSMutableAttributedString(string: "AskAnAnthropologist— inspired by the “wildly” successful AskABiologist.asu. edu—is a resource where students and teachers can  nd information about the science of anthropology. Aimed primarily at the middle school level, the articles and activities are challenging and may be used for higher school levels as well.")
        
        // Set the 'click here' substring to be the link
        attributedString.setAttributes(linkAttributes, range: NSMakeRange(5, 10))
        
        self.textView.delegate = self
        self.textView.attributedText = attributedString

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
