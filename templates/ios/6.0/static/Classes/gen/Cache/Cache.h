//
//  Cache.h
//  EMT
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol Cache <NSObject>
// Put object into cache
- (void)setObject:(id)value forKey:(id)key;

// Retrieve object from cache
- (id)valueForKey:(id)key;

// Remove object from cache
- (void)removeObjectForKey:(id)key;

// Retrieve all keys
- (NSArray *)allKeys;

// Remove all elements
- (void)removeAllObjects;
@end
