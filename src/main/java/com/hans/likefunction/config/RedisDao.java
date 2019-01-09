package com.hans.likefunction.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 通过注解的方式进行配置
 */
@Component
public class RedisDao {

	@Resource(name="redisTemplate")
	private StringRedisTemplate redisTemplate;
	
	/**
	 * get
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * set
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * set with expire
	 * @param key
	 * @param value
	 * @param ExpireDate
	 */
	public void set(String key,String value, long ExpireDate){
		redisTemplate.opsForValue().set(key, value, ExpireDate, TimeUnit.SECONDS);
	}
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param second
	 * @return
	 */
	public boolean expire(String key, long second) {
		return redisTemplate.expire(key, second, TimeUnit.SECONDS);
	}
	
	/**
	 * 删除
	 * @param key
	 */
	public void del(String key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * list 左进
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String value) {
		redisTemplate.opsForList().leftPush(key, value);
	}
	
	/**
	 * list 右出
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}
	
	/**
	 * 是否包含key
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 加
	 * @param key
	 * @param value
	 * @return
	 */
	public long incrBy(String key, long value) {
		return redisTemplate.opsForValue().increment(key, value);
	}
	
	/**
	 * hashmap set
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hset(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	/**
	 * hashmap get
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public String hget(String key, String hashKey) {
		Object obj = redisTemplate.opsForHash().get(key, hashKey);
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * 获取map的所有值
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hgetAll(String key) {
		 return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * 删除map中的keys
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public long hdel(String key, Object... hashKeys) {
		return redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	/**
	 * set if not exists
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setnx(String key, String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}
	 /** 
     * 添加set 
     * @param key 
     * @param value 
     */  
    public void sadd(String key, String... value) {  
    	redisTemplate.boundSetOps(key).add(value);  
    }
    /** 
     * 删除set集合中的对象 
     * @param key 
     * @param value 
     */  
    public void srem(String key, String... value) {  
        redisTemplate.boundSetOps(key).remove(value);  
    }
    /**
     * 判断value 是否存在Set中
     * @param key
     * @param value
     */
    public boolean sismember(String key, String value){
    	return redisTemplate.boundSetOps(key).isMember(value);
    }
    /**
     * 返回key 对应的Set集合
     * @param key
     * @return
     */
    public Set<String> smembers (String key){
    	return redisTemplate.boundSetOps(key).members();
    }
    /**
     * 返回key 对应过期时间
     */
    public Long ttl(String key){
    	return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
