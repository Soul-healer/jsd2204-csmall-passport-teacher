package cn.tedu.csmall.passport.web;

import cn.tedu.csmall.passport.ex.ServiceException;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一的响应结果类型
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class JsonResult<T> implements Serializable {

    /**
     * 业务状态码
     */
    private Integer state;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public static JsonResult ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(ServiceCode.OK);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult<Void> fail(ServiceException e) {
        return fail(e.getState(), e.getMessage());
    }

    public static JsonResult<Void> fail(Integer state, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(state);
        jsonResult.setMessage(message);
        return jsonResult;
    }

}