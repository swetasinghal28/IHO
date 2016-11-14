//
//  Science.h
//  IHO-ASU
//
//  Created by Cynosure on 4/16/14.
//  Copyright (c) 2014 ASU. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface Science : NSObject{

int _ScienceId;
NSString *_Sciencetitle;
NSString *_ScienceLink;
}

@property (nonatomic,assign)int ScienceId;
@property (nonatomic, copy) NSString *Sciencetitle;
@property (nonatomic, copy) NSString *ScienceLink;

@property (nonatomic) sqlite3 *asuIHO;

- (id)initWithScid:(int)newsId Sciencetitle:(NSString *)Sciencetitle  ScienceLink:(NSString *)ScienceLink;



@end
