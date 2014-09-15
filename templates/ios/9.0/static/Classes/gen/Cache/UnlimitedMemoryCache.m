//
// Created by Service-Generator
//

#import "UnlimitedMemoryCache.h"

@implementation UnlimitedMemoryCache {

@private
    NSMutableDictionary *_cache;
}

- (id)init {
    self = [super init];
    if (self) {
        _cache = [NSMutableDictionary new];
    }
    return self;
}

- (void)setObject:(id)value forKey:(id)key {
    @synchronized (_cache) {
        [_cache setObject:value forKey:key];
    }
}

- (id)objectForKey:(id)key {
    return [_cache objectForKey:key];
}

- (void)removeObjectForKey:(id)key {
    @synchronized (_cache) {
        [_cache removeObjectForKey:key];
    }
}

- (NSArray *)allKeys {
    return [_cache allKeys];
}

- (void)removeAllObjects {
    @synchronized (_cache) {
        [_cache removeAllObjects];
    }
}

@end