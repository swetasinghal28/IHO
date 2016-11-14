//
//  LecturerDetailsViewController.m
//  IHO
//
//  Created by Cynosure on 4/9/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import "LecturerDetailsViewController.h"
#import "LecturerDetail.h"
#import "UITableViewController+LecturerGallery.h"
#import <MessageUI/MessageUI.h>

@interface LecturerDetailsViewController ()<MFMailComposeViewControllerDelegate>
{
    LecturerDetail *detail;
}
@end

@implementation LecturerDetailsViewController

@synthesize lectID=_lectID,lectImage=_lectImage,bio=_bio,lecttitle=_lecttitle,nameTitle=_nameTitle;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
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

    detail  = nil;
    // Do any additional setup after loading the view.
    NSString *sqLiteDb = [[NSBundle mainBundle] pathForResource:@"asuIHO" ofType:@"db"];
    
    if (sqlite3_open([sqLiteDb UTF8String],&_asuIHO)==SQLITE_OK)
    {
        
       
        detail = [self lectDetail:_lectID];
        
        // Do any additional setup after loading the view.
        if (detail!= nil) {
           
            _lectImage.image=[UIImage imageWithData:detail.image];
            _lecttitle.text = detail.title;
            _bio.text = detail.bio;
            _nameTitle.text = detail.name;
            self.email = detail.email;
            
            
        }
        
    }
    else{
        NSLog(@"Not working");
    }

    self.tableView.separatorColor = [UIColor clearColor];
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


- (IBAction)emailQ:(id)sender {
    MFMailComposeViewController *picker = [[MFMailComposeViewController alloc] init];
    picker.mailComposeDelegate = self;
    
    picker.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};
   
    
    // Set up the recipients.
    NSArray *toRecipients = [NSArray arrayWithObject:detail.email];
    [picker setToRecipients:toRecipients];
    
    
    [self presentViewController:picker animated:YES completion:nil];

    
}


-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    if([[segue identifier] isEqualToString:@"viewLectGallery"]){
        LecturerGallery *getDetails = segue.destinationViewController;
        getDetails.lectEmail = self.email;
        
    }
}

-(void)viewDidLayoutSubviews
{
    [_lecttitle sizeToFit];
    [_bio sizeToFit];
}



- (void)mailComposeController:(MFMailComposeViewController*)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error{
    [self dismissViewControllerAnimated:YES completion:nil];
}


-(LecturerDetail *)lectDetail:(int)letID{
    
    sqlite3_stmt *statement;
    LecturerDetail *newItem = nil;
    NSString *query = [NSString stringWithFormat:@"SELECT LectID,Name,Image,Bio,Title,Link,Email FROM Lecturer WHERE LectID=%d",letID];
    const char *query_stmt = [query UTF8String];
    if(sqlite3_prepare_v2(_asuIHO,query_stmt,-1,&statement,NULL)==SQLITE_OK)
    {
        while(sqlite3_step(statement)==SQLITE_ROW){
            
            int LectID = sqlite3_column_int(statement, 0);
            
            //read data from the result
            NSString *Name =  [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 1)];
            NSData *imgData = [[NSData alloc] initWithBytes:sqlite3_column_blob(statement, 2) length:sqlite3_column_bytes(statement, 2)];
            NSString *Bio = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 3)];
            NSString *title = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 4)];

            NSString *link  = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 5)];
            NSString *email =  [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 6)];
            newItem = [[LecturerDetail alloc] initWithLectid:LectID name:Name image:imgData bio:Bio title:title link:link email:email];
            
        }
       
    }
    sqlite3_finalize(statement);
    
    sqlite3_close(_asuIHO);
    return  newItem;

    
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
 
 - (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
 {
 if(indexPath.row==0)
 return 280;
 else if(indexPath.row==1)
 return _lecttitle.frame.size.height;
 else
 return 189;
 
 }
*/


- (IBAction)linkB:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:detail.link]];
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
