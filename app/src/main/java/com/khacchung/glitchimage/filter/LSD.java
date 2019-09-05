package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class LSD extends FilterRender {
    private static float f6546A = (MyApplication.imgWidth / 2.0f);
    private static float f6547D;
    private static float f6548E = (MyApplication.imgHeight / 2.0f);
    private static float f6549z;
    private String f6550B = "touchY";
    private int f6551C;
    private int f6552v;
    private int f6553w;
    private String f6554x = "touchX";
    private int f6555y;

    public LSD() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0-mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec4 t = texture2D(inputImageTexture, uv);\ngl_FragColor = smoothstep(vec4(0.0), vec4(0.9),fract(t*(1.6 * 1.6)* (amountx+0.05) * 4.));\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6555y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6554x);
        this.f6551C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6550B);
        this.f6552v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6553w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6555y, f6549z);
        GLES20.glUniform1f(this.f6551C, f6547D);
        GLES20.glUniform1f(this.f6552v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6553w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6549z = f6546A;
        f6547D = f6548E;
    }

    public static void change(float f, float f2) {
        f6546A = f;
        f6548E = f2;
    }
}
