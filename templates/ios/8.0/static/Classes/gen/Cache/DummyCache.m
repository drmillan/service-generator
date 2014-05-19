//
// Created by Service-Generator
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