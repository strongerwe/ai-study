//package cn.stronger.we.web;
//
//
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.model.Media;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.ai.openai.OpenAiChatOptions;
//import org.springframework.ai.openai.api.OpenAiApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.MimeTypeUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//
///**
// * @author qiang.w
// * @version release-1.0.0
// * @class MultimodalApiController.class
// * @department Platform R&D
// * @date 2025/2/25
// * @desc do what?
// */
//@RestController
//public class MultimodalApiController {
//
//    @Autowired
//    private OpenAiChatModel openAiChatModel;
//
//    /**
//     * 多模态
//     * @param message message
//     * @return {@link String }
//     * @throws MalformedURLException e
//     */
//    @GetMapping("/ai/multimodal")
//    public String multimodal(@RequestParam(value = "message", defaultValue = "解释一下你在这张图片上看到了什么?") String message) throws MalformedURLException {
//        var userMessage = new UserMessage(message,
//                List.of(new Media(MimeTypeUtils.IMAGE_PNG,
//                        new URL("https://docs.spring.io/spring-ai/reference/1.0-SNAPSHOT/_images/multimodal.test.png"))));
//        ChatResponse response = openAiChatModel.call(new Prompt(List.of(userMessage),
//                OpenAiChatOptions.builder().model("deepseek-ai/DeepSeek-R1-Distill-Llama-8B").build()));
//        return response.getResult().getOutput().getText();
//    }
//}
