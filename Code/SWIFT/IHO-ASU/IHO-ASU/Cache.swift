//
//  Cache.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 4/10/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation

var mainInstance = Cache()

open class Cache{
    
    var newsList:[String : News] = [String : News]()
    var names:[String]=[String]()
    var newsId:[String]=[String]()
    var imageList:[String : Image] = [String : Image]()
    var imageNames:[String]=[String]()
    var imageId:[String]=[String]()
    
    
    func clearNewsCache(){
        self.newsList = [:]
        self.names = []
        self.newsId = []
        
    }
    
    func clearImageCache(){
        self.imageList = [:]
        self.imageNames = []
        self.imageId = []
    }
    
    
}
