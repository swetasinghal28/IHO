//
//  NewScienceViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation
import UIKit


class NewScienceViewController: UITableViewController {
    
    @IBOutlet var newScienceTableView: UITableView!
    var urlString:String = ""
    var newsList:[String : Science] = [String : Science]()
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
        
        self.navigationItem.title = "Science"
        
        let flag = reachability.connectedToNetwork();
        if flag{
            let url = URL(string:urlString + "newscienceobjects" )
            
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
                            if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                                for news in newsFromJSON{
                                    let newsObject = Science()
                                    if let title = news["title"] as? String,let id = news["id"] as? String,let link = news["link"] as? String, let scienceOrder = news["order"] as? Double{
                                        newsObject.id = id
                                        newsObject.title = title
                                        newsObject.scienceOrder = scienceOrder
                                        newsObject.link = link
                                        self.names.append(newsObject.title)
                                    }
                                    self.newsList[newsObject.title] = newsObject
                                }
                            }
                            let sortedArray = self.newsList.sorted { $0.value.scienceOrder < $1.value.scienceOrder }
                            self.names = sortedArray.map {$0.0 }
                            self.newScienceTableView.reloadData()
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
                if let file = Bundle.main.url(forResource: "newscience", withExtension: "json") {
                    
                    let data = try Data(contentsOf: file)
                    let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                    
                    if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                        for news in newsFromJSON{
                            let newsObject = Science()
                            if let title = news["title"] as? String,let desc = news["desc"] as? String,let id = news["id"] as? String,let link = news["link"] as? String{
                                newsObject.id = id
                                newsObject.title = title
                                newsObject.desc = desc
                                newsObject.link = link
                                self.names.append(newsObject.title)
                            }
                            self.newsList[newsObject.title] = newsObject
                        }
                    }
                    else {
                        
                        print("JSON is invalid")
                        
                    }
                    
                    
                    
                }else {
                    print("no file")
                }
                self.newScienceTableView.reloadData()
                
                
            }catch {
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
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.tableView.deselectRow(at: self.tableView.indexPathForSelectedRow!, animated: true)
        let title = self.names[(indexPath.row)]
        let scienceObjectToBeSend = newsList[title]! as Science
        
        
        let url = URL(string: scienceObjectToBeSend.link)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = newScienceTableView.dequeueReusableCell(withIdentifier: "News Cell", for: indexPath)
        
        if(self.names != nil){
        cell.textLabel?.text = self.names[indexPath.row]
        }
        return cell
    }
}
