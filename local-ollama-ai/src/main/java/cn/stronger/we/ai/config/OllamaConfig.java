package cn.stronger.we.ai.config;


import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class OllamaConfig.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@Configuration
public class OllamaConfig {

    @Value("${ai.ollama.model:deepseek-r1:1.5b}")
    private String localModel;

    @Bean
    public OllamaChatClient ollamaChatClient() {
        OllamaApi ollamaApi = new OllamaApi();
        OllamaOptions options = new OllamaOptions();
        options.setModel("deepseek-r1:1.5b");
        return new OllamaChatClient(ollamaApi).withDefaultOptions(options);
    }
}
