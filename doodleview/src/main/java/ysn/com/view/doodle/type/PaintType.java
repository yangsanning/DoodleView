package ysn.com.view.doodle.type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author yangsanning
 * @ClassName PaintType
 * @Description 涂鸦画笔类型
 * @Date 2019/8/9
 * @History 2019/8/9 author: description:
 */
@IntDef({PaintType.PATH, PaintType.LINE, PaintType.RECT, PaintType.CIRCLE,
        PaintType.FILL_RECT, PaintType.FILLED_CIRCLE})
@Retention(RetentionPolicy.SOURCE)
public @interface PaintType {

    /**
     * 曲线
     */
    int PATH = 0;

    /**
     * 直线
     */
    int LINE = 1;

    /**
     * 矩形
     */
    int RECT = 2;

    /**
     * 空心圆
     */
    int CIRCLE = 3;

    /**
     * 实心矩形
     */
    int FILL_RECT = 4;

    /**
     * 实心圆
     */
    int FILLED_CIRCLE = 5;
}
