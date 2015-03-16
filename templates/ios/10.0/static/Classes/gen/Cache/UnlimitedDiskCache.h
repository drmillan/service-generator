//
//  UnlimitedDiskCache.h
//
//  Created by Service-Generator
//

#import <Foundation/Foundation.h>
#import "Cache.h"

@interface UnlimitedDiskCache : NSObject <Cache>
- (instancetype)initWithDirectory:(NSString *)directory;

+ (instancetype)cacheWithDirectory:(NSString *)directory;

- (NSString *)fileForKey:(id)key;

@end
