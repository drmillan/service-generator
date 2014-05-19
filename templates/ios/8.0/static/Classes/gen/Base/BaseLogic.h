//
//  BaseLogic.h
//
//  Created by Service-Generator
//

#import <Foundation/Foundation.h>
#import "LogicFilter.h"
#import "AFSecurityPolicy.h"

@interface BaseLogic:LogicFilter
-(void) addFilter:(LogicFilter *)filter;
-(void) removeFilter:(LogicFilter *)filter;
-(NSString *) fixURLParameter:(NSString *)param;
@end
