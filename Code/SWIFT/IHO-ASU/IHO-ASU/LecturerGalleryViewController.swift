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
    
    var urlString:String = "http://107.170.239.62:3000/lectureimages"
    var imageList:[String : Image] = [String : Image]()
    var names:[String]=[String]()
    var lecEmail:String = ""
    var reachability: Reachability = Reachability();
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Gallery"
        let flag = reachability.connectedToNetwork();
        if flag{
            print("Yes internet connection")
            let url = URL(string:urlString + "/"+lecEmail )
            print("url for lecturer", url)
            
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
                            //let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                            
                            let allImagesData = try Data(contentsOf: url!)
                            let allImages = try JSONSerialization.jsonObject(with: allImagesData, options: JSONSerialization.ReadingOptions.allowFragments) as! [String : AnyObject]
                            print("all Images", allImages)
                            
                            //let imagesArray = try JSONSerialization.jsonObject(with: myJSON, options: JSONSerialization.ReadingOptions.allowFragments) as! [String : AnyObject]
                            if let imageFromJSON = allImages["imagesarray"] as? NSArray{
                                print("imageFromJSON", imageFromJSON)
                                for index in 0...imageFromJSON.count-1 {
                                    
                                    let aObject = imageFromJSON[index] as! [String : AnyObject]
                                    print("aObject", aObject)
                                    let imageObject = Image()
                                    imageObject.title = aObject["title"] as! String
                                    print("aObject title", aObject["title"] as! String)
                                    imageObject.id =  aObject["id"] as! String
                                    print("aObject id  ", aObject["id"] as! String)
                                    imageObject.image = aObject["image"] as! String
                                    print("aObject image", aObject["image"] as! String)
                                    
                                    self.names.append(imageObject.title)
                                    print("self. names", self.names)
                                    
                                    self.imageList[imageObject.title] = imageObject
                                    print("self. imageList", self.imageList)
                                }
                            }
                            
                            //print (self.news)
                            //self.tableView.reloadData()
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
        var cell = galleryTableView.dequeueReusableCell(withIdentifier: "LecturerimageCell", for: indexPath)as! LecturerGalleryTableViewCell
        
        // Configure the cell...
        /*  if cell == nil {
         cell = UITableViewCell(style: .default, reuseIdentifier: "imageCell");
         }
         
         let title = self.names[(indexPath.row)]
         let imageObject = imageList[title]! as Image
         
         if (imageObject.image != nil)
         {
         //base64 string to NSData
         let decodedData = NSData(base64Encoded: imageObject.image, options: NSData.Base64DecodingOptions(rawValue: 0))
         
         
         
         //var ImgItem: image? =  UIImage(data: decodedData! as Data)
         //[cell setBackgroundColor:[UIColor colorWithRed:5 green:56 blue:104 alpha:1.0 ]];
         var caption: UITextView? = (cell.viewWithTag(102) as? UITextView)
         caption?.text = imageObject.title
         var image: UIImageView? = (cell.viewWithTag(101) as? UIImageView)
         image?.image = UIImage(data: decodedData! as Data)
         image?.contentMode = .scaleAspectFit
         
         }
         
         */
        
        
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
        
        //cell.textLabel?.text = imageObject.title
        
        //        var caption: UITextView? = (cell.viewWithTag(102) as? UITextView)
        //        caption?.text = imageObject.title
        
        
        
        
        
        
        
        
        return cell
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        return 484.0;//Choose your custom row height
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
    
    
}

