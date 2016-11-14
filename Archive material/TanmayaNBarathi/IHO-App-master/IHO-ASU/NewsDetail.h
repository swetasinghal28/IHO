//
//  NewsDetail.h
//  IHO
//
//  Created by Cynosure on 3/26/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface NewsDetail : NSObject{
//int _rowId;
int _newsId;
    NSString *_newstitle;
    NSData *_image;
    NSString *_text;
    NSString *_newslink;

}
@property (nonatomic,assign)int newsId;
@property (nonatomic, copy) NSString *newstitle;
@property (nonatomic, copy) NSData *image;
@property (nonatomic, copy) NSString *text;
@property (nonatomic, copy) NSString *newslink;

@property (nonatomic) sqlite3 *asuIHO;

- (id)initWithnewsid:(int)newsId newstitle:(NSString *)newstitle image:(NSData *)image text:(NSString *)text newslink:(NSString *)newslink;



@end
