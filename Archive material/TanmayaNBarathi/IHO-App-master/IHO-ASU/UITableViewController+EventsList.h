//
//  UITableViewController+EventsList.h
//  IHO-ASU
//
//  Created by PrashMaya on 10/27/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@class EventDetailsViewController;

@interface EventsList : UITableViewController

@property (strong, nonatomic) NSString *databasePath;
@property (nonatomic) sqlite3 *asuIHO;


-(NSArray *) eventsDetailInfo;

@end
