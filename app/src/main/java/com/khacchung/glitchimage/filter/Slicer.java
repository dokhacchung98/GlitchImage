package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Slicer extends FilterRender {
    private static float f6182E = 0.0f;
    private static float f6183F = (MyApplication.imgWidth / 2.0f);
    private static float f6184I = 0.0f;
    private static float f6185J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6186v = false;
    private float f6187A;
    private long f6188B = System.currentTimeMillis();
    private String f6189C = "touchX";
    private int f6190D;
    private String f6191G = "touchY";
    private int f6192H;
    private long f6193K;
    private int f6194w;
    private int f6195x;
    private String f6196y = "iTime";
    private int f6197z;

    public Slicer() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\nreturn fract(sin(dot(co.xy + iTime,vec2(12.0 ,78.0)))) * 1.0;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*0.5;\nfloat amounty = 0.0-mouse.y/iResolution.y*60.;\nfloat ring1 = amounty;\nfloat ring2 = amounty/2.;\nfloat push1 = 5.4;\nfloat push2 = 10.0;\nfloat diminish = 0.05;\nfloat time = iTime ;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat r1 = rand(floor(uv.yy*ring1 )/ring1);\nfloat r2 = rand(floor(uv.yy*ring2 )/ring2);\nr1 = -1.0 + 2.0 * r1;\nr2 = -1.0 + 2.0 * r2;\nr1 *= (push1 * amountx);\nr2 *= (push2 * amountx);\nr1 += r2;\nr1 *= diminish;\nvec4 tex = texture2D(inputImageTexture, uv + vec2(r1,0.0));\nif(uv.x+r1 > (1.0 - amountx) || uv.x+r1 <= (amountx)){\ngl_FragColor = vec4(vec3(0.0),1.0);   \n} else {\ngl_FragColor =tex;\n}\n}");
        this.f6193K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6197z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6196y);
        this.f6190D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6189C);
        this.f6192H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6191G);
        this.f6194w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6195x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6197z, this.f6187A);
        GLES20.glUniform1f(this.f6190D, f6182E);
        GLES20.glUniform1f(this.f6192H, f6184I);
        GLES20.glUniform1f(this.f6194w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6195x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6186v) {
            this.f6188B = System.currentTimeMillis() - this.f6193K;
            if (this.f6188B > 1000) {
                this.f6193K = System.currentTimeMillis();
            }
        }
        this.f6187A = (((float) this.f6188B) / 500.0f) * 2.0f * 3.14159f * 0.01f;
        f6182E = f6183F;
        f6184I = f6185J;
    }

    public static void change(float f, float f2) {
        f6183F = f;
        f6185J = f2;
    }
}
