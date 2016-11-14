//
//  NewsDetail.m
//  IHO
//
//  Created by Cynosure on 3/26/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import "NewsDetail.h"
#import <sqlite3.h>

@implementation NewsDetail

@synthesize newslink=_newslink,newstitle=_newstitle,image=_image,text=_text,newsId=_newsId;

-(id) init{
 
    return self;
}

-(id)initWithnewsid:(int)newsId newstitle:(NSString *)newstitle image:(NSData *)image text:(NSString *)text newslink:(NSString *)newslink
{
    
     if ((self = [super init]))
    {
        self.newsId = newsId;
        self.newslink = newslink;
        self.newstitle = newstitle;
        self.text = text;
        self.image = image;
        
    }
    return self;
}




@end
