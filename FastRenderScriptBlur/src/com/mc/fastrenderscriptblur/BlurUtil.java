package com.mc.fastrenderscriptblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.Matrix3f;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.ScriptIntrinsicColorMatrix;
import android.support.v8.renderscript.ScriptIntrinsicConvolve5x5;
import android.widget.ImageView;



/**
 * 使用RenderScript 渲染图像  异步。。。
 * Created by The_One_Su on 2015/6/19.
 */
public class BlurUtil {
    public static void  doBlur (Context context,Bitmap mBitmapIn,IBlurCallBack callBack){
        RenderScript  mRS = RenderScript.create(context);
        Allocation mInAllocation =  Allocation.createFromBitmap(mRS, mBitmapIn);
        Bitmap mBitmapsOut =  Bitmap.createBitmap(mBitmapIn.getWidth(),
                mBitmapIn.getHeight(), mBitmapIn.getConfig());
        Allocation   mOutAllocations = Allocation.createFromBitmap(mRS, mBitmapsOut);
        ScriptIntrinsicBlur mScriptBlur = ScriptIntrinsicBlur.create(mRS, Element.U8_4(mRS));
        ScriptIntrinsicConvolve5x5 mScriptConvolve = ScriptIntrinsicConvolve5x5.create(mRS,
                Element.U8_4(mRS));
        ScriptIntrinsicColorMatrix mScriptMatrix = ScriptIntrinsicColorMatrix.create(mRS,
                Element.U8_4(mRS));
        BlurModel model = new BlurModel();
        model.mScriptBlur = mScriptBlur;
        model.callBack = callBack;
        model.mRS = mRS;
        model.mBitmapsOut = mBitmapsOut;
        model.mInAllocation = mInAllocation;
        model.mOutAllocations = mOutAllocations;
        model.mRadius = 25;
        new RenderScriptTask().execute(model);
    }
    private static class RenderScriptTask extends AsyncTask<BlurModel, BlurModel, BlurModel> {
        private  IBlurCallBack mCallback;
        protected BlurModel doInBackground(BlurModel... values) {
                BlurModel model = values[0];
                performFilter(model);
            return model;
        }
        protected void onPostExecute(BlurModel model) {
            model.callBack.BlurFinished(model.mBitmapsOut);
            model.mRS.destroy();
        }
    }
    private static  void performFilter(BlurModel model) {
        model.mScriptBlur.setRadius(model.mRadius);
        model.mScriptBlur.setInput(model.mInAllocation);
        model.mScriptBlur.forEach(model.mOutAllocations);
        model.mOutAllocations.copyTo(model.mBitmapsOut);
    }
}
