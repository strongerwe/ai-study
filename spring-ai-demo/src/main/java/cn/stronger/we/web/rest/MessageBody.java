package cn.stronger.we.web.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class MessageBody.class
 * @department Platform R&D
 * @date 2025/2/27
 * @desc do what?
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MessageBody implements Serializable {

    private String text;
    private long timestamp;
}
