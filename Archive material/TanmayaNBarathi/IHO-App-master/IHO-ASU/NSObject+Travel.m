//
//  NSObject+Travel.m
//  IHO-ASU
//
//  Created by PrashMaya on 10/27/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "NSObject+Travel.h"


@implementation Travel
@synthesize TravelId=_TrId,TravelLink=_TravelLink,Traveltitle=_Traveltitle;

-(id) init{
    
    return self;
}

-(id)initWithTrid:(int)Id Traveltitle:(NSString *)Traveltitle TravelLink:(NSString *)TravelLink{
    
    if ((self = [super init]))
    {
        self.TravelId = Id;
        self.TravelLink = TravelLink;
        self.Traveltitle = Traveltitle;
    }
    return self;
}

@end
