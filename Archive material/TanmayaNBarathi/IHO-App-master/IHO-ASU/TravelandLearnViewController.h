//
//  TravelandLearnViewController.h
//  IHO-ASU
//
//  Created by Cynosure on 4/29/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@interface TravelandLearnViewController : UITableViewController
@property (strong, nonatomic) NSString *databasePath;
@property (nonatomic) sqlite3 *asuIHO;

-(NSArray *) trDetailsInfo;

@end
