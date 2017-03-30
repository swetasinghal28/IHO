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
    //var news: [News]? = []
    var newsList:[String : News] = [String : News]()
    var names:[String]=[String]()
//    var NumberOfRows = 0
//    var newsList = [NSManagedObject]()
//    var appDel:AppDelegate?
//    var mContext:NSManagedObjectContext?



    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "News"
        
        // getting URL string from Info.plist
//        if let infoPlist = Bundle.main.infoDictionary {
//            self.urlString = ((infoPlist["ServerURLString"]) as?  String!)!
//            NSLog("The default urlString from info.plist is \(self.urlString)")
//        } else {
//            NSLog("error getting urlString from info.plist")
//        }
        
       let url = URL(string:"http://107.170.239.62:3000/newsobjects" )
        
        let task = URLSession.shared.dataTask(with: url!){ (data, response, error) in
            if error != nil
            {
                print("ERROR",error ?? "There is an error")
            }
            else
            {
                if let content = data
                {
                    //self.news = [News]()
                    do{
                        //Array
                    let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                       // let myJSON = try JSONSerialization.jsonObject(with: data!,options:.mutableContainers) as? [String:AnyObject]
                        
                        print("myJSON", myJSON)
                        
//                        for dict2 in myJSON {
//                            let id = dict2["id"]
//                            let title = dict2["title"]
//                            let desc = dict2["desc"]
//                            println(id)
//                            println(main)
//                            println(description)
//                        }
                        
                        
                        
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
                                    //print(title)
                                
                                }
                                //self.news?.append(newsObject)
                                self.newsList[newsObject.title] = newsObject
                            }
                        }
                        
                        //print (self.news)
                        //self.tableView.reloadData()
                        self.newsTableView.reloadData()

                        
                    }
                    catch let error{
                        print(error)
                    }
                }
            }
            
            
        }
        task.resume()
        
        
//        self.names.removeAll()
//        if let infoPlist = Bundle.main.infoDictionary {
//            self.urlString = ((infoPlist["ServerURLString"]) as?  String!)!
//            NSLog("The default urlString from info.plist is \(self.urlString)")
//        } else {
//            NSLog("error getting urlString from info.plist")
//        }
//        // These vars are used to access the Movie and Genre entities
//        appDel = (UIApplication.shared.delegate as! AppDelegate)
//        mContext = appDel!.managedObjectContext
//        let selectRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "title")
//        do{
//            let results = try mContext!.fetch(selectRequest)
//            newsList = results as! [NSManagedObject]
//            NSLog("Trying to see NewsList\(newsList)")
//        } catch let error as NSError{
//            NSLog("Error selecting all movies: \(error)")
//        }
//        if newsList.count > 0 {
//            for news in newsList{
//                if(news.value(forKey: "title") != nil){
//                    let title:String = (news.value(forKey: "title") as? String)!
//                    self.names.append(title)
//                }
//            }
//        }
        //self.tableview.reloadData()
        
        
        

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
        
        
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
    
//    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
//        let cell = tableView.dequeueReusableCellWithIdentifier("News Cell", forIndexPath: indexPath)
//        
//        cell.textLabel?.text = self.names[indexPath.row]
//        //cell.detailTextLabel?.text = "Testing huhhahhahah"
//        return cell
//    }
   
    
  

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = newsTableView.dequeueReusableCell(withIdentifier: "News Cell", for: indexPath)
        
        // Configure the cell...
        cell.textLabel?.text = self.names[indexPath.row]
        

        

        return cell
    }
 

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
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
