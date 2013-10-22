//
//  LogicFilter.h
//  PeopleSports
//
//  Created by DRM on 22/10/13.
//  Copyright (c) 2013 Mobivery. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LogicFilter : NSObject
- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName;
- (NSString *) escapeUrl:(NSString *)url;
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request;
- (NSString *) preprocessResponse:(NSString *)responseString;
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict;
- (UIView *) loadingViewForTasks;
@end
