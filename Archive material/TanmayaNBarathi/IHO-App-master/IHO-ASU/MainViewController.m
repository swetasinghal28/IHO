//
//  ViewController.m
//  IHO
//
//  Created by Cynosure on 11/13/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import "MainViewController.h"
#import "CreditDetailsViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

@synthesize  news,field,donate,about,gallery,contact,ihoLogo,customItem1,coR,credits;

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    UIImageView *imageView;
    bool ipad = ([[UIDevice currentDevice]userInterfaceIdiom ] == UIUserInterfaceIdiomPad);
    NSString *htmlpath = nil;
    if(!ipad){
        imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,self.navigationController.navigationBar.frame.size.width/1.5, self.navigationController.navigationBar.frame.size.height/1.5)];
    }
    else
    {
        imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,self.navigationController.navigationBar.frame.size.width/2.5, self.navigationController.navigationBar.frame.size.height/1.5)];
        
    }
    
    imageView.image = [UIImage imageNamed:@"IHOlogoforapp.jpg"];
    UIView *logoView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, imageView.frame.size.width, imageView.frame.size.height)];
    [logoView addSubview:imageView];
    
    
    [self.navigationItem setTitleView:logoView];
    
    news.layer.cornerRadius=15;
    //[news setBackgroundColor:[UIColor colorWithRed:0 green:0.2 blue:0.4 alpha:1.0]];
    about.layer.cornerRadius=15;
    //[about setBackgroundColor:[UIColor colorWithRed:0 green:0.2f blue:0.4f alpha:1.0]];
    donate.layer.cornerRadius=15;
    // [donate setBackgroundColor:[UIColor colorWithRed:0 green:0.2f blue:0.4f alpha:1.0]];
    gallery.layer.cornerRadius=15;
    //[gallery setBackgroundColor:[UIColor colorWithRed:0 green:0.2f blue:0.4f alpha:1.0]];
    contact.layer.cornerRadius=15;
    //[contact setBackgroundColor:[UIColor colorWithRed:0 green:0.2f blue:0.4f alpha:1.0]];
    field.layer.cornerRadius=15;
    // [field setBackgroundColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0]];
    

    
    if(!ipad)
        htmlpath = [[NSBundle mainBundle] pathForResource:@"skull" ofType:@"html"];
    else{
        htmlpath = [[NSBundle mainBundle] pathForResource:@"skulliPad" ofType:@"html"];
        [ihoLogo setFrame:CGRectMake(102,383 , 593, 475)];
    }
    
    NSString *html = [NSString stringWithContentsOfFile:htmlpath encoding:NSUTF8StringEncoding error:nil];
    NSURL *baseURL = [NSURL fileURLWithPath:[NSString stringWithFormat:@"%@", [[NSBundle mainBundle] bundlePath]]];
    ihoLogo.scalesPageToFit = NO;
    [self.ihoLogo loadHTMLString:html baseURL:baseURL];
    ihoLogo.scrollView.scrollEnabled=NO;
    
    //set up toolbar
    self.navigationController.toolbarHidden = NO;
    [self.navigationController.toolbar setTranslucent:NO];
    
    NSShadow *shadow = [NSShadow new];
    [shadow setShadowColor:[UIColor colorWithRed:1.0 green:1.0 blue:1.0 alpha:1.0]];
    [shadow setShadowOffset:CGSizeMake(0, 1)];
   
    NSDictionary *textAttributes = [NSDictionary dictionaryWithObjectsAndKeys:[UIFont fontWithName:@"Arial-MT" size:14],
                                    NSFontAttributeName,[UIColor whiteColor],NSForegroundColorAttributeName,nil] ;
    
    [coR setTitleTextAttributes:textAttributes forState:UIControlStateNormal];
    [coR setTintColor:[UIColor colorWithWhite:1 alpha:1]];
    [credits setTitleTextAttributes:textAttributes forState:UIControlStateNormal];
      [credits setTintColor:[UIColor colorWithWhite:1 alpha:1]];
    
}



/*- (void)awakeFromNib
 {
 isShowingLandscapeView = NO;
 [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
 [[NSNotificationCenter defaultCenter] addObserver:self
 selector:@selector(orientationChanged:)
 name:UIDeviceOrientationDidChangeNotification
 object:nil];
 }
 
 - (void)orientationChanged:(NSNotification *)notification
 {
 UIDeviceOrientation deviceOrientation = [UIDevice currentDevice].orientation;
 if (UIDeviceOrientationIsLandscape(deviceOrientation) &&
 !isShowingLandscapeView)
 {
 [self performSegueWithIdentifier:@"DisplayAlternateView" sender:self];
 isShowingLandscapeView = YES;
 }
 else if (UIDeviceOrientationIsPortrait(deviceOrientation) &&
 isShowingLandscapeView)
 {
 [self dismissViewControllerAnimated:YES completion:nil];
 isShowingLandscapeView = NO;
 }
 }*/



-(void) viewWillAppear:(BOOL)animated{
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}




@end
