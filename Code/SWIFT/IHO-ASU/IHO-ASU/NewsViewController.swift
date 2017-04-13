//
//  NewsViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 2/13/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit
import CoreData

class NewsViewController: UITableViewController {
   
    @IBOutlet var newsTableView: UITableView!
    var urlString:String = ""
    var newsList:[String : News] = [String : News]()
    var names:[String]=[String]()
    var newsId:[String]=[String]()
    var reachability: Reachability = Reachability();
    let dispatch_group = DispatchGroup()
    
    
    
    func loadNewsList(){
        
        print("Loading from Internet")
        
        let url = URL(string:"http://107.170.239.62:3000/newsobjects" )
        
        var task = URLSession.shared.dataTask(with: url!){ (data, response, error) in
            if error != nil
            {
                print("ERROR",error ?? "There is an error")
            }
            else
            {
                print("Loading news Objects")
                
                if let content = data
                {
                    //self.news = [News]()
                    do{
                        //Array
                        let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        // let myJSON = try JSONSerialization.jsonObject(with: data!,options:.mutableContainers) as? [String:AnyObject]
                        
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
                                //self.news?.append(newsObject)
                                self.newsList[newsObject.title] = newsObject
                            }
                        }
                        
                        //print (self.news)
                        mainInstance.newsList = self.newsList;
                        mainInstance.names = self.names;
                        self.newsTableView.reloadData()
                    }
                    catch let error{
                        print(error)
                    }
                }
            }
        }
        task.resume()

        
        
    }
    
    func loadNewsId(){
        let urlId = URL(string:"http://107.170.239.62:3000/newsid" )
      
        dispatch_group.enter()
        var taskUrlId = URLSession.shared.dataTask(with: urlId!){ (data, response, error) in
            if error != nil
            {
                print("ERROR",error ?? "There is an error")
            }
            else
            {
                print("Inside Load News Id function")
                
                self.newsId = [];
                
                if let content = data
                {
                    do{
                        //Array
                        let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                            for news in newsFromJSON{
                                if let newsId = news["id"] as? String{
                                    self.newsId.append(newsId)
                                }
                            }
                        }
                        print("News Id: ", self.newsId)
                    }
                    catch let error{
                        print(error)
                    }
                }
            self.dispatch_group.leave()
            }
        }
        taskUrlId.resume()
    }




    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "News"
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2017"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
        

        let flag = reachability.connectedToNetwork();
        if flag
        {
            
            print("Yes internet connection")
            
            if (!mainInstance.newsList.isEmpty && !mainInstance.names.isEmpty){
                
                print("Calling news Id from internet")
                loadNewsId();
                dispatch_group.wait()
                print("Completed loading news Id from Internet")
                
                print("Cache news Id",mainInstance.newsId)
                print("Internet news Id",self.newsId)
                
                if(mainInstance.newsId == self.newsId){
                
                    print("News List unchanged")
                
                self.newsList = mainInstance.newsList;
                
                self.names = mainInstance.names;
                
                self.newsTableView.reloadData();
                }
                else{
                    
                    print("News List updated")
                    mainInstance.clearNewsCache()
                    loadNewsList();
                    loadNewsId();
                    dispatch_group.wait()
                    mainInstance.newsId = self.newsId
                
                }
                
            }else{
                
                print("Loading from Internet")
                
                mainInstance.clearNewsCache()
                loadNewsList();
                loadNewsId();
                dispatch_group.wait()
                mainInstance.newsId = self.newsId
                
            }
            
            print("News Id from cache: ", mainInstance.newsId)
            
        }else{
            
            print("No internet connection")
            print("Cache value : ", mainInstance.names)
            
            if (!mainInstance.newsList.isEmpty && !mainInstance.names.isEmpty){
                
                print("Loading from Cache")
                
                self.newsList = mainInstance.newsList;
                
                self.names = mainInstance.names;
                
                self.newsTableView.reloadData();
                
            }else{
                
                print("Loading from Local")
                
                do {
                    
                    if let file = Bundle.main.url(forResource: "news", withExtension: "json") {
                        
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
                                //self.news?.append(newsObject)
                                self.newsList[newsObject.title] = newsObject
                            }
                            
                        } else {
                            
                            print("JSON is invalid")
                            
                        }
                        
                    } else {
                        print("no file")
                    }
                    mainInstance.newsList = self.newsList;
                    mainInstance.names = self.names;
                    self.newsTableView.reloadData()
                    
                } catch {
                  print(error.localizedDescription)
                }
            }
        }
        self.newsTableView.reloadData()
        //print("Cache value while exiting: ", mainInstance.names)
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
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
        // #warning Incomplete implementation, return the number of rows
        //print("rows",self.names.count)
        return self.names.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = newsTableView.dequeueReusableCell(withIdentifier: "News Cell", for: indexPath)
        
        // Configure the cell...
        cell.textLabel?.text = self.names[indexPath.row]
        

        

        return cell
    }
 
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "NewsDetail" {
            let viewController:NewsDetailViewController = segue.destination as! NewsDetailViewController
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
