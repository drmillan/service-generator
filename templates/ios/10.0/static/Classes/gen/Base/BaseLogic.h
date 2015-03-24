//
//  BaseLogic.h
//
//  Created by Service-Generator
//

#import <Foundation/Foundation.h>
#import "LogicFilter.h"
#import "AFSecurityPolicy.h"

typedef NS_OPTIONS(NSUInteger, ServiceGeneratorCacheOption) {

    ServiceGeneratorCacheOptionNoCache = 1 << 0,	/* Ignore cache */
    ServiceGeneratorCacheOptionMemoryOnly = 1 << 1,	/* Disable on-disk cache */
    ServiceGeneratorCacheOptionRefresh = 1 << 2		/* If cached, the success block is called with the cached response and with the final response again. */
};

typedef NS_ENUM(NSInteger, ServiceGeneratorCacheType) {

    ServiceGeneratorCacheTypeNone,		/* The response were not cached. */
    ServiceGeneratorCacheTypeDisk,		/* The response were cached on disk. */
    ServiceGeneratorCacheTypeMemory,	/* The response were cached on memory */
    ServiceGeneratorCacheTypeUnknown	/* The response were cached on an uknown cache system */
};

@interface BaseLogic:LogicFilter
-(void) addFilter:(LogicFilter *)filter;
-(void) removeFilter:(LogicFilter *)filter;
-(NSString *) fixURLParameter:(NSString *)param;
@end
