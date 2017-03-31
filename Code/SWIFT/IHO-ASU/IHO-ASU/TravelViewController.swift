//
//  TravelViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

//
//  FeaturedNewsViewController.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//
import Foundation
import UIKit

class TravelViewControlller: UITableViewController {
    @IBOutlet var featuredNewsTableView: UITableView!
    @IBAction func readMoreLink(_ sender: Any) {
        
        newsLink = "https://iho.asu.edu/outreach/travel"
        
        let url = URL(string: newsLink!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
        
    }
    @IBOutlet weak var nTitle: UILabel!
    @IBOutlet weak var nDesc: UILabel!
    @IBOutlet weak var nImage: UIImageView!
    @IBOutlet weak var readMoreButton: UIButton!
    var newsTitle: String?
    var newsDesc: String?
    var newsId: String?
    var newsImage: String?
    var newsLink: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        //
        //        tableView.rowHeight = UITableViewAutomaticDimension
        //        tableView.estimatedRowHeight = 40
        
        self.navigationItem.title = "Travel+Learn"
        
        
        self.readMoreButton.layer.cornerRadius = 15
        
        print("Inside News detail view controller")
        print("News Title", self.newsTitle ?? "no value")
        print("News Id",self.newsId ?? "no value")
        print("News Desc",self.newsDesc ?? "no value")
        print("News Image", self.newsImage ?? "no value")
        print("News Link", self.newsLink ?? "no value")
        
        self.nTitle.text = "Travel + Learn"
        self.nTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        self.nTitle.numberOfLines = 0
        self.nDesc.text = "IHO’s travel program is different from any other travel experience. This is not just travel—it is immersion in the span of human history, hosted by IHO and ASU scientists who add a richer understanding of your travel destination. At the same time, our travel adventures are designed for fun, excitement, and comfort and take advantage of the best accommodations and sailing vessels available in the industry. We partner with top travel providers who are specialists in exotic areas of the world. Plus, tour leaders, such as Bill Kimbel and Don Johanson, have been accompanying our travelers since the 1980s to Ethiopia, France, Galápagos, Madagascar, South Africa, and Tanzania, as well as being seasoned world travelers themselves. From years of experience, we understand the balance between a great travel experience and a rich learning program. In fact, our trips are so unique and engaging that we have many satisfied repeat travelers."
        self.nDesc.lineBreakMode = NSLineBreakMode.byWordWrapping
        self.nDesc.numberOfLines = 0
        
        if (self.newsImage != nil)
        {
            //base64 string to NSData
            let decodedData = NSData(base64Encoded: self.newsImage!, options: NSData.Base64DecodingOptions(rawValue: 0))
            
            //NSData to UIImage
            self.nImage.image = UIImage(data: decodedData! as Data)
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
        
        
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        //tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
        /* let url = URL(string:"http://107.170.239.62:3000/featureobjects" )
         
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
         //let newsObject = News()
         if let title = news["title"] as? String,let desc = news["desc"] as? String,let id = news["id"] as? String,let image = news["image"] as? String, let link = news["link"] as? String{
         self.newsId = id
         self.newsTitle = title
         self.newsDesc = desc
         self.newsImage = image
         self.newsLink = link
         //self.names.append(newsObject.title)
         //print("News Title ", self.newsTitle)
         
         }
         //self.news?.append(newsObject)
         //self.newsList[newsObject.title] = newsObject
         }
         }
         
         //print (self.news)
         //self.tableView.reloadData()
         // self.newsTableView.reloadData()
         
         
         self.readMoreButton.layer.cornerRadius = 15
         
         print("Inside News detail view controller")
         print("News Title", self.newsTitle ?? "no value")
         print("News Id",self.newsId ?? "no value")
         print("News Desc",self.newsDesc ?? "no value")
         print("News Image", self.newsImage ?? "no value")
         print("News Link", self.newsLink ?? "no value")
         
         self.nTitle.text = self.newsTitle
         self.nTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
         self.nTitle.numberOfLines = 0
         self.nDesc.text = self.newsDesc
         self.nDesc.lineBreakMode = NSLineBreakMode.byWordWrapping
         self.nDesc.numberOfLines = 0
         
         if (self.newsImage != nil)
         {
         //base64 string to NSData
         let decodedData = NSData(base64Encoded: self.newsImage!, options: NSData.Base64DecodingOptions(rawValue: 0))
         
         //NSData to UIImage
         self.nImage.image = UIImage(data: decodedData! as Data)
         }
         
         
         
         // Uncomment the following line to preserve selection between presentations
         // self.clearsSelectionOnViewWillAppear = false
         
         // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
         // self.navigationItem.rightBarButtonItem = self.editButtonItem()
         
         
         //self.featuredNewsTableView.reloadData()
         //self.nTitle.reloadInputViews()
         
         
         }
         catch let error{
         print(error)
         }
         }
         }
         
         
         }
         task.resume()
         */
        
        
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
    
    //    override func numberOfSections(in tableView: UITableView) -> Int {
    //        // #warning Incomplete implementation, return the number of sections
    //        return 3
    //    }
    //
    //    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    //        // #warning Incomplete implementation, return the number of rows
    //        return 0
    //    }
    
    
    
    /*
     override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
     let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)
     
     // Configure the cell...
     
     return cell
     }
     */
    
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


