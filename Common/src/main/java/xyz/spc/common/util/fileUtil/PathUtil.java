package xyz.spc.common.util.fileUtil;

import cn.hutool.core.util.StrUtil;

/**
 * 路径工具类
 */
public final class PathUtil {


    /**
     * 检查路径合法性：
     * - 不为空，且不包含 \ / 字符
     *
     * @param names 文件路径
     * @throws IllegalArgumentException 文件名包含非法字符时会抛出此异常
     */
    private static void checkNameSecurity(String... names) {
        for (String name : names) {
            // 路径中不能包含 .. 不然可能会获取到上层文件夹的内容
            if (StrUtil.containsAny(name, "\\", "/")) {
                throw new IllegalArgumentException("文件名存在安全隐患: " + name);
            }
        }
    }
}
