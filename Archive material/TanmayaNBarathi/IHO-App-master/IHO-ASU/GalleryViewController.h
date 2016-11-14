//
//  GalleryViewController.h
//  IHO
//
//  Created by Cynosure on 12/11/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import <sqlite3.h>
#import <UIKit/UIKit.h>


@interface GalleryViewController : UITableViewController

@property (strong, nonatomic) NSString *databasePath;
@property (nonatomic) sqlite3 *asuIHO;



@end
