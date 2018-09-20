package com.kinglloy.wallpaper.chrooma;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Palette {
    private static int actualPaletteNumber = 0;
    static Color[] n1 = new Color[]{hexColor("675948"), hexColor("B27E2D"), hexColor("FCA212"), hexColor("BFD3C0"), hexColor("939684")};
    static Color[] n10 = new Color[]{new Color(0.3764706f, 0.47058824f, 0.28235295f, 1.0f), new Color(0.47058824f, 0.5647059f, 0.28235295f, 1.0f), new Color(0.7529412f, 0.84705883f, 0.3764706f, 1.0f), new Color(0.9411765f, 0.9411765f, 0.84705883f, 1.0f), new Color(0.3764706f, 0.28235295f, 0.28235295f, 1.0f)};
    static Color[] n11 = new Color[]{hexColor("edebe6"), hexColor("d6e1c7"), hexColor("94c7b6"), hexColor("403b33"), hexColor("D3643B")};
    static Color[] n12 = new Color[]{hexColor("5E3929"), hexColor("CD8C52"), hexColor("B7D1A3"), hexColor("DEE8BE"), hexColor("FCF7D3")};
    static Color[] n13 = new Color[]{hexColor("DAD6CA"), hexColor("1BB0CE"), hexColor("4F8699"), hexColor("6A5E72"), hexColor("563444")};
    static Color[] n14 = new Color[]{hexColor("4E395D"), hexColor("827085"), hexColor("8EBE94"), hexColor("CCFC8E"), hexColor("DC5B3E")};
    static Color[] n15 = new Color[]{hexColor("831C1D"), hexColor("B34127"), hexColor("FDB458"), hexColor("3C8D86"), hexColor("136A71")};
    static Color[] n16 = new Color[]{hexColor("F03C02"), hexColor("C21A01"), hexColor("A30006"), hexColor("6B0103"), hexColor("1C0113")};
    static Color[] n17 = new Color[]{hexColor("F5E0D3"), hexColor("FFD0B3"), hexColor("FFB88C"), hexColor("DE6262"), hexColor("4D3B3B")};
    static Color[] n18 = new Color[]{hexColor("d9F2F9"), hexColor("3A89C9"), hexColor("F26C4F"), hexColor("9CC4E4"), hexColor("1B325F")};
    static Color[] n19 = new Color[]{hexColor("bce0e0"), hexColor("686263"), hexColor("efeadc"), hexColor("3b7a7f"), hexColor("2e302f")};
    static Color[] n2 = new Color[]{hexColor("ECD078"), new Color(0.8509804f, 0.35686275f, 0.2627451f, 1.0f), new Color(0.7529412f, 0.16078432f, 0.25882354f, 1.0f), new Color(0.32941177f, 0.14117648f, 0.21568628f, 1.0f), new Color(0.3254902f, 0.46666667f, 0.47843137f, 1.0f)};
    static Color[] n20 = new Color[]{hexColor("f9cf8d"), hexColor("fd9c61"), hexColor("f3644f"), hexColor("ea3341"), hexColor("3e8182")};
    static Color[] n21 = new Color[]{hexColor("fd9826"), hexColor("424242"), hexColor("e9e9e9"), hexColor("bcbcbc"), hexColor("3899b9")};
    static Color[] n22 = new Color[]{hexColor("0ca5b0"), hexColor("4e3f30"), hexColor("fefeeb"), hexColor("f8f4e4"), hexColor("a5b3aa")};
    static Color[] n23 = new Color[]{hexColor("69d2e7"), hexColor("a7dbd8"), hexColor("e0e4cc"), hexColor("f38630"), hexColor("fa6900")};
    static Color[] n24 = new Color[]{hexColor("00dffc"), hexColor("00b4cc"), hexColor("008c9e"), hexColor("005f6b"), hexColor("343838")};
    static Color[] n25 = new Color[]{hexColor("a70267"), hexColor("f10c49"), hexColor("fb6b41"), hexColor("f6d86b"), hexColor("339194")};
    static Color[] n26 = new Color[]{hexColor("11766d"), hexColor("410936"), hexColor("a00b53"), hexColor("e46f0a"), hexColor("f0b300")};
    static Color[] n27 = new Color[]{hexColor("cfffdd"), hexColor("b4dec1"), hexColor("58535f"), hexColor("a85163"), hexColor("ff1f4c")};
    static Color[] n3 = new Color[]{new Color(0.8117647f, 0.9411765f, 0.61960787f, 1.0f), new Color(0.65882355f, 0.85882354f, 0.65882355f, 1.0f), new Color(0.4745098f, 0.7411765f, 0.6039216f, 1.0f), new Color(0.23137255f, 0.5254902f, 0.5254902f, 1.0f), new Color(0.043137256f, 0.28235295f, 0.41960785f, 1.0f)};
    static Color[] n4 = new Color[]{hexColor("AEBDA6"), hexColor("F24B52"), hexColor("B6D8BD"), hexColor("F0DEB6"), hexColor("2E2613")};
    static Color[] n5 = new Color[]{new Color(0.25490198f, 0.24313726f, 0.2901961f, 1.0f), new Color(0.4509804f, 0.38431373f, 0.43137255f, 1.0f), new Color(0.7019608f, 0.5058824f, 0.5176471f, 1.0f), new Color(0.9411765f, 0.7058824f, 0.61960787f, 1.0f), new Color(0.96862745f, 0.89411765f, 0.74509805f, 1.0f)};
    static Color[] n6 = new Color[]{new Color(0.3647059f, 0.25490198f, 0.34117648f, 1.0f), new Color(0.5137255f, 0.5254902f, 0.5372549f, 1.0f), new Color(0.65882355f, 0.7921569f, 0.7294118f, 1.0f), new Color(0.7921569f, 0.84313726f, 0.69803923f, 1.0f), new Color(0.92156863f, 0.8901961f, 0.6666667f, 1.0f)};
    static Color[] n7 = new Color[]{new Color(0.9411765f, 0.44705883f, 0.25490198f, 1.0f), new Color(0.7529412f, 0.28235295f, 0.28235295f, 1.0f), new Color(0.3764706f, 0.09411765f, 0.28235295f, 1.0f), new Color(0.28235295f, 0.0f, 0.28235295f, 1.0f), new Color(0.1882353f, 0.0f, 0.1882353f, 1.0f)};
    static Color[] n8 = new Color[]{new Color(0.627451f, 0.77254903f, 0.37254903f, 1.0f), new Color(0.47843137f, 0.7019608f, 0.09019608f, 1.0f), new Color(0.050980393f, 0.40392157f, 0.34901962f, 1.0f), new Color(0.043137256f, 0.18039216f, 0.34901962f, 1.0f), new Color(0.16470589f, 0.015686275f, 0.2901961f, 1.0f)};
    static Color[] n9 = new Color[]{hexColor("6A1D03"), hexColor("A71A13"), hexColor("C8600B"), hexColor("E9AB3C"), hexColor("E3CFAC")};
    public static Color[][] palettes = new Color[][]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, n21, n22, n23, n24, n25, n26, n27};

    public static int getActualPaletteNumber() {
        return actualPaletteNumber;
    }

    public static Color[] getRandom() {
        Array<Integer> colors = GamePreferences.instance.getColors();
        if (colors.size <= 0) {
            return palettes[0];
        }
        int i = MathUtils.random(colors.size - 1);
        actualPaletteNumber = i;
        if (i >= GamePreferences.instance.getSelectedGeneratedPalette()) {
            return palettes[colors.get(i) - GamePreferences.instance.getPaletteCount()];
        }
        return generatedColors(colors.get(i));
    }

    private static Color[] generatedColors(int i) {
        return GamePreferences.instance.getGeneratedColors(i);
    }

    public static Color hexColor(String colorStr) {
        return new Color(((float) Integer.valueOf(colorStr.substring(0, 2), 16).intValue()) / 255.0f, ((float) Integer.valueOf(colorStr.substring(2, 4), 16).intValue()) / 255.0f, ((float) Integer.valueOf(colorStr.substring(4, 6), 16).intValue()) / 255.0f, 1.0f);
    }
}
