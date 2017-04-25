//
//  Image.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/29/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation

open class Image{
    var title: String
    var id: String
    var image: String
    var order: Double
    init(title: String, id: String,image: String, order:Double){
        self.title = title
        self.id = id
        self.image = image
        self.order = order
    }
    init() {
        self.title = "unknown"
        self.id = "unknown"
        self.image = "unknown"
        self.order = 0
    }
    
    init (jsonStr: String){
        self.title = ""
        self.id = ""
        self.image = ""
        self.order = 0
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data, options:.mutableContainers) as?[String:AnyObject]
                self.title = (dict!["title"] as? String)!
                self.id = (dict!["id"] as? String)!
                self.image = (dict!["image"] as? String)!
                self.order = (dict!["order"] as? Double)!
            }catch {
                print("unable to convert to dictionary")
            }
        }
    }
    
    init(dict: [String:AnyObject]) {
        self.title = dict["title"] as! String
        self.id = dict["id"] as! String
        self.image = dict["image"] as! String
        self.order = dict["order"] as! Double
    }
    
    func toJsonString () ->String{
        var jsonStr = "";
        let dict = ["title": title,"id": id, "image": image, "order":order] as [String : Any]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print (error)
        }
        return jsonStr
    }
    
}
