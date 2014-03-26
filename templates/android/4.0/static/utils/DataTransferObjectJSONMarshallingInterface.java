package {{{staticPackage}}}.utils;

import org.json.JSONObject;

/**
 * Interface for defining DTO marshalling strategies
 * @author Service Generator
 *
 * Generated Class - DO NOT MODIFY
 */
public interface DataTransferObjectJSONMarshallingInterface<Q> {
    public JSONObject serialize(Q object);
    public Q deserialize(JSONObject jsonObject);
}
