package cn.stronger.we.web;


import java.io.Serializable;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class QuestionRequest.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
public class QuestionRequest implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QuestionRequest(String message) {
        this.message = message;
    }

    public QuestionRequest(){

    }
}
