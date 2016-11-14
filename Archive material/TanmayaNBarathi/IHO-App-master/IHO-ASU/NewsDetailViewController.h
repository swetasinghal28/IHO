//
//  NewsDetailViewController.h
//  IHO
//
//  Created by Cynosure on 3/26/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@class NewsDetail;

@interface NewsDetailViewController : UITableViewController{
    int _newsId;
}
@property (weak, nonatomic) IBOutlet UIImageView *newsImage;
@property (weak, nonatomic) IBOutlet UILabel *newsContent;
@property (nonatomic,assign) int newsId;

@property (nonatomic) sqlite3 *asuIHO;

-(NewsDetail *)newsDetail:(int)newId;

- (IBAction)newsLink:(id)sender;

@end
