//
//  EventsDetail.h
//  IHO-ASU
//
//  Created by Cynosure on 4/23/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface EventsDetail : NSObject{
    int _eventID;
    NSString *_title;
    NSString *_when;
    NSString *_where;
    NSString *_mapLink;
    NSString *_description;
    NSString *_registerLink;

}

@property (nonatomic,assign)int eventId;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *when;
@property (nonatomic, copy) NSString *where;
@property (nonatomic, copy) NSString *maplink;
@property (nonatomic, copy) NSString *description;
@property (nonatomic, copy) NSString *registerLink;




@property (nonatomic) sqlite3 *asuIHO;

- (id)initWitheventid:(int)eventId title:(NSString *)title when:(NSString *)when where:(NSString *)where mapLink:(NSString *)mapLink description:(NSString *)description registerLink:(NSString *)registerLink;

-(id)initWithTitle:(int)eventId title:(NSString *)title;


@end
