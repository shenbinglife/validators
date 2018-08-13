package io.github.shenbinglife.validators;

/**
 * 常量定义
 *
 * @author shenbing
 * @version 2017/11/23
 * @since since
 */
public interface Consts {

    /**
     * 默认的校验错误码
     */
    int DEFAULT_ECODE = -1;

    /**
     * 默认的字段值
     */
    String DEFAULT_FIELD = "field";

    /**
     * 默认的错误信息字符串插值参数
     */
    public static class MessageFillParams {

        /**
         * 字段插值参数
         */
        public static final String FIELD = "field";

        /**
         * 校验对象参数
         */
        public static final String VAL = "val";
    }
}
