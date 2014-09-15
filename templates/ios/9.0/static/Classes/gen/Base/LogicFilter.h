//
//  LogicFilter.h
//
//  Created by Service-Generator
//

#import <Foundation/Foundation.h>
#import "AFSecurityPolicy.h"
#import "AFHTTPRequestOperation.h"

@interface LogicFilter : NSObject
- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) escapeUrl:(NSString *)url onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) preprocessResponse:(NSString *)responseString onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (id)preprocessCacheHitForRequest:(id)request withCachedResponse:(id)cachedResponse onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (AFSecurityPolicy *) preprocessSecurityPolicy:(AFSecurityPolicy *)securityPolicy onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (UIView *) loadingViewForTasks;
- (void) processOperation:(AFHTTPRequestOperation *)operation;
@end
