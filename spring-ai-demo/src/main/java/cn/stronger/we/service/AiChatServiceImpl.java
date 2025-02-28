package cn.stronger.we.service;

import cn.stronger.we.mapper.entity.AiChatLog;
import cn.stronger.we.web.rest.MessageBody;
import cn.stronger.we.web.rest.MessageDto;
import cn.stronger.we.web.rest.QuestionRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class AiChatServiceImpl.class
 * @department Platform R&D
 * @date 2025/2/27
 * @desc do what?
 */
@Service
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;
    private final AiChatContextService service;
    private final AiChatLogService logService;

    public AiChatServiceImpl(ChatClient chatClient,
                             AiChatContextService service,
                             AiChatLogService logService) {
        this.chatClient = chatClient;
        this.service = service;
        this.logService = logService;
    }

    @Override
    public Flux<String> aiStreamString(QuestionRequest request) {
        logService.addLog(new AiChatLog(
                request.getUserId(),
                request.getUserName(),
                request.getTerminalNo(),
                request.getClientNo(),
                request.getMessage()));
        /* 1.获取用户上下文 */
        List<MessageDto> redisContext = service.getRedisContext(request.getUserId());
        if (CollectionUtils.isEmpty(redisContext)) {
            redisContext = new ArrayList<>();
        }
        /* 2.添加新增消息 */
        redisContext.add(new MessageDto(System.currentTimeMillis(), MessageType.USER.getValue(), request.getMessage()));
        /* 3.添加新的上下文存储 */
        service.addMessage(request.getUserId(), new MessageDto(System.currentTimeMillis(), MessageType.USER.getValue(), request.getMessage()));
        /* 4.封装API上下文消息 */
        List<Message> context = context(redisContext);
        /* 5.封装Prompt */
        Prompt prompt = new Prompt(context);
        /* 6.调用API */
        Flux<String> content = chatClient.prompt(prompt).stream().content();
        StringBuilder aiResponseBuffer = new StringBuilder();
        return content.map(response -> {
            /* 7.响应并且封装上下文 */
            aiResponseBuffer.append(response);
            return response;
        }).doOnComplete(() -> {
            /* 8.新增AI回复上下文 */
            service.addMessage(request.getUserId(),
                    new MessageDto(System.currentTimeMillis(), MessageType.ASSISTANT.getValue(), aiResponseBuffer.toString()));
        });
    }

    private List<Message> context(List<MessageDto> redisContext) {
        List<Message> context = new ArrayList<>();
        redisContext.forEach(messageDto -> {
            if (MessageType.USER.getValue().equals(messageDto.getMessageType())) {
                context.add(new UserMessage(messageDto.getText()));
            } else {
                context.add(new AssistantMessage(messageDto.getText()));
            }
        });
        return context;
    }

    @Override
    public Flux<MessageBody> aiStreamMessageBody(QuestionRequest request) {
        return null;
    }

    @Override
    public Flux<ChatResponse> aiStreamChatResponse(QuestionRequest request) {
        return null;
    }
}
