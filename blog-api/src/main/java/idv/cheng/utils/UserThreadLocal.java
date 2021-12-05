package idv.cheng.utils;

import idv.cheng.dao.pojo.SysUser;

/**
 * @author cheng
 * @since 2021/12/5 14:08
 **/
public class UserThreadLocal {
    private UserThreadLocal() {
    }

    // 執行緒變數隔離
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
