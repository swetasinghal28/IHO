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
        
        if let path = Bundle.main.path(forResource: "Info", ofType: "plist") {
            let dictRoot = NSDictionary(contentsOfFile: path)
            if let dict = dictRoot {
                urlString = dict["URL"] as! String
            }
        }
        
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Scientists"
        
        let flag = reachability.connectedToNetwork();
        if flag{
            let url = URL(string:urlString + "lectureobjects" )
            
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
                            
                            if let lecturerFromJSON = myJSON as? [[String: AnyObject]]{
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
            
            do{
                if let file = Bundle.main.url(forResource: "lecturer", withExtension: "json") {
                    let data = try Data(contentsOf: file)
                    
                    //Array
                    let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                    
                    if let lecturerFromJSON = myJSON as? [[String: AnyObject]]{
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
    }
    
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return self.names.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = lecturerTableView.dequeueReusableCell(withIdentifier: "Lecturer Cell", for: indexPath)
        
        if(self.names != nil){
        cell.textLabel?.text = self.names[indexPath.row]
        }
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "LecturerDetail" {
            let viewController:LecturerDetailViewController = segue.destination as! LecturerDetailViewController
            let indexPath = self.tableView.indexPathForSelectedRow!
            let title = self.names[(indexPath.row)]
            let lecturerObjectToBeSend = lecturerList[title]! as Lecturer
            viewController.newsTitle = lecturerObjectToBeSend.title
            viewController.newsBio = lecturerObjectToBeSend.bio
            viewController.newsId = lecturerObjectToBeSend.id
            viewController.newsImage = lecturerObjectToBeSend.image
            viewController.newsLink = lecturerObjectToBeSend.link
            viewController.newsName = lecturerObjectToBeSend.name
            viewController.newsEmail = lecturerObjectToBeSend.email
        }
    }
    
}

