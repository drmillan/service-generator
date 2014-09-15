//
// Created by Service-Generator
//

#import <Foundation/Foundation.h>
#import "Cache.h"

@interface LimitedAgeMemoryCache : NSObject <Cache>
- (instancetype)initWithCache:(id <Cache>)caching maximumAgeInSeconds:(NSTimeInterval)maximumAge;

+ (instancetype)cacheWithCache:(id <Cache>)caching maximumAgeInSeconds:(NSTimeInterval)maximumAge;

@end