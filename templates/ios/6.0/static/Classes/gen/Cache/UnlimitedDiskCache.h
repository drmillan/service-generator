//
//  UnlimitedDiskCache.h
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Cache.h"

@interface UnlimitedDiskCache : NSObject <Cache>
- (instancetype)initWithDirectory:(NSString *)directory;

+ (instancetype)cacheWithDirectory:(NSString *)directory;

- (NSString *)fileForKey:(id)key;

@end
