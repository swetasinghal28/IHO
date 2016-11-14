//
//  FieldViewController.h
//  IHO
//
//  Created by Cynosure on 12/11/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@class LecturerDetailsViewController;

@interface FieldViewController : UITableViewController


@property (strong, nonatomic) NSString *databasePath;
@property (nonatomic) sqlite3 *asuIHO;

- (IBAction)visitNow:(id)sender;

//-(NSArray *) lectDetailInfo;
//-(NSArray *) scDetailsInfo;
-(void)reloadTableView;

@end
