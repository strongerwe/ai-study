package cn.stronger.we.service;

import cn.stronger.we.redis.ICustomRedisTemplate;
import cn.stronger.we.web.rest.MessageDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class ConversationContextService.class
 * @department Platform R&D
 * @date 2025/2/26
 * @desc do what?
 */
@Service
public class ConversationContextService {
    // Key: 会话ID (如用户ID), Value: 对话历史
    private final ConcurrentHashMap<String, List<Message>> contextStore = new ConcurrentHashMap<>();
    private final ICustomRedisTemplate customRedisTemplate;

    public ConversationContextService(ICustomRedisTemplate customRedisTemplate) {
        this.customRedisTemplate = customRedisTemplate;
    }

    // 获取上下文
    public List<Message> getContext(String sessionId) {
        return contextStore.getOrDefault(sessionId, new ArrayList<>());
    }

    // 更新上下文
    public void updateContext(String sessionId, List<Message> messages) {
        contextStore.put(sessionId, messages);
    }

    // 清空上下文（可选）
    public void clearContext(String sessionId) {
        contextStore.remove(sessionId);
    }

    private static final String REDIS_KEY_PREFIX = "ai_context:";

    // 存储单条消息
    public void addMessage(String userId, MessageDto message) {
        String key = REDIS_KEY_PREFIX + userId;
        long times = System.currentTimeMillis();
        String field = "msg_" + times;
        message.setTimestamp(times);
        String value = JSONObject.toJSONString(message);
        customRedisTemplate.lPush(key, value);
        Long listSize = customRedisTemplate.lSize(key);
        if (listSize > 10) {
            customRedisTemplate.rPop(key, (listSize.intValue() - 1));
        }
//        customRedisTemplate.hashSet(key, field, value);
//        customRedisTemplate.expire(key, 3600);

        // TODO 移除之前的消息
    }

    // 获取全部上下文
    public List<MessageDto> getRedisContext(String userId) {
        String key = REDIS_KEY_PREFIX + userId;
        List<String> data = customRedisTemplate.getList(key, 10);
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().map(item -> JSONObject.parseObject(item, MessageDto.class)).sorted(Comparator.comparingLong(MessageDto::getTimestamp)).collect(Collectors.toList());
        }
//        Map<Object, Object> data = customRedisTemplate.hashGet(key);
//        List<MessageDto> messages = new ArrayList<>();
//        for (Object value : data.values()) {
//            messages.add(JSONObject.parseObject(value.toString(), MessageDto.class));
//        }
//        // 按时间戳排序（如果需要）
//        messages.sort(Comparator.comparingLong(MessageDto::getTimestamp));
//        if (messages.size() > 10) {
//            return messages.stream().limit(10).toList();
//        }
        return new ArrayList<>();
    }

    // 清理上下文
    public void clearRedisContext(String userId) {
        customRedisTemplate.delete(REDIS_KEY_PREFIX + userId);
    }
}
