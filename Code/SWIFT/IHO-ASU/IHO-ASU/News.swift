//
//  News.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/15/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation


//
//  MovieDescription.swift
//  MovieDescCoreData
//  Created by Sweta Singhal on 4/20/16.
//  Copyright © 2016 Sweta Singhal. All rights reserved.
////  @author   Sweta Singhal    mailto:sweta.singhal@asu.edu.
//  @version 4/20/16
//
//  Created by Sweta Singhal on 4/20/16.
//  Copyright © 2016 Sweta Singhal.
//  I provide the instuctor - Prof. Tim Lindquist and the University- Arizona State University with the right to build and evaluate the software package and for making further changes whatsoever is required for the purpose of determining my grade and program assessment.
//

import Foundation

open class News{
    var title: String
    var id: String
    var desc: String
    var image: String
    
    init(title: String, id: String,desc: String,image: String){
        self.title = title
        self.id = id
        self.desc = desc
        self.image = image
    }
    init() {
        self.title = "unknown"
        self.id = "unknown"
        self.desc = "unknown"
        self.image = "unknown"
    }
    
    init (jsonStr: String){
        self.title = ""
        self.id = ""
        self.desc = ""
        self.image = ""
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data, options:.mutableContainers) as?[String:AnyObject]
                self.title = (dict!["title"] as? String)!
                self.id = (dict!["id"] as? String)!
                self.desc = (dict!["desc"] as? String)!
                self.image = (dict!["image"] as? String)!
            }catch {
                print("unable to convert to dictionary")
            }
        }
    }
    
    init(dict: [String:AnyObject]) {
        self.title = dict["title"] as! String
        self.id = dict["id"] as! String
        self.desc = dict["desc"] as! String
        self.image = dict["image"] as! String }
    
    func toJsonString () ->String{
        var jsonStr = "";
        let dict = ["title": title,"id": id,"desc": desc, "image": image]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print (error)
        }
        return jsonStr
    }
    
}
