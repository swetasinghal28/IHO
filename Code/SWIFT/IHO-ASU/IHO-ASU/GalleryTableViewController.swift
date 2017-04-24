//
//  GalleryTableViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/29/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class GalleryTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var imageview: UIImageView!
    
    @IBOutlet weak var textlabel: UITextView!
}

class GalleryTableViewController: UITableViewController {
    @IBOutlet var galleryTableView: UITableView!
    
    var urlString:String = ""
    //var news: [News]? = []
    var imageList:[String : Image] = [String : Image]()
    var names:[String]=[String]()
    var imageId:[String]=[String]()
    var reachability: Reachability = Reachability();
    let dispatch_group = DispatchGroup()
    
    func loadImageList(){
        
        print("Loading from Internet")
        
        let url = URL(string:"http://107.170.239.62:3000/galleryobjects" )
        
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
                        if let imageFromJSON = myJSON as? [[String: AnyObject]]{
                            for imageO in imageFromJSON{
                                let imageObject = Image()
                                if let title = imageO["title"] as? String,let id = imageO["id"] as? String,let image = imageO["image"] as? String{
                                    imageObject.id = id
                                    imageObject.title = title
                                    imageObject.image = image
                                    self.names.append(imageObject.title)
                                }
                                self.imageList[imageObject.title] = imageObject
                            }
                        }
                        
                        mainInstance.imageList = self.imageList;
                        mainInstance.imageNames = self.names;
                        self.galleryTableView.reloadData()
                    }
                    catch let error{
                        print(error)
                    }
                }
            }
            
            
        }
        task.resume()
        
    }
    
    func loadImageId(){
        
        let urlId = URL(string:"http://107.170.239.62:3000/galleryid" )
        
        dispatch_group.enter()
        var taskUrlId = URLSession.shared.dataTask(with: urlId!){ (data, response, error) in
            if error != nil
            {
                print("ERROR",error ?? "There is an error")
            }
            else
            {
                print("Inside Load News Id function")
                
                self.imageId = [];
                
                if let content = data
                {
                    do{
                        //Array
                        let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                            for news in newsFromJSON{
                                if let newsId = news["id"] as? String{
                                    self.imageId.append(newsId)
                                }
                            }
                        }
                        print("Image Id: ", self.imageId)
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
        
        self.navigationItem.title = "Gallery"
        
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
            
            if (!mainInstance.imageList.isEmpty && !mainInstance.imageNames.isEmpty){
                
                print("Calling news Id from internet")
                loadImageId();
                dispatch_group.wait()
                print("Completed loading news Id from Internet")
                
                print("Cache news Id",mainInstance.imageId)
                print("Internet news Id",self.imageId)
                
                if(mainInstance.imageId == self.imageId){
                    
                    print("News List unchanged")
                    
                    self.imageList = mainInstance.imageList;
                    
                    self.names = mainInstance.imageNames;
                    
                    self.galleryTableView.reloadData();
                }
                else{
                    
                    print("News List updated")
                    mainInstance.clearImageCache()
                    loadImageList();
                    loadImageId();
                    dispatch_group.wait()
                    mainInstance.imageId = self.imageId
                    
                }
                
            }else{
                
                print("Loading from Internet")
                mainInstance.clearImageCache()
                loadImageList();
                loadImageId();
                dispatch_group.wait()
                mainInstance.imageId = self.imageId
                
            }
            
            print("News Id from cache: ", mainInstance.imageId)
            
        }else{
            
            print("No internet connection")
            print("Cache value : ", mainInstance.names)
            
            if (!mainInstance.imageList.isEmpty && !mainInstance.imageNames.isEmpty){
                
                print("Loading from Cache")
                self.imageList = mainInstance.imageList;
                self.names = mainInstance.imageNames;
                self.galleryTableView.reloadData();
                
            }else{
                
                print("Loading from Local")
                do{
                    if let file = Bundle.main.url(forResource: "gallery", withExtension: "json") {
                    let data = try Data(contentsOf: file)
                        
                        let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        if let imageFromJSON = myJSON as? [[String: AnyObject]]{
                            for imageO in imageFromJSON{
                                let imageObject = Image()
                                if let title = imageO["title"] as? String,let id = imageO["id"] as? String,let image = imageO["image"] as? String{
                                    imageObject.id = id
                                    imageObject.title = title
                                    imageObject.image = image
                                    self.names.append(imageObject.title)
                                }
                                self.imageList[imageObject.title] = imageObject
                            }
                        }
                        
                    }
                    else{
                        print("no file")
                    }
                    mainInstance.imageList = self.imageList;
                    mainInstance.imageNames = self.names;
                    self.galleryTableView.reloadData()
                    
                } catch {
                    print(error.localizedDescription)
                }
            }
        }
        self.galleryTableView.reloadData()
        
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
    
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = galleryTableView.dequeueReusableCell(withIdentifier: "imageCell", for: indexPath)as! GalleryTableViewCell
        cell.textlabel?.text = self.names[indexPath.row]
        
        let title = self.names[(indexPath.row)]
        let imageObject = imageList[title]! as Image
        
        if (imageObject.image != nil)
        {
            //base64 string to NSData
            let decodedData = NSData(base64Encoded: imageObject.image, options: NSData.Base64DecodingOptions(rawValue: 0))
            
            //NSData to UIImage
            cell.imageview?.image = UIImage(data: decodedData! as Data)
            cell.imageview?.contentMode = .scaleAspectFit
        }
        return cell
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        return 484.0;//Choose your custom row height
    }
    
}

