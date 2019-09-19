package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class GB extends FilterRender {
    private static float f6462A = (MyApplication.imgWidth / 2.0f);
    private static float f6463D;
    private static float f6464E = (MyApplication.imgHeight / 2.0f);
    private static float f6465z;
    private String f6466B = "touchY";
    private int f6467C;
    private int f6468v;
    private int f6469w;
    private String f6470x = "touchX";
    private int f6471y;

    public GB() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\namountx += 0.5;\namounty -= 0.5;\nvec3 col;\ncol.r = texture2D( inputImageTexture, vec2(uv.x+amountx/3., uv.y+amounty/3.) ).r;\ncol.gb = texture2D( inputImageTexture, uv ).gb;\ngl_FragColor = vec4(col,1.0);\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6471y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6470x);
        this.f6467C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6466B);
        this.f6468v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6469w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6471y, f6465z);
        GLES20.glUniform1f(this.f6467C, f6463D);
        GLES20.glUniform1f(this.f6468v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6469w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6465z = f6462A;
        f6463D = f6464E;
    }

    public static void change(float f, float f2) {
        f6462A = f;
        f6464E = f2;
    }
}
