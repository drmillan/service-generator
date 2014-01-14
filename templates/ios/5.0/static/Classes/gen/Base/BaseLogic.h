//
//  BaseLogic.h
//
//  Created by DRM on 22/10/13.
//  Copyright (c) 2013 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LogicFilter.h"

@interface BaseLogic:LogicFilter
-(void) addFilter:(LogicFilter *)filter;
-(void) removeFilter:(LogicFilter *)filter;
-(NSString *) fixURLParameter:(NSString *)param;
@end
