//
//  ViewController.h
//  IHO
//
//  Created by Cynosure on 11/13/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MainViewController : UIViewController

@property (weak, nonatomic) IBOutlet UIButton *news;
@property (weak, nonatomic) IBOutlet UIButton *contact;

@property (weak, nonatomic) IBOutlet UIButton *field;
@property (weak, nonatomic) IBOutlet UIButton *gallery;
@property (weak, nonatomic) IBOutlet UIButton *about;

@property (weak, nonatomic) IBOutlet UIButton *donate;

@property (weak, nonatomic) IBOutlet UIWebView *ihoLogo;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *customItem1;

@property (weak, nonatomic) IBOutlet UIBarButtonItem *coR;

@property (weak, nonatomic) IBOutlet UIBarButtonItem *credits;

@end
