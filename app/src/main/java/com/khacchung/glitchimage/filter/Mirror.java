package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Mirror extends FilterRender {
    private static float f6022A = (MyApplication.imgWidth / 2.0f);
    private static float f6023D;
    private static float f6024E = (MyApplication.imgHeight / 2.0f);
    private static float f6025z;
    private String f6026B = "touchY";
    private int f6027C;
    private int f6028v;
    private int f6029w;
    private String f6030x = "touchX";
    private int f6031y;

    public Mirror() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.1+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nif (amountx <= 0.25){\nuv.x = abs(uv.x - 0.5) + 0.5;\n}else if (amountx <= 0.5){\nuv.x   = abs(uv.x - 0.5) + 0.;\n}else if (amountx <= 0.75){\nuv.y = abs(uv.y - 0.5) + 0.5;\n}else if (amountx <= 1.0){\nuv.y = abs(uv.y - 0.5) + 0.0;\n}\nvec4 image1 = texture2D(inputImageTexture, uv);\ngl_FragColor = image1;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6031y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6030x);
        this.f6027C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6026B);
        this.f6028v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6029w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6031y, f6025z);
        GLES20.glUniform1f(this.f6027C, f6023D);
        GLES20.glUniform1f(this.f6028v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6029w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6025z = f6022A;
        f6023D = f6024E;
    }

    public static void change(float f, float f2) {
        f6022A = f;
        f6024E = f2;
    }
}
