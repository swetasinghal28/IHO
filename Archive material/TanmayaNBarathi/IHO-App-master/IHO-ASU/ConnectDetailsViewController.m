//
//  ConnectDetailsViewController.m
//  IHO-ASU
//
//  Created by Cynosure on 4/20/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "ConnectDetailsViewController.h"

@interface ConnectDetailsViewController ()

@end

@implementation ConnectDetailsViewController
@synthesize TextContact,TextLocation;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    bool ipad = ([[UIDevice currentDevice]userInterfaceIdiom ] == UIUserInterfaceIdiomPad);
  //  [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0 ]];
    
     self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};
    
    self.TextLocation.selectable = YES;
    TextLocation.font = [UIFont fontWithName:@"Arial" size:14];
    [TextLocation setText:@"Social Sciences Building,Room 103\n951 South Cady Mall\nTempe, AZ 85287-4101"];
    self.TextContact.selectable = YES;
    TextContact.font = [UIFont fontWithName:@"Arial" size:14];
    NSString *contact = @"Phone:480.727.6580\nFax:480.727.6570\nEmail:iho@asu.edu";
    TextContact.text = contact;
    
      // Do any additional setup after loading the view.
    
    self.navigationController.toolbarHidden = NO;
    [self.navigationController.toolbar setTranslucent:NO];
    [UIFont fontWithName:@"Arial-MT" size:15];
    UIBarButtonItem *customItem1 = [[UIBarButtonItem alloc]
                                    initWithTitle:nil style:UIBarButtonItemStyleBordered
                                    target:self action:nil];
    
    UIBarButtonItem *customItem2 = [[UIBarButtonItem alloc]
                                    initWithTitle:@"@IHO ASU 2014" style:UIBarButtonItemStyleDone
                                    target:self action:nil];
    customItem2.tintColor = [UIColor colorWithWhite:1 alpha:1];
    
    
    if(!ipad){
        
        [customItem1 setWidth:55];
        [customItem2 setWidth:90];
        
    }
    else{
        
    }
    
    
    NSArray *toolbarItems = [NSArray arrayWithObjects:
                             customItem1,customItem2,nil];
    
    self.toolbarItems = toolbarItems;

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)officialWebsite:(id)sender {
    NSString *link=@"https://iho.asu.edu/";
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:link]];
    
}

- (IBAction)buttonCon:(id)sender {
    NSString *link=@"https://iho.asu.edu/contact/contact-us";
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:link]];

}

- (IBAction)buttonLoc:(id)sender {
    NSString *link=@"https://iho.asu.edu/contact/contact-us";
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:link]];
}
@end
