package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Fall extends FilterRender {
    private static float f6436A = (MyApplication.imgWidth / 2.0f);
    private static float f6437D;
    private static float f6438E = (MyApplication.imgHeight / 2.0f);
    private static float f6439z;
    private String f6440B = "touchY";
    private int f6441C;
    private int f6442v;
    private int f6443w;
    private String f6444x = "touchX";
    private int f6445y;

    public Fall() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat pour( float y, float amount ) {\nfloat t = min( 1., amount), d = (y - t);\nreturn (y < t) ? t - d/1000. : y;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\namounty = 1.0 - amounty;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nuv.y = pour( uv.y, amounty );\ngl_FragColor = texture2D( inputImageTexture, uv );\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6445y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6444x);
        this.f6441C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6440B);
        this.f6442v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6443w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6445y, f6439z);
        GLES20.glUniform1f(this.f6441C, f6437D);
        GLES20.glUniform1f(this.f6442v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6443w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6439z = f6436A;
        f6437D = f6438E;
    }

    public static void change(float f, float f2) {
        f6436A = f;
        f6438E = f2;
    }
}
