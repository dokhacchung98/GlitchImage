package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Fall2 extends FilterRender {
    private static float f6446E = 0.0f;
    private static float f6447F = (MyApplication.imgWidth / 2.0f);
    private static float f6448I = 0.0f;
    private static float f6449J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6450v = false;
    private float f6451A;
    private long f6452B = System.currentTimeMillis();
    private String f6453C = "touchX";
    private int f6454D;
    private String f6455G = "touchY";
    private int f6456H;
    private long f6457K;
    private int f6458w;
    private int f6459x;
    private String f6460y = "iTime";
    private int f6461z;

    public Fall2() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \n    return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.01+mouse.x/iResolution.x*1.;\nfloat amounty = 0.01+mouse.y/iResolution.y*1.;\namounty = 1.0 - amounty;\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nfloat time = floor(iTime*50.);\nfor (float i = 0.0; i < amounty; i += 0.05) {\nif(uv.y<i+random2d(vec2(time, i))*0.2){\nuv.y = i;\nuv.x -= random2d(vec2(time, i))*amountx*0.2;\n}\n}\nvec3 col = texture2D(inputImageTexture, uv).rgb;\ngl_FragColor = vec4(col,1.0);\n}");
        this.f6457K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6461z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6460y);
        this.f6454D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6453C);
        this.f6456H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6455G);
        this.f6458w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6459x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6461z, this.f6451A);
        GLES20.glUniform1f(this.f6454D, f6446E);
        GLES20.glUniform1f(this.f6456H, f6448I);
        GLES20.glUniform1f(this.f6458w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6459x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6450v) {
            this.f6452B = System.currentTimeMillis() - this.f6457K;
            if (this.f6452B > 2000) {
                this.f6457K = System.currentTimeMillis();
            }
        }
        this.f6451A = (((float) this.f6452B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6446E = f6447F;
        f6448I = f6449J;
    }

    public static void change(float f, float f2) {
        f6447F = f;
        f6449J = f2;
    }
}
