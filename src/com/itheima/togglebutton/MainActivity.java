package com.itheima.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.itheima.togglebutton.view.ToggleView;
import com.itheima.togglebutton.view.interf.OnToggleStateChangeListener;

public class MainActivity extends Activity implements OnToggleStateChangeListener{

	private ToggleView toggleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toggleView = (ToggleView)findViewById(R.id.toggleview);
		int backgroundResID = R.drawable.switch_background;
		int slideResID =R.drawable.slide_button_background;
		toggleView.setImagesResID(backgroundResID,slideResID);
		toggleView.setCurrentToggleState(!true);
		toggleView.setOnToggleStateChangeListener(this);
	}

	@Override
	public void onToggleState(boolean currentState) {
		Toast.makeText(getApplicationContext(), currentState ? "¿ªÆô":"¹Ø±Õ", 0).show();
	}
}
