package cn.stronger.we.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class AiChatLog.class
 * @department Platform R&D
 * @date 2025/2/26
 * @desc do what?
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ai_chat_log")
public class AiChatLog implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String userId;
    private String userName;
    private String terminalNo;
    private String clientNo;
    private String question;
    private Date createTime;

    public AiChatLog(String userId, String userName, String terminalNo, String clientNo, String question) {
        this.userId = userId;
        this.userName = userName;
        this.terminalNo = terminalNo;
        this.clientNo = clientNo;
        this.question = question;
        this.createTime = new Date();
    }
}
