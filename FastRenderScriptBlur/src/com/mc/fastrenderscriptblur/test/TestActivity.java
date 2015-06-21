package com.mc.fastrenderscriptblur.test;

import com.mc.fastrenderscriptblur.BlurUtil;
import com.mc.fastrenderscriptblur.IBlurCallBack;
import com.mc.fastrenderscriptblur.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class TestActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mmainlayout);
		final ImageView iv = (ImageView) findViewById(R.id.iv_main);
		Bitmap mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		BlurUtil.doBlur(this, mBmp, new IBlurCallBack() {
			@Override
			public void BlurFinished(Bitmap bitmap) {
				iv.setImageBitmap(bitmap);
			}
		});
		
		
	}

}
