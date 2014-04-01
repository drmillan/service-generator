//
//  BaseLogic.h
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LogicFilter.h"
#import "AFSecurityPolicy.h"

@interface BaseLogic:LogicFilter
-(void) addFilter:(LogicFilter *)filter;
-(void) removeFilter:(LogicFilter *)filter;
-(NSString *) fixURLParameter:(NSString *)param;
@end
