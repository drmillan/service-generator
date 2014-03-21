//
// Created by Service-Generator
// Copyright (c) 2014 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Cache.h"

@interface LimitedAgeMemoryCache : NSObject <Cache>
- (instancetype)initWithCache:(id <Cache>)caching;

+ (instancetype)cacheWithCache:(id <Cache>)caching;

@end