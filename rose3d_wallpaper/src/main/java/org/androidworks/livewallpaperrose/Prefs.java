package org.androidworks.livewallpaperrose;

public class Prefs {
    public static final String OPT_AUTOROTATE = "autorotate";
    private static final boolean OPT_AUTOROTATE_DEF = true;
    public static final String OPT_BACKGROUND = "background";
    private static final String OPT_BACKGROUND_DEF = "1";
    public static final String OPT_DOUBLETAP = "doubletap";
    private static final boolean OPT_DOUBLETAP_DEF = true;
    public static final String OPT_FIRST_LAUNCH = "first_launch";
    private static final boolean OPT_FIRST_LAUNCH_DEF = true;
    public static final String OPT_INTERACTIVE = "dream_interactive";
    private static final boolean OPT_INTERACTIVE_DEF = false;
    public static final String OPT_PETAL = "petal";
    private static final String OPT_PETAL_DEF = "1";
    public static final String OPT_RANDOM = "random";
    private static final boolean OPT_RANDOM_DEF = false;
    public static final String OPT_ROTATE = "rotate";
    public static final String OPT_ROTATEDIR = "rotateDirection";
    private static final String OPT_ROTATEDIR_DEF = "-1";
    private static final boolean OPT_ROTATE_DEF = true;
    public static final String OPT_TILT = "tilt";
    private static final boolean OPT_TILT_DEF = false;
    public static final String OPT_VIGNETTE = "vignette";
    private static final boolean OPT_VIGNETTE_DEF = false;
    public static final String PREFERENCES = "org.androidworks.org.androidworks.livewallpaperrose";

    public static boolean rotate = true;

    public static boolean tilt = true;

    public static String background = "1";

    public static String petal = "1";

    public static boolean vignette = false;

    public static boolean getRotate() {
        return rotate;
    }

    public static boolean getTilt() {
        return tilt;
    }

    public static String getBackground() {
        return background;
    }

    public static String getPetal() {
        return petal;
    }

    public static boolean getVignette() {
        return vignette;
    }

    public static boolean getRandomize() {
        return false;
    }


    public static void randomize() {
//        Random rand = new Random();
//        Editor editor = prefs.edit();
//        String[] arrBackground = res.getStringArray(C0000R.array.background_values);
//        String[] arrPetal = res.getStringArray(C0000R.array.petal_values);
//        editor.putString(OPT_BACKGROUND, arrBackground[rand.nextInt(arrBackground.length - 1)]);
//        editor.putString(OPT_PETAL, arrPetal[rand.nextInt(arrPetal.length - 1)]);
//        editor.commit();
    }

    public static String getRotateDir() {
        return OPT_ROTATEDIR_DEF;
    }

    public static boolean getAutoRotate() {
        return true;
    }

    public static boolean getInteractive() {
        return false;
    }

    public static boolean getFirstLaunch() {
        return false;
    }

    public static void updateFirstLaunch() {
    }
}
