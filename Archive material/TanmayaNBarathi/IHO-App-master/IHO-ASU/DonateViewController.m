//
//  DonateViewController.m
//  IHO
//
//  Created by Cynosure on 11/30/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import "DonateViewController.h"

@interface DonateViewController ()

@end

@implementation DonateViewController



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
	// Do any additional setup after loading the view.
    //menubar specifications
  //  [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0 ]];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};

    
    if(!ipad){
    //Assign label
    CGRect textview1 = CGRectMake(10, 0,300, 100);
    UILabel *link_desc1 = [[UILabel alloc] initWithFrame:textview1];
    link_desc1.text  = @"To give directly to IHO online through the ASU Foundation:";
    [link_desc1 setFont:[UIFont fontWithName:@"Arial" size:14] ];
    [link_desc1 setNumberOfLines:3];
    //Assign rest of text
    CGRect textview2 = CGRectMake(10, 120,300,350);
    UITextView *link_desc2 = [[UITextView alloc] initWithFrame:textview2];
    NSString *path= [[NSBundle mainBundle] pathForResource:@"Donate" ofType:@"txt"];
    link_desc2.text  = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding  error:NULL];
    [link_desc2 setFont:[UIFont systemFontOfSize:14] ];
    link_desc2.editable=NO;
   
    
    //add to the view
    [self.view addSubview:link_desc1];
    [self.view addSubview:link_desc2];
    
    }
    
    
    
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


@end
