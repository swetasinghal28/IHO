//
//  ConnectDetailsViewController.h
//  IHO-ASU
//
//  Created by Cynosure on 4/20/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ConnectDetailsViewController : UITableViewController

- (IBAction)buttonLoc:(id)sender;

@property (weak, nonatomic) IBOutlet UITextView *TextLocation;
@property (weak, nonatomic) IBOutlet UITextView *TextContact;

- (IBAction)officialWebsite:(id)sender;
- (IBAction)buttonCon:(id)sender;

@end
