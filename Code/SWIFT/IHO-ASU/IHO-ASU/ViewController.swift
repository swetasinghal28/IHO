//
//  ViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/7/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var donate: UIButton!
    @IBOutlet weak var about: UIButton!
    @IBOutlet weak var gallery: UIButton!
    @IBOutlet weak var connect: UIButton!
    @IBOutlet weak var field: UIButton!
    @IBOutlet weak var news: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // button sytle
        news.layer.cornerRadius = 15
        about.layer.cornerRadius = 15
        donate.layer.cornerRadius = 15
        gallery.layer.cornerRadius = 15
        connect.layer.cornerRadius = 15
        field.layer.cornerRadius = 15
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

