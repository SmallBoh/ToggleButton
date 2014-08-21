package com.itheima.togglebutton.view.interf;

/**
 * 当开关改变时的监听事件
 * 
 * @author lenovo
 * 
 */
public interface OnToggleStateChangeListener {
	/**
	 * 当开关状态改变时的方法
	 * @param currentState 改变后的值
	 */
	void onToggleState(boolean currentState);
}
