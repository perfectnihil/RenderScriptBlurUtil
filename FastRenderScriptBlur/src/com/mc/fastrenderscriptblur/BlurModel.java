package com.mc.fastrenderscriptblur;

import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;


/**
 * Created by The_One_Su on 2015/6/19.
 */
public class BlurModel {
    public Allocation mInAllocation;
    public Allocation mOutAllocations;
    public Bitmap mBitmapsOut;
    public  int mRadius;
    public ScriptIntrinsicBlur mScriptBlur;
    public RenderScript mRS;

    public IBlurCallBack callBack;
}
