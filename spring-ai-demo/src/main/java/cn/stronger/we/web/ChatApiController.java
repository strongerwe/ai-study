package cn.stronger.we.web;

import cn.stronger.we.service.AiChatService;
import cn.stronger.we.service.ConversationContextService;
import cn.stronger.we.web.rest.MessageBody;
import cn.stronger.we.web.rest.MessageModel;
import cn.stronger.we.web.rest.QuestionRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class ChatDemoController.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@RestController
public class ChatApiController {
    Logger log = org.slf4j.LoggerFactory.getLogger(ChatApiController.class);
    private final ChatClient chatClient;
    private final OpenAiChatModel chatModel;
    private final ConversationContextService service;

    private final AiChatService aiChatService;

    public ChatApiController(OpenAiChatModel chatModel, ChatClient chatClient, ConversationContextService service, AiChatService aiChatService) {
        this.chatModel = chatModel;
        this.chatClient = chatClient;
        this.service = service;
        this.aiChatService = aiChatService;
    }

    @PostMapping(value = "/ai/stream")
    public Flux<String> aiStream(@RequestBody QuestionRequest request) {
        log.info("[{}]用户提问：{}", request.getUserId(), request.getMessage());
        return aiChatService.aiStreamString(request);
    }

    @PostMapping(value = "/ai/stream2",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageBody> ollamaByStream(@RequestBody QuestionRequest request) {
        System.out.println("[" + request.getUserId() + "]用户提问：" + request.getMessage());
        if (StringUtils.isEmpty(request.getUserId())) {
            request.setUserId("default");
            request.setMessages(new ArrayList<>());
        }
        List<Message> context = context(request);
        // 3. 创建 Prompt 并调用流式 API
        Prompt prompt = new Prompt(context);
        return chatClient.prompt(prompt).stream().content()
                .map(content -> {
                    System.out.println(content);
                    return new MessageBody(content, System.currentTimeMillis());
                })
                // 3. 错误处理
//                .onErrorResume(e -> {
//                    return Flux.just(Map.of(
//                            "error", e.getMessage(),
//                            "type", e.getClass().getSimpleName()
//                    ));
//                })
                // 4. 流控制（可选：添加延迟）
                .delayElements(Duration.ofMillis(50));
    }

    @PostMapping(value = "/ai/stream3",
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<MessageBody> ollamaByStream2(@RequestBody QuestionRequest request) {
        List<Message> context = context(request);
        // 3. 创建 Prompt 并调用流式 API
        Prompt prompt = new Prompt(context);

//        Flux<MessageBody> mapFlux =
        return chatModel.stream(prompt).map(chatResponse -> {
                    if (!chatResponse.getResults().isEmpty()) {
                        return chatResponse.getResults().get(0).getOutput().getText();
                    }
                    return ""; // 空内容处理
                })
                // 2. 转换为目标格式（示例：包装为带时间戳的对象）
                .map(content -> {
                    System.out.println(content);
                    return new MessageBody(content, System.currentTimeMillis());
                })
                // 3. 错误处理
//                .onErrorResume(e -> {
//                    return Flux.just(Map.of(
//                            "error", e.getMessage(),
//                            "type", e.getClass().getSimpleName()
//                    ));
//                })
                // 4. 流控制（可选：添加延迟）
                .delayElements(Duration.ofMillis(50));
//        return mapFlux;
//       return this.chatModel.stream(prompt).ofType(ChatResponse.class);
    }

    /**
     * 封装上下文
     *
     * @param request request
     * @return {@link List }<{@link Message }>
     */
    private List<Message> context(QuestionRequest request) {
        List<Message> messages = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getMessages())) {
            messages.add(new UserMessage(request.getMessage()));
            return messages;
        }
        List<MessageModel> collect = request.getMessages().stream().sorted(Comparator.comparing(MessageModel::getMessageId)).toList();
        for (MessageModel messageModel : collect) {
            if (MessageType.USER.getValue().equals(messageModel.getMessageType())) {
                messages.add(new UserMessage(messageModel.getText()));
            } else {
                messages.add(new AssistantMessage(messageModel.getText()));
            }
        }
        messages.add(new UserMessage(request.getMessage()));
        return messages;
    }

    @GetMapping(value = "/chat/{message}")
    public String chat(@PathVariable("message") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping(value = "/chat/stream/{message}")
    public Flux<String> chatStream(@PathVariable("message") String message) {
        return chatClient.prompt()
                .user(message)
                .stream().content();
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message), OpenAiChatOptions.builder().model("deepseek-ai/DeepSeek-R1-Distill-Llama-8B").build());
        return this.chatModel.stream(prompt);
    }
}
