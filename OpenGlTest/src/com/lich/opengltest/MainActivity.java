package com.lich.opengltest;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		GLSurfaceView glView = new GLSurfaceView(this);  
        // 创建GLSurfaceView的内容绘制器  
        MyRenderer myRender = new MyRenderer(this,glView);  
        // 为GLSurfaceView设置绘制器  
        glView.setRenderer(myRender);  
        setContentView(glView);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}