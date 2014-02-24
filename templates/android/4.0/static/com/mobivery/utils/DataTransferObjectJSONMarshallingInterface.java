package com.mobivery.utils;

import org.json.JSONObject;

/**
 * Interface for defining DTO marshalling strategies
 * @author Service Generator
 */
public interface DataTransferObjectJSONMarshallingInterface<Q> {
    public JSONObject serialize(Q object);
    public Q deserialize(JSONObject jsonObject);
}
