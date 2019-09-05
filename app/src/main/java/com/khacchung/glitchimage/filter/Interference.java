package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Interference extends FilterRender {
    private static float f6530E = 0.0f;
    private static float f6531F = (MyApplication.imgWidth / 2.0f);
    private static float f6532I = 0.0f;
    private static float f6533J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6534v = false;
    private float f6535A;
    private long f6536B = System.currentTimeMillis();
    private String f6537C = "touchX";
    private int f6538D;
    private String f6539G = "touchY";
    private int f6540H;
    private long f6541K;
    private int f6542w;
    private int f6543x;
    private String f6544y = "iTime";
    private int f6545z;

    public Interference() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rng2(vec2 seed) {\nreturn fract(sin(dot(seed * floor(iTime * 100.), vec2(2.0,3.0))) * 1.0);\n}\nfloat rng(float seed) {\nreturn rng2(vec2(seed, 1.0));\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 3.0+mouse.y/iResolution.y*200.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 blockS = floor(uv * vec2(iResolution.x / 140., iResolution.y / 140.));\nvec2 blockL = floor(uv * vec2(iResolution.x / amounty, iResolution.y / amounty));\nfloat lineNoise = pow(rng2(blockS), 8.0) * pow(rng2(blockL), 3.0) * (0.5 * amountx);\ngl_FragColor = vec4(texture2D(inputImageTexture, uv - vec2(lineNoise * (0.0005 * amountx) * rng(31.0), 0)));\n}");
        this.f6541K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6545z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6544y);
        this.f6538D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6537C);
        this.f6540H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6539G);
        this.f6542w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6543x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6545z, this.f6535A);
        GLES20.glUniform1f(this.f6538D, f6530E);
        GLES20.glUniform1f(this.f6540H, f6532I);
        GLES20.glUniform1f(this.f6542w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6543x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6534v) {
            this.f6536B = System.currentTimeMillis() - this.f6541K;
            if (this.f6536B > 1000) {
                this.f6541K = System.currentTimeMillis();
            }
        }
        this.f6535A = (((float) this.f6536B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f6530E = f6531F;
        f6532I = f6533J;
    }

    public static void change(float f, float f2) {
        f6531F = f;
        f6533J = f2;
    }
}
