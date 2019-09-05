package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Gba1 extends FilterRender {
    private static float f6288A = (MyApplication.imgWidth / 2.0f);
    private static float f6289D;
    private static float f6290E = (MyApplication.imgHeight / 2.0f);
    private static float f6291z;
    private String f6292B = "touchY";
    private int f6293C;
    private int f6294v;
    private int f6295w;
    private String f6296x = "touchX";
    private int f6297y;

    public Gba1() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define GB_SOURCE_ADD -0.267\n#define GB_SOURCE_POWER 0.5\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0-mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy * amountx;\nif (uv.x <= 1. && uv.y <= 1.) {\nvec4 color = texture2D(inputImageTexture, uv);\nfloat gray = (color.r * 0.8 + color.g * 1.0 + color.b * 0.9) / 2.7;\ngray = pow(clamp(gray + GB_SOURCE_ADD, 0., 1.), GB_SOURCE_POWER);\ngl_FragColor = vec4(gray, gray, gray, 1.);\n} else {\ngl_FragColor = vec4(0,0,0,1);\n}\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6297y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6296x);
        this.f6293C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6292B);
        this.f6294v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6295w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6297y, f6291z);
        GLES20.glUniform1f(this.f6293C, f6289D);
        GLES20.glUniform1f(this.f6294v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6295w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6291z = f6288A;
        f6289D = f6290E;
    }

    public static void change(float f, float f2) {
        f6288A = f;
        f6290E = f2;
    }
}
