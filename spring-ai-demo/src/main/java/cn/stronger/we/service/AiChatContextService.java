package cn.stronger.we.service;


import cn.stronger.we.web.rest.MessageDto;

import java.util.List;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @interface AiChatContextService.class
 * @department Platform R&D
 * @date 2025/2/27
 * @desc do what?
 */
public interface AiChatContextService {

    /**
     * addMessage
     *
     * @param userId  userId
     * @param message message
     */
    void addMessage(String userId, MessageDto message);

    /**
     * getRedisContext
     *
     * @param userId userId
     * @return {@link List }<{@link MessageDto }>
     */
    List<MessageDto> getRedisContext(String userId);
}
