package com.khacchung.glitchimage.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.util.Path;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class Corruption extends MultiBitmapInputRender {
    public static boolean f6727C = false;
    private static final String[] f6728D = {Path.DRAWABLE.wrap("2131165355")};
    private static float f6729M;
    private static float f6730N = (MyApplication.imgWidth / 2.0f);
    private static float f6731Q;
    private static float f6732R = (MyApplication.imgHeight / 2.0f);
    private int f6733E;
    private int f6734F;
    private String f6735G = "iTime";
    private int f6736H;
    private float f6737I;
    private long f6738J = System.currentTimeMillis();
    private String f6739K = "touchX";
    private int f6740L;
    private String f6741O = "touchY";
    private int f6742P;
    private long f6743S;

    public Corruption(Context context) {
        super(context, f6728D);
        setVertexShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*2.0;\nfloat amounty = 5.0+mouse.y/iResolution.y*15.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 block = floor(gl_FragCoord.xy / vec2(iResolution.x/amounty));\nvec2 uv_noise = block / vec2(iResolution.x/amounty);\nuv_noise += floor(vec2(iTime) * vec2(iResolution.x, iResolution.y)) / vec2(iResolution.x/amounty);\nfloat block_thresh = pow(fract(iTime * 1236.0453), 2.0) * (0.5 * amountx);\nfloat line_thresh = pow(fract(iTime * 2236.0453), 3.0) * (1.0 * amountx);\nvec2 uv_r = uv, uv_g = uv, uv_b = uv;\nif (texture2D(inputImageTexture2, uv_noise).r < block_thresh ||\ntexture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).g < line_thresh) {\nvec2 dist = (fract(uv_noise) - .5) * .3;\nuv_r += dist * 0.1;\nuv_g += dist * 0.2;\nuv_b += dist * .125;\n}\ngl_FragColor.r = texture2D(inputImageTexture, uv_r).r;\ngl_FragColor.g = texture2D(inputImageTexture, uv_g).g;\ngl_FragColor.b = texture2D(inputImageTexture, uv_b).b;\nif (texture2D(inputImageTexture2, uv_noise).g < block_thresh)\ngl_FragColor.rgb = gl_FragColor.ggg;\nif (texture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).b * 3.5 < line_thresh)\ngl_FragColor.rgb = vec3(0.0, dot(gl_FragColor.rgb, vec3(1.0)), 0.0);\nif (texture2D(inputImageTexture2, uv_noise).g * 1.0 < block_thresh ||\ntexture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).g * 2.5 < line_thresh) {\nfloat line = fract(gl_FragCoord.y / 3.0);\nvec3 mask = vec3(3.0, 0.0, 0.0);\nif (line > 0.333)\nmask = vec3(0.0, 3.0, 0.0);\nif (line > 0.666)\nmask = vec3(0.0, 0.0, 3.0);\ngl_FragColor.xyz *= mask;\n}\n}");
        this.f6743S = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6736H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6735G);
        this.f6740L = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6739K);
        this.f6742P = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6741O);
        this.f6733E = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6734F = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6736H, this.f6737I);
        GLES20.glUniform1f(this.f6740L, f6729M);
        GLES20.glUniform1f(this.f6742P, f6731Q);
        GLES20.glUniform1f(this.f6733E, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6734F, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6727C) {
            this.f6738J = System.currentTimeMillis() - this.f6743S;
            if (this.f6738J > 200) {
                this.f6743S = System.currentTimeMillis();
            }
        }
        this.f6737I = (((float) this.f6738J) / 1000.0f) * 2.0f * 3.14159f * 0.01f;
        f6729M = f6730N;
        f6731Q = f6732R;
    }

    public static void change(float f, float f2) {
        f6730N = f;
        f6732R = f2;
    }
}
