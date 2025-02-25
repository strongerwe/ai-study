package cn.stronger.we.ai.web;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class OllamaAiDemoController.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@RestController
public class OllamaAiDemoController {

    @Autowired
    private OllamaChatClient chatClient;

    @PostMapping("/ai/stream")
    public Flux<String> ollamaByStream(@RequestBody QuestionRequest request){
        System.out.println(request.getMessage());
        return chatClient.stream(new Prompt(request.getMessage())).map(a -> {
            String content = a.getResult().getOutput().getContent();
            System.out.println(content);
            return content;
        });
    }

    @GetMapping("/ai/chat")
    public Flux<String> chatByStream(@RequestParam(value = "msg") String msg){
        return chatClient.stream(new Prompt(msg)).map(a -> {
            String content = a.getResult().getOutput().getContent();
            System.out.println(content);
            return content;
        });
    }
}
