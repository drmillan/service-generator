//
//  UnlimitedDiskCache.m
//
//  Created by Service-Generator
//

#import "UnlimitedDiskCache.h"

@implementation UnlimitedDiskCache {
    @protected
    NSString *_directory;
}
- (instancetype)initWithDirectory:(NSString *)directory {
    self = [super init];
    if (self) {
        _directory = directory;
    }
    
    return self;
}

+ (instancetype)cacheWithDirectory:(NSString *)directory {
    return [[self alloc] initWithDirectory:directory];
}


- (void)setObject:(id)value forKey:(id)key {
    NSString *filePath = [self fileForKey:key];
    BOOL result = [NSKeyedArchiver archiveRootObject:value toFile:filePath];
    if (!result) {
        NSLog(@"Problem writing cache file");
    }
}

- (id)objectForKey:(id)key {
    NSString *filePath = [self fileForKey:key];
    if (![[NSFileManager defaultManager] fileExistsAtPath:filePath]) {
        return nil;
    }
    return [NSKeyedUnarchiver unarchiveObjectWithFile:[self fileForKey:key]];
}

- (void)removeObjectForKey:(id)key {
    NSString *filePath = [self fileForKey:key];
    if ([[NSFileManager defaultManager] fileExistsAtPath:filePath]) {
        NSError *error = nil;
        [[NSFileManager defaultManager] removeItemAtPath:filePath error:&error];
        if (error) {
            NSLog(@"Unable to delete file");
        }
    }
}

- (NSArray *)allKeys {
    NSString *directory = [self privateDirectory];
    NSMutableArray *tempResult = [NSMutableArray new];
    NSError *error = nil;
    for (NSString *filePath in [[NSFileManager defaultManager] contentsOfDirectoryAtPath:directory error:&error]) {
        [tempResult addObject:filePath];
    }
    return [NSArray arrayWithArray:tempResult];
}

- (void)removeAllObjects {
    NSString *directory = [self privateDirectory];
    NSError *error = nil;
    for (NSString *filePath in [[NSFileManager defaultManager] contentsOfDirectoryAtPath:directory error:&error]) {
        BOOL success = [[NSFileManager defaultManager] removeItemAtPath:[NSString stringWithFormat:@"%@/%@", directory, filePath] error:&error];
        if (!success || error) {
            NSLog(@"Cache file deletion failed (removeAll)");
        }
    }
}

- (NSString *)fileForKey:(id)key {
    return [[self privateDirectory] stringByAppendingPathComponent:[NSString stringWithFormat:@"%lu", (unsigned long)[key hash]]];
}

- (NSString *)privateDirectory {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask, YES);
    NSString *directory = [[paths objectAtIndex:0] stringByAppendingPathComponent:_directory];
    [[NSFileManager defaultManager] createDirectoryAtPath:directory withIntermediateDirectories:YES attributes:nil error:nil];
    return directory;
}

@end
