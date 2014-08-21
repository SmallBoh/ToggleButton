package com.itheima.togglebutton.view;

import com.itheima.togglebutton.view.interf.OnToggleStateChangeListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleView extends View {

	private Bitmap backgroundBitmap;
	private Bitmap slideButtonBitmap;
	private Rect rectOff, rectON;
	private Matrix matrix;
	private Paint paint;
	private boolean toggleState = false;// 开关状态

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		matrix = new Matrix();
		paint = new Paint();
	}

	/**
	 * 设置开关的资源图片
	 * 
	 * @param backgroundResID
	 *            背景图片
	 * @param slideResID
	 *            滑动按钮的图片
	 */
	public void setImagesResID(int backgroundResID, int slideResID) {
		backgroundBitmap = BitmapFactory.decodeResource(getResources(),
				backgroundResID);
		slideButtonBitmap = BitmapFactory.decodeResource(getResources(),
				slideResID);
		// 开关关闭状态的矩形
		rectOff = new Rect(0, 0, slideButtonBitmap.getWidth(),
				backgroundBitmap.getHeight());
		// 开关开启状态的矩形
		rectON = new Rect(backgroundBitmap.getWidth()
				- slideButtonBitmap.getWidth(), 0, backgroundBitmap.getWidth(),
				backgroundBitmap.getHeight());
	}

	/**
	 * 测量里调用,初始化控件的宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//
		setMeasuredDimension(backgroundBitmap.getWidth(),
				backgroundBitmap.getHeight());
	}

	/**
	 * 绘制控件里调用,手动调用 invailidate()方法时,也能调用 onDraw
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制背景图片
		canvas.drawBitmap(backgroundBitmap, matrix, paint);
		if (isSliding) {// 滑动时
			//计算出x轴偏移量的占的值,在滑动点中间的位置
			int left = currentX - slideButtonBitmap.getWidth() / 2;
			// 边界
			if (left < rectOff.left) {
				left = 0;
			} else if (left > rectON.left) {
				left = rectON.left;
			}
			canvas.drawBitmap(slideButtonBitmap, left, 0, paint);
		} else {// 点击时
			// 绘制滑动图片
			if (toggleState) {
				canvas.drawBitmap(slideButtonBitmap, rectON.left, rectON.top,
						paint);
			} else {
				canvas.drawBitmap(slideButtonBitmap, rectOff.left, rectOff.top,
						paint);
			}
		}
	}

	public void setCurrentToggleState(boolean state) {
		this.toggleState = state;
		this.invalidate();
	}

	public boolean getCurrentToggleState() {
		return this.toggleState;
	}

	private int currentX;
	private boolean isSliding = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// 按下
			isSliding = true;
			currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:// 移动
			currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:// 抬起
			isSliding = false;
			currentX = (int) event.getX();
			// 在滑动到某一边时,
			boolean state = currentX > backgroundBitmap.getWidth() / 2;//
			if (this.listener != null) {
				if(state != toggleState){//如果状态没有改变
					this.listener.onToggleState(state);
				}
			}
			
			toggleState = state;
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}

	private OnToggleStateChangeListener listener;

	public void setOnToggleStateChangeListener(
			OnToggleStateChangeListener listener) {
		this.listener = listener;
	}

}
