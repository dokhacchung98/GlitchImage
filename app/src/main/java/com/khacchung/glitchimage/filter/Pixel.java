package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Pixel extends FilterRender {
    private static float f6068A = (MyApplication.imgWidth / 2.0f);
    private static float f6069D;
    private static float f6070E = (MyApplication.imgHeight / 2.0f);
    private static float f6071z;
    private String f6072B = "touchY";
    private int f6073C;
    private int f6074v;
    private int f6075w;
    private String f6076x = "touchX";
    private int f6077y;

    public Pixel() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 cor;\ncor.x =  gl_FragCoord.x/amountx;\ncor.y = (gl_FragCoord.y+amountx*1.5*mod(floor(cor.x),2.0))/(amountx*3.0);\nvec2 ico = floor( cor );\nvec2 fco = fract( cor );\nvec3 pix = step( 1.5, mod( vec3(0.0,1.0,2.0) + ico.x, 3.0 ) );\nvec3 ima = texture2D( inputImageTexture,amountx*ico*vec2(1.0,3.0)/iResolution.xy ).xyz;\nvec3 col = pix*dot( pix, ima );\ncol *= step( abs(fco.x-0.5), 0.4 );\ncol *= step( abs(fco.y-0.5), 0.4 );\ncol *= 1.2;\ngl_FragColor = vec4( col, 1.0 );\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6077y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6076x);
        this.f6073C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6072B);
        this.f6074v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6075w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6077y, f6071z);
        GLES20.glUniform1f(this.f6073C, f6069D);
        GLES20.glUniform1f(this.f6074v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6075w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6071z = f6068A;
        f6069D = f6070E;
    }

    public static void change(float f, float f2) {
        f6068A = f;
        f6070E = f2;
    }
}
