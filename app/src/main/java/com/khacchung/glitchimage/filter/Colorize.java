package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Colorize extends FilterRender {
    private static float f6378E = 0.0f;
    private static float f6379F = 0.0f;
    private static float f6380I = 0.0f;
    private static float f6381J = 0.0f;
    public static boolean f6382v = false;
    private float f6383A;
    private long f6384B = System.currentTimeMillis();
    private String f6385C = "touchX";
    private int f6386D;
    private String f6387G = "touchY";
    private int f6388H;
    private long f6389K;
    private int f6390w;
    private int f6391x;
    private String f6392y = "iTime";
    private int f6393z;

    public Colorize() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);\n}\nvoid main(){\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nif (touchY > 0.0){\nfloat time = floor(iTime*20.);\nfloat rnd = random2d(vec2(time, 10.));\nvec4 col = (texture2D(inputImageTexture, uv+vec2(0.0,rnd/10.)));\nfor (float i = 0.0; i < 5.0; i += 1.0) {\nfloat rnd = random2d(vec2(time, i*10.));\nfloat rnd2 = random2d(vec2(time, 10.+i));\nfloat rnd3 = random2d(vec2(time, 20.+i));\nif (rnd<.30){\nif( uv.y < rnd2 && uv.y > rnd3 )\ncol.rg = col.rg * rnd*10.;\n}else if (rnd<0.60){\nif( uv.y < rnd2 && uv.y > rnd3 )\ncol.gb = col.gb * rnd*10.;\n}else if (rnd<.90){\nif( uv.y < rnd2 && uv.y > rnd3 )\ncol.rb = col.rb * rnd*10.;\n}\n}\ngl_FragColor = col;\n}else{\ngl_FragColor = texture2D(inputImageTexture, uv);\n}\n}");
        this.f6389K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6393z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6392y);
        this.f6386D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6385C);
        this.f6388H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6387G);
        this.f6390w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6391x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6393z, this.f6383A);
        GLES20.glUniform1f(this.f6386D, f6378E);
        GLES20.glUniform1f(this.f6388H, f6380I);
        GLES20.glUniform1f(this.f6390w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6391x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6382v) {
            this.f6384B = System.currentTimeMillis() - this.f6389K;
            if (this.f6384B > 5000) {
                this.f6389K = System.currentTimeMillis();
            }
        }
        this.f6383A = (((float) this.f6384B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f6378E = f6379F;
        f6380I = f6381J;
    }

    public static void change(float f, float f2) {
        f6379F = f;
        f6381J = f2;
    }
}
