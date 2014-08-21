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
	private boolean toggleState = false;// ����״̬

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		matrix = new Matrix();
		paint = new Paint();
	}

	/**
	 * ���ÿ��ص���ԴͼƬ
	 * 
	 * @param backgroundResID
	 *            ����ͼƬ
	 * @param slideResID
	 *            ������ť��ͼƬ
	 */
	public void setImagesResID(int backgroundResID, int slideResID) {
		backgroundBitmap = BitmapFactory.decodeResource(getResources(),
				backgroundResID);
		slideButtonBitmap = BitmapFactory.decodeResource(getResources(),
				slideResID);
		// ���عر�״̬�ľ���
		rectOff = new Rect(0, 0, slideButtonBitmap.getWidth(),
				backgroundBitmap.getHeight());
		// ���ؿ���״̬�ľ���
		rectON = new Rect(backgroundBitmap.getWidth()
				- slideButtonBitmap.getWidth(), 0, backgroundBitmap.getWidth(),
				backgroundBitmap.getHeight());
	}

	/**
	 * ���������,��ʼ���ؼ��Ŀ��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//
		setMeasuredDimension(backgroundBitmap.getWidth(),
				backgroundBitmap.getHeight());
	}

	/**
	 * ���ƿؼ������,�ֶ����� invailidate()����ʱ,Ҳ�ܵ��� onDraw
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// ���Ʊ���ͼƬ
		canvas.drawBitmap(backgroundBitmap, matrix, paint);
		if (isSliding) {// ����ʱ
			int left = currentX - slideButtonBitmap.getWidth() / 2;
			// �߽�
			if (left < rectOff.left) {
				left = 0;
			} else if (left > rectON.left) {
				left = rectON.left;
			}
			canvas.drawBitmap(slideButtonBitmap, left, 0, paint);
		} else {// ���ʱ
			// ���ƻ���ͼƬ
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
		case MotionEvent.ACTION_DOWN:// ����
			isSliding = true;
			currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:// �ƶ�
			currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:// ̧��
			isSliding = false;
			currentX = (int) event.getX();
			// �ڻ�����ĳһ��ʱ,
			boolean state = currentX > backgroundBitmap.getWidth() / 2;//
			if (this.listener != null) {
				if(state != toggleState){//���״̬û�иı�
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
