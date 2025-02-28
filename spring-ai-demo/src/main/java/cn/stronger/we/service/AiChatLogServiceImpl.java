package cn.stronger.we.service;

import cn.stronger.we.mapper.AiChatLogMapper;
import cn.stronger.we.mapper.entity.AiChatLog;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class AiChatLogServiceImpl.class
 * @department Platform R&D
 * @date 2025/2/26
 * @desc do what?
 */
@Slf4j
@Service
public class AiChatLogServiceImpl extends ServiceImpl<AiChatLogMapper, AiChatLog> implements AiChatLogService {

    private final ThreadPoolTaskExecutor taskExecutor;
    private ExecutorService executorService;

    public AiChatLogServiceImpl(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    public void init() {
        executorService = taskExecutor.getThreadPoolExecutor();
    }

    @PreDestroy
    public void destroy() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Override
    public void addLog(AiChatLog aiChatLog) {
        executorService.submit(() -> {
            try {
                log.info("[AiChatLogServiceImpl.addLog]保存aiChat日志|{}", JSONObject.toJSONString(aiChatLog));
                save(aiChatLog);
            } catch (Exception e) {
                log.error("[AiChatLogServiceImpl.addLog] 添加日志出现异常", e);
            }
        });
    }
}