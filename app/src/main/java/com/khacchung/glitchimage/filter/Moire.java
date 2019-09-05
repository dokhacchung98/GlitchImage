package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Moire extends FilterRender {
    private static float f6032A;
    private static float f6033D;
    private static float f6034E;
    private static float f6035z;
    private String f6036B = "touchY";
    private int f6037C;
    private int f6038v;
    private int f6039w;
    private String f6040x = "touchX";
    private int f6041y;

    public Moire() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define TV_SCREEN_RESOLUTION_X 1340.0\nstruct Rectangle {\nvec3 v1;\nvec3 v2;\nvec3 v3;\n};\nvec3 CalcNormal(Rectangle A) {\nvec3 first = A.v1 - A.v2;\nvec3 second = A.v2 - A.v3;\nreturn cross(first,second);\n}\nvec3 Intersect(vec3 B, vec3 r, vec3 A, vec3 p) {\nfloat u=0.0;\nif (r.x*p.x+r.y*p.y+r.z*p.z!=0.0) {\nu =  (dot(A,p)-dot(B,p))/(dot(r,p));\n}\nreturn B+r*u;\n}\nvec2 Calc_ul(vec3 B, vec3 C, vec3 r, vec3 s) {\nfloat l = (C.y*r.x-B.y*r.x-C.x*r.y+B.x*r.y)/(s.y-s.x*r.y);\nfloat u = 0.0;\nif (r.x==0.0) {\nu=0.0;\n}else{\nu = (C.x-B.x-l*s.x)/r.x;\n}\nreturn vec2(u,l);\n}\nvec3 GetScreenPixelColor(vec2 ul) {\nfloat x = mod(ul.x * TV_SCREEN_RESOLUTION_X / (1700.0/iResolution.x) * 3.0,3.0);\nvec3 tex = texture2D(inputImageTexture, vec2(ul.x,ul.y) ).xyz;\nreturn mix(mix(vec3(tex.r,0.0,0.0),vec3(0.0,tex.g,0.0),step(1.0,x)),\nvec3(0.0,0.0,tex.b),step(2.0,x));\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.5+mouse.x/iResolution.x*1.;\nfloat amounty = 0.5+mouse.y/iResolution.y*1.;\namountx = 2.0-amountx;\namountx -= 1.0;\nvec2 q = gl_FragCoord.xy / iResolution.xy;\nvec2 rect_offset = vec2(-0.6, -0.4);\nfloat zoom = 0.5;\nRectangle rect;\nrect.v1 = vec3(0.1+rect_offset.x, 0.1+rect_offset.y, 0.4+(amounty*0.1));\nrect.v2 = vec3(0.1+rect_offset.x, 0.9+rect_offset.y, 0.5+(amountx*0.05));\nrect.v3 = vec3(0.9+rect_offset.x, 0.9+rect_offset.y, 0.5-(amountx*0.1));\nfloat bu = abs(rect.v2.x - rect.v3.x);\nfloat bl = abs(rect.v1.y - rect.v2.y);\nvec3 rect_normal_vector = CalcNormal(rect);\nrect_normal_vector = normalize(rect_normal_vector);\nvec3 plane_vector1 = normalize(rect.v2 - rect.v1);\nvec3 plane_vector2 = normalize(rect.v3 - rect.v2);\nvec3 screen_vector = vec3( q.x/10.0 , q.y/10.0 , -1);\nvec3 camera = vec3( q.x-0.5, q.y+0.5, zoom);\nvec3 ray = camera - screen_vector;\nvec3 intersection = Intersect(camera,ray,rect.v3,rect_normal_vector);\nvec2 ul = Calc_ul(rect.v2, intersection, plane_vector2, plane_vector1);\nvec3 new_col = vec3(0.0, 0.0, 0.0);\nvec3 old_col = vec3(0.0, 0.0, 0.0);\nif (ul.x>0.0 && ul.x<1.0 && ul.y>0.0 && ul.y<1.0) {\nfloat count=0.0;\nfor (float i=-1.0;i<1.0;i+=0.33) {\nnew_col += GetScreenPixelColor(vec2(ul.x+i/iResolution.x,ul.y));\ncount = count + 1.0;\n}\nnew_col = 3.0*new_col/count;\nold_col = texture2D(inputImageTexture, vec2(ul.x,ul.y) ).xyz;\n}\ngl_FragColor = vec4(new_col,1.0);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6041y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6040x);
        this.f6037C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6036B);
        this.f6038v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6039w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6041y, f6035z);
        GLES20.glUniform1f(this.f6037C, f6033D);
        GLES20.glUniform1f(this.f6038v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6039w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6035z = f6032A;
        f6033D = f6034E;
    }

    public static void change(float f, float f2) {
        f6032A = f;
        f6034E = f2;
    }
}
