package com.khacchung.glitchimage.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.util.Path;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class Artifacts extends MultiBitmapInputRender {
    public static boolean f6692C = false;
    private static final String[] f6693D = {Path.DRAWABLE.wrap("2131165355")};
    private static float f6694M;
    private static float f6695N = (MyApplication.imgWidth / 2.0f);
    private static float f6696Q;
    private static float f6697R = (MyApplication.imgHeight / 2.0f);
    private int f6698E;
    private int f6699F;
    private String f6700G = "iTime";
    private int f6701H;
    private float f6702I;
    private long f6703J = System.currentTimeMillis();
    private String f6704K = "touchX";
    private int f6705L;
    private String f6706O = "touchY";
    private int f6707P;
    private long f6708S;

    public Artifacts(Context context) {
        super(context, f6693D);
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*2.0;\nfloat amounty = 5.0+mouse.y/iResolution.y*25.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 block = floor(gl_FragCoord.xy / vec2(iResolution.x/amounty));\nvec2 uv_noise = block / vec2(iResolution.x/amounty);\nuv_noise += floor(vec2(iTime) * vec2(iResolution.x, iResolution.y)) / vec2(iResolution.x/amounty);\nfloat block_thresh = pow(fract(iTime * 1236.0453), 2.0) * (1.5 * amountx);\nfloat line_thresh = pow(fract(iTime * 2236.0453), 3.0) * (1.0 * amountx);\nvec2 uv_r = uv, uv_g = uv, uv_b = uv;\nif (texture2D(inputImageTexture2, uv_noise).r < block_thresh ||\ntexture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).g < line_thresh) {\nvec2 dist = (fract(uv_noise) - .5) * .3;\nuv_r += dist * 2.0;\nuv_g += dist * 0.2;\nuv_b += dist * 2.0;\n}\ngl_FragColor.r = texture2D(inputImageTexture, uv_r).r;\ngl_FragColor.g = texture2D(inputImageTexture, uv_g).g;\ngl_FragColor.b = texture2D(inputImageTexture, uv_b).b;\n}");
        this.f6708S = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6701H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6700G);
        this.f6705L = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6704K);
        this.f6707P = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6706O);
        this.f6698E = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6699F = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6701H, this.f6702I);
        GLES20.glUniform1f(this.f6705L, f6694M);
        GLES20.glUniform1f(this.f6707P, f6696Q);
        GLES20.glUniform1f(this.f6698E, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6699F, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6692C) {
            this.f6703J = System.currentTimeMillis() - this.f6708S;
            if (this.f6703J > 200) {
                this.f6708S = System.currentTimeMillis();
            }
        }
        this.f6702I = (((float) this.f6703J) / 1000.0f) * 2.0f * 3.14159f * 0.01f;
        f6694M = f6695N;
        f6696Q = f6697R;
    }

    public static void change(float f, float f2) {
        f6695N = f;
        f6697R = f2;
    }
}
