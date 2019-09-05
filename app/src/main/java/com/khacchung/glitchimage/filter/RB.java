package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

/* renamed from: Filters.RB */
public class RB extends FilterRender {
    private static float f6094A = (MyApplication.imgWidth / 2.0f);
    private static float f6095D;
    private static float f6096E = (MyApplication.imgHeight / 2.0f);
    private static float f6097z;
    private String f6098B = "touchY";
    private int f6099C;
    private int f6100v;
    private int f6101w;
    private String f6102x = "touchX";
    private int f6103y;

    public RB() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\namountx += 0.5;\namounty -= 0.5;\nvec3 col;\ncol.g = texture2D( inputImageTexture, vec2(uv.x+amountx/3., uv.y+amounty/3.) ).g;\ncol.rb = texture2D( inputImageTexture, uv ).rb;\ngl_FragColor = vec4(col,1.0);\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6103y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6102x);
        this.f6099C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6098B);
        this.f6100v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6101w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6103y, f6097z);
        GLES20.glUniform1f(this.f6099C, f6095D);
        GLES20.glUniform1f(this.f6100v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6101w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6097z = f6094A;
        f6095D = f6096E;
    }

    public static void change(float f, float f2) {
        f6094A = f;
        f6096E = f2;
    }
}
