//
//  LecturerListViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.

import UIKit
import CoreData

class LecturerListViewController: UITableViewController {
    @IBOutlet var lecturerTableView: UITableView!
    
    var urlString:String = ""
    var lecturerList:[String : Lecturer] = [String : Lecturer]()
    var names:[String]=[String]()
    var reachability: Reachability = Reachability();
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Scientists"
        
        let flag = reachability.connectedToNetwork();
        if flag{
            print("Yes internet connection")
            let url = URL(string:"http://107.170.239.62:3000/lectureobjects" )
            
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
                            
                            print("myJSON", myJSON)
                            
                            
                            if let lecturerFromJSON = myJSON as? [[String: AnyObject]]{
                                print("lecturerFromJSON", lecturerFromJSON)
                                for lecturer in lecturerFromJSON{
                                    let lecturerObject = Lecturer()
                                    if let title = lecturer["title"] as? String,let bio = lecturer["bio"] as? String,let id = lecturer["id"] as? String,let image = lecturer["image"] as? String, let link = lecturer["link"] as? String, let name = lecturer["name"] as? String, let email = lecturer["email"] as? String, let order = lecturer["order"] as? Double{
                                        lecturerObject.id = id
                                        lecturerObject.title = title
                                        lecturerObject.bio = bio
                                        lecturerObject.image = image
                                        lecturerObject.link = link
                                        lecturerObject.name = name
                                        lecturerObject.email = email
                                        lecturerObject.lecOrder = order
                                        self.names.append(lecturerObject.name)
                                    }
                                    self.lecturerList[lecturerObject.name] = lecturerObject
                                }
                            }
                            let sortedArray = self.lecturerList.sorted { $0.value.lecOrder < $1.value.lecOrder }
                            self.names = sortedArray.map {$0.0 }
                            self.lecturerTableView.reloadData()
                        }
                        catch let error{
                            print(error)
                        }
                    }
                }
                
                
            }
            task.resume()
            
        }
        else{
            print("No internet connection")
            
            print("Loading from Local")
            
            do{
                if let file = Bundle.main.url(forResource: "lecturer", withExtension: "json") {
                    let data = try Data(contentsOf: file)
                    
                    //Array
                    let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                    
                    print("myJSON", myJSON)
                    
                    
                    if let lecturerFromJSON = myJSON as? [[String: AnyObject]]{
                        print("lecturerFromJSON", lecturerFromJSON)
                        for lecturer in lecturerFromJSON{
                            let lecturerObject = Lecturer()
                            if let title = lecturer["title"] as? String,let bio = lecturer["bio"] as? String,let id = lecturer["id"] as? String,let image = lecturer["image"] as? String, let link = lecturer["link"] as? String, let name = lecturer["name"] as? String, let email = lecturer["email"] as? String, let order = lecturer["order"] as? Double{
                                lecturerObject.id = id
                                lecturerObject.title = title
                                lecturerObject.bio = bio
                                lecturerObject.image = image
                                lecturerObject.link = link
                                lecturerObject.name = name
                                lecturerObject.email = email
                                lecturerObject.lecOrder = order
                                self.names.append(lecturerObject.name)
                            }
                            self.lecturerList[lecturerObject.name] = lecturerObject
                        }
                    }
                }else{
                    print("no file")
                }
                let sortedArray = self.lecturerList.sorted { $0.value.lecOrder < $1.value.lecOrder }
                self.names = sortedArray.map {$0.0 }
                self.lecturerTableView.reloadData()
                
            }
                
            catch{
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
        let cell = lecturerTableView.dequeueReusableCell(withIdentifier: "Lecturer Cell", for: indexPath)
        
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
        if segue.identifier == "LecturerDetail" {
            let viewController:LecturerDetailViewController = segue.destination as! LecturerDetailViewController
            let indexPath = self.tableView.indexPathForSelectedRow!
            
            //let moviedata = self.tableView.indexPathForSelectedRow
            
            // let aMovie = movieLib.movies[movieLib.names[indexPath.row]]! as MovieDescription
            let title = self.names[(indexPath.row)]
            let lecturerObjectToBeSend = lecturerList[title]! as Lecturer
            
            //print( "Trying to print selected news object ", newsList[title]?.desc ?? "No value" , title)
            
            
            viewController.newsTitle = title
            viewController.newsBio = lecturerObjectToBeSend.bio
            viewController.newsId = lecturerObjectToBeSend.id
            viewController.newsImage = lecturerObjectToBeSend.image
            viewController.newsLink = lecturerObjectToBeSend.link
            viewController.newsName = lecturerObjectToBeSend.name
            viewController.newsEmail = lecturerObjectToBeSend.email
        }
    }
    
}

