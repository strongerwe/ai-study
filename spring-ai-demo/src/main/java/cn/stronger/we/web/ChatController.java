package cn.stronger.we.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class ChatController.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@Controller
public class ChatController {
    @RequestMapping("/ai")
    public String chat()
    {
        return "chat.html";
    }
}
