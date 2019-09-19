package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

/* renamed from: Filters.RG */
public class RG extends FilterRender {
    private static float f6104A = (MyApplication.imgWidth / 2.0f);
    private static float f6105D;
    private static float f6106E = (MyApplication.imgHeight / 2.0f);
    private static float f6107z;
    private String f6108B = "touchY";
    private int f6109C;
    private int f6110v;
    private int f6111w;
    private String f6112x = "touchX";
    private int f6113y;

    public RG() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\namountx += 0.5;\namounty -= 0.5;\nvec3 col;\ncol.b = texture2D( inputImageTexture, vec2(uv.x+amountx/3., uv.y+amounty/3.) ).b;\ncol.rg = texture2D( inputImageTexture, uv ).rg;\ngl_FragColor = vec4(col,1.0);\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6113y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6112x);
        this.f6109C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6108B);
        this.f6110v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6111w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6113y, f6107z);
        GLES20.glUniform1f(this.f6109C, f6105D);
        GLES20.glUniform1f(this.f6110v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6111w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6107z = f6104A;
        f6105D = f6106E;
    }

    public static void change(float f, float f2) {
        f6104A = f;
        f6106E = f2;
    }
}
