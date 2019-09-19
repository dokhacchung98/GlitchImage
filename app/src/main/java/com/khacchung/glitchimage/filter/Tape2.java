package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Tape2 extends FilterRender {
    private int f6220v;
    private int f6221w;

    public Tape2() {
        setFragmentShader("precision highp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nfloat NUM_LINES \t= iResolution.y/3.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat nL = floor(uv.y * NUM_LINES);\nfloat x = mod(nL, 2.0);\nfloat posInTrans = (nL * 1.0/NUM_LINES)*0.5 + x * 0.5;\nfloat trans = (1.0)*0.5;\ntrans = step(posInTrans, trans);\nvec3 src = texture2D(inputImageTexture, uv).rgb;\nvec3 dest = texture2D(inputImageTexture, uv + vec2(0.02,0.0)).rgb;\ngl_FragColor.rgb =  mix(src, dest, trans);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6220v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6221w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6220v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6221w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
    }
}
