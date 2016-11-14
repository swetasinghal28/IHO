//
//  LecturerDetailsViewController.h
//  IHO
//
//  Created by Cynosure on 4/9/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@class LecturerDetail;

@interface LecturerDetailsViewController : UITableViewController {
    int _lectID;
    
}
@property (nonatomic,assign) int lectID;
@property (nonatomic) sqlite3 *asuIHO;

@property (weak, nonatomic) IBOutlet UIImageView *lectImage;
@property (weak, nonatomic) IBOutlet UILabel *lecttitle;
@property (weak, nonatomic) IBOutlet UILabel *bio;
@property (weak, nonatomic) IBOutlet UILabel *nameTitle;
@property (weak, nonatomic) NSString *email;

- (IBAction)linkB:(id)sender;
- (IBAction)emailQ:(id)sender;





-(LecturerDetail *) lectDetail:(int)lecID;


@end
