//
//  UITableViewController+LecturerList.h
//  IHO-ASU
//
//  Created by PrashMaya on 10/25/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@class LecturerDetailsViewController;

@interface LecturerList:UITableViewController



@property (strong, nonatomic) NSString *databasePath;
@property (nonatomic) sqlite3 *asuIHO;


-(NSArray *) lectDetailInfo;

-(void)reloadTableView;

@end
