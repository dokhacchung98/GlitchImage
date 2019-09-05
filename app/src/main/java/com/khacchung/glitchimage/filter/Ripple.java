package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Ripple extends FilterRender {
    private static float f6134E = 0.0f;
    private static float f6135F = (MyApplication.imgWidth / 2.0f);
    private static float f6136I = 0.0f;
    private static float f6137J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6138v = false;
    private float f6139A;
    private long f6140B = System.currentTimeMillis();
    private String f6141C = "touchX";
    private int f6142D;
    private String f6143G = "touchY";
    private int f6144H;
    private long f6145K;
    private int f6146w;
    private int f6147x;
    private String f6148y = "iTime";
    private int f6149z;

    public Ripple() {
        setFragmentShader("precision mediump float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat x(float t) {\nt = mod(t, 1.0);\nreturn abs(t) - abs(t-1.0) - abs(t-2.0) + abs(t-3.0) - 1.0;\t\n}\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 1.0);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*12.0;\nfloat amounty = 0.1+mouse.y/iResolution.y*2.0;\namounty = 2.-amounty;\nfloat time = floor(iTime*(1.0*60.0));\nfloat rnd = random2d(vec2(time , 1.0));\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 p = abs(mod(uv*90.0, 1.0));\nvec2 cell = floor(uv*100.0);\nfloat t =  atan(cell.y+rnd*100.,cell.x)/1.57*4.0+ length(texture2D(inputImageTexture,uv));\nt *= 2.5;\nvec2 s = vec2(x(t), x(t-1.0))*(0.05*100.)+0.5; \nfloat d = max(abs(p.x-s.x), abs(p.y-s.y))* amounty;\ngl_FragColor = vec4(smoothstep(0.35, 0.051, d))* texture2D(inputImageTexture,cell / 100.0).bbra * texture2D(inputImageTexture,cell / 100.0).rbra * 3.2 ;\ngl_FragColor = mix(gl_FragColor, texture2D(inputImageTexture, uv), 1.0 - amountx/10.);\n}");
        this.f6145K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6149z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6148y);
        this.f6142D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6141C);
        this.f6144H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6143G);
        this.f6146w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6147x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderTextures() {
        super.bindShaderTextures();
        GLES20.glUniform1f(this.f6149z, this.f6139A);
        GLES20.glUniform1f(this.f6142D, f6134E);
        GLES20.glUniform1f(this.f6144H, f6136I);
        GLES20.glUniform1f(this.f6146w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6147x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6138v) {
            this.f6140B = System.currentTimeMillis() - this.f6145K;
            if (this.f6140B > 2000) {
                this.f6145K = System.currentTimeMillis();
            }
        }
        this.f6139A = (((float) this.f6140B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6134E = f6135F;
        f6136I = f6137J;
    }

    public static void change(float f, float f2) {
        f6135F = f;
        f6137J = f2;
    }
}
