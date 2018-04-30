package com.maxelus.galaxypacklivewallpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.mybadlogic.gdx.Gdx;
import com.mybadlogic.gdx.graphics.Pixmap;
import com.mybadlogic.gdx.graphics.Pixmap.Format;
import com.mybadlogic.gdx.graphics.Texture;
import com.maxelus.galaxypacklivewallpaper.galaxies.SpiralGalaxy;
import com.maxelus.galaxypacklivewallpaper.galaxies.ZoomGalaxy;
import com.maxelus.gdx.backends.android.livewallpaper.InputProcessorLW;
import com.maxelus.gdxlw.LibdgxWallpaperApp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class GalaxyPackWallpaper extends LibdgxWallpaperApp implements OnSharedPreferenceChangeListener, InputProcessorLW {
    private static int[] bufNr = new int[]{-1, -1, -1, -1, -1};
    private static Pixmap[] bufPix = new Pixmap[5];
    private static int bufPos = 0;
    private static boolean loading;
    public static int maxFreq = getMaxFreq();
    public static int numCores = getNumCores();
    private static final int[] textures = new int[]{1032, 3581, 3305, 3132, 231941, 222102, 220819, 323866, 457165, 386542, 188272, 155353, 193381, 461112, 197008, 202561, 300991, 3264, 3405, 3250, 176348, 179837, 246198, 381081, 296269, 401472, 433577, 341160, 407496, 110850, 229605, 730833, 1426320, 669748, 469773, 708165};
    private int galaxy = -1;
    private SharedPreferences mPrefs;
    private int newgalaxy = 0;
    private int randCount;
    private boolean randGalaxyBox;
    private boolean randSkyBox;
    private long randTimer;
    private boolean readPref = true;
    private GalaxyRenderer renderer = null;

    public GalaxyPackWallpaper(SharedPreferences mP) {
        this.mPrefs = mP;
        this.mPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void create() {
        Gdx.input.setInputProcessor(this);
        onSharedPreferenceChanged(this.mPrefs, null);
        createRenderer(this.newgalaxy);
        this.galaxy = this.newgalaxy;
    }

    public void dispose() {
        if (this.renderer != null) {
            this.renderer.dispose();
        }
    }

    public void pause() {
        if (this.renderer != null) {
            this.renderer.pause();
        }
    }

    public void render() {
        if (this.renderer != null) {
            this.renderer.render();
        }
    }

    public void resize(int width, int height) {
        if (this.renderer != null) {
            this.renderer.resize(width, height);
        }
    }

    public void resume() {
        int r = this.galaxy;
        if (this.randGalaxyBox && System.currentTimeMillis() >= this.randTimer + ((long) this.randCount)) {
            this.randTimer = System.currentTimeMillis();
            if (this.randGalaxyBox) {
                while (r == this.galaxy) {
                    r = (int) (Math.random() * 23.0d);
                }
                this.newgalaxy = r;
                this.readPref = false;
                Editor e = this.mPrefs.edit();
                e.putString("galaxy_pref", String.valueOf(r));
                e.commit();
            }
        }
        if (this.galaxy != this.newgalaxy) {
            createRenderer(this.newgalaxy);
            this.galaxy = this.newgalaxy;
        } else if (this.renderer != null) {
            this.renderer.resume();
        }
    }

    public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
        if (this.renderer != null) {
            this.renderer.offsetChange(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
        }
    }

    public boolean keyDown(int arg0) {
        return false;
    }

    public boolean keyTyped(char arg0) {
        return false;
    }

    public boolean keyUp(int arg0) {
        return false;
    }

    public boolean scrolled(int arg0) {
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int newParam) {
        if (this.renderer != null) {
            this.renderer.touchDown(x, y, pointer, newParam);
        }
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        if (this.renderer != null) {
            this.renderer.touchDragged(x, y, pointer);
        }
        return false;
    }

    public boolean touchMoved(int x, int y) {
        if (this.renderer != null) {
            this.renderer.touchMoved(x, y);
        }
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        if (this.renderer != null) {
            this.renderer.touchUp(x, y, pointer, button);
        }
        return false;
    }

    public void touchDrop(int x, int y) {
        if (this.renderer != null) {
            this.renderer.touchDrop(x, y);
        }
    }

    public void touchTap(int x, int y) {
    }

    public void createRenderer(int g) {
        switch (g) {
            case 0:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(0);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 0, this.readPref);
                this.renderer.create();
                break;
            case 1:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(1);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 1, this.readPref);
                this.renderer.create();
                break;
            case 2:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(2);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 2, this.readPref);
                this.renderer.create();
                break;
            case 3:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(3);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 3, this.readPref);
                this.renderer.create();
                break;
            case 4:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(4);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 4, this.readPref);
                this.renderer.create();
                break;
            case 5:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(5);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 5, this.readPref);
                this.renderer.create();
                break;
            case 6:
                if (this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 9 || this.galaxy == 10) {
                    if (this.renderer != null) {
                        this.renderer.setType(6);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 6);
                this.renderer.create();
                break;
            case 7:
                if (this.galaxy == 6 || this.galaxy == 8 || this.galaxy == 9 || this.galaxy == 10) {
                    if (this.renderer != null) {
                        this.renderer.setType(7);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 7);
                this.renderer.create();
                break;
            case 8:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 9 || this.galaxy == 10) {
                    if (this.renderer != null) {
                        this.renderer.setType(8);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 8);
                this.renderer.create();
                break;
            case 9:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 10) {
                    if (this.renderer != null) {
                        this.renderer.setType(9);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 9);
                this.renderer.create();
                break;
            case 10:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 9) {
                    if (this.renderer != null) {
                        this.renderer.setType(10);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 10);
                this.renderer.create();
                break;
            case 11:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(6);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 6, this.readPref);
                this.renderer.create();
                break;
            case 12:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(7);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 7, this.readPref);
                this.renderer.create();
                break;
            case 13:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(8);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 8, this.readPref);
                this.renderer.create();
                break;
            case 14:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(9);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 9, this.readPref);
                this.renderer.create();
                break;
            case 15:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(10);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 10, this.readPref);
                this.renderer.create();
                break;
            case 16:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(11);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 11, this.readPref);
                this.renderer.create();
                break;
            case 17:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 9 || this.galaxy == 18) {
                    if (this.renderer != null) {
                        this.renderer.setType(17);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 17);
                this.renderer.create();
                break;
            case 18:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 9 || this.galaxy == 17) {
                    if (this.renderer != null) {
                        this.renderer.setType(18);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 18);
                this.renderer.create();
                break;
            case 19:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(12);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 12, this.readPref);
                this.renderer.create();
                break;
            case 20:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(13);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 13, this.readPref);
                this.renderer.create();
                break;
            case 21:
                if ((this.galaxy < 6 && this.galaxy >= 0) || ((this.galaxy >= 11 && this.galaxy < 17) || (this.galaxy >= 19 && this.galaxy <= 21))) {
                    if (this.renderer != null) {
                        this.renderer.setType(14);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new SpiralGalaxy(this.mPrefs, 14, this.readPref);
                this.renderer.create();
                break;
            case 22:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 9 || this.galaxy == 17 || this.galaxy == 18 || this.galaxy == 23) {
                    if (this.renderer != null) {
                        this.renderer.setType(22);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 22);
                this.renderer.create();
                break;
            case 23:
                if (this.galaxy == 6 || this.galaxy == 7 || this.galaxy == 8 || this.galaxy == 9 || this.galaxy == 17 || this.galaxy == 18 || this.galaxy == 22) {
                    if (this.renderer != null) {
                        this.renderer.setType(23);
                        break;
                    }
                }
                if (this.renderer != null) {
                    this.renderer.dispose();
                }
                this.renderer = null;
                this.renderer = new ZoomGalaxy(this.mPrefs, 23);
                this.renderer.create();
                break;
        }
        this.readPref = true;
    }

    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (key == null) {
            this.newgalaxy = Integer.parseInt(sp.getString("galaxy_pref", "0"));
            this.readPref = true;
            this.randGalaxyBox = sp.getBoolean("galaxy_rand_box_pref", false);
            if (this.randGalaxyBox) {
                this.randTimer = System.currentTimeMillis();
            }
            this.randCount = Integer.parseInt(sp.getString("random_count_pref", "1800")) * 1000;
        } else if (key.equalsIgnoreCase("galaxy_pref")) {
            this.newgalaxy = Integer.parseInt(sp.getString("galaxy_pref", "0"));
            this.readPref = false;
        } else if (key.equalsIgnoreCase("random_count_pref")) {
            this.randCount = Integer.parseInt(sp.getString("random_count_pref", "1800")) * 1000;
            this.randTimer = System.currentTimeMillis();
        } else if (key.equalsIgnoreCase("galaxy_rand_box_pref")) {
            this.randGalaxyBox = sp.getBoolean("galaxy_rand_box_pref", false);
            this.randTimer = System.currentTimeMillis();
        }
    }

    public static byte[] readFile(int pos, int size, InputStream in) {
        DataInputStream dis;
        byte[] buf = new byte[size];
        boolean loading = true;
        DataInputStream dis2 = null;
        while (loading) {
            try {
                dis = new DataInputStream(new BufferedInputStream(in, 8192));
                try {
                    int skip = dis.skipBytes(pos);
                    int read = dis.read(buf, 0, size);
                    dis.close();
                    if (skip == pos && read == size) {
                        loading = false;
                    }
                    dis2 = dis;
                } catch (Exception e) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e2) {
                    }
                    loading = true;
                    dis2 = dis;
                }
            } catch (Exception e3) {
                dis = dis2;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loading = true;
                dis2 = dis;
            }
        }
        return buf;
    }

    public static int getPos(int nr, int[] tab) {
        int s = 15;
        for (int i = 0; i < nr; i++) {
            s += tab[i];
        }
        return s;
    }

    public static Texture getTexture(int nr, int typ, boolean mipBol) {
        Pixmap pixmap;
        Texture tex = null;
        for (int i = 0; i < 5; i++) {
            if (bufNr[i] == nr) {
                return new Texture(bufPix[i], Format.RGB565, mipBol);
            }
        }
        int pos = getPos(nr, textures);
        int size = textures[nr];
        long t = System.currentTimeMillis();
        loading = true;
        while (loading) {
            try {
                Texture tex2 = null;
                byte[] buf = readFile(pos, size, Gdx.files.internal("galaxy/g.png").read());
                Pixmap pix = new Pixmap(buf, 0, buf.length);
                try {
                    tex2 = new Texture(pix, Format.RGB565, mipBol);
                } catch (Exception e) {
                    pixmap = pix;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e2) {
                    }
                    loading = true;
                }
                try {
                    if (bufPix[typ] != null) {
                        bufPix[typ].dispose();
                    }
                    bufNr[typ] = nr;
                    bufPix[typ] = pix;
                    loading = false;
                    pixmap = pix;
                    tex = tex2;
                } catch (Exception e3) {
                    pixmap = pix;
                    tex = tex2;
                    Thread.sleep(500);
                    loading = true;
                }
            } catch (Exception e4) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loading = true;
            }
        }
        return tex;
    }

    private static int getNumCores() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }).length;
        } catch (Exception e) {
            return 1;
        }
    }

    private static int getMaxFreq() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")), 20);
            String load = reader.readLine();
            reader.close();
            return Integer.parseInt(load);
        } catch (Exception e) {
            return 0;
        }
    }
}
