//
//  AboutViewController.m
//  IHO
//
//  Created by Cynosure on 12/10/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import "AboutViewController.h"

@interface AboutViewController ()

@end

@implementation AboutViewController

@synthesize about_link,mapIt,location,mailing,contact;

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
	// Do any additional setup after loading the view.
    
    bool ipad = ([[UIDevice currentDevice]userInterfaceIdiom ] == UIUserInterfaceIdiomPad);
    
    //menubar specifications
   // [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0 ]];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};

    
    //Declare the webview to display content
    NSString *path = [[NSBundle mainBundle] pathForResource:@"About" ofType:@"html"];
    if (path){
    
        NSData *data=[NSData dataWithContentsOfFile:path];
        [about_link loadData:data MIMEType:@"text/html" textEncodingName:@"convert"  baseURL:nil];
    //[self.view addSubview:about_link];
    }
    
    [self.view bringSubviewToFront:mapIt];
    mapIt.layer.cornerRadius=15;
    //[mapIt setBackgroundColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0] ];
    [mapIt addTarget:self action:@selector(BtnClick:) forControlEvents:UIControlEventTouchUpInside];
    /*
    if(!ipad){
    //display location
    CGRect textview1= CGRectMake(8,325,150,40) ;
    UITextView *address=[[UITextView alloc] initWithFrame:textview1];
    address.font=[UIFont fontWithName:@"Arial" size:8];
    address.text = @"951 South Cady Mall\nSocial Sciences building #103\nASU Tempe Campus";
    address.editable=NO;
    [self.view addSubview:address];
    
    //mapIt button
    
   // //number,email
    CGRect textview2= CGRectMake(8,418,100,30) ;
    UITextView *number=[[UITextView alloc] initWithFrame:textview2];
    number.font=[UIFont fontWithName:@"Arial" size:8];
    number.text = @"480.727.6580\niho@asu.edu";
    number.editable=NO;
    number.scrollEnabled = NO;
    [self.view addSubview:number];
    
    //mailing
    CGRect textview3= CGRectMake(8,484,150,40) ;
    UITextView *Mailing=[[UITextView alloc] initWithFrame:textview3];
    Mailing.font=[UIFont fontWithName:@"Arial" size:8];
    Mailing.text = @"ASU Institute of Human Origins\nPO Box 874101\nTempe, AZ 85287-4101";
    Mailing.editable=NO;
    Mailing.scrollEnabled = NO;
    [self.view addSubview:Mailing];
    
    }
    else{
        location.font=[UIFont fontWithName:@"Arial" size:10];
        location.text = @"951 South Cady Mall\nSocial Sciences building #103\nASU Tempe Campus";
        contact.font=[UIFont fontWithName:@"Arial" size:10];
        contact.text = @"480.727.6580\niho@asu.edu";
        mailing.font=[UIFont fontWithName:@"Arial" size:10];
        mailing.text = @"ASU Institute of Human Origins\nPO Box 874101\nTempe, AZ 85287-4101";
        
    }
    
    */
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


- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType
{
    if ( navigationType == UIWebViewNavigationTypeLinkClicked )
    {
        [[UIApplication sharedApplication] openURL:[request URL]];
        return NO;
    }
    return YES;
}


-(void) BtnClick:(id) sender
{
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"https://maps.google.com/maps?q=951+South+Cady+Mall,+Tempe,+AZ&hl=en&ll=33.420231,-111.930749&spn=0.011158,0.014999&sll=33.41972,-111.934757&sspn=0.002933,0.002591&oq=951+South+Cady+Mall&hnear=951+S+Cady+Mall,+Tempe,+Maricopa,+Arizona+85281&t=m&z=16"]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



-(void)reloadTableView
{
    
    [self reloadTableView];
    
}



- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [self.tableView deselectRowAtIndexPath:self.tableView.indexPathForSelectedRow animated:YES];
}

@end
