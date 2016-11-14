//
//  Science.m
//  IHO-ASU
//
//  Created by Cynosure on 4/16/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "Science.h"

@implementation Science

@synthesize ScienceId=_ScienceId,ScienceLink=_ScienceLink,Sciencetitle=_Sciencetitle;

-(id) init{
    
    return self;
}

-(id)initWithScid:(int)Id Sciencetitle:(NSString *)Sciencetitle ScienceLink:(NSString *)ScienceLink{
    
    if ((self = [super init]))
    {
        self.ScienceId = Id;
        self.ScienceLink = ScienceLink;
        self.Sciencetitle = Sciencetitle;
    }
    return self;
}

@end
