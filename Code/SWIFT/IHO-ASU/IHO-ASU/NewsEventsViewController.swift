//
//  NewsEventsViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/9/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class NewsEventsViewController: UITableViewController {
    
    var newsList:[String : News] = [String : News]()
    var names:[String]=[String]()
    var reachability: Reachability = Reachability();
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "News + Events"
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2017"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
        
        //Featured News
        let flag = reachability.connectedToNetwork();
        if flag{
            
            print("Yes internet connection")
            
            
            let url = URL(string:"http://107.170.239.62:3000/featureobjects" )
            
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
                            
                            print("myJSON", myJSON)
                            
                            if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                                print("newsFromJSON", newsFromJSON)
                                for news in newsFromJSON{
                                    let newsObject = News()
                                    if let title = news["title"] as? String,let desc = news["desc"] as? String,let id = news["id"] as? String,let image = news["image"] as? String, let link = news["link"] as? String{
                                        newsObject.id = id
                                        newsObject.title = title
                                        newsObject.desc = desc
                                        newsObject.image = image
                                        newsObject.link = link
                                        self.names.append(newsObject.title)
                                        
                                    }
                                    self.newsList[newsObject.title] = newsObject
                                }
                            }
                            
                            
                            
                        }
                        catch let error{
                            print(error)
                        }
                    }
                }
                
                
            }
            task.resume()
            
            
            
        }else{
            
            print("No internet connection")
            
            print("Loading from Local")
            
            do {
                
                if let file = Bundle.main.url(forResource: "featuredNews", withExtension: "json") {
                    
                    let data = try Data(contentsOf: file)
                    
                    //let json = try JSONSerialization.jsonObject(with: data, options: [])
                    
                    
                    
                    let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                    
                    //print("myJSON", myJSON)
                    if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                        
                        //print("newsFromJSON", newsFromJSON)
                        
                        for news in newsFromJSON{
                            
                            let newsObject = News()
                            
                            if let title = news["title"] as? String,let desc = news["desc"] as? String,let id = news["id"] as? String,let image = news["image"] as? String, let link = news["link"] as? String{
                                
                                newsObject.id = id
                                
                                newsObject.title = title
                                
                                newsObject.desc = desc
                                
                                newsObject.image = image
                                
                                newsObject.link = link
                                
                                self.names.append(newsObject.title)
                                
                                //print(title)
                            }
                            self.newsList[newsObject.title] = newsObject
                        }
                        
                    } else {
                        
                        print("JSON is invalid")
                        
                    }
                    
                } else {
                    print("no file")
                }
                
            } catch {
                print(error.localizedDescription)
            }
            
        }
        
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.navigationController?.navigationBar.isHidden=false;
        self.navigationController?.navigationBar.barTintColor = UIColor(red: CGFloat((3 / 255.0)), green: CGFloat((36 / 255.0)), blue: CGFloat((83 / 255.0)), alpha: CGFloat(1))
        
        self.navigationController?.navigationBar.tintColor = UIColor.white
        
        self.navigationController!.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName : UIColor.white]
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
  
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "FeaturedNews" {
            let viewController:FeaturedNewsViewController = segue.destination as! FeaturedNewsViewController
            let indexPath = self.tableView.indexPathForSelectedRow!
            
            //let moviedata = self.tableView.indexPathForSelectedRow
            
            // let aMovie = movieLib.movies[movieLib.names[indexPath.row]]! as MovieDescription
            let title = self.names[(indexPath.row)]
            let newsObjectToBeSend = newsList[title]! as News
            
            //print( "Trying to print selected news object ", newsList[title]?.desc ?? "No value" , title)
            
            
            viewController.newsTitle = title
            viewController.newsDesc = newsObjectToBeSend.desc
            viewController.newsId = newsObjectToBeSend.id
            viewController.newsImage = newsObjectToBeSend.image
            viewController.newsLink = newsObjectToBeSend.link
        }
    }
    
}
