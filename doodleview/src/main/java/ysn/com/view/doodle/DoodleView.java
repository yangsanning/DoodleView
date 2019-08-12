package ysn.com.view.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import ysn.com.paint.CirclePaint;
import ysn.com.paint.FillCirclePaint;
import ysn.com.paint.FillRectPaint;
import ysn.com.paint.LinePaint;
import ysn.com.paint.PathPaint;
import ysn.com.paint.RectPaint;
import ysn.com.paint.base.BasePaint;
import ysn.com.view.doodle.type.PaintType;

/**
 * @Author yangsanning
 * @ClassName DoodleView
 * @Description 涂鸦View
 * @Date 2019/8/9
 * @History 2019/8/9 author: description:
 */
public class DoodleView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder = null;

    /**
     * 画笔类型
     */
    private @PaintType
    int paintType = PaintType.PATH;

    /**
     * 画笔颜色(默认黑色)
     */
    private int paintColor = Color.BLACK;

    /**
     * 画笔宽度
     */
    private int strokeWidth = 5;

    /**
     * 画笔
     */
    private BasePaint doodlePaint = null;

    private Paint paint;

    /**
     * 画笔集合
     */
    private List<BasePaint> doodlePaintList;

    /**
     * 涂鸦画
     */
    private Bitmap bitmap;

    public DoodleView(Context context) {
        this(context, null);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        initPaint();
    }

    private void init() {
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        this.setFocusable(true);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        // 将画布解锁并显示在屏幕上
        surfaceHolder.unlockCanvasAndPost(canvas);
        doodlePaintList = new ArrayList<>();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initDoodlePaint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                for (BasePaint paint : doodlePaintList) {
                    paint.onDraw(canvas);
                }
                doodlePaint.onMove(event.getX(), event.getY());
                doodlePaint.onDraw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
                break;
            case MotionEvent.ACTION_UP:
                doodlePaintList.add(doodlePaint);
                doodlePaint = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                return false;
            default:
                break;
        }
        return true;
    }

    /**
     * 根据画笔类型初始化画笔
     */
    private void initDoodlePaint(float x, float y) {
        switch (paintType) {
            case PaintType.PATH:
                doodlePaint = new PathPaint(x, y, strokeWidth, paintColor);
                break;
            case PaintType.LINE:
                doodlePaint = new LinePaint(x, y, strokeWidth, paintColor);
                break;
            case PaintType.RECT:
                doodlePaint = new RectPaint(x, y, strokeWidth, paintColor);
                break;
            case PaintType.CIRCLE:
                doodlePaint = new CirclePaint(x, y, strokeWidth, paintColor);
                break;
            case PaintType.FILL_RECT:
                doodlePaint = new FillRectPaint(x, y, strokeWidth, paintColor);
                break;
            case PaintType.FILLED_CIRCLE:
                doodlePaint = new FillCirclePaint(x, y, strokeWidth, paintColor);
                break;
            default:
                break;
        }
    }

    /**
     * 设置画笔的类型
     *
     * @param paintType 画笔类型
     */
    public DoodleView setPaintType(@PaintType int paintType) {
        this.paintType = paintType;
        return this;
    }

    /**
     * 设置画笔的颜色
     *
     * @param colorStr 十六进制颜色值(#ff0000)
     */
    public DoodleView setColor(String colorStr) {
        this.paintColor = Color.parseColor(colorStr);
        return this;
    }

    /**
     * 设置画笔的颜色
     *
     * @param colorInt 颜色值
     */
    public DoodleView setColor(@ColorInt int colorInt) {
        this.paintColor = colorInt;
        return this;
    }

    /**
     * 设置画笔宽度
     *
     * @param strokeWidth 画笔宽度
     */
    public DoodleView setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    /**
     * 获取当前涂鸦画
     *
     * @return Bitmap
     */
    public Bitmap getDoodleBitmap() {
        // 将当前的画布转换成一个 Bitmap
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        for (BasePaint action : doodlePaintList) {
            action.onDraw(canvas);
        }
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmap;
    }

    /**
     * 回退
     *
     * @return true: 回退成功, false: 回退失败。
     */
    public boolean back() {
        if (doodlePaintList != null && doodlePaintList.size() > 0) {
            doodlePaintList.remove((doodlePaintList.size() - 1));
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BasePaint action : doodlePaintList) {
                action.onDraw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
            return true;
        }
        return false;
    }

    /**
     * 归零（重置）
     */
    public void reset() {
        if (doodlePaintList != null && doodlePaintList.size() > 0) {
            doodlePaintList.clear();
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BasePaint action : doodlePaintList) {
                action.onDraw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}