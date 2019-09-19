package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Melt extends FilterRender {
    private static float f6012A = (MyApplication.imgWidth / 2.0f);
    private static float f6013D;
    private static float f6014E = (MyApplication.imgHeight / 2.0f);
    private static float f6015z;
    private String f6016B = "touchY";
    private int f6017C;
    private int f6018v;
    private int f6019w;
    private String f6020x = "touchX";
    private int f6021y;

    public Melt() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\n    float amountx = 0.0+mouse.x/iResolution.x*0.3;\n    float amounty = 0.01+mouse.y/iResolution.y*0.25;\n    float linear_steps = 2.0*amounty*100.;\n   vec2 uv = gl_FragCoord.xy / iResolution.xy;\n    uv.y -= amounty/1.2;\n    vec2 dir = vec2(0.0,(0.-amounty / linear_steps));\n    for (float i = 0.0; i < linear_steps; i++) {\n        if (\n        pow(length(texture2D(inputImageTexture,uv - i * dir).rgb) / 1.4, 1.0)\n        > i / (linear_steps)) {\n             gl_FragColor = texture2D(inputImageTexture, uv - i * dir) * i / (linear_steps);\n        }\n     }\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6021y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6020x);
        this.f6017C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6016B);
        this.f6018v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6019w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6021y, f6015z);
        GLES20.glUniform1f(this.f6017C, f6013D);
        GLES20.glUniform1f(this.f6018v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6019w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6015z = f6012A;
        f6013D = f6014E;
    }

    public static void change(float f, float f2) {
        f6012A = f;
        f6014E = f2;
    }
}
