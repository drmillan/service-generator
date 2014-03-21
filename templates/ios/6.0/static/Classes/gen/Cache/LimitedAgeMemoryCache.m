//
// Created by Service-Generator
// Copyright (c) 2014 Mobivery. All rights reserved.
//

#import "LimitedAgeMemoryCache.h"

@implementation LimitedAgeMemoryCache {
@private
    id <Cache> _caching;
    NSMutableDictionary *_datesMap;
    NSTimeInterval _maximumAge;
}

- (instancetype)initWithCache:(id <Cache>)caching maximumAgeInSeconds:(NSTimeInterval)maximumAge {
    self = [super init];
    if (self) {
        _caching = caching;
        _maximumAge = maximumAge;
        _datesMap = [NSMutableDictionary new];
    }

    return self;
}

+ (instancetype)cacheWithCache:(id <Cache>)caching {
    return [[self alloc] initWithCache:caching];
}


- (void)setObject:(id)value forKey:(id)key {
    [_caching setObject:value forKey:key];
    [_datesMap setObject:[NSDate date] forKey:key];
}

- (id)valueForKey:(id)key {
    NSDate *date = [_datesMap valueForKey:key];
    NSDate *now = [NSDate date];
    if (date) {
        NSDate *validUntil = [date dateByAddingTimeInterval:_maximumAge];
        if ([validUntil timeIntervalSinceDate:now] < 0) {
            [_datesMap removeObjectForKey:key];
            [_caching removeObjectForKey:key];
        }
    }
    return [_caching valueForKey:key];
}

- (void)removeObjectForKey:(id)key {
    [_caching removeObjectForKey:key];
    [_datesMap removeObjectForKey:key];
}

- (NSArray *)allKeys {
    return [_caching allKeys];
}

- (void)removeAllObjects {
    [_caching removeAllObjects];
    [_datesMap removeAllObjects];
}

@end