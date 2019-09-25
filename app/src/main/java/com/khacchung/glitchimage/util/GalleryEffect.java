package com.khacchung.glitchimage.util;

import android.content.Context;

import com.khacchung.glitchimage.filter.Ascii;
import com.khacchung.glitchimage.filter.BWRender;
import com.khacchung.glitchimage.filter.Bit16;
import com.khacchung.glitchimage.filter.CRT1;
import com.khacchung.glitchimage.filter.Clones;
import com.khacchung.glitchimage.filter.Colorize;
import com.khacchung.glitchimage.filter.Compression;
import com.khacchung.glitchimage.filter.Data;
import com.khacchung.glitchimage.filter.DragImage;
import com.khacchung.glitchimage.filter.Emoji;
import com.khacchung.glitchimage.filter.Fall;
import com.khacchung.glitchimage.filter.Fall2;
import com.khacchung.glitchimage.filter.GB;
import com.khacchung.glitchimage.filter.Glitch;
import com.khacchung.glitchimage.filter.Glitch2;
import com.khacchung.glitchimage.filter.Hotline;
import com.khacchung.glitchimage.filter.Hue;
import com.khacchung.glitchimage.filter.Interference;
import com.khacchung.glitchimage.filter.LSD;
import com.khacchung.glitchimage.filter.Lens;
import com.khacchung.glitchimage.filter.Lines;
import com.khacchung.glitchimage.filter.Magnitude;
import com.khacchung.glitchimage.filter.Melt;
import com.khacchung.glitchimage.filter.Mirror;
import com.khacchung.glitchimage.filter.Moire;
import com.khacchung.glitchimage.filter.OldMobile;
import com.khacchung.glitchimage.filter.Parallax;
import com.khacchung.glitchimage.filter.Pixel;
import com.khacchung.glitchimage.filter.Plaza;
import com.khacchung.glitchimage.filter.RB;
import com.khacchung.glitchimage.filter.RG;
import com.khacchung.glitchimage.filter.RGB;
import com.khacchung.glitchimage.filter.RGBWave;
import com.khacchung.glitchimage.filter.Ripple;
import com.khacchung.glitchimage.filter.Shampain;
import com.khacchung.glitchimage.filter.Sinwave;
import com.khacchung.glitchimage.filter.Slicer;
import com.khacchung.glitchimage.filter.Spectrum;
import com.khacchung.glitchimage.filter.Tape2;
import com.khacchung.glitchimage.filter.VHSPause;
import com.khacchung.glitchimage.filter.VaporGlitch;
import com.khacchung.glitchimage.filter.Vlc;
import com.khacchung.glitchimage.filter.Waves;
import com.khacchung.glitchimage.filter.Waves2;
import com.khacchung.glitchimage.filter.WobbleRender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ezandroid.ezfilter.core.FilterRender;

public class GalleryEffect {
    public static ArrayList<FilterRender> renderList;
    public static List<String> listName = new ArrayList<>();

    public static class FilterType {
        public enum enum1 {
            None,
            COMPRESSION,
            CRT1,
            GLITCH,
            GLITCH2,
            HOTLINE,
            INTERFERENCE,
            LINES,
            OLDMOBILE,
            GB,
            RB,
            RG,
            RGB,
            RGBWAVE,
            RIPPLE,
            SHAMPAIN,
            SINWAVE,
            SLICER,
            SPECTRUM,
            BWRender,
            ASCII,
            BIT16,
            CLONES,
            COLORIZE,
            DRAGIMAGE,
            EMOJI,
            FALL,
            FALL2,
            HUE,
            LENS,
            LSD,
            MAGNITUDE,
            MELT,
            MIRROR,
            MOIRE,
            PARALLAX,
            PIXEL,
            PLAZA,
            TAPE2,
            VAPORGLITCH,
            VHSPAUSE,
            VLC,
            WAVES,
            WAVES2,
            WOBBLE,
            DATA
        }
    }

    public static List<String> getName() {
        return listName = Arrays.asList(Arrays.toString(FilterType.enum1.values())
                .replaceAll("^.|.$", "").split(", "));
    }

    public static FilterRender getEffect(Context context, int i) {
        renderList = new ArrayList<>(listName.size());
        switch (i) {
            case 0:
                return null;
            case 1:
                return new Compression();
            case 2:
                return new CRT1();
            case 3:
                return new Glitch();
            case 4:
                return new Glitch2();
            case 5:
                return new Hotline();
            case 6:
                return new Interference();
            case 7:
                return new Lines();
            case 8:
                return new OldMobile();
            case 9:
                return new GB();
            case 10:
                return new RB();
            case 11:
                return new RG();
            case 12:
                return new RGB();
            case 13:
                return new RGBWave();
            case 14:
                return new Ripple();
            case 15:
                return new Shampain();
            case 16:
                return new Sinwave();
            case 17:
                return new Slicer();
            case 18:
                return new Spectrum();
            case 19:
                return new BWRender(context);
            case 20:
                return new Ascii(context);
            case 21:
                return new Bit16();
            case 22:
                return new Clones();
            case 23:
                return new Colorize();
            case 24:
                return new DragImage();
            case 25:
                return new Emoji(context);
            case 26:
                return new Fall();
            case 27:
                return new Fall2();
            case 28:
                return new Hue();
            case 29:
                return new Lens();
            case 30:
                return new LSD();
            case 31:
                return new Magnitude();
            case 32:
                return new Melt();
            case 33:
                return new Mirror();
            case 34:
                return new Moire();
            case 35:
                return new Parallax();
            case 36:
                return new Pixel();
            case 37:
                return new Plaza();
            case 38:
                return new Tape2();
            case 39:
                return new VaporGlitch();
            case 40:
                return new VHSPause();
            case 41:
                return new Vlc();
            case 42:
                return new Waves();
            case 43:
                return new Waves2();
            case 44:
                return new WobbleRender();
            case 45:
                return new Data();
        }
        return null;
    }

    public static void setTouch(int i, int f, int f2) {
        switch (i) {
            case 0:
            case 44:
                return;
            case 1:
                Compression.change(f, f2);
                return;
            case 2:
                CRT1.change(f, f2);
                return;
            case 3:
                Glitch.change(f, f2);
                return;
            case 4:
                Glitch2.change(f, f2);
                return;
            case 5:
                Hotline.change(f, f2);
                return;
            case 6:
                Interference.change(f, f2);
                return;
            case 7:
                Lines.change(f, f2);
                return;
            case 8:
                OldMobile.change(f, f2);
                return;
            case 9:
                GB.change(f, f2);
                return;
            case 10:
                RB.change(f, f2);
                return;
            case 11:
                RG.change(f, f2);
                return;
            case 12:
                RGB.change(f, f2);
                return;
            case 13:
                RGBWave.change(f, f2);
                return;
            case 14:
                Ripple.change(f, f2);
                return;
            case 15:
                Shampain.change(f, f2);
                return;
            case 16:
                Sinwave.change(f, f2);
                return;
            case 17:
                Slicer.change(f, f2);
                return;
            case 18:
                Spectrum.change(f, f2);
                return;
            case 19:
                return;
            case 20:
                Ascii.change(f, f2);
                return;
            case 21:
                Bit16.change(f, f2);
                return;
            case 22:
                Clones.change(f, f2);
                return;
            case 23:
                Colorize.change(f, f2);
                return;
            case 24:
                DragImage.change(f, f2);
                return;
            case 25:
                Emoji.change(f, f2);
                return;
            case 26:
                Fall.change(f, f2);
                return;
            case 27:
                Fall2.change(f, f2);
                return;
            case 28:
                Hue.change(f, f2);
                return;
            case 29:
                Lens.change(f, f2);
                return;
            case 30:
                LSD.change(f, f2);
                return;
            case 31:
                Magnitude.change(f, f2);
                return;
            case 32:
                Melt.change(f, f2);
                return;
            case 33:
                Mirror.change(f, f2);
                return;
            case 34:
                Moire.change(f, f2);
                return;
            case 35:
                Parallax.change(f, f2);
                return;
            case 36:
                Pixel.change(f, f2);
                return;
            case 37:
                Plaza.change(f, f2);
                return;
            case 38:
                return;
            case 39:
                VaporGlitch.change(f, f2);
                return;
            case 40:
                VHSPause.change(f, f2);
                return;
            case 41:
                Vlc.change(f, f2);
                return;
            case 42:
                Waves.change(f, f2);
                return;
            case 43:
                Waves2.change(f, f2);
                return;
            case 45:
                Data.change(f, f2);
                return;
        }
    }
}
