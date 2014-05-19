//
//  LimitedAgeDiskCache.h
//
//  Created by Service-Generator
//

#import <Foundation/Foundation.h>
#import "UnlimitedDiskCache.h"

@interface LimitedAgeUnlimitedDiskCache : UnlimitedDiskCache

- (instancetype)initWithDirectory:(NSString*)directory maximumAgeInSeconds:(NSTimeInterval)maximumAge;
+ (instancetype)cacheWithDirectory:(NSString*)directory maximumAgeInSeconds:(NSTimeInterval)maximumAge;

@end
