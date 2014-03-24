//
// Created by Service-Generator
// Copyright (c) 2014 Mobivery. All rights reserved.
//

#import "UnlimitedMemoryCache.h"

@implementation UnlimitedMemoryCache {

@private
    NSCache *_cache;
    NSMutableArray *_keys;

}

- (id)init {
    self = [super init];
    if (self) {
        _cache = [NSCache new];
        _keys = [NSMutableArray new];
    }
    return self;
}

- (void)setValue:(id)value forKey:(id)key {
    [_cache setValue:value forKey:key];
    @synchronized (_keys) {
        [_keys addObject:key];
    }
}

- (id)valueForKey:(id)key {
    return [_cache objectForKey:key];
}

- (void)removeObjectForKey:(id)key {
    [_cache removeObjectForKey:key];
    @synchronized (_keys) {
        [_keys removeObject:key];
    }
}

- (NSArray *)allKeys {
    return [NSArray arrayWithArray:_keys];
}

- (void)removeAllObjects {
    [_cache removeAllObjects];
    @synchronized (_keys) {
        [_keys removeAllObjects];
    }
}

@end