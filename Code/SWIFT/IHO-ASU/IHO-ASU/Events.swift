//
//  Events.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/29/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//
//

import Foundation

open class Events{
    var title: String
    var id: String
    var desc: String
    var date: String
    var location: String
    var place: String
    var regURL: String
    var eventDate: Date
    var eventDateString: String
    
    init(title: String, id: String,desc: String,date: String,location: String,place: String,regURL: String,eventDate: Date, eventDateString: String){
        self.title = title
        self.id = id
        self.desc = desc
        self.date = date
        self.location = location
        self.place = place
        self.regURL = regURL
        self.eventDate = eventDate
        self.eventDateString = eventDateString
    }
    init() {
        self.title = "unknown"
        self.id = "unknown"
        self.desc = "unknown"
        self.date = "unknown"
        self.location = "unknown"
        self.place = "unknown"
        self.regURL = "unknown"
        self.eventDateString = "unknown"
        self.eventDate = Date.init()
    }
    
    init (jsonStr: String){
        self.title = ""
        self.id = ""
        self.desc = ""
        self.date = ""
        self.location = ""
        self.place = ""
        self.regURL = ""
        self.eventDateString = ""
        self.eventDate = Date.init()
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data, options:.mutableContainers) as?[String:AnyObject]
                self.title = (dict!["title"] as? String)!
                self.id = (dict!["id"] as? String)!
                self.desc = (dict!["desc"] as? String)!
                self.date = (dict!["date"] as? String)!
                self.regURL = (dict!["regURL"] as? String)!
                self.place = (dict!["place"] as? String)!
                self.location = (dict!["location"] as? String)!
                self.eventDateString = (dict!["date"] as? String)!
            }catch {
                print("unable to convert to dictionary")
            }
        }
    }
    
    init(dict: [String:AnyObject]) {
        self.title = dict["title"] as! String
        self.id = dict["id"] as! String
        self.desc = dict["desc"] as! String
        self.date = dict["date"] as! String
        self.regURL = dict["regURL"] as! String
        self.place = dict["place"] as! String
        self.location = dict["location"] as! String
        self.eventDateString = dict["date"] as! String
        self.eventDate = Date.init()
    
    }
    
    func toJsonString () ->String{
        var jsonStr = "";
        let dict = ["title": title,"id": id,"desc": desc, "location": location, "place": place, "regURL": regURL, "date": date]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print (error)
        }
        return jsonStr
    }
    
}
