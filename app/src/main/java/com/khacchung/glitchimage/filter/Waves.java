package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Waves extends FilterRender {
    private static float f6264E = 0.0f;
    private static float f6265F = (MyApplication.imgWidth / 2.0f);
    private static float f6266I = 0.0f;
    private static float f6267J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6268v = false;
    private float f6269A;
    private long f6270B = System.currentTimeMillis();
    private String f6271C = "touchX";
    private int f6272D;
    private String f6273G = "touchY";
    private int f6274H;
    private long f6275K;
    private int f6276w;
    private int f6277x;
    private String f6278y = "iTime";
    private int f6279z;

    public Waves() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 1.0-mouse.x/iResolution.x*7.0;\nfloat amounty = 10.0+mouse.y/iResolution.y*60.0;\nvec2 uv = gl_FragCoord.xy; \nuv /= amounty;\nfloat t = 0.+fract(iTime*5.);\nt=fract(t);\nfloat y = floor(uv.y-.5+t)-t;\n#define T texture2D(inputImageTexture,amounty*vec2(uv.x,y)/iResolution.xy)\ngl_FragColor += cos( 6.28*(uv.y-y) + amountx*(2.*T-1.) ) -gl_FragColor;\n}");
        this.f6275K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6279z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6278y);
        this.f6272D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6271C);
        this.f6274H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6273G);
        this.f6276w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6277x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6279z, this.f6269A);
        GLES20.glUniform1f(this.f6272D, f6264E);
        GLES20.glUniform1f(this.f6274H, f6266I);
        GLES20.glUniform1f(this.f6276w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6277x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6268v) {
            this.f6270B = System.currentTimeMillis() - this.f6275K;
            if (this.f6270B > 10000) {
                this.f6275K = System.currentTimeMillis();
            }
        }
        this.f6269A = (((float) this.f6270B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f6264E = f6265F;
        f6266I = f6267J;
    }

    public static void change(float f, float f2) {
        f6265F = f;
        f6267J = f2;
    }
}
