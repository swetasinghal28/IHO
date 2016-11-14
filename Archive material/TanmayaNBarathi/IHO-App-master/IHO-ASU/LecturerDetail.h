//
//  LecturerDetail.h
//  IHO
//
//  Created by Cynosure on 4/9/14.
//  Copyright (c) 2014 asu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface LecturerDetail : NSObject{
    int _lectID;
    NSData *_image;
    NSString *_name;
    NSString *_bio;
    NSString *_title;
    NSString *_link;
    NSString *_email;
}

@property (nonatomic,assign) int lectID;
@property  (nonatomic, copy) NSString *name;
@property  (nonatomic, copy) NSData *image;
@property (nonatomic, copy) NSString *bio;
@property (nonatomic, copy) NSString *link;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *email;

- (id)initWithLectid:(int)lectID name:(NSString *)name image:(NSData *)image bio:(NSString *)bio title:(NSString *)title link:(NSString *)link email:(NSString *)email;


@end
