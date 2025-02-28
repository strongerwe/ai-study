package cn.stronger.we.service;


import cn.stronger.we.mapper.entity.AiChatLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @interface AiChatLogService.class
 * @department Platform R&D
 * @date 2025/2/26
 * @desc do what?
 */
public interface AiChatLogService extends IService<AiChatLog> {

    /**
     * 新增日志
     *
     * @param log aiChatLog
     */
    void addLog(AiChatLog log);
}
