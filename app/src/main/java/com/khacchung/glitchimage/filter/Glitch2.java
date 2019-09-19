package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Glitch2 extends FilterRender {
    private static float f6488E = 0.0f;
    private static float f6489F = (MyApplication.imgWidth / 2.0f);
    private static float f6490I = 0.0f;
    private static float f6491J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6492v = false;
    private float f6493A;
    private long f6494B = System.currentTimeMillis();
    private String f6495C = "touchX";
    private int f6496D;
    private String f6497G = "touchY";
    private int f6498H;
    private long f6499K;
    private int f6500w;
    private int f6501x;
    private String f6502y = "iTime";
    private int f6503z;

    public Glitch2() {
        setFragmentShader("precision mediump float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 0.7);\n}\nfloat randomRange (in vec2 seed, in float min, in float max) {\nreturn min + random2d(seed) * (max - min);\n}\nfloat insideRange(float v, float bottom, float top) {\nreturn step(bottom, v) - step(top, v);\n}\nfloat SPEED = 0.6;\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat time = floor(iTime * SPEED * 60.0);    \nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec3 outCol = texture2D(inputImageTexture, uv).rgb;\nfloat rnd = random2d(vec2(time , 9545.0));\nvec2 colOffset = vec2(randomRange(vec2(time , 9545.0), 0.0, 0.1), \nrandomRange(vec2(time , 7205.0), 0.0, 0.1));\nif (rnd < 0.33){\noutCol.g = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).g;\n}else if (rnd < 0.66){\noutCol.r = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).r;\n}else{\noutCol.b = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).b;\n}\ngl_FragColor = vec4(outCol,1.0);\n}");
        this.f6499K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6503z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6502y);
        this.f6496D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6495C);
        this.f6498H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6497G);
        this.f6500w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6501x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6503z, this.f6493A);
        GLES20.glUniform1f(this.f6496D, f6488E);
        GLES20.glUniform1f(this.f6498H, f6490I);
        GLES20.glUniform1f(this.f6500w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6501x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6492v) {
            this.f6494B = System.currentTimeMillis() - this.f6499K;
            if (this.f6494B > 2000) {
                this.f6499K = System.currentTimeMillis();
            }
        }
        this.f6493A = (((float) this.f6494B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6488E = f6489F;
        f6490I = f6491J;
    }

    public static void change(float f, float f2) {
        f6489F = f;
        f6491J = f2;
    }
}
