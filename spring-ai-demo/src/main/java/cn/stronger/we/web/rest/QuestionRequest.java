package cn.stronger.we.web.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class QuestionRequest.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionRequest implements Serializable {
    private String message;
    private String userId = "default";
    private String userName = "default";
    private String terminalNo = "local";
    private String clientNo = "local";
    private List<MessageModel> messages;
}
