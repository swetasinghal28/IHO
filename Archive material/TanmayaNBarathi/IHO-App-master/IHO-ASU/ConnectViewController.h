//
//  ContactViewController.h
//  IHO
//
//  Created by Cynosure on 12/11/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ConnectViewController : UITableViewController

@property (weak, nonatomic) IBOutlet UIButton *connectFB;

@property (weak, nonatomic) IBOutlet UIButton *connectTW;

- (IBAction)connectFB:(id)sender;
- (IBAction)connectTW:(id)sender;

@end
