package com.kinglloy.wallpaper.chrooma;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidPreferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GamePreferences {
    public static final String TAG = GamePreferences.class.getName();
    public static final GamePreferences instance = new GamePreferences();
    public boolean breathAnimation;
    public boolean hideIcon;
    public boolean optionsSet = false;
    public boolean parallax;
    private Preferences prefs = new AndroidPreferences(new CustomPreferences());
    public boolean shake;
    public boolean startingAnimation;

    public GamePreferences() {
        load();
    }

    private void load() {
        this.breathAnimation = this.prefs.getBoolean(Constants.BREATHANIMATIONKEY, true);
        this.startingAnimation = this.prefs.getBoolean(Constants.STARTINGANIMATIONKEY, false);
        this.parallax = this.prefs.getBoolean(Constants.PARALLAX_BACK, true);
        this.shake = this.prefs.getBoolean(Constants.SHAKE_TO_CHANGE, true);
        if (this.prefs.getInteger(Constants.INITSHAPES) == 0) {
            initializeShapesArray();
            this.prefs.putInteger(Constants.INITSHAPES, 1);
            this.prefs.flush();
        }
        if (this.prefs.getInteger(Constants.INITCOLORS) == 0) {
            initializeColorsArray();
            this.prefs.putInteger(Constants.INITCOLORS, 1);
            this.prefs.flush();
        }
    }

    public void savePrefsChecks(boolean breathAnimation, boolean startingAnimation, boolean hideIcon, boolean parallax, boolean shake) {
        this.prefs.putBoolean(Constants.BREATHANIMATIONKEY, breathAnimation);
        this.prefs.putBoolean(Constants.STARTINGANIMATIONKEY, startingAnimation);
        this.prefs.putBoolean(Constants.HIDEICONKEY, hideIcon);
        this.prefs.putBoolean(Constants.PARALLAX_BACK, parallax);
        this.prefs.putBoolean(Constants.SHAKE_TO_CHANGE, shake);
        this.prefs.flush();
        this.breathAnimation = breathAnimation;
        this.startingAnimation = startingAnimation;
        this.hideIcon = hideIcon;
        this.parallax = parallax;
        this.shake = shake;
    }

    public void saveShapes(int position) {
        boolean z = true;
        String path = "shape-" + position;
        Preferences preferences = this.prefs;
        if (this.prefs.getBoolean(path, true)) {
            z = false;
        }
        preferences.putBoolean(path, z);
        this.prefs.flush();
    }

    private void initializeShapesArray() {
        for (int i = 0; i < 24; i++) {
            this.prefs.putBoolean("shape-" + i, true);
        }
        this.prefs.flush();
    }

    public void selectAllShapes() {
        int i;
        if (this.prefs.getInteger(Constants.INITSHAPES) == 1) {
            for (i = 0; i < 24; i++) {
                this.prefs.putBoolean("shape-" + i, true);
            }
            this.prefs.putInteger(Constants.INITSHAPES, 2);
        } else {
            for (i = 0; i < 24; i++) {
                this.prefs.putBoolean("shape-" + i, false);
            }
            this.prefs.putInteger(Constants.INITSHAPES, 1);
        }
        this.prefs.flush();
    }

    public void resetShape() {
        for (int i = 0; i < 24; i++) {
            this.prefs.putBoolean("shape-" + i, false);
        }
        this.prefs.flush();
    }

    public String getShape() {
        Array<Integer> shapes = new Array();
        for (int i = 0; i < 24; i++) {
            if (this.prefs.getBoolean("shape-" + i, false)) {
                shapes.add(Integer.valueOf(i));
            }
        }
        if (shapes.size == 0) {
            return "shapes/1.png";
        }
        return getShapeString(((Integer) shapes.get(MathUtils.random(Math.max(0, shapes.size - 1)))).intValue());
    }

    public Array<Integer> getShapes() {
        Array<Integer> shapes = new Array();
        for (int i = 0; i < 24; i++) {
            if (this.prefs.getBoolean("shape-" + i, false)) {
                shapes.add(Integer.valueOf(i));
            }
        }
        return shapes;
    }

    private String getShapeString(int number) {
        return "shapes/" + (number + 1) + ".png";
    }

    public int getShapePosition() {
        return this.prefs.getInteger(Constants.SHAPEKEY);
    }

    public void saveColors(int position) {
        boolean z = true;
        String path = "color-" + position;
        Preferences preferences = this.prefs;
        if (this.prefs.getBoolean(path, true)) {
            z = false;
        }
        preferences.putBoolean(path, z);
        this.prefs.flush();
    }

    private void initializeColorsArray() {
        for (int i = 0; i < getTotalPaletteNumber(); i++) {
            this.prefs.putBoolean("color-" + i, true);
        }
        this.prefs.flush();
    }

    public void selectAllColors() {
        int i;
        if (this.prefs.getInteger(Constants.INITCOLORS) == 1) {
            for (i = 0; i < getTotalPaletteNumber(); i++) {
                this.prefs.putBoolean("color-" + i, true);
            }
            this.prefs.putInteger(Constants.INITCOLORS, 2);
        } else {
            for (i = 0; i < getTotalPaletteNumber(); i++) {
                this.prefs.putBoolean("color-" + i, false);
            }
            this.prefs.putInteger(Constants.INITCOLORS, 1);
        }
        this.prefs.flush();
    }

    public void resetColor() {
        for (int i = 0; i < getTotalPaletteNumber(); i++) {
            this.prefs.putBoolean("color-" + i, false);
        }
        this.prefs.flush();
    }

    public Array<Integer> getColors() {
        Array<Integer> colors = new Array();
        for (int i = 0; i < getTotalPaletteNumber(); i++) {
            if (this.prefs.getBoolean("color-" + i, false)) {
                colors.add(Integer.valueOf(i));
            }
        }
        return colors;
    }

    public void saveBreathAnimationSpeed(int speed) {
        this.prefs.putInteger(Constants.BREATHSPEEDKEY, speed);
        this.prefs.flush();
    }

    public int getBreathAnimationSpeed() {
        if (this.prefs.getInteger(Constants.BREATHSPEEDKEY) != 0) {
            return this.prefs.getInteger(Constants.BREATHSPEEDKEY);
        }
        this.prefs.putInteger(Constants.BREATHSPEEDKEY, 50);
        return 50;
    }

    public void saveStartingAnimationSpeed(int speed) {
        this.prefs.putInteger(Constants.STARTINGSPEEDKEY, speed);
        this.prefs.flush();
    }

    public int getStartingAnimationSpeed() {
        if (this.prefs.getInteger(Constants.STARTINGSPEEDKEY) != 0) {
            return this.prefs.getInteger(Constants.STARTINGSPEEDKEY);
        }
        this.prefs.putInteger(Constants.STARTINGSPEEDKEY, 50);
        return 50;
    }

    public void saveBatteryMode(boolean checked) {
        this.prefs.putBoolean(Constants.BATTERYMODEKEY, checked);
        this.prefs.flush();
    }

    public boolean getBatteryMode() {
        return this.prefs.getBoolean(Constants.BATTERYMODEKEY, false);
    }

    public void setTimeLimit(int limit) {
        this.prefs.putInteger(Constants.CHANGESCREENKEY, limit);
        this.prefs.flush();
    }

    public float getTimeLimit() {
        switch (this.prefs.getInteger(Constants.CHANGESCREENKEY)) {
            case 1:
                return 60.0f;
            case 2:
                return 600.0f;
            case 3:
                return 1800.0f;
            case 4:
                return 3600.0f;
            case 5:
                return 21600.0f;
            case 6:
                return 86400.0f;
            case 7:
                return 604800.0f;
            default:
                return 0.0f;
        }
    }

    public int getTimeLimitOption() {
        return this.prefs.getInteger(Constants.CHANGESCREENKEY);
    }

    public void saveCurrentTime(long l) {
        this.prefs.putLong(Constants.CURRENTTIME, l);
        this.prefs.flush();
    }

    public long getCurrentTime() {
        return this.prefs.getLong(Constants.CURRENTTIME);
    }

    public int getSelectedGeneratedPalette() {
        int count = 0;
        for (int i = 0; i < getPaletteCount(); i++) {
            if (this.prefs.getBoolean("color-" + i, false)) {
                count++;
            }
        }
        return count;
    }

    public void saveNewPalette(int[] selectedColor) {
        for (int i = 0; i < selectedColor.length; i++) {
            this.prefs.putInteger(getPaletteKey(getPaletteCount(), i), selectedColor[i]);
        }
        incrementPaletteCount();
        resetColor();
        this.prefs.flush();
    }

    public void overwritePalette(int[] palette, int index) {
        for (int i = 0; i < palette.length; i++) {
            this.prefs.putInteger(getPaletteKey(index, i), palette[i]);
        }
        this.prefs.flush();
    }

    private void incrementPaletteCount() {
        this.prefs.putInteger(Constants.PALETTECOUNT, this.prefs.getInteger(Constants.PALETTECOUNT) + 1);
        this.prefs.flush();
    }

    private void decrementPaletteCount() {
        this.prefs.putInteger(Constants.PALETTECOUNT, this.prefs.getInteger(Constants.PALETTECOUNT) - 1);
        this.prefs.flush();
    }

    public int getPaletteCount() {
        return this.prefs.getInteger(Constants.PALETTECOUNT);
    }

    public Color[] getGeneratedColors(int i) {
        Color[] c = new Color[5];
        for (int j = 0; j < 5; j++) {
            c[j] = new Color();
            Color.argb8888ToColor(c[j], getGeneratedSingleColor(i, j));
        }
        return c;
    }

    public int[] getGeneratedColorsInteger(int i) {
        int[] c = new int[5];
        for (int j = 0; j < 5; j++) {
            c[j] = getGeneratedSingleColor(i, j);
        }
        return c;
    }

    public void deletePalette(int i) {
        int j;
        for (j = 0; j < 5; j++) {
            this.prefs.remove(getPaletteKey(i, j));
        }
        for (j = i + 1; j < getPaletteCount(); j++) {
            updatePaletteIndex(j, j - 1);
        }
        decrementPaletteCount();
    }

    private void updatePaletteIndex(int oldIndex, int newIndex) {
        for (int i = 0; i < 5; i++) {
            int color = this.prefs.getInteger(getPaletteKey(oldIndex, i));
            this.prefs.remove(getPaletteKey(oldIndex, i));
            this.prefs.putInteger(getPaletteKey(newIndex, i), color);
        }
    }

    private int getGeneratedSingleColor(int i, int j) {
        return this.prefs.getInteger(Constants.NEWPALETTKEY + i + j);
    }

    public int getTotalPaletteNumber() {
        return Constants.PALETTENUMBER + getPaletteCount();
    }

    private String getPaletteKey(int paletteIndex, int paletteColor) {
        return Constants.NEWPALETTKEY + paletteIndex + paletteColor;
    }

    public boolean isGyroEnabled() {
        return this.parallax;
    }

    public boolean shakeToChange() {
        return this.shake;
    }

    public void rated() {
        this.prefs.putBoolean(Constants.RATING, true);
        this.prefs.flush();
    }

    private boolean hasRated() {
        return this.prefs.getBoolean(Constants.RATING, false);
    }

    public boolean shouldRate() {
        return this.prefs.getInteger(Constants.RATE_COUNTER) > 3 && !hasRated();
    }

    public void incrementRateCounter() {
        this.prefs.putInteger(Constants.RATE_COUNTER, this.prefs.getInteger(Constants.RATE_COUNTER) + 1);
        this.prefs.flush();
    }

    public void setTileSize(int size) {
        this.prefs.putInteger(Constants.TILE_SIZE, size);
        this.prefs.flush();
    }

    public int getTileSize() {
        if (this.prefs.getInteger(Constants.TILE_SIZE) != 0) {
            return this.prefs.getInteger(Constants.TILE_SIZE);
        }
        this.prefs.putInteger(Constants.TILE_SIZE, 90);
        return 90;
    }

    public void saveGyroSensibility(int sensibility) {
        this.prefs.putInteger(Constants.GYRO_SENSIBILITY, sensibility);
        this.prefs.flush();
    }

    public int getGyroSensibility() {
        return this.prefs.getInteger(Constants.GYRO_SENSIBILITY, 30);
    }

    public void saveParallaxSensibility(int sensibility) {
        this.prefs.putInteger(Constants.PARALLAX_SENSIBILITY, sensibility);
        this.prefs.flush();
    }

    public int getParallaxSensibility() {
        return this.prefs.getInteger(Constants.PARALLAX_SENSIBILITY, 30);
    }

    public void isSharing() {
        this.prefs.putBoolean(Constants.HAS_SHARED, true);
    }

    public boolean hasShared() {
        return this.prefs.getBoolean(Constants.HAS_SHARED, false);
    }
}
