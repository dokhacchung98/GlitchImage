package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class RGB extends FilterRender {
    private static float f6114A = (MyApplication.imgWidth / 2.0f);
    private static float f6115D;
    private static float f6116E = (MyApplication.imgHeight / 2.0f);
    private static float f6117z;
    private String f6118B = "touchY";
    private int f6119C;
    private int f6120v;
    private int f6121w;
    private String f6122x = "touchX";
    private int f6123y;

    public RGB() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\namountx += 0.5;\namounty -= 0.5;\nvec3 col;\ncol.r = texture2D( inputImageTexture, vec2(uv.x+amountx/3., uv.y+amounty/3.) ).r;\ncol.g = texture2D( inputImageTexture, uv ).g;\ncol.b = texture2D( inputImageTexture, vec2(uv.x-amountx/3., uv.y-amounty/3.) ).b;\ngl_FragColor = vec4(col,1.0);\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6123y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6122x);
        this.f6119C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6118B);
        this.f6120v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6121w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6123y, f6117z);
        GLES20.glUniform1f(this.f6119C, f6115D);
        GLES20.glUniform1f(this.f6120v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6121w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6117z = f6114A;
        f6115D = f6116E;
    }

    public static void change(float f, float f2) {
        f6114A = f;
        f6116E = f2;
    }
}
