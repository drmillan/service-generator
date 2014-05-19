//
// Created by Service-Generator
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

+ (instancetype)cacheWithCache:(id <Cache>)caching maximumAgeInSeconds:(NSTimeInterval)maximumAge {
    return [[self alloc] initWithCache:caching maximumAgeInSeconds:maximumAge];
}

- (void)setObject:(id)value forKey:(id)key {
    [_caching setObject:value forKey:key];
    [_datesMap setObject:[NSDate date] forKey:key];
}

- (id)objectForKey:(id)key {
    NSDate *date = [_datesMap valueForKey:key];
    NSDate *now = [NSDate date];
    if (date) {
        NSDate *validUntil = [date dateByAddingTimeInterval:_maximumAge];
        if ([validUntil timeIntervalSinceDate:now] < 0) {
            [_datesMap removeObjectForKey:key];
            [_caching removeObjectForKey:key];
        }
    }
    return [_caching objectForKey:key];
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