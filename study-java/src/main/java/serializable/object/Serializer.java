package serializable.object;

/**
 * Created by zouziwen on 2018/1/13.
 */
public interface Serializer {
    byte[] serialize(Object object);
    Object deserialize(byte[] bytes);
}

