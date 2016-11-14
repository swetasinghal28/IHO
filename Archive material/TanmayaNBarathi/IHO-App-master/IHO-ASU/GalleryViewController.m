//
//  GalleryViewController.m
//  IHO
//
//  Created by Cynosure on 12/11/13.
//  Copyright (c) 2013 asu. All rights reserved.
//

#import "GalleryViewController.h"
#import <sqlite3.h>
#import "NSObject+image.h"
@interface GalleryViewController ()

{
    NSMutableArray *images;
}

@end

@implementation GalleryViewController


static GalleryViewController *_database;

+ (GalleryViewController*)database {
    if (_database == nil) {
        _database = [[GalleryViewController alloc] init];
    }
    return _database;
}
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
    images = [[NSMutableArray alloc]init];
    image *imageItem;
     NSString *sqLiteDb = [[NSBundle mainBundle] pathForResource:@"asuIHO" ofType:@"db"];

    sqlite3_stmt *statement;
        
        if (sqlite3_open([sqLiteDb UTF8String],&_asuIHO)==SQLITE_OK)
        {
            NSString *query = [NSString stringWithFormat:@"SELECT ImageID,ImageName,ImageCaption FROM Gallery where LectEmail is NULL"];
            const char *query_stmt = [query UTF8String];
            if(sqlite3_prepare_v2(_asuIHO,query_stmt,-1,&statement,NULL)==SQLITE_OK)
            {
                while(sqlite3_step(statement)==SQLITE_ROW)
                {
                if (sqlite3_column_blob(statement, 1))
                {
                    int iD = sqlite3_column_int(statement, 0);
                    //read data from the result
                    NSData *imgData = [[NSData alloc] initWithBytes:sqlite3_column_blob(statement, 1) length:sqlite3_column_bytes(statement, 1)];
                    NSString *caption = [NSString  stringWithUTF8String:(char *)sqlite3_column_text(statement, 2)];
                    
                    imageItem = [[image alloc] initWithid:iD image:imgData caption:caption];
                    
                        if(imgData==nil)
                            NSLog(@"No data present");
                            else
                    //UIImage *img = [[UIImage alloc]initWithData:imgData ];
                        [images addObject:imageItem];
                        
                    
                    NSLog(@"retrieved images");
                    }
                    else
                        NSLog(@"No bytes in data");
                    
                }
            }
            sqlite3_finalize(statement);
        }
        sqlite3_close(_asuIHO);
        
        //return passImages;

        
    
    
    
    //images = [NSArray arrayWithObjects:@"fossils.jpg",@"hadar_landscape.jpg", nil];
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


/*
-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return images.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath

{
    UICollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"Cell" forIndexPath:indexPath];
    
   //UIImageView *imageView = (UIImageView *)[cell viewWithTag:100]
    UIImageView *galleryImage = [[UIImageView alloc] initWithImage:[images objectAtIndex:indexPath.row]];
    //galleryImage.image = [UIImage imageWithData:[images objectAtIndex:indexPath.row]];
    galleryImage.contentMode = UIViewContentModeScaleAspectFit;
    //imageView.image = [images objectAtIndex:indexPath.row];
    //imageView.image = [UIImage imageWithData:[images objectAtIndex:indexPath.row]];
    
    [cell.selectedBackgroundView addSubview:galleryImage];
    return cell;
}



- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([segue.identifier isEqualToString:@"imageSegue"]) {
        NSArray *indexPaths = [self.collectionView indexPathsForSelectedItems];
        GalleryShowImageViewController *destViewController = segue.destinationViewController;
        NSIndexPath *indexPath = [indexPaths objectAtIndex:0];/Users/PrashMaya/IHO/IHO-IOS/IHO-ASU/GalleryViewController.m
        destViewController.eachImage = [images[indexPath.section] objectAtIndex:indexPath.row];
        [self.collectionView deselectItemAtIndexPath:indexPath animated:NO];
    }
}
 */
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
    
    return [images count];
    
}


 - (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
 {
 
 static NSString *CellIdentifier = @"imageCell";
 UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
     
     if(cell==nil){
         cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
     }
     
     image *ImgItem = [images objectAtIndex:indexPath.row];
  //[cell setBackgroundColor:[UIColor colorWithRed:5 green:56 blue:104 alpha:1.0 ]];
     UITextView *caption = (UITextView *)[cell viewWithTag:102];
     caption.text = ImgItem.caption;
     UIImageView *image = (UIImageView *)[cell viewWithTag:101];
     image.image = [ UIImage imageWithData:ImgItem.image];
     image.contentMode = UIViewContentModeScaleAspectFit;
     /*view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, cell.frame.size.width,self.tableView.frame.size.height/1.5)];
     [view addSubview:image];
     [view addSubview:caption];
     [cell addSubview:view];*/
     
     return cell;
 }

/*-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return self.tableView.frame.size.height;
}*/


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
