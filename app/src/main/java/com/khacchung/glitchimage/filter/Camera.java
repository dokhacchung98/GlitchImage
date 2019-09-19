package com.khacchung.glitchimage.filter;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Camera extends FilterRender {
    public Camera() {
        setFragmentShader("precision lowp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nvoid main() {\nvec2 uv = textureCoordinate.xy;\ngl_FragColor = texture2D(inputImageTexture, uv);\n}");
    }
}
