package cn.tedu.csmall.passport.web;

/**
 * 业务状态码
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface ServiceCode {

    /**
     * 成功
     */
    Integer OK = 20000;
    /**
     * 请求参数的格式错误
     */
    Integer ERR_BAD_REQUEST = 40000;
    /**
     * 相关资源或数据不存在
     */
    Integer ERR_NOT_FOUND = 40400;
    /**
     * 数据冲突，例如尝试添加不允许重复的数据
     */
    Integer ERR_CONFLICT = 40900;
    /**
     * 服务器内部错误，通常是未确定的异常类型
     */
    Integer ERR_INTERNAL_SERVER_ERROR = 50000;
    /**
     * 插入数据时的错误
     */
    Integer ERR_INSERT = 50001;
    /**
     * 删除数据时的错误
     */
    Integer ERR_DELETE = 50002;
    /**
     * 修改数据时的错误
     */
    Integer ERR_UPDATE = 50003;

}
