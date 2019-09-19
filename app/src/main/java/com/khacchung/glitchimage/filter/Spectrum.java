package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Spectrum extends FilterRender {
    private static float f6198E = 0.0f;
    private static float f6199F = (MyApplication.imgWidth / 2.0f);
    private static float f6200I = 0.0f;
    private static float f6201J = (MyApplication.imgHeight / 2.0f);
    private static float f6202L = 0.3f;
    public static boolean f6203v = false;
    private float f6204A;
    private long f6205B = System.currentTimeMillis();
    private String f6206C = "touchX";
    private int f6207D;
    private String f6208G = "touchY";
    private int f6209H;
    private String f6210K = "amount";
    private long f6211M;
    private int f6212w;
    private int f6213x;
    private String f6214y = "iTime";
    private int f6215z;

    public Spectrum() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat sat( float t ) {\nreturn clamp( t, 0.0, 1.0 );\n}\nvec2 sat( vec2 t ) {\nreturn clamp( t, 0.0, 1.0 );\n}\nfloat remap  ( float t, float a, float b ) {\nreturn sat( (t - a) / (b - a) );\n}\nfloat linterp( float t ) {\nreturn sat( 1.0 - abs( 2.0*t - 1.0 ) );\n}\nvec3 spectrum_offset( float t ) {\nvec3 ret;\nfloat lo = step(t,0.5);\nfloat hi = 1.0-lo;\nfloat w = linterp( remap( t, 1.0/6.0, 5.0/6.0 ) );\nfloat neg_w = 1.0-w;\nret = vec3(lo,1.0,hi) * vec3(neg_w, w, neg_w);\nreturn pow( ret, vec3(1.0/2.2) );\n}\nfloat rand( vec2 n ) {\nreturn fract(sin(dot(n.xy, vec2(12.9898, 78.233)))* 43758.5453);\n}\nfloat srand( vec2 n ) {\nreturn rand(n) * 2.0 - 1.0;\n}\nfloat mytrunc( float x, float num_levels ) {\nreturn floor(x*num_levels) / num_levels;\n}\nvec2 mytrunc( vec2 x, float num_levels ) {\nreturn floor(x*num_levels) / num_levels;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nuv.y = uv.y;\nfloat time = mod(iTime*100.0, 32.0)/110.0;\nfloat gnm = sat( amountx );\nfloat rnd0 = rand( mytrunc( vec2(time, time), 6.0 ) );\nfloat r0 = sat((1.0-gnm)*0.7 + rnd0);\nfloat rnd1 = rand( vec2(mytrunc( uv.x, uv.y ), time) );\nfloat r1 = 0.5 - 0.5 * gnm + rnd1;\nr1 = 1.0 - max( 0.0, ((r1<1.0) ? r1 : 0.9999999) );\nfloat rnd2 = rand( vec2(mytrunc( uv.y, uv.x ), time) );\nfloat r2 = sat( rnd2 );\nfloat rnd3 = rand( vec2(mytrunc( uv.y, uv.x ), time) );\nfloat r3 = (1.0-sat(rnd3+0.8)) - 0.1;\nfloat pxrnd = rand( uv + time );\nfloat ofs = 0.05 * r2 * amountx * ( rnd0 > 0.5 ? 1.0 : -1.0 );\nofs += 0.5 * pxrnd * ofs;\nuv.y += 0.1 * r3 * amountx;\nconst int NUM_SAMPLES = 10;\nconst float RCP_NUM_SAMPLES_F = 1.0 / float(NUM_SAMPLES);\nvec4 sum = vec4(0.0);\nvec3 wsum = vec3(0.0);\nfor( int i=0; i<NUM_SAMPLES; ++i ) {\nfloat t = float(i) * RCP_NUM_SAMPLES_F;\nuv.x = sat( uv.x + ofs * t );\nvec4 samplecol = texture2D( inputImageTexture, uv, -10.0 );\nvec3 s = spectrum_offset( t );\nsamplecol.rgb = samplecol.rgb * s;\nsum += samplecol;\nwsum += s;\n}sum.rgb /= wsum;\nsum.a *= RCP_NUM_SAMPLES_F;\ngl_FragColor.a = sum.a;\ngl_FragColor.rgb = sum.rgb;\n}\n");
        this.f6211M = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6215z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6214y);
        this.f6207D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6206C);
        this.f6209H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6208G);
        this.f6212w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6213x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6215z, this.f6204A);
        GLES20.glUniform1f(this.f6207D, f6198E);
        GLES20.glUniform1f(this.f6209H, f6200I);
        GLES20.glUniform1f(this.f6212w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6213x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6203v) {
            this.f6205B = System.currentTimeMillis() - this.f6211M;
            if (this.f6205B > 1000) {
                this.f6211M = System.currentTimeMillis();
            }
        }
        this.f6204A = (((float) this.f6205B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f6198E = f6199F;
        f6200I = f6201J;
    }

    public static void change(float f, float f2) {
        f6199F = f;
        f6201J = f2;
    }
}
