//
//  ContactViewController.m
//  IHO
//
//  Created by Cynosure on 12/11/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import "ConnectViewController.h"
#import <MessageUI/MessageUI.h>

@interface ConnectViewController () <MFMailComposeViewControllerDelegate>

@end

@implementation ConnectViewController

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

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
  //  [self.navigationController.navigationBar setBarTintColor:[UIColor colorWithRed:0.22f green:0.42f blue:0.62f alpha:1.0 ]];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};
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

#pragma mark - Table view data source
/*
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 4;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
#warning Incomplete method implementation.
    // Return the number of rows in the section.
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if(indexPath.section==0){
        
        static NSString *CellIdentifier = @"ihoConnect";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
            return cell;
    }
    if(indexPath.section==1){
        static NSString *CellIdentifier = @"beingHuman";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
        return cell;
    }
    if(indexPath.section==2)
    {
        static NSString *CellIdentifier = @"signUp";
        UITableViewCell *cell =  [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
        return cell;
    }
    if(indexPath.section==3)
    {
        static NSString *CellIdentifier = @"socialCell";
        UITableViewCell *cell =  [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
        return cell;
    }
    
    return nil;

}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a story board-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

 */

- (IBAction)connectFB:(id)sender {
    NSString *link=@"https://www.facebook.com/pages/Lucy-and-ASU-Institute-of-Human-Origins/146317035387367";
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:link]];
    
}

- (IBAction)connectTW:(id)sender {
    NSString *link=@"https://twitter.com/LucyASUIHO";
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:link]];
    
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    
    [self.tableView deselectRowAtIndexPath:self.tableView.indexPathForSelectedRow animated:YES];
   
    if(indexPath.section==2){
    MFMailComposeViewController *picker = [[MFMailComposeViewController alloc] init];
    picker.mailComposeDelegate = self;
    
    picker.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName : [UIColor whiteColor]};
    NSString *emailTitle = @"ENews Subscription";
    // Email Content
    NSString *messageBody = @"Sign me up for E News!";
    // To address
    NSArray *toRecipents = [NSArray arrayWithObject:@"iho@asu.edu"];
    
     [picker setSubject:emailTitle];
    [picker setMessageBody:messageBody isHTML:NO];
    [picker setToRecipients:toRecipents];
    
    
    
    [self presentViewController:picker animated:YES completion:nil];
    }
    
    
}

- (void)mailComposeController:(MFMailComposeViewController*)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error{
    [self dismissViewControllerAnimated:YES completion:nil];
}



@end
