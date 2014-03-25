//
//  LogicFilter.h
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LogicFilter : NSObject
- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) escapeUrl:(NSString *)url onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) preprocessResponse:(NSString *)responseString onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (id)preprocessCacheHitForRequest:(id)request withCachedResponse:(id)cachedResponse onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (UIView *) loadingViewForTasks;
@end
