package com.nemo.example.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nemo.example.Example;

/**
 * @author mingLong.zhao
 */
public class JsonExample {

    public void example() {
        Example example = new Example();
        // {"id":null,"name":null,"number":null,"text":null}
        String jsonWithNull = JSON.toJSONString(example, SerializerFeature.WriteMapNullValue);
    }
}
