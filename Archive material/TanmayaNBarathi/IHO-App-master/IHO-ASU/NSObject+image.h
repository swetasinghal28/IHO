//
//  NSObject+image.h
//  IHO-ASU
//
//  Created by PrashMaya on 11/5/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface image:
NSObject{
    
    int _imageId;
    NSData *_image;
    NSString *_caption;
}

@property (nonatomic,assign)int imageId;
@property (nonatomic, copy) NSData *image;
@property (nonatomic, copy) NSString *caption;

@property (nonatomic) sqlite3 *asuIHO;

- (id)initWithid:(int)imageId image:(NSData *)image caption:(NSString *)caption;


@end
