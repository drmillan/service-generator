//
//  LogicFilter.m
//  PeopleSports
//
//  Created by DRM on 22/10/13.
//  Copyright (c) 2013 Mobivery. All rights reserved.
//

#import "LogicFilter.h"

@implementation LogicFilter
- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    return url;
}
- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    return url;
}
- (NSString *) escapeUrl:(NSString *)url onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
     return url;
}
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    return request;
}
- (NSString *) preprocessResponse:(NSString *)responseString  onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    return responseString;
}
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    return responseDict;
}
- (UIView *) loadingViewForTasks
{
    return nil;
}
@end
