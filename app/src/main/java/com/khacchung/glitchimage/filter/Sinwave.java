package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Sinwave extends FilterRender {
    private static float f6166E = 0.0f;
    private static float f6167F = (MyApplication.imgWidth / 2.0f);
    private static float f6168I = 0.0f;
    private static float f6169J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6170v = false;
    private float f6171A;
    private long f6172B = System.currentTimeMillis();
    private String f6173C = "touchX";
    private int f6174D;
    private String f6175G = "touchY";
    private int f6176H;
    private long f6177K;
    private int f6178w;
    private int f6179x;
    private String f6180y = "iTime";
    private int f6181z;

    public Sinwave() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*0.5;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat POWER = amountx;\nfloat VERTICAL_SPREAD = 4.0;\nfloat ANIM_SPEED = 0.4;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 uv1 = gl_FragCoord.xy / iResolution.xy;\nfloat y = (uv.y + iTime * ANIM_SPEED) * VERTICAL_SPREAD;\nuv.x += ( \nsin(y) \n + sin(y * 0.0) * 0.2 \n + sin(y * 0.0) * 0.03\n)\n * POWER\n * sin(uv.y * 3.14)\n * sin(iTime);\nfloat r = texture2D(inputImageTexture, uv).r;\nfloat g = texture2D(inputImageTexture, uv1).g;\nfloat b = texture2D(inputImageTexture, uv).b;\ngl_FragColor = vec4(r,g,b,1.0);\n}");
        this.f6177K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6181z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6180y);
        this.f6174D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6173C);
        this.f6176H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6175G);
        this.f6178w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6179x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6181z, this.f6171A);
        GLES20.glUniform1f(this.f6174D, f6166E);
        GLES20.glUniform1f(this.f6176H, f6168I);
        GLES20.glUniform1f(this.f6178w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6179x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6170v) {
            this.f6172B = System.currentTimeMillis() - this.f6177K;
            if (this.f6172B > 20000) {
                this.f6177K = System.currentTimeMillis();
            }
        }
        this.f6171A = (((float) this.f6172B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6166E = f6167F;
        f6168I = f6169J;
    }

    public static void change(float f, float f2) {
        f6167F = f;
        f6169J = f2;
    }
}
