package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class RGBWave extends FilterRender {
    private static float f6124A = (MyApplication.imgWidth / 2.0f);
    private static float f6125D;
    private static float f6126E = (MyApplication.imgHeight / 2.0f);
    private static float f6127z;
    private String f6128B = "touchY";
    private int f6129C;
    private int f6130v;
    private int f6131w;
    private String f6132x = "touchX";
    private int f6133y;

    public RGBWave() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvec2 fault(vec2 uv, float s){\nfloat v = pow(0.5 - 0.5 * cos(1.50 * 2.1 * uv.y), 20.0);\nuv.x += v * s;\nreturn uv;\n}\nvoid main(){\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat t = 0.+mouse.y/iResolution.y*1.;\nvec4 color;\ncolor.r = texture2D(inputImageTexture, uv).r;\nuv = fault(uv + vec2(0.0, fract(t)), 0.15-mouse.x/iResolution.x*0.3) - vec2(0.0, fract(t));\ncolor.g = texture2D(inputImageTexture, uv).g;\nuv = gl_FragCoord.xy / iResolution.xy;\nuv = fault(uv + vec2(0.0, fract(t)), 0.3-mouse.x/iResolution.x*0.6) - vec2(0.0, fract(t));\ncolor.b = texture2D(inputImageTexture, uv).b;\ngl_FragColor = color;\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6133y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6132x);
        this.f6129C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6128B);
        this.f6130v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6131w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6133y, f6127z);
        GLES20.glUniform1f(this.f6129C, f6125D);
        GLES20.glUniform1f(this.f6130v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6131w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6127z = f6124A;
        f6125D = f6126E;
    }

    public static void change(float f, float f2) {
        f6124A = f;
        f6126E = f2;
    }
}
