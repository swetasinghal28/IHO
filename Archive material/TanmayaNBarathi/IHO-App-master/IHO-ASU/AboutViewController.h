//
//  AboutViewController.h
//  IHO
//
//  Created by Cynosure on 12/10/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AboutViewController : UITableViewController<UIWebViewDelegate>

@property (weak, nonatomic) IBOutlet UIWebView *about_link;

@property (weak, nonatomic) IBOutlet UIButton *mapIt;
@property (weak, nonatomic) IBOutlet UITextView *location;
@property (weak, nonatomic) IBOutlet UITextView *contact;

@property (weak, nonatomic) IBOutlet UITextView *mailing;

@end
