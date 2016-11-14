//
//  LucyViewController.h
//  IHO-ASU
//
//  Created by Cynosure on 4/29/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LucyViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIWebView *displayLucy;

-(void) loadView;

@end
