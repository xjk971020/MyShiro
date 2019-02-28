package cn.xjk.shiro.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author xjk
 * @date 2019/2/24 -  11:33
 * redis 工具类
 **/
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key ,byte[] value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key,value);
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int seconds) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key,seconds);
        } finally {
            jedis.close();
        }
    }
}
