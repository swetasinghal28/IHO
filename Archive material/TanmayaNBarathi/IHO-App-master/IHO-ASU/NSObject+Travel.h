//
//  NSObject+Travel.h
//  IHO-ASU
//
//  Created by PrashMaya on 10/27/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface Travel:NSObject {
int _TravelId;
NSString *_Traveltitle;
NSString *_TravelLink;
}

@property (nonatomic,assign)int TravelId;
@property (nonatomic, copy) NSString *Traveltitle;
@property (nonatomic, copy) NSString *TravelLink;
@property (nonatomic) sqlite3 *asuIHO;

- (id)initWithTrid:(int)TravelId Traveltitle:(NSString *)Traveltitle  TravelLink:(NSString *)TravelLink;


@end
