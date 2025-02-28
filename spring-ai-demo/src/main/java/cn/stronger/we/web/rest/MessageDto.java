package cn.stronger.we.web.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class MessageDto.class
 * @department Platform R&D
 * @date 2025/2/26
 * @desc do what?
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MessageDto implements Serializable {
    private long timestamp;
    private String messageType;
    private String text;

    public MessageDto(long timestamp, String messageType, String text) {
        this.timestamp = timestamp;
        this.messageType = messageType;
        if (text.length() > 5000) {
            this.text = text.substring(0, 5000);
        } else {
            this.text = text;
        }
    }
}
