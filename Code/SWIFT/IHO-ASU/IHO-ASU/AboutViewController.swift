//
//  AboutViewController.swift
//  IHO-ASU
//
//  Created by Arpit Jaiswal on 2/9/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class AboutViewController: UIViewController {

    @IBAction func mapIt(_ sender: Any) {
        
        UIApplication.shared.openURL(URL(string: "https://maps.google.com/maps?q=951+South+Cady+Mall,+Tempe,+AZ&hl=en&ll=33.420231,-111.930749&spn=0.011158,0.014999&sll=33.41972,-111.934757&sspn=0.002933,0.002591&oq=951+South+Cady+Mall&hnear=951+S+Cady+Mall,+Tempe,+Maricopa,+Arizona+85281&t=m&z=16")!)
    }
    @IBOutlet weak var aboutView: UIWebView!
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        
        
        aboutView.loadRequest(URLRequest(url: URL(fileURLWithPath: Bundle.main.path(forResource: "About", ofType: "html")!)))

        
       
        
      //  aboutView.loadRequest(NSURLRequest(URL : NSURL(fileURLWithpath : NSBundle.mainBundle().pathForResource("About", ofType:"html")!)!))

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
