package com.khacchung.glitchimage.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.util.Path;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class Ascii extends MultiBitmapInputRender {
    private static final String[] f6709E = {Path.DRAWABLE.wrap("2131165276")};
    private static float f6710H;
    private static float f6711I = (MyApplication.imgWidth / 2.0f);
    private static float f6712L;
    private static float f6713M = (MyApplication.imgHeight / 2.0f);
    private int f6714C;
    private int f6715D;
    private String f6716F = "touchX";
    private int f6717G;
    private String f6718J = "touchY";
    private int f6719K;

    public Ascii(Context context) {
        super(context, f6709E);
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define G 11 \nvoid main() {  \nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 fragCoord = vec2(gl_FragCoord.x, gl_FragCoord.y);\nvec2 fragColor = vec2(gl_FragColor.x, gl_FragColor.y);\nvec2 R = iResolution.xy;\nvec2 S = vec2(amountx*1.6,amountx*1.6);\nfragCoord /= S;\nfragColor-=fragColor;\nfloat v = texture2D(inputImageTexture,floor(fragCoord)*S/R).r;\nfragCoord = fract(fragCoord);\nfragCoord.x = fragCoord.x/1.0;\nint c = int(float(G)*v);\nfloat mix1 = 1.0 - amountx/10.;\nif (mix1 < 0.0){\nmix1 = 0.0;\n}\ngl_FragColor.rgb = mix(texture2D(inputImageTexture2, fragCoord/5. + fract( vec2(c, c/5) / 5.)), texture2D(inputImageTexture, gl_FragCoord.xy/R), mix1).rgb;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6717G = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6716F);
        this.f6719K = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6718J);
        this.f6714C = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6715D = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6717G, f6710H);
        GLES20.glUniform1f(this.f6719K, f6712L);
        GLES20.glUniform1f(this.f6714C, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6715D, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6710H = f6711I;
        f6712L = f6713M;
    }

    public static void change(float f, float f2) {
        f6711I = f;
        f6713M = f2;
    }
}
