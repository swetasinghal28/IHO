//
//  EventsDetail.m
//  IHO-ASU
//
//  Created by Cynosure on 4/23/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import "EventsDetail.h"

@implementation EventsDetail


@synthesize eventId=_eventID,title=_title,maplink=_mapLink,when=_when,where=_where,description=_description;

-(id) init{
    
    return self;
}

-(id)initWitheventid:(int)eventId title:(NSString *)title when:(NSString *)when where:(NSString *)where mapLink:(NSString *)mapLink description:(NSString *)description registerLink:(NSString *)registerLink{
   
    if ((self = [super init]))
    {
        self.eventId = eventId;
        self.title = title;
        self.when = when;
        self.where = where;
        self.maplink = mapLink;
        self.description = description;
        self.registerLink = registerLink;
    }
    return self;
}
-(id) initWithTitle:(int)eventId title:(NSString *)title{
    if ((self = [super init]))
    {
        self.eventId = eventId;
        self.title=title;
    }
    return self;
}

@end
