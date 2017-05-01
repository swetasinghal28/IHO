//
//  LecturerGalleryViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 4/19/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import UIKit

class LecturerGalleryTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var imageview: UIImageView!
    
    @IBOutlet weak var textlabel: UITextView!
}

class LecturerGalleryViewController: UITableViewController {
    
    @IBOutlet var galleryTableView: UITableView!
    
    var urlString:String = ""
    var imageList:[String : Image] = [String : Image]()
    var names:[String]=[String]()
    var lecEmail:String = ""
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
        
        self.navigationItem.title = "Gallery"
        let flag = reachability.connectedToNetwork();
        if flag{
            let url = URL(string:urlString + "lectureimages" + "/"+lecEmail )
            
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
                            
                            let allImagesData = try Data(contentsOf: url!)
                            let allImages = try JSONSerialization.jsonObject(with: allImagesData, options: JSONSerialization.ReadingOptions.allowFragments) as! [String : AnyObject]
                            
                            if let imageFromJSON = allImages["imagesarray"] as? NSArray{
                                if(imageFromJSON.count != 0){
                                    for index in 0...imageFromJSON.count-1 {
                                        
                                        let aObject = imageFromJSON[index] as! [String : AnyObject]
                                        let imageObject = Image()
                                        imageObject.title = aObject["title"] as! String
                                        imageObject.id =  aObject["id"] as! String
                                        imageObject.image = aObject["image"] as! String
                                        imageObject.order = aObject["order"] as! Double
                                        self.names.append(imageObject.title)
                                        self.imageList[imageObject.title] = imageObject
                                    }
                                }
                            }
                            let sortedArray = self.imageList.sorted { $0.value.order < $1.value.order }
                            self.names = sortedArray.map {$0.0 }
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
        else{
            
            let alert = UIAlertController(title: "No Internet", message: "Please try again later with internet connection", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Close", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
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
        var cell = galleryTableView.dequeueReusableCell(withIdentifier: "LecturerimageCell", for: indexPath)as! LecturerGalleryTableViewCell
        
        
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
        return 484.0;
    }
}

