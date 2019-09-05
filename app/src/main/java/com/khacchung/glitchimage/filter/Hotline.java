package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Hotline extends FilterRender {
    private static float f6504E = 0.0f;
    private static float f6505F = (MyApplication.imgWidth / 2.0f);
    private static float f6506I = 0.0f;
    private static float f6507J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6508v = false;
    private float f6509A;
    private long f6510B = System.currentTimeMillis();
    private String f6511C = "touchX";
    private int f6512D;
    private String f6513G = "touchY";
    private int f6514H;
    private long f6515K;
    private int f6516w;
    private int f6517x;
    private String f6518y = "iTime";
    private int f6519z;

    public Hotline() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 size = vec2(50.0, 50.0);\nvec2 distortion = vec2((30.0 * amountx), (30.0 * amountx));\nfloat speed = .75;\nvec2 transformed = vec2(\ngl_FragCoord.x + sin(gl_FragCoord.y / size.x + iTime * speed) * distortion.x,\ngl_FragCoord.y + cos(gl_FragCoord.x / size.y + iTime * speed) * distortion.y\n);\nvec2 relCoord = gl_FragCoord.xy / iResolution.xy;\ngl_FragColor = texture2D(inputImageTexture, transformed / iResolution.xy) + vec4(\n(cos(relCoord.x + iTime * speed * 4.0) + 1.0) / 2.0,\n(relCoord.x + relCoord.y) / 2.0,\n(sin(relCoord.y + iTime * speed) + 1.0) / 2.0, 0 ) / (1. / amountx);\n}\n");
        this.f6515K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6519z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6518y);
        this.f6512D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6511C);
        this.f6514H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6513G);
        this.f6516w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6517x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6519z, this.f6509A);
        GLES20.glUniform1f(this.f6512D, f6504E);
        GLES20.glUniform1f(this.f6514H, f6506I);
        GLES20.glUniform1f(this.f6516w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6517x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6508v) {
            this.f6510B = System.currentTimeMillis() - this.f6515K;
            if (this.f6510B > 50000) {
                this.f6515K = System.currentTimeMillis();
            }
        }
        this.f6509A = (((float) this.f6510B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f6504E = f6505F;
        f6506I = f6507J;
    }

    public static void change(float f, float f2) {
        f6505F = f;
        f6507J = f2;
    }
}
