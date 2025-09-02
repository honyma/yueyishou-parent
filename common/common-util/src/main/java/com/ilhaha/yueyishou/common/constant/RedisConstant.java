package com.ilhaha.yueyishou.common.constant;

public class RedisConstant {

    //用户登录
    public static final String USER_LOGIN_KEY_PREFIX = "user:login:";

    //后管运营登录
    public static final String OPERATE_LOGIN_KEY_PREFIX = "operate:login:";

    //用户微信登录sessionKey
    public static final String USER_LOGIN_SESSION_KEY_PREFIX = "user:session:";

    // 回收员ID
    public static final String RECYCLER_INFO_KEY_PREFIX = "recycler:info:";

    //用户登录过期时间7天
    public static final int USER_LOGIN_KEY_TIMEOUT = 60 * 60 * 24 * 7;

    //回收员GEO地址
    public static final String RECYCLER_GEO_LOCATION = "recycler:geo:location";

    //废品回收品类
    public static final String CATEGORY_TREE = "category:tree";

    //待接单的订单
    public static final String WAITING_ORDER = "waiting:order:";

    //查询所有待接单的订单key
    public static final String SELECT_WAITING_ORDER = "waiting:order:*";
}
