package com.demo.codec;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dragon
 *
 * Protostuff 序列化和反序列化
 */
public class SerializerUtil{
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializerUtil.class);

    private static final Map<Class<?>, Schema> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    private static <T> Schema<T> getCachedSchema(Class<T> clazz){
        Schema<T> schema = cachedSchema.get(clazz);
        if(schema == null){
            schema = RuntimeSchema.createFrom(clazz);
            if(schema != null){
                cachedSchema.put(clazz, schema);
            }
        }
        return schema;
    }

    public static <T> byte[] serialize(T object) {
        Class<T> clazz =  (Class<T>)object.getClass();
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<T> cachedSchema = getCachedSchema(clazz);
        try {
            LOGGER.info("序列化对象："+object);
            return ProtobufIOUtil.toByteArray(object, cachedSchema, linkedBuffer);
        } catch (Exception e) {
            throw e;
        } finally {
            linkedBuffer.clear();
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T object = objenesis.newInstance(clazz);
        Schema<T> cachedSchema = getCachedSchema(clazz);
        try {
            ProtobufIOUtil.mergeFrom(bytes, object, cachedSchema);
            LOGGER.info("反序列化对象："+object);
            return object;
        } catch (Exception e) {
            throw e;
        }

    }
}
