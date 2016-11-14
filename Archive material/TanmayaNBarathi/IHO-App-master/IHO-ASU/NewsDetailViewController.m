//
//  NewsDetailViewController.m
//  IHO
//
//  Created by Cynosure on 3/26/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import "NewsDetailViewController.h"
#import "NewsDetail.h"

@interface NewsDetailViewController ()
{
    NewsDetail *details;
}
@end

@implementation NewsDetailViewController
@synthesize newsId=_newsId,newsContent,newsImage;

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
   // [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.419f blue:0.619f alpha:1.0 ]];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};

    
    NSString *sqLiteDb = [[NSBundle mainBundle] pathForResource:@"asuIHO" ofType:@"db"];
    
    if (sqlite3_open([sqLiteDb UTF8String],&_asuIHO)==SQLITE_OK)
    {
         
        details = [self newsDetail:_newsId];
    
    // Do any additional setup after loading the view.
        if (details != nil) {
        newsImage.image = [UIImage imageWithData:details.image];
        [newsContent setFont:[UIFont fontWithName:@"Arial" size:15]];
        [newsContent setText:details.text];
        
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

-(NewsDetail *)newsDetail:(int)newId{
    NewsDetail *item = nil;
    sqlite3_stmt *statement;
    
    NSString *query = [NSString stringWithFormat:@"SELECT NewsId,NewsTitle,NewsImage,NewsText,NewsLink FROM News WHERE NewsId=%d",newId];
    const char *query_stmt = [query UTF8String];
    if(sqlite3_prepare_v2(_asuIHO,query_stmt,-1,&statement,NULL)==SQLITE_OK)
    {
        while(sqlite3_step(statement)==SQLITE_ROW){
            
            int iD = sqlite3_column_int(statement, 0);
            
            //read data from the result
            NSString *title =  [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 1)];
            NSData *imgData = [[NSData alloc] initWithBytes:sqlite3_column_blob(statement, 2) length:sqlite3_column_bytes(statement, 2)];
            NSString *text = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 3)];
            NSString *link  = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 4)];
            item = [[NewsDetail alloc] initWithnewsid:iD newstitle:title image:imgData text:text newslink:link];
            
            break;
        }
    }
    sqlite3_finalize(statement);
    
    sqlite3_close(_asuIHO);
    return item;
    
    
}

- (IBAction)newsLink:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:details.newslink]];
    
}

-(void)viewDidLayoutSubviews
{
    [newsContent sizeToFit];
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

@end
