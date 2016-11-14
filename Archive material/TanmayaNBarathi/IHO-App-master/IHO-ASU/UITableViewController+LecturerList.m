//
//  UITableViewController+LecturerList.m
//  IHO-ASU
//
//  Created by PrashMaya on 10/25/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "UITableViewController+LecturerList.h"
#import "LecturerDetail.h"
#import "LecturerDetailsViewController.h"

@interface LecturerList ()
{
    NSArray *lectItems;
}

@end

@implementation LecturerList


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
    // Do any additional setup after loading the view.
    
    // [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0 ]];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};
    
    lectItems = [[NSArray alloc] init];
     NSString *sqLiteDb = [[NSBundle mainBundle] pathForResource:@"asuIHO" ofType:@"db"];
    
    if (sqlite3_open([sqLiteDb UTF8String],&_asuIHO)==SQLITE_OK)
    {
        lectItems = [self lectDetailInfo];
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


-(NSArray *) lectDetailInfo{
    
    NSMutableArray *obj = [[NSMutableArray alloc ] init ];
    sqlite3_stmt *statement;
    
    NSString *query = [NSString stringWithFormat:@"SELECT LectID,Name,Image,Bio,Title,Link,Email FROM Lecturer"];
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
            LecturerDetail *newItem = [[LecturerDetail alloc] initWithLectid:LectID name:Name image:imgData bio:Bio title:title link:link email:email];
            
            //UIImage *img = [[UIImage alloc]initWithData:imgData ];
            [obj addObject:newItem];
            
            
            NSLog(@"retrieved data");
            
        }
    }
    sqlite3_finalize(statement);
    
    return obj;
    
}





- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
#warning Incomplete method implementation.
    // Return the number of rows in the section.
    
        return [lectItems count];
    
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    cell.backgroundColor = [UIColor colorWithRed:(0/255.0) green:(56/255.0) blue:(104/255.0) alpha:1.0];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    
           static NSString *CellIdentifier = @"lectCell";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
        LecturerDetail *Item = [lectItems objectAtIndex:indexPath.row];
        //[cell setBackgroundColor:[UIColor colorWithRed:5 green:56 blue:104 alpha:1.0 ]];
        
        [cell.textLabel setTextColor:[UIColor colorWithWhite:1.0 alpha:1.0]];
        [cell.textLabel setFont:[UIFont fontWithName:@"Arial-BoldMT" size:15]];
        [cell.textLabel setText:[NSString stringWithString:Item.name]];
        [cell.textLabel setNumberOfLines:2];
        return cell;
    }



-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    
    
        NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
        [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
        
        LecturerDetail *info = [lectItems objectAtIndex:indexPath.row];
        LecturerDetailsViewController *getDetails = segue.destinationViewController;
        getDetails.lectID= info.lectID;
        
    
    
    
}


-(void)reloadTableView
{
    
    [self reloadTableView];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [self.tableView deselectRowAtIndexPath:self.tableView.indexPathForSelectedRow animated:YES];
}



@end
