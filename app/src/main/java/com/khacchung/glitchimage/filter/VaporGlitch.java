package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class VaporGlitch extends FilterRender {
    private static float f6238E = 0.0f;
    private static float f6239F = (MyApplication.imgWidth / 2.0f);
    private static float f6240I = 0.0f;
    private static float f6241J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6242v = false;
    private float f6243A;
    private long f6244B = System.currentTimeMillis();
    private String f6245C = "touchX";
    private int f6246D;
    private String f6247G = "touchY";
    private int f6248H;
    private long f6249K;
    private int f6250w;
    private int f6251x;
    private String f6252y = "iTime";
    private int f6253z;

    public VaporGlitch() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\nreturn fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 10.0);\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 2.0+mouse.y/iResolution.y*1.0;\nvec2 uv = vec2(gl_FragCoord.x + tan(gl_FragCoord.y * iTime * 10.0 * rand(vec2(iTime))) * (amountx*10.), gl_FragCoord.y) / iResolution.xy;\nvec4 t = texture2D(inputImageTexture, uv);\nvec4 tt = t;\ntt.r *= texture2D(inputImageTexture, uv - (amountx*0.1) * tan(gl_FragCoord.y * rand(vec2(iTime)))).r * t.r * amounty;\ntt.b *= texture2D(inputImageTexture, uv + (amountx*0.1) * tan(gl_FragCoord.y * rand(vec2(iTime)))).b * t.b * amounty;\ntt.g /= amounty*0.5;\ngl_FragColor = tt * 1.0;\n}");
        this.f6249K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6253z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6252y);
        this.f6246D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6245C);
        this.f6248H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6247G);
        this.f6250w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6251x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6253z, this.f6243A);
        GLES20.glUniform1f(this.f6246D, f6238E);
        GLES20.glUniform1f(this.f6248H, f6240I);
        GLES20.glUniform1f(this.f6250w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6251x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6242v) {
            this.f6244B = System.currentTimeMillis() - this.f6249K;
            if (this.f6244B > 2000) {
                this.f6249K = System.currentTimeMillis();
            }
        }
        this.f6243A = (((float) this.f6244B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6238E = f6239F;
        f6240I = f6241J;
    }

    public static void change(float f, float f2) {
        f6239F = f;
        f6241J = f2;
    }
}
