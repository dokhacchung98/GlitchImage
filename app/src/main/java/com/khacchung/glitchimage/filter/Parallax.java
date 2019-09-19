package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Parallax extends FilterRender {
    private static float f6058A = (MyApplication.imgWidth / 2.0f);
    private static float f6059D;
    private static float f6060E = (MyApplication.imgHeight / 2.0f);
    private static float f6061z;
    private String f6062B = "touchY";
    private int f6063C;
    private int f6064v;
    private int f6065w;
    private String f6066x = "touchX";
    private int f6067y;

    public Parallax() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*0.7;\nfloat amounty = 0.1+mouse.y/iResolution.y*1.0;\nfloat distance_steps = amountx;\nfloat linear_steps = 10.+distance_steps*12.;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 dir = vec2(uv - vec2(0.5, 0.5)) * (distance_steps / linear_steps);\nfor (float i = 0.01; i < linear_steps; i++) {\nif (\npow(length(texture2D(inputImageTexture, uv - i * dir).rgb) / 1.4, 0.50)\n> i / (linear_steps)) {\ngl_FragColor = texture2D(inputImageTexture, uv - i * dir) * i / (linear_steps);\n}\n}\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6067y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6066x);
        this.f6063C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6062B);
        this.f6064v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6065w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6067y, f6061z);
        GLES20.glUniform1f(this.f6063C, f6059D);
        GLES20.glUniform1f(this.f6064v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6065w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6061z = f6058A;
        f6059D = f6060E;
    }

    public static void change(float f, float f2) {
        f6058A = f;
        f6060E = f2;
    }
}
