package cn.wind.common.support.json;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author xukk
 * @date 2018/5/4
 */
public class GsonEnumTypeAdapter<E> implements JsonSerializer<E>, JsonDeserializer<E> {

    private final GsonIntEnum<E> gsonEnum;

    public GsonEnumTypeAdapter(GsonIntEnum<E> gsonEnum) {
        this.gsonEnum = gsonEnum;
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (null != src && src instanceof GsonIntEnum) {
            return new JsonPrimitive(((GsonIntEnum) src).serialize());
        }
        return null;
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (null != json) {
            return gsonEnum.deserialize(json.getAsString());
        }
        return null;
    }

}