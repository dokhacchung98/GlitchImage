package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Magnitude extends FilterRender {
    private static float f6582E = 0.0f;
    private static float f6583F = (MyApplication.imgWidth / 2.0f);
    private static float f6584I = 0.0f;
    private static float f6585J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6586v = false;
    private float f6587A;
    private long f6588B = System.currentTimeMillis();
    private String f6589C = "touchX";
    private int f6590D;
    private String f6591G = "touchY";
    private int f6592H;
    private long f6593K;
    private int f6594w;
    private int f6595x;
    private String f6596y = "iTime";
    private int f6597z;

    public Magnitude() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nuniform float iTime;\nfloat rand(vec2 co){\nfloat b = 10.;\nfloat dt= dot(co.xy ,vec2(b,b));\nfloat sn= mod(dt,0.);\nreturn fract(tan(sn) * b);\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat magnitude = 0.1 * amountx;\nvec2 offsetRedUV = uv;\noffsetRedUV.x = uv.x + rand(vec2(iTime*0.03,uv.y*2.42)) * 0.02 * amountx;\noffsetRedUV.x += cos(rand(vec2(iTime*0.2, uv.y)))*magnitude*uv.x;\nvec2 offsetGreenUV = uv;\noffsetGreenUV.x = uv.x + rand(vec2(iTime*0.4,uv.y*uv.x*100000.0)) * .2 * amountx;\noffsetGreenUV.x += cos(iTime*9.0)*magnitude*uv.y;\nvec2 offsetBlueUV = uv;\noffsetBlueUV.x = uv.x + rand(vec2(iTime/0.4,uv.y*uv.x*100000.0)) * .2 * amountx;\noffsetBlueUV.x += rand(vec2(cos(iTime*0.1),sin(uv.x*uv.y)));\nfloat r = texture2D(inputImageTexture, offsetRedUV).r;\nfloat g = texture2D(inputImageTexture, offsetGreenUV).g;\nfloat b = texture2D(inputImageTexture, uv).b;\ngl_FragColor = vec4(r,g,b,0);\n}");
        this.f6593K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6597z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6596y);
        this.f6590D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6589C);
        this.f6592H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6591G);
        this.f6594w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6595x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6597z, this.f6587A);
        GLES20.glUniform1f(this.f6590D, f6582E);
        GLES20.glUniform1f(this.f6592H, f6584I);
        GLES20.glUniform1f(this.f6594w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6595x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6586v) {
            this.f6588B = System.currentTimeMillis() - this.f6593K;
            if (this.f6588B > 20000) {
                this.f6593K = System.currentTimeMillis();
            }
        }
        this.f6587A = (((float) this.f6588B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f6582E = f6583F;
        f6584I = f6585J;
    }

    public static void change(float f, float f2) {
        f6583F = f;
        f6585J = f2;
    }
}
