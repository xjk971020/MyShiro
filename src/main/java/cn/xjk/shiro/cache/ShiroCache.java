package cn.xjk.shiro.cache;

import cn.xjk.shiro.utils.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author xjk
 * @date 2019/2/24 -  11:00
 **/
@Component
public class ShiroCache<K,V> implements Cache<K,V> {

    @Autowired
    JedisUtil jedisUtil;

    private static final String PREFIX = "shiro-cache:";

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (PREFIX + k).getBytes();
        } else {
            return SerializationUtils.serialize(k);
        }
    }

    @Override
    public V get(K k) throws CacheException {
        byte[] value = jedisUtil.get(getKey(k));
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
       byte[] value = jedisUtil.get(getKey(k));
       jedisUtil.del(value);
       if (value != null) {
           return (V) SerializationUtils.deserialize(value);
       }
       return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
