//
//  TravelandLearnViewController.m
//  IHO-ASU
//
//  Created by Cynosure on 4/29/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "TravelandLearnViewController.h"
#import "NSObject+Travel.h"

@interface TravelandLearnViewController ()
{
    UIImageView *initImage ;
    bool ipad;
    UITextView *text;
   NSArray *trItems;
}
@end

@implementation TravelandLearnViewController

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
    ipad = ([[UIDevice currentDevice]userInterfaceIdiom ] == UIUserInterfaceIdiomPad);
    // Do any additional setup after loading the view.
   // [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.419f blue:0.619f alpha:1.0 ]];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};
    
        trItems = [[NSArray alloc] init];
    NSString *sqLiteDb = [[NSBundle mainBundle] pathForResource:@"asuIHO" ofType:@"db"];
    
    if (sqlite3_open([sqLiteDb UTF8String],&_asuIHO)==SQLITE_OK)
    {
               trItems = [self trDetailsInfo];
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


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 2;
}

 - (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
 {
 #warning Incomplete method implementation.
 // Return the number of rows in the section.
 if(section==0)
 return 1;
 else
 return [trItems count];
 
 }


-(NSArray *) trDetailsInfo{
    NSMutableArray *obj = [[NSMutableArray alloc ] init ];
    sqlite3_stmt *statement;
    
    NSString *query = [NSString stringWithFormat:@"SELECT TravelId,TravelText,TravelLink FROM TravelLearn"];
    const char *query_stmt = [query UTF8String];
    if(sqlite3_prepare_v2(_asuIHO,query_stmt,-1,&statement,NULL)==SQLITE_OK)
    {
        while(sqlite3_step(statement)==SQLITE_ROW){
            
            int TRID = sqlite3_column_int(statement, 0);
            
            //read data from the result
            
            NSString *title =  [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 1)];
            NSString *link  = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 2)];
            Travel *newItem = [[Travel alloc] initWithTrid:TRID Traveltitle:title TravelLink:link];
            if(title==nil)
                NSLog(@"No data present");
            else
                //UIImage *img = [[UIImage alloc]initWithData:imgData ];
                [obj addObject:newItem];
            
            
            NSLog(@"retrieved data");
            
        }
    }
    sqlite3_finalize(statement);
    
    sqlite3_close(_asuIHO);
    return obj;
}


 
 - (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
 {
 
 
 if(indexPath.section==0){
 static NSString *CellIdentifier = @"initCell";
 UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
 [cell setBackgroundColor:[UIColor whiteColor]];
     UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, cell.frame.size.width,self.tableView.frame.size.height/1.5)];
     UILabel *title = [[UILabel alloc] initWithFrame:CGRectMake(10, 5, 150, 30)];
     title.text = @"Travel+Learn" ;
     [title setFont:[UIFont fontWithName:@"Arial-BoldMT" size:16]];
     [title setNumberOfLines:1];
     if(!ipad){
         initImage = [[UIImageView alloc] initWithFrame:CGRectMake(80,40,175,150)];
         text =  [[UITextView alloc] initWithFrame:CGRectMake(10, 190, 304, 100)];
     }
     else{
         view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, cell.frame.size.width,600)];
         initImage = [[UIImageView alloc] initWithFrame:CGRectMake(80,40,200,200)];
         text =  [[UITextView alloc] initWithFrame:CGRectMake(10, 190, self.view.frame.size.width*2.5/3,100)];
     }
     
     initImage.image = [ UIImage imageNamed:@"travellearnimge.jpg"];
     initImage.contentMode = UIViewContentModeScaleAspectFit;
     text.scrollEnabled = YES;
     text.editable = NO;
     text.text = @"IHO’s travel program is different from any other travel experience. This is not just travel—it is immersion in the span of human history, hosted by IHO and ASU scientists who add a richer understanding of your travel destination. At the same time, our travel adventures are designed for fun, excitement, and comfort and take advantage of the best accommodations and sailing vessels available in the industry. We partner with top travel providers who are specialists in exotic areas of the world. Plus, tour leaders, such as Bill Kimbel and Don Johanson, have been accompanying our travelers since the 1980s to Ethiopia, France, Galápagos, Madagascar, South Africa, and Tanzania, as well as being seasoned world travelers themselves. From years of experience, we understand the balance between a great travel experience and a rich learning program. In fact, our trips are so unique and engaging that we have many satisfied repeat travelers.";
     [text setFont:[UIFont fontWithName:@"Arial" size:14]];
     [view addSubview:initImage];
     [view addSubview:title];
     [view addSubview:text];
  [cell addSubview:view];
 return cell;
 }
 else {
 static NSString *CellIdentifier = @"eachCell";
 UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
 Travel *Item = [trItems objectAtIndex:indexPath.row];
 [cell.textLabel setTextColor:[UIColor colorWithWhite:1.0 alpha:1.0]];
 [cell.textLabel setFont:[UIFont fontWithName:@"Arial-BoldMT" size:16]];
 [cell.textLabel setText:[NSString stringWithString:Item.Traveltitle]];
 return cell;
 }
  }
 

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(indexPath.section==0)
        return 330;
    else
        return 44;
}

 -(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
 if (indexPath.section==1){
 [self.tableView deselectRowAtIndexPath:self.tableView.indexPathForSelectedRow animated:YES];
 
 Travel *info  = [trItems objectAtIndex:indexPath.row];
 [[UIApplication sharedApplication] openURL:[NSURL URLWithString:info.TravelLink]];
 }
 
 
 }

-(void)reloadTableView
{
    
    [self reloadTableView];
    
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(indexPath.section==1)
    cell.backgroundColor = [UIColor colorWithRed:(0/255.0) green:(56/255.0) blue:(104/255.0) alpha:1.0];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [self.tableView deselectRowAtIndexPath:self.tableView.indexPathForSelectedRow animated:YES];
}

@end
