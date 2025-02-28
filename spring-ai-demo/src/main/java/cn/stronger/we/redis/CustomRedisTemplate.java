package cn.stronger.we.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class redisTemplate
 * @department Platform Center
 * @date 2024-09-20 21:35
 */
@Slf4j
public class CustomRedisTemplate implements ICustomRedisTemplate {

    protected final StringRedisTemplate stringRedisTemplate;

    public CustomRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 查询
     *
     * @param key 关键
     * @return {@link String}
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 设置
     *
     * @param key     关键
     * @param value   价值
     * @param timeout 超时
     */
    @Override
    public void set(String key, String value, long timeout) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("[MatchRedisTemplate.set]设置redis出现异常", e);
            return;
        }
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 关键
     * @return Boolean
     */
    @Override
    public Boolean hasKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("[MatchRedisTemplate.hasKey]判断key是否存在出现异常", e);
            return false;
        }
    }

    /**
     * 删除
     *
     * @param key 关键
     * @return boolean
     */
    @Override
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param key 关键
     */
    @Override
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                stringRedisTemplate.delete(key[0]);
            } else {
                stringRedisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 设置到期时间
     *
     * @param key  关键
     * @param time 时间
     */
    @Override
    public void expire(String key, long time) {
        try {
            if (time > 0) {
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("[MatchRedisTemplate.expire]设置到期时间出现异常", e);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 关键
     * @return long 时间(秒) 返回 0 代表为永久有效
     */
    @Override
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * hashGet
     *
     * @param key  关键
     * @param item 项
     * @return {@link String}
     */
    @Override
    public String hashGet(String key, String item) {
        Object result = stringRedisTemplate.opsForHash().get(key, item);
        return result != null ? result.toString() : "";
    }

    /**
     * hashGet
     *
     * @param key 关键
     * @return {@link Map}<{@link Object}, {@link Object}>
     */
    @Override
    public Map<Object, Object> hashGet(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * hashSet
     *
     * @param key 关键
     * @param map 地图
     * @return Boolean
     */
    @Override
    public Boolean hashSet(String key, Map<String, Object> map) {
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("[MatchRedisTemplate.hashSet]出现异常", e);
            return false;
        }
    }

    /**
     * hashSet 并设置超时时间
     *
     * @param key  关键
     * @param map  地图
     * @param time 时间
     * @return Boolean
     */
    @Override
    public Boolean hashSet(String key, Map<String, Object> map, long time) {
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("[MatchRedisTemplate.hashSet]并设置超时时间出现异常", e);
            return false;
        }
    }

    @Override
    public String hmget(String key, String item) {
        return hashGet(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return hashGet(key);
    }

    public List<Object> hmsget(String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

    @Override
    public Boolean hmset(String key, Map<String, Object> map) {
        return hashSet(key, map);
    }

    @Override
    public Boolean hmset(String key, Map<String, Object> map, long time) {
        return hashSet(key, map, time);
    }

    @Override
    public void hmset(String key, String field, String value) {
        hashSet(key, field, value);
    }

    /**
     * 添加or更新hash的值
     *
     * @param key   关键
     * @param field 场
     * @param value 价值
     */
    @Override
    public void hashSet(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向有序集合中添加元素
     *
     * @param key   关键
     * @param value 值
     * @param score 分数
     */
    public void zAdd(String key, String value, double score) {
        try {
            stringRedisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("[CustomRedisTemplate.zAdd]向有序集合中添加元素出现异常", e);
        }
    }

    /**
     * 查询有序集合中的元素
     *
     * @param key   关键
     * @param start 开始位置
     * @param end   结束位置
     * @return {@link Set}<{@link String}>
     */
    public Set<String> zRange(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("[CustomRedisTemplate.zRange]查询有序集合中的元素出现异常", e);
            return new HashSet<>();
        }
    }

    /**
     * 从有序集合中删除元素
     *
     * @param key    关键
     * @param values 值
     */
    public void zRemove(String key, String... values) {
        if (values != null && values.length > 0) {
            try {
                stringRedisTemplate.opsForZSet().remove(key, (Object[]) values);
            } catch (Exception e) {
                log.error("[CustomRedisTemplate.zRemove]从有序集合中删除元素出现异常", e);
            }
        }
    }

    @Override
    public void lPush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public List<String> rPop(String key, int count) {
        return stringRedisTemplate.opsForList().rightPop(key, count);
    }

    @Override
    public List<String> getList(String key, int count) {
        return stringRedisTemplate.opsForList().range(key, 0, count);
    }

    @Override
    public Long lSize(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    @Override
    public void lRemove(String key, long size) {
        stringRedisTemplate.opsForList().trim(key, 0, size);
    }
}
