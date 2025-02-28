package cn.stronger.we.service;


import cn.stronger.we.web.rest.MessageBody;
import cn.stronger.we.web.rest.QuestionRequest;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @interface AiChatService.class
 * @department Platform R&D
 * @date 2025/2/27
 * @desc do what?
 */
public interface AiChatService {

    /**
     * aiStreamString
     *
     * @param request request
     * @return {@link Flux }<{@link String }>
     */
    Flux<String> aiStreamString(QuestionRequest request);

    /**
     * aiStreamMessageBody
     *
     * @param request request
     * @return {@link Flux }<{@link MessageBody }>
     */
    Flux<MessageBody> aiStreamMessageBody(QuestionRequest request);

    /**
     * aiStreamChatResponse
     *
     * @param request request
     * @return {@link Flux }<{@link ChatResponse }>
     */
    Flux<ChatResponse> aiStreamChatResponse(QuestionRequest request);
}
