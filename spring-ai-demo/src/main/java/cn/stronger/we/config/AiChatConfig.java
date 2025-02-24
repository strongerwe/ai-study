package cn.stronger.we.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class AiChatConfig.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@Configuration
public class AiChatConfig {
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient
                .builder(openAiChatModel)
                .build();
    }
}
