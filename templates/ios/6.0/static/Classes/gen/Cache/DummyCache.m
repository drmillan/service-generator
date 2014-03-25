//
// Created by Service-Generator
// Copyright (c) 2014 Mobivery. All rights reserved.
//

#import "DummyCache.h"


@implementation DummyCache

- (void)setObject:(id)value forKey:(id)key {
    NSLog(@"setValue called");
}

- (id)objectForKey:(id)key {
    NSLog(@"valueForKey called");
    return nil;
}

- (void)removeObjectForKey:(id)key {
    NSLog(@"removeObjectForKey called");
}

- (NSArray *)allKeys {
    NSLog(@"allKeys called");
    return nil;
}

- (void)removeAllObjects {
    NSLog(@"removeAllObjects called");
}

@end