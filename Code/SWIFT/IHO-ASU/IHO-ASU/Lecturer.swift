//
//  Lecturer.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation

open class Lecturer{
    var title: String
    var name: String
    var id: String
    var bio: String
    var image: String
    var link: String
    var email: String
    
    init(title: String, id: String,bio: String,image: String,link: String,name: String,email: String){
        self.title = title
        self.id = id
        self.bio = bio
        self.image = image
        self.link = link
        self.name = name
        self.email = email
    }
    init() {
        self.title = "unknown"
        self.id = "unknown"
        self.bio = "unknown"
        self.image = "unknown"
        self.link = "unknown"
        self.name = "unknown"
        self.email = "unknown"
    }
    
    init (jsonStr: String){
        self.title = ""
        self.id = ""
        self.bio = ""
        self.image = ""
        self.link = ""
        self.name = ""
        self.email = ""
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data, options:.mutableContainers) as?[String:AnyObject]
                self.title = (dict!["title"] as? String)!
                self.id = (dict!["id"] as? String)!
                self.bio = (dict!["bio"] as? String)!
                self.image = (dict!["image"] as? String)!
                self.link = (dict!["link"] as? String)!
                self.name = (dict!["name"] as? String)!
                self.email = (dict!["email"] as? String)!
            }catch {
                print("unable to convert to dictionary")
            }
        }
    }
    
    init(dict: [String:AnyObject]) {
        self.title = dict["title"] as! String
        self.id = dict["id"] as! String
        self.bio = dict["desc"] as! String
        self.image = dict["image"] as! String
        self.link = dict["link"] as! String
    self.name = dict["name"] as! String
        self.email = dict["email"] as! String
    }
    
    func toJsonString () ->String{
        var jsonStr = "";
        let dict = ["title": title,"id": id,"bio": bio, "image": image, "link": link, "name": name, "email": email]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print (error)
        }
        return jsonStr
    }
    
}
