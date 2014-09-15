//
//  LimitedAgeDiskCache.m
//
//  Created by Service-Generator
//

#import "LimitedAgeUnlimitedDiskCache.h"

@implementation LimitedAgeUnlimitedDiskCache {
    NSTimeInterval _maximumAge;
}

- (instancetype)initWithDirectory:(NSString*)directory maximumAgeInSeconds:(NSTimeInterval)maximumAge {
    self = [super initWithDirectory:directory];
    if (self) {
        _maximumAge = maximumAge;
    }
    return self;
}

+ (instancetype)cacheWithDirectory:(NSString*)directory maximumAgeInSeconds:(NSTimeInterval)maximumAge {
    return [[self alloc] initWithDirectory:directory maximumAgeInSeconds:maximumAge];
}


- (void)setObject:(id)value forKey:(id)key {
    [super setObject:value forKey:key];
    NSString *filePath = [self fileForKey:key];
    [[NSFileManager defaultManager] setAttributes:@{NSFileModificationDate: [NSDate date]} ofItemAtPath:filePath error:nil];
}

- (id)objectForKey:(id)key {
    NSString *filePath = [self fileForKey:key];
    if ([[NSFileManager defaultManager] fileExistsAtPath:filePath]) {
        NSDate *now = [NSDate date];
        NSError *error = nil;
        NSDictionary *fileAttributes = [[NSFileManager defaultManager] attributesOfItemAtPath:filePath error:&error];
        if (!error) {
            NSDate *validUntil = [[fileAttributes objectForKey:NSFileModificationDate] dateByAddingTimeInterval:_maximumAge];
            if ([validUntil timeIntervalSinceDate:now] < 0) {
                [super removeObjectForKey:key];
            }
        }
    }
    return [super objectForKey:key];
}

@end
