package cn.stronger.we.service;


import cn.stronger.we.redis.ICustomRedisTemplate;
import cn.stronger.we.web.rest.MessageDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class AiChatContextServiceImpl.class
 * @department Platform R&D
 * @date 2025/2/27
 * @desc do what?
 */
@Service
public class AiChatContextServiceImpl implements AiChatContextService {

    private final ICustomRedisTemplate customRedisTemplate;
    private static final String REDIS_KEY_PREFIX = "ai_context:";

    public AiChatContextServiceImpl(ICustomRedisTemplate customRedisTemplate) {
        this.customRedisTemplate = customRedisTemplate;
    }

    @Override
    public void addMessage(String userId, MessageDto message) {
        String key = REDIS_KEY_PREFIX + userId;
        long times = System.currentTimeMillis();
        message.setTimestamp(times);
        String value = JSONObject.toJSONString(message);
        customRedisTemplate.lPush(key, value);
        Long listSize = customRedisTemplate.lSize(key);
        if (listSize > 10) {
            customRedisTemplate.rPop(key, (listSize.intValue() - 1));
        }
        customRedisTemplate.expire(key, 3600);
    }

    @Override
    public List<MessageDto> getRedisContext(String userId) {
        String key = REDIS_KEY_PREFIX + userId;
        List<String> data = customRedisTemplate.getList(key, 10);
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        return data.stream().map(item -> JSONObject.parseObject(item, MessageDto.class))
                .sorted(Comparator.comparingLong(MessageDto::getTimestamp))
                .collect(Collectors.toList());
    }
}
