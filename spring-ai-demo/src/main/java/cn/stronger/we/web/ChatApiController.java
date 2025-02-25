package cn.stronger.we.web;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    @Autowired
    private ChatClient chatClient;

    private final OpenAiChatModel chatModel;

    @Autowired
    public ChatApiController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/ai/stream")
    public Flux<String> ollamaByStream(@RequestBody QuestionRequest request) {
        System.out.println("用户提问：" + request.getMessage());
        return chatClient.prompt()
                .user(request.getMessage())
                .stream().content();
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
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}
