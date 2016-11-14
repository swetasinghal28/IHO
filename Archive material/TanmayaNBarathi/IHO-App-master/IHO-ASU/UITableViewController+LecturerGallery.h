//
//  UITableViewController+LecturerGallery.h
//  IHO-ASU
//
//  Created by PrashMaya on 11/6/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>
@interface LecturerGallery : UITableViewController
@property (nonatomic,assign) NSString *lectEmail;
@property (strong, nonatomic) NSString *databasePath;
@property (nonatomic) sqlite3 *asuIHO;


@end
