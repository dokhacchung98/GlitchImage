package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Hue extends FilterRender {
    private static float f6520A = (MyApplication.imgWidth / 2.0f);
    private static float f6521D;
    private static float f6522E = (MyApplication.imgHeight / 2.0f);
    private static float f6523z;
    private String f6524B = "touchY";
    private int f6525C;
    private int f6526v;
    private int f6527w;
    private String f6528x = "touchX";
    private int f6529y;

    public Hue() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nconst mat3 rgb2yuv_mat = mat3(\n0.2126,    0.7152,   0.0722,\n-0.09991,  -0.33609,  0.436,\n0.615,    -0.55861, -0.05639\n);\nconst mat3 yuv2rgb_mat = mat3(\n1.0,  0.0,      1.28033,\n1.0, -0.21482, -0.38059,\n1.0,  2.12798,  0.0\n);\nvec3 rgb2yuv(vec3 rgb) {\nreturn rgb * rgb2yuv_mat;\n}\nvec3 yuv2rgb(vec3 rgb) {\nreturn rgb * yuv2rgb_mat;\n}\nvec2 cartesian2polar(vec2 uv) {\nfloat r = sqrt(pow(uv.x, 2.0) + pow(uv.y, 2.0));\nfloat fi = atan(uv.y, uv.x);\nreturn vec2(r, fi);\n}\nvec2 polar2cartesian(vec2 uv) {  \nreturn vec2(uv.x*cos(uv.y), uv.x*sin(uv.y));   \n}\nvec2 rotate2(vec2 v, float fi) {\nreturn v*mat2(cos(fi), -sin(fi), sin(fi), cos(fi));\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*6.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec4 color = texture2D(inputImageTexture, uv); \nvec3 yuv = rgb2yuv(color.rgb);\nvec3 rgb = yuv2rgb(vec3(yuv.x, rotate2(yuv.yz, amountx)));\ncolor = vec4(rgb, 1.0);\ngl_FragColor = color;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6529y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6528x);
        this.f6525C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6524B);
        this.f6526v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6527w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6529y, f6523z);
        GLES20.glUniform1f(this.f6525C, f6521D);
        GLES20.glUniform1f(this.f6526v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6527w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6523z = f6520A;
        f6521D = f6522E;
    }

    public static void change(float f, float f2) {
        f6520A = f;
        f6522E = f2;
    }
}
