package cn.stronger.we.ai.web;


import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "msg")String msg){

        return "hello！你对我说了:"+msg;
    }
    @GetMapping("/ai/getMsg")
    public Object getOllame(@RequestParam(value = "msg") String msg) {
        Prompt prompt = new Prompt(msg);
        ChatResponse call = chatClient.call(prompt);
        return call.getResult().getOutput().getContent();
    }

    @GetMapping("/ai/stream/getMsg")
    public Object getOllameByStream(@RequestParam(value = "msg") String msg) {
        Prompt prompt = new Prompt(msg);
        Flux<ChatResponse> stream = chatClient.stream(prompt);
        List<String> result = stream.toStream().map(a -> {
            String content = a.getResult().getOutput().getContent();
            System.out.println(content);
            return content;
        }).collect(Collectors.toList());
        return result;
    }

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
    public Flux<String> chatByStream(String msg){
        return chatClient.stream(new Prompt(msg)).map(a -> {
            String content = a.getResult().getOutput().getContent();
            System.out.println(content);
            return content;
        });
    }
}
