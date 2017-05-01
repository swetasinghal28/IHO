//
//  NewsViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/13/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit
import CoreData

class EventsViewController: UITableViewController {
    
    @IBOutlet var eventsTableView: UITableView!
    var urlString:String = ""
    var eventsList:[String : Events] = [String : Events]()
    var names:[String]=[String]()
    var reachability: Reachability = Reachability();
    let df = DateFormatter()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let path = Bundle.main.path(forResource: "Info", ofType: "plist") {
            let dictRoot = NSDictionary(contentsOfFile: path)
            if let dict = dictRoot {
                urlString = dict["URL"] as! String
            }
        }
        
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Events"
        df.dateFormat = "MM-dd-yyyy"
        
        let flag = reachability.connectedToNetwork();
        if flag{
            
            let url = URL(string:urlString + "eventobjects" )
            
            let task = URLSession.shared.dataTask(with: url!){ (data, response, error) in
                if error != nil
                {
                    print("ERROR",error ?? "There is an error")
                }
                else
                {
                    if let content = data
                    {
                        do{
                            //Array
                            let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                            
                            if let eventsFromJSON = myJSON as? [[String: AnyObject]]{
                                for event in eventsFromJSON{
                                    let eventsObject = Events()
                                    if let title = event["title"] as? String,let desc = event["desc"] as? String,let id = event["id"] as? String,let date = event["date"] as? String, let place = event["place"] as? String, let location = event["location"] as? String, let regURL = event["regURL"] as? String{
                                        eventsObject.id = id
                                        eventsObject.title = title
                                        eventsObject.desc = desc
                                        eventsObject.place = place
                                        eventsObject.eventDateString = date
                                        eventsObject.regURL = regURL
                                        eventsObject.location = location
                                        self.names.append(eventsObject.title)
                                        
                                    }
                                    self.eventsList[eventsObject.title] = eventsObject
                                }
                            }
                            
                            self.eventsTableView.reloadData()
                        }
                        catch let error{
                            print(error)
                        }
                    }
                }
                
                
            }
            task.resume()

        }else{
                
                do {
                    
                    if let file = Bundle.main.url(forResource: "events", withExtension: "json") {
                        
                        let data = try Data(contentsOf: file)
                        
                        
                        let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        if let eventsFromJSON = myJSON as? [[String: AnyObject]]{
                            for event in eventsFromJSON{
                                let eventsObject = Events()
                                if let title = event["title"] as? String,let desc = event["desc"] as? String,let id = event["id"] as? String,let date = event["date"] as? String, let place = event["place"] as? String, let location = event["location"] as? String, let regURL = event["regURL"] as? String{
                                    eventsObject.id = id
                                    eventsObject.title = title
                                    eventsObject.desc = desc
                                    eventsObject.place = place
                                    eventsObject.regURL = regURL
                                    eventsObject.eventDateString = date
                                    eventsObject.location = location
                                    self.names.append(eventsObject.title)
                                    
                                }
                                self.eventsList[eventsObject.title] = eventsObject
                            }
                        }
                        
                         else {
                            
                            print("JSON is invalid")
                            
                        }
                        
                    } else {
                        print("no file")
                    }
                    self.eventsTableView.reloadData()
                    
                } catch {
                    print(error.localizedDescription)
                }
            
        }
        
        
        
        
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
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.names.count
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = eventsTableView.dequeueReusableCell(withIdentifier: "Events Cell", for: indexPath)
        
        if(self.names != nil){
        cell.textLabel?.text = self.names[indexPath.row]
        }
        
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "EventsDetail" {
            let viewController:EventsDetailViewController = segue.destination as! EventsDetailViewController
            let indexPath = self.tableView.indexPathForSelectedRow!
            let title = self.names[(indexPath.row)]
            let eventsObjectToBeSend = eventsList[title]! as Events
            viewController.eTitle = title
            viewController.eventDesc = eventsObjectToBeSend.desc
            viewController.eventId = eventsObjectToBeSend.id
            viewController.eventPlace = eventsObjectToBeSend.place
            viewController.eventRegURL = eventsObjectToBeSend.regURL
            viewController.eventLocation = eventsObjectToBeSend.location
            viewController.eventDate = eventsObjectToBeSend.eventDateString
        }
    }
    
}
