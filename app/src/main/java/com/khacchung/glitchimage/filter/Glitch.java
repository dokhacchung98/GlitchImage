package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Glitch extends FilterRender {
    private static float f6472E = 0.0f;
    private static float f6473F = (MyApplication.imgWidth / 2.0f);
    private static float f6474I = 0.0f;
    private static float f6475J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6476v = false;
    private float f6477A;
    private long f6478B = System.currentTimeMillis();
    private String f6479C = "touchX";
    private int f6480D;
    private String f6481G = "touchY";
    private int f6482H;
    private long f6483K;
    private int f6484w;
    private int f6485x;
    private String f6486y = "iTime";
    private int f6487z;

    public Glitch() {
        setFragmentShader("precision mediump float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.01+mouse.x/iResolution.x*1.;\nfloat amounty = 0.01+mouse.y/iResolution.y*1.;\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nvec2 uv1 = gl_FragCoord.xy/iResolution.xy;\nfloat time = floor(iTime*30.);\nfloat rnd = random2d(vec2(time, 10.));\nfor (float i = 0.0; i < 1.; i += 0.2) {\nif(uv1.y<random2d(vec2(time, i))&&uv1.y>random2d(vec2(time, i+12.))){\nuv1.x -= amountx*0.1;\nuv1.y = uv.y+((rnd+i)/20.)*amountx;\n}\n}\nvec4 col;\nif ( rnd <= 0.3 ) {\ncol.r = texture2D(inputImageTexture, uv1+vec2((amountx*0.1)*rnd, 0.)).r;\ncol.gb = texture2D(inputImageTexture, uv1).gb;\n} else if ( rnd <= 0.6 ) {\ncol.g = texture2D(inputImageTexture, uv1+vec2((amountx*0.1)*rnd, 0.)).g;\ncol.rb = texture2D(inputImageTexture, uv1).rb;    \n} else if ( rnd <= 1.0 ) {\ncol.b = texture2D(inputImageTexture, uv1+vec2((amountx*0.1)*rnd, 0.)).b;\ncol.rg = texture2D(inputImageTexture, uv1).rg;   \n}\ngl_FragColor =  col;\n}");
        this.f6483K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6487z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6486y);
        this.f6480D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6479C);
        this.f6482H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6481G);
        this.f6484w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6485x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6487z, this.f6477A);
        GLES20.glUniform1f(this.f6480D, f6472E);
        GLES20.glUniform1f(this.f6482H, f6474I);
        GLES20.glUniform1f(this.f6484w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6485x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6476v) {
            this.f6478B = System.currentTimeMillis() - this.f6483K;
            if (this.f6478B > 2000) {
                this.f6483K = System.currentTimeMillis();
            }
        }
        this.f6477A = (((float) this.f6478B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6472E = f6473F;
        f6474I = f6475J;
    }

    public static void change(float f, float f2) {
        f6473F = f;
        f6475J = f2;
    }
}
