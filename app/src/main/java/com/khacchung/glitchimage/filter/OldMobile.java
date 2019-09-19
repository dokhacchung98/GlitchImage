package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class OldMobile extends FilterRender {
    private static float f6042E = 0.0f;
    private static float f6043F = (MyApplication.imgWidth / 2.0f);
    private static float f6044I = 0.0f;
    private static float f6045J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6046v = false;
    private float f6047A;
    private long f6048B = System.currentTimeMillis();
    private String f6049C = "touchX";
    private int f6050D;
    private String f6051G = "touchY";
    private int f6052H;
    private long f6053K;
    private int f6054w;
    private int f6055x;
    private String f6056y = "iTime";
    private int f6057z;

    public OldMobile() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvec4 posterize(vec4 color, float numColors){\nreturn floor(color * numColors - 0.5) / numColors;\n}\nvec2 quantize(vec2 v, float steps){\nreturn floor(v * steps) / steps;\n}\nfloat dist(vec2 a, vec2 b){\nreturn sqrt(pow(b.x - a.x, 2.0) + pow(b.y - a.y, 2.0));\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat amount1 = pow(amountx, 2.0);\nvec2 pixel = 0. / gl_FragCoord.xy;\nvec4 color = texture2D(inputImageTexture, uv);\nfloat t = mod(mod(iTime, amount1 * 100.0 * (amount1 - 0.5)) * 109.0, 1.0);\nvec4 postColor = posterize(color, 16.0);\nvec4 a = posterize(texture2D(inputImageTexture, quantize(uv, 64.0 * t) + pixel * (postColor.rb - vec2(.5)) * 100.0), 5.0).rbga;\nvec4 b = posterize(texture2D(inputImageTexture, quantize(uv, 32.0 - t) + pixel * (postColor.rg - vec2(.5)) * 1000.0), 4.0).gbra;\nvec4 c = posterize(texture2D(inputImageTexture, quantize(uv, 16.0 + t) + pixel * (postColor.rg - vec2(.5)) * 20.0), 16.0).bgra;\ngl_FragColor = mix(\ntexture2D(inputImageTexture,\nuv + amount1 * (quantize((a * t - b + c - (t + t / 2.0) / 10.0).rg, 16.0) - vec2(.5)) * pixel * 100.0),\n(a + b + c) / 3.0,\n(5.0 - (dot(color, postColor) - 1.5)) * amountx);\n}");
        this.f6053K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6057z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6056y);
        this.f6050D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6049C);
        this.f6052H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6051G);
        this.f6054w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6055x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6057z, this.f6047A);
        GLES20.glUniform1f(this.f6050D, f6042E);
        GLES20.glUniform1f(this.f6052H, f6044I);
        GLES20.glUniform1f(this.f6054w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6055x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6046v) {
            this.f6048B = System.currentTimeMillis() - this.f6053K;
            if (this.f6048B > 5000) {
                this.f6053K = System.currentTimeMillis();
            }
        }
        this.f6047A = (((float) this.f6048B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f6042E = f6043F;
        f6044I = f6045J;
    }

    public static void change(float f, float f2) {
        f6043F = f;
        f6045J = f2;
    }
}
