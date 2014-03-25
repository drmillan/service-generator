//
//  BaseLogic.m
//
//  Created by Service-Generator
//  Copyright (c) 2014 Mobivery. All rights reserved.
//

#import "BaseLogic.h"

@interface BaseLogic()
@property (nonatomic,strong) NSMutableArray *filters;
@end

@implementation BaseLogic


-(id) init
{
    self = [super init];
    if(self) {
        self.filters=[[NSMutableArray alloc]init];
    }
    return self;
}

-(void) addFilter:(LogicFilter *)filter {
    [self.filters addObject:filter];
}

-(void) removeFilter:(LogicFilter *)filter {
    [self.filters removeObject:filter];
}

- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    NSString *returnURL=url;
    for(LogicFilter *filter in self.filters) {
        returnURL=[filter preInjectURLParameters:url withObject:obj onService:serviceName onMethod:methodName];
    }
    return returnURL;
}

- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    NSString *returnURL=url;
    for(LogicFilter *filter in self.filters) {
        returnURL=[filter postInjectURLParameters:url withObject:obj onService:serviceName onMethod:methodName];
    }
    return returnURL;
}
- (NSString *) escapeUrl:(NSString *)url onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    NSString *returnURL=url;
    for(LogicFilter *filter in self.filters) {
        returnURL=[filter escapeUrl:url onService:serviceName onMethod:methodName];
    }
    return returnURL;
}
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    NSMutableURLRequest *returnRequest=request;
    for(LogicFilter *filter in self.filters) {
        returnRequest=[filter manageRequest:request onService:serviceName onMethod:methodName];
    }
    return returnRequest;
}
- (NSString *) preprocessResponse:(NSString *)responseString  onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    NSString *returnString=responseString;
    for(LogicFilter *filter in self.filters) {
        returnString=[filter preprocessResponse:returnString onService:serviceName onMethod:methodName];
    }
    return returnString;
}
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    NSDictionary *returnDict=responseDict;
    for(LogicFilter *filter in self.filters) {
        returnDict=[filter preprocessResponseAsDictionary:returnDict onService:serviceName onMethod:methodName];
    }
    return returnDict;
}
- (id)preprocessCacheHitForRequest:(id)request withCachedResponse:(id)cachedResponse onService:(NSString *)serviceName onMethod:(NSString *)methodName {
    id returnStuff = cachedResponse;
    for(LogicFilter *filter in self.filters) {
        returnStuff = [filter preprocessCacheHitForRequest:request withCachedResponse:returnStuff onService:serviceName onMethod:methodName];
    }
    return returnStuff;
}

- (UIView *) loadingViewForTasks {
    return nil;
}

- (NSString *)fixURLParameter:(NSString *)param {
    return(NSString *)CFBridgingRelease(CFURLCreateStringByAddingPercentEscapes(
                                                                                NULL,
                                                                                (__bridge CFStringRef) param,
                                                                                NULL,
                                                                                CFSTR("!*'();:@&=+$,/?%#[]\" "),
                                                                                kCFStringEncodingUTF8));
}
@end
