//
//  LogicFilter.m
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import "LogicFilter.h"

@implementation LogicFilter
- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return url;
}
- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return url;
}
- (NSString *) escapeUrl:(NSString *)url onService:(NSString *)serviceName onMethod:(NSString *)methodName {
     return url;
}
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return request;
}
- (NSString *) preprocessResponse:(NSString *)responseString  onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return responseString;
}
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return responseDict;
}
- (id)preprocessCacheHitForRequest:(id)request withCachedResponse:(id)cachedResponse onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return cachedResponse;
}
- (AFSecurityPolicy *) preprocessSecurityPolicy:(AFSecurityPolicy *)securityPolicy onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    return securityPolicy;
}
- (UIView *) loadingViewForTasks {
    return nil;
}
@end
