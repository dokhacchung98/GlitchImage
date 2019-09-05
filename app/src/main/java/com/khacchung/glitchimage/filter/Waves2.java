package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Waves2 extends FilterRender {
    public static boolean f6280v = false;
    static int f6284w;
    private static int f6285x;
    private float f6281A;
    private long f6282B = System.currentTimeMillis();
    private long f6283C;
    private String f6286y = "iTime";
    private int f6287z;

    public Waves2() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvec2 fault(vec2 uv, float s){\nfloat v = pow(0.5 - 0.5 * sin(0.50 * 100.1 / uv.y), 1.0);\nuv.x += v * s;\nreturn uv;\n}\nvoid main(){\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 fg = gl_FragCoord.xy;\nfg /= 20.; \nfloat t = 0.+fract(iTime*5.);\nt=fract(t);\nfloat  y = floor(fg.x-0.5+t)-t;\nvec2 distort = fault(uv + vec2(0.0, fract(1.)), 0.15) - vec2(0.0, fract(10.));\n#define T texture2D(inputImageTexture,10.*vec2(fg.xy*(distort*1.1))/iResolution.xy)\ngl_FragColor += cos(7.*(fg.x+y) + 10./(10./5.)*(1.0/T+1.0) ) - gl_FragColor;\nvec4 bg = gl_FragColor/=2.;\nvec4 col = texture2D(inputImageTexture, uv);\ncol.rgb /= 2.;\ncol = mix(col, bg,0.2);\nfloat threshold = 0.2;\ngl_FragColor.rgb = max(col.g, max(col.g, col.b)) >= threshold ? vec3(bg) : col.rgb;\n}");
        this.f6283C = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6287z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6286y);
        f6284w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        f6285x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6287z, this.f6281A);
        GLES20.glUniform1f(f6284w, MyApplication.imgWidth);
        GLES20.glUniform1f(f6285x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6280v) {
            this.f6282B = System.currentTimeMillis() - this.f6283C;
            if (this.f6282B > 10000) {
                this.f6283C = System.currentTimeMillis();
            }
        }
        this.f6281A = (((float) this.f6282B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
    }

    public static void change(float f, float f2) {
        f6284w = (int) f;
        f6285x = (int) f2;
    }
}
