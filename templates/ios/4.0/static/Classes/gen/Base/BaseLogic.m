//
//  BaseLogic.m
//  PeopleSports
//
//  Created by DRM on 22/10/13.
//  Copyright (c) 2013 Mobivery. All rights reserved.
//

#import "BaseLogic.h"

@interface BaseLogic()
    @property (nonatomic,strong) NSMutableArray *filters;
@end

@implementation BaseLogic


-(id) init
{
    self = [super init];
    if(self)
    {
       //do something
    }
    return self;
}

-(void) addFilter:(LogicFilter *)filter
{
    [self.filters addObject:filter];
}
-(void) removeFilter:(LogicFilter *)filter
{
    [self.filters removeObject:filter];
}
- (NSString *) preInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    NSString *returnURL=url;
    for(LogicFilter *filter in self.filters)
    {
        returnURL=[filter preInjectURLParameters:url withObject:obj onService:serviceName onMethod:methodName];
    }
    return returnURL;
}
- (NSString *) postInjectURLParameters:(NSString *)url withObject:(NSObject *)obj onService:(NSString *)serviceName onMethod:(NSString *)methodName
{
    NSString *returnURL=url;
    for(LogicFilter *filter in self.filters)
    {
        returnURL=[filter postInjectURLParameters:url withObject:obj onService:serviceName onMethod:methodName];
    }
    return returnURL;
}
- (NSString *) escapeUrl:(NSString *)url
{
    NSString *returnURL=url;
    for(LogicFilter *filter in self.filters)
    {
        returnURL=[filter escapeUrl:url];
    }
    return returnURL;
}
- (NSMutableURLRequest *)manageRequest:(NSMutableURLRequest *)request
{
    NSMutableURLRequest *returnRequest=request;
    for(LogicFilter *filter in self.filters)
    {
        returnRequest=[filter manageRequest:request];
    }
    return returnRequest;
}
- (NSString *) preprocessResponse:(NSString *)responseString
{
    NSString *returnString=responseString;
    for(LogicFilter *filter in self.filters)
    {
        returnString=[filter preprocessResponse:returnString];
    }
    return returnString;
}
- (NSDictionary *) preprocessResponseAsDictionary:(NSDictionary *)responseDict
{
    NSDictionary *returnDict=responseDict;
    for(LogicFilter *filter in self.filters)
    {
        returnDict=[filter preprocessResponseAsDictionary:returnDict];
    }
    return returnDict;
}
- (UIView *) loadingViewForTasks
{
    return nil;
}
@end
