package cn.stronger.we.redis;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.w
 * @version 1.0.0
 * @interface IMatchRedisTemplate
 * @department Platform Center
 * @date 2024-09-20 21:36
 */
public interface ICustomRedisTemplate {

    /**
     * redis 常用 失效时间
     */
    int SS_30 = 30;
    int MIN_1 = 60;
    int MIN_5 = 300;
    int MIN_10 = 600;
    int MIN_30 = 1800;
    int HOUR_1 = 3600;
    int HOUR_2 = 7200;
    int HOUR_24 = 86400;
    int MONTH_1 = 2678400;

    /**
     * 获取缓存结果
     *
     * @param key key
     * @return r
     */
    String get(String key);

    /**
     * 设置缓存
     *
     * @param key     key
     * @param value   value
     * @param timeout timeout
     */
    void set(String key, String value, long timeout);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    Boolean hasKey(String key);

    /**
     * 删除
     *
     * @param key 关键
     * @return boolean
     */
    Boolean delete(String key);

    /**
     * 删除
     *
     * @param key 关键
     */
    void delete(String... key);

    /**
     * 设置失效时间
     *
     * @param key  key
     * @param time time
     */
    void expire(String key, long time);

    /**
     * 获取缓存失效时间
     *
     * @param key key
     * @return l
     */
    Long getExpire(String key);

    /**
     * 哈希得到
     *
     * @param key  关键
     * @param item 项
     * @return {@link String}
     */
    String hashGet(String key, String item);

    /**
     * 哈希得到
     *
     * @param key 关键
     * @return {@link Map}<{@link Object}, {@link Object}>
     */
    Map<Object, Object> hashGet(String key);

    /**
     * 散列集
     *
     * @param key 关键
     * @param map 地图
     * @return Boolean
     */
    Boolean hashSet(String key, Map<String, Object> map);

    /**
     * 散列集
     *
     * @param key  关键
     * @param map  地图
     * @param time 时间
     * @return Boolean
     */
    Boolean hashSet(String key, Map<String, Object> map, long time);

    /**
     * 获取缓存
     *
     * @param key  key
     * @param item item
     * @return s
     */
    String hmget(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> hmget(String key);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    Boolean hmset(String key, Map<String, Object> map);

    /**
     * HashSet 设置超时时间
     *
     * @param key  key
     * @param map  map
     * @param time time
     * @return b
     */
    Boolean hmset(String key, Map<String, Object> map, long time);

    /**
     * 添加or更新hash的值
     *
     * @param key   key
     * @param field field
     * @param value value
     */
    void hmset(String key, String field, String value);

    /**
     * 散列集
     *
     * @param key   关键
     * @param field 场
     * @param value 价值
     */
    void hashSet(String key, String field, String value);

    void lPush(String key, String value);

    List<String> rPop(String key, int count);

    List<String> getList(String key, int count);

    Long lSize(String key);

    void lRemove(String key, long size);
}
