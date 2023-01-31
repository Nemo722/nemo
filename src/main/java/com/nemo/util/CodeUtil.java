package com.nemo.util;

import java.text.FieldPosition;
import java.text.NumberFormat;

import static java.text.NumberFormat.INTEGER_FIELD;

/**
 * @author Nemo
 */
public class CodeUtil {

    /**
     * 生成对应格式的编号
     *
     * @param number           需要编码的数字
     * @param mumIntegerDigits 编号里数字的整数位数（传入数字不足该位数会自动补零）
     * @param prefix           编号前缀
     * @return 返回对应格式的编号
     * 例如传入1，8，TEST  返回TEST00000001
     */
    private static String generator(int number, int mumIntegerDigits, String prefix) {

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置最小整数位数
        numberFormat.setMinimumIntegerDigits(mumIntegerDigits);
        // 去掉逗号
        numberFormat.setGroupingUsed(false);

        // 前缀
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefix);

        return numberFormat.format(number, buffer, new FieldPosition(INTEGER_FIELD)).toString();
    }

    /**
     * 生成对应格式的编号
     *
     * @param number           需要编码的数字
     * @param mumIntegerDigits 编号里数字的整数位数（传入数字不足该位数会自动补零）
     * @return 返回对应格式的编号
     * 例如传入1，8  返回00000001
     */
    private static String generator(int number, int mumIntegerDigits) {

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置最小整数位数
        numberFormat.setMinimumIntegerDigits(mumIntegerDigits);
        // 去掉逗号
        numberFormat.setGroupingUsed(false);

        return numberFormat.format(number);
    }
}
