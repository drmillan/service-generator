//
//  LimitedAgeDiskCache.h
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UnlimitedDiskCache.h"

@interface LimitedAgeUnlimitedDiskCache : UnlimitedDiskCache

- (instancetype)initWithDirectory:(NSString*)directory maximumAgeInSeconds:(NSTimeInterval)maximumAge;
+ (instancetype)cacheWithDirectory:(NSString*)directory maximumAgeInSeconds:(NSTimeInterval)maximumAge;

@end
