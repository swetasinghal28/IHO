//
//  NSObject+image.m
//  IHO-ASU
//
//  Created by PrashMaya on 11/5/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "NSObject+image.h"

@implementation image:NSObject

@synthesize imageId=_imageId,caption=_caption,image=_image;

-(id) init{
    
    return self;
}

-(id)initWithid:(int)imageId image:(NSData *)image caption:(NSString *)caption{
    
    if ((self = [super init]))
    {
    self.imageId = imageId;
    self.image = image;
    self.caption = caption;
    }
    return self;
}

@end
