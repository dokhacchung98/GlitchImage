package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Clones extends FilterRender {
    private static float f6362E = 0.0f;
    private static float f6363F = (MyApplication.imgWidth / 2.0f);
    private static float f6364I = 0.0f;
    private static float f6365J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6366v = false;
    private float f6367A;
    private long f6368B = System.currentTimeMillis();
    private String f6369C = "touchX";
    private int f6370D;
    private String f6371G = "touchY";
    private int f6372H;
    private long f6373K;
    private int f6374w;
    private int f6375x;
    private String f6376y = "iTime";
    private int f6377z;

    public Clones() {
        setFragmentShader("precision lowp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define taps 6.0\n#define tau 6.28\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*0.5;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = textureCoordinate.xy;\nvec4 c = texture2D(inputImageTexture,uv);\nfloat t = iTime*0.5;\nfloat d = amountx/2.;\nfor(float i = 0.; i<tau;i+=tau/taps){\nfloat a = i+t;\nvec4 c2 = texture2D(inputImageTexture,vec2(uv.x+cos(a)*d,uv.y+sin(a)*d));\n#ifdef light\nc = max(c,c2);\n#else\nc = min(c,c2);\n#endif\n}\ngl_FragColor = c;\n}");
        this.f6373K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6377z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6376y);
        this.f6370D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6369C);
        this.f6372H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6371G);
        this.f6374w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6375x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6377z, this.f6367A);
        GLES20.glUniform1f(this.f6370D, f6362E);
        GLES20.glUniform1f(this.f6372H, f6364I);
        GLES20.glUniform1f(this.f6374w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6375x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6366v) {
            this.f6368B = System.currentTimeMillis() - this.f6373K;
            if (this.f6368B > 50000) {
                this.f6373K = System.currentTimeMillis();
            }
        }
        this.f6367A = (((float) this.f6368B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f6362E = f6363F;
        f6364I = f6365J;
    }

    public static void change(float f, float f2) {
        f6363F = f;
        f6365J = f2;
    }
}
