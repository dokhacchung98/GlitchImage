package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Shampain extends FilterRender {
    private static float f6150E = 0.0f;
    private static float f6151F = (MyApplication.imgWidth / 2.0f);
    private static float f6152I = 0.0f;
    private static float f6153J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6154v = false;
    private float f6155A;
    private long f6156B = System.currentTimeMillis();
    private String f6157C = "touchX";
    private int f6158D;
    private String f6159G = "touchY";
    private int f6160H;
    private long f6161K;
    private int f6162w;
    private int f6163x;
    private String f6164y = "iTime";
    private int f6165z;

    public Shampain() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.;\namounty = 1.0-amounty;\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nfloat time = floor(iTime*20.);\nfloat rnd = random2d(vec2(time, 10.));\nvec4 col = texture2D(inputImageTexture, uv);\nfloat rnd2 = random2d(vec2(time, 10.));\nfloat rnd3 = random2d(vec2(time, 20.));\nif( uv.y > amounty-0.1+(rnd2/5.) && uv.y < amounty+0.01+(rnd3/5.) )\ncol.r = texture2D(inputImageTexture, uv+vec2(0.0,rnd/10.0)).r * 3.0;\ngl_FragColor = col;\n}");
        this.f6161K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6165z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6164y);
        this.f6158D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6157C);
        this.f6160H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6159G);
        this.f6162w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6163x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderTextures() {
        super.bindShaderTextures();
        GLES20.glUniform1f(this.f6165z, this.f6155A);
        GLES20.glUniform1f(this.f6158D, f6150E);
        GLES20.glUniform1f(this.f6160H, f6152I);
        GLES20.glUniform1f(this.f6162w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6163x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6154v) {
            this.f6156B = System.currentTimeMillis() - this.f6161K;
            if (this.f6156B > 5000) {
                this.f6161K = System.currentTimeMillis();
            }
        }
        this.f6155A = (((float) this.f6156B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f6150E = f6151F;
        f6152I = f6153J;
    }

    public static void change(float f, float f2) {
        f6151F = f;
        f6153J = f2;
    }
}
