package com.maxelus.galaxypacklivewallpaper.galaxies;

import android.content.SharedPreferences;

import com.mybadlogic.gdx.Gdx;
import com.mybadlogic.gdx.graphics.GL11;
import com.mybadlogic.gdx.graphics.Texture;
import com.mybadlogic.gdx.graphics.Texture.TextureFilter;
import com.mybadlogic.gdx.graphics.g2d.ParticleEmitter;
import com.mybadlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;
import com.mybadlogic.gdx.graphics.g2d.Sprite;
import com.mybadlogic.gdx.graphics.g2d.SpriteBatch;
import com.mybadlogic.gdx.utils.Array;
import com.maxelus.galaxypacklivewallpaper.GalaxyPackWallpaper;
import com.maxelus.galaxypacklivewallpaper.GalaxyRenderer;
import com.maxelus.galaxypacklivewallpaper.ParticleEffectEnc;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidGraphicsLW;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidInputLW;

public class ZoomGalaxy extends GalaxyRenderer {
    private static final int emiSize = 2;
    private static final String imgDir = "galaxy";
    private static final int maxWH = 1000;
    private static float wind = 0.0f;
    private long FPS = 25;
    private Array<ParticleEmitter> ParticleE = null;
    private Array<ParticleEmitter> ParticleEFull = null;
    private boolean accON;
    private float accSpeed;
    private boolean anim = false;
    private float ax;
    float[] axT = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] axT2;
    private float axd;
    private int axi = 0;
    private float ay;
    float[] ayT = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] ayT2;
    private float ayd;
    private float cX;
    private float cY;
    private float delta = 1.0f;
    private int dx;
    private float dxx;
    private ParticleEffectEnc effect;
    private float emX;
    private float emXnew;
    private float emY;
    private String emiFile1 = "/assets/galaxy/g1.parc";
    private String emiFile2 = "/assets/galaxy/g2.parc";
    private String emiFile3 = "/assets/galaxy/g3.parc";
    private float[] f112g = new float[]{0.0f};
    int[][] galaxyPackTex = new int[][]{new int[]{6, 0}, new int[]{9, 0}, new int[]{20, 0}, new int[]{21, 0}, new int[]{22, 0}, new int[]{29, 0}, new int[]{30, 0}, new int[]{34, 0}, new int[]{35, 0}};
    private Texture galaxyTex;
    private int galaxyTexNr;
    int galaxyType = 0;
    private float graw;
    private float grawA;
    private boolean load = false;
    private SharedPreferences mPrefs;
    private int maxP = 10;
    private int oldx;
    private int orient;
    private int pref = 0;
    private float scale;
    private SpriteBatch spriteBatch;
    private Texture starTex;
    private int starTexNr;
    private boolean starsON;
    private boolean tab = false;
    private boolean toutch;
    private float u_scale;
    private float[] f113w = new float[]{0.0f};
    private float wind2;
    private float windA;
    private float xadM = 1.0f;
    private float yadM = 1.0f;

    public ZoomGalaxy(SharedPreferences mP, int newgalaxy) {
        this.galaxyType = newgalaxy;
        this.mPrefs = mP;
        this.mPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void create() {
        if (Gdx.graphics.getWidth() > 1000 || Gdx.graphics.getHeight() > 1000) {
            this.tab = true;
            if (GalaxyPackWallpaper.numCores >= 2 && GalaxyPackWallpaper.maxFreq > 1200000) {
                this.tab = false;
            }
        } else {
            this.tab = false;
        }
        Gdx.input.setInputProcessor(this);
        onSharedPreferenceChanged(this.mPrefs, null);
        this.spriteBatch = null;
        this.spriteBatch = new SpriteBatch();
        setType(this.galaxyType);
        this.orient = 0;
        this.axT2 = this.axT;
        this.ayT2 = this.ayT;
        this.xadM = 1.0f;
        this.yadM = 1.0f;
        this.orient = ((AndroidInputLW) Gdx.input).getOrientation();
        switch (this.orient) {
            case 0:
                this.axT2 = this.axT;
                this.ayT2 = this.ayT;
                this.xadM = 1.0f;
                this.yadM = 1.0f;
                break;
            case 1:
                this.axT2 = this.ayT;
                this.ayT2 = this.axT;
                this.xadM = 1.0f;
                this.yadM = 1.0f;
                break;
            case 2:
                this.axT2 = this.axT;
                this.ayT2 = this.ayT;
                this.xadM = -1.0f;
                this.yadM = -1.0f;
                break;
            case 3:
                this.axT2 = this.ayT;
                this.ayT2 = this.axT;
                this.xadM = -1.0f;
                this.yadM = -1.0f;
                break;
        }
        this.load = true;
    }

    public void dispose() {
        this.load = false;
        this.mPrefs.unregisterOnSharedPreferenceChangeListener(this);
        this.mPrefs = null;
        this.galaxyTex = null;
        this.starTex = null;
        this.spriteBatch = null;
        this.effect = null;
    }

    public void pause() {
    }

    public void render() {
        if (this.load) {
            GL11 gl = Gdx.graphics.getGL11();
            if (gl != null) {
                gl.glClear(16384);
                this.spriteBatch.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
                if (Gdx.graphics.getDeltaTime() > 0.001f) {
                    this.delta = Gdx.graphics.getDeltaTime();
                }
                try {
                    this.spriteBatch.begin();
                    this.effect.draw(this.spriteBatch, this.delta);
                    this.spriteBatch.end();
                } catch (Exception e) {
                    try {
                        this.spriteBatch.end();
                    } catch (Exception e2) {
                    }
                }
                if (this.anim) {
                    if (this.accON) {
                        this.ax = Gdx.input.getAccelerometerX();
                        this.ay = Gdx.input.getAccelerometerY();
                        this.axT[this.axi] = this.ax;
                        this.ayT[this.axi] = this.ay;
                        this.axi++;
                        if (this.axi > 9) {
                            this.axi = 0;
                        }
                        this.axd = 0.0f;
                        this.ayd = 0.0f;
                        for (int i = 0; i < 10; i++) {
                            this.axd += this.axT2[i];
                        }
                        if (this.axd < 60.0f && this.axd > -60.0f) {
                            this.wind2 = (this.axd * this.accSpeed) * this.xadM;
                        }
                    }
                    if (testEmiters()) {
                        wind *= 0.99f;
                        this.wind2 *= 0.99f;
                    } else {
                        wind *= 0.99f;
                        this.wind2 *= 0.99f;
                    }
                    try {
                        this.f113w = ((ParticleEmitter) this.ParticleE.get(0)).getWind().getScaling();
                        this.f113w[0] = ((wind + this.wind2) / 2.0f) + 0.5f;
                        this.f113w[1] = 0.5f - (wind + this.wind2);
                        if (this.starsON) {
                            ((ParticleEmitter) this.ParticleE.get(1)).getWind().setScaling(this.f113w);
                        }
                    } catch (Exception e3) {
                    }
                }
            }
        }
    }

    public void resize(int width, int height) {
        if (this.load) {
            GL11 gl = Gdx.graphics.getGL11();
            if (gl != null) {
                gl.glClear(16384);
                this.cX = (float) (Gdx.graphics.getWidth() / 2);
                this.cY = (float) (Gdx.graphics.getHeight() / 2);
                this.emXnew = this.cX;
                this.emX = this.cX;
                this.emY = this.cY;
                this.dxx = 0.0f;
                this.effect.setPosition(this.emX, this.emY);
                this.orient = 0;
                this.axT2 = this.axT;
                this.ayT2 = this.ayT;
                this.xadM = 1.0f;
                this.yadM = 1.0f;
                this.orient = ((AndroidInputLW) Gdx.input).getOrientation();
                switch (this.orient) {
                    case 0:
                        this.axT2 = this.axT;
                        this.ayT2 = this.ayT;
                        this.xadM = 1.0f;
                        this.yadM = 1.0f;
                        return;
                    case 1:
                        this.axT2 = this.ayT;
                        this.ayT2 = this.axT;
                        this.xadM = 1.0f;
                        this.yadM = 1.0f;
                        return;
                    case 2:
                        this.axT2 = this.axT;
                        this.ayT2 = this.ayT;
                        this.xadM = -1.0f;
                        this.yadM = -1.0f;
                        return;
                    case 3:
                        this.axT2 = this.ayT;
                        this.ayT2 = this.axT;
                        this.xadM = -1.0f;
                        this.yadM = -1.0f;
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void resume() {
        switch (this.pref) {
            case 4:
                setFPS(this.FPS);
                break;
            case 5:
                if (!this.starsON) {
                    if (this.effect.getEmitters().size == 2) {
                        this.effect.getEmitters().removeIndex(1);
                        break;
                    }
                } else if (this.effect.getEmitters().size == 1) {
                    this.effect.getEmitters().add(this.ParticleEFull.get(1));
                    this.ParticleE = this.effect.getEmitters();
                    this.effect.setPosition(this.emX, this.emY);
                    break;
                }
                break;
        }
        this.pref = 0;
        this.load = false;
        loadTextures(this.galaxyTexNr, this.starTexNr);
        this.load = true;
    }

    public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
    }

    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        String fps;
        if (key == null) {
            this.starsON = sp.getBoolean("galaxy_stars_box_pref", true);
            try {
                if (this.starsON) {
                    if (this.effect.getEmitters().size == 1) {
                        this.effect.getEmitters().add(this.ParticleEFull.get(1));
                        this.ParticleE = this.effect.getEmitters();
                        this.effect.setPosition(this.emX, this.emY);
                    }
                } else if (this.effect.getEmitters().size == 2) {
                    this.effect.getEmitters().removeIndex(1);
                }
            } catch (Exception e) {
            }
            setAccSpeed(Integer.parseInt(sp.getString("accSpeed_pref", "1")));
            this.anim = sp.getBoolean("anim_pref", true);
            this.accON = sp.getBoolean("acc_pref", true);
            if (this.accON) {
                ((AndroidInputLW) Gdx.input).registerSensorListeners();
                ((AndroidInputLW) Gdx.input).config.useAccelerometer = true;
            } else {
                ((AndroidInputLW) Gdx.input).unregisterSensorListeners();
                ((AndroidInputLW) Gdx.input).config.useAccelerometer = false;
            }
            fps = sp.getString("battery_pref", "");
            if (fps == "") {
                if (this.tab) {
                    fps = "40";
                    this.maxP = 8;
                } else {
                    fps = "1";
                    this.maxP = 6;
                }
            }
            this.FPS = new Long(fps).longValue();
            setFPS(this.FPS);
        } else if (key.equalsIgnoreCase("battery_pref")) {
            this.pref = 4;
            fps = sp.getString(key, "");
            if (fps == "") {
                if (this.tab) {
                    fps = "40";
                    this.maxP = 8;
                } else {
                    fps = "1";
                    this.maxP = 6;
                }
            }
            this.FPS = new Long(fps).longValue();
        } else if (key.equalsIgnoreCase("anim_pref")) {
            this.pref = 3;
            this.anim = sp.getBoolean("anim_pref", true);
        } else if (key.equalsIgnoreCase("galaxy_stars_box_pref")) {
            this.pref = 5;
            this.starsON = sp.getBoolean("galaxy_stars_box_pref", true);
        } else if (key.equalsIgnoreCase("acc_pref")) {
            this.accON = sp.getBoolean("acc_pref", true);
            if (this.accON) {
                ((AndroidInputLW) Gdx.input).registerSensorListeners();
                ((AndroidInputLW) Gdx.input).config.useAccelerometer = true;
                return;
            }
            ((AndroidInputLW) Gdx.input).unregisterSensorListeners();
            ((AndroidInputLW) Gdx.input).config.useAccelerometer = false;
        } else if (key.equalsIgnoreCase("accSpeed_pref")) {
            setAccSpeed(Integer.parseInt(sp.getString("accSpeed_pref", "1")));
        }
    }

    private void setAccSpeed(int nr) {
        switch (nr) {
            case 0:
                this.accSpeed = 0.01f;
                return;
            case 1:
                this.accSpeed = 0.015f;
                return;
            case 2:
                this.accSpeed = 0.02f;
                return;
            default:
                return;
        }
    }

    private void setFPS(long ms) {
        if (this.tab) {
            if (ms == 40) {
                this.maxP = 8;
            } else if (ms == 30) {
                this.maxP = 7;
            } else if (ms == 25) {
                this.maxP = 6;
            } else if (ms == 1) {
                this.maxP = 6;
            }
        } else if (ms == 40) {
            this.maxP = 8;
        } else if (ms == 30) {
            this.maxP = 7;
        } else if (ms == 25) {
            this.maxP = 6;
        } else if (ms == 1) {
            this.maxP = 6;
        }
        if (testEmiters()) {
            ((ParticleEmitter) this.ParticleE.get(0)).setMaxParticleCount(this.maxP);
        }
        ((AndroidGraphicsLW) Gdx.graphics).setMSSleep(ms);
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
        this.oldx = x;
        this.toutch = true;
        this.emX = (float) x;
        this.emY = (float) (Gdx.graphics.getHeight() - y);
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        this.dx = this.oldx - x;
        this.emX = (float) x;
        this.emY = (float) (Gdx.graphics.getHeight() - y);
        return false;
    }

    public boolean touchMoved(int arg0, int arg1) {
        return false;
    }

    public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
        if (this.load && this.anim) {
            wind = 0.0f;
            if (this.tab) {
                if (this.dx > 0) {
                    wind = 0.15f;
                } else {
                    wind = -0.3f;
                }
            } else if (this.dx > 0) {
                wind = 0.2f;
            } else {
                wind = -0.4f;
            }
            if (testEmiters()) {
                try {
                } catch (Exception e) {
                }
            }
            this.f113w = ((ParticleEmitter) this.ParticleE.get(0)).getWind().getScaling();
            this.f113w[0] = ((wind + this.wind2) / 2.0f) + 0.5f;
            this.f113w[1] = 0.5f - (wind + this.wind2);
            if (this.starsON) {
                ((ParticleEmitter) this.ParticleE.get(1)).getWind().setScaling(this.f113w);
            }
        }
        return false;
    }

    private boolean testEmiters() {
        if (this.ParticleE == null || this.ParticleE.size != 2) {
            return false;
        }
        return true;
    }

    public void touchDrop(int x, int y) {
    }

    public void touchTap(int x, int y) {
    }

    public void setType(int typ) {
        this.galaxyType = typ;
        this.load = false;
        this.effect = null;
        this.effect = new ParticleEffectEnc();
        boolean loading;
        switch (typ) {
            case 6:
                this.galaxyTexNr = this.galaxyPackTex[0][0];
                this.starTexNr = this.galaxyPackTex[0][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile1);
                        loading = false;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e2) {
                        }
                        loading = true;
                    }
                }
                break;
            case 7:
                this.galaxyTexNr = this.galaxyPackTex[1][0];
                this.starTexNr = this.galaxyPackTex[1][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile2);
                        loading = false;
                    } catch (Exception e3) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e4) {
                        }
                        loading = true;
                    }
                }
                break;
            case 8:
                this.galaxyTexNr = this.galaxyPackTex[2][0];
                this.starTexNr = this.galaxyPackTex[2][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile1);
                        loading = false;
                    } catch (Exception e5) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e6) {
                        }
                        loading = true;
                    }
                }
                break;
            case 9:
                this.galaxyTexNr = this.galaxyPackTex[3][0];
                this.starTexNr = this.galaxyPackTex[3][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile3);
                        loading = false;
                    } catch (Exception e7) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e8) {
                        }
                        loading = true;
                    }
                }
                break;
            case 10:
                this.galaxyTexNr = this.galaxyPackTex[4][0];
                this.starTexNr = this.galaxyPackTex[4][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile3);
                        loading = false;
                    } catch (Exception e9) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e10) {
                        }
                        loading = true;
                    }
                }
                break;
            case 17:
                this.galaxyTexNr = this.galaxyPackTex[5][0];
                this.starTexNr = this.galaxyPackTex[5][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile3);
                        loading = false;
                    } catch (Exception e11) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e12) {
                        }
                        loading = true;
                    }
                }
                break;
            case 18:
                this.galaxyTexNr = this.galaxyPackTex[6][0];
                this.starTexNr = this.galaxyPackTex[6][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile3);
                        loading = false;
                    } catch (Exception e13) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e14) {
                        }
                        loading = true;
                    }
                }
                break;
            case 22:
                this.galaxyTexNr = this.galaxyPackTex[7][0];
                this.starTexNr = this.galaxyPackTex[7][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile3);
                        loading = false;
                    } catch (Exception e15) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e16) {
                        }
                        loading = true;
                    }
                }
                break;
            case 23:
                this.galaxyTexNr = this.galaxyPackTex[8][0];
                this.starTexNr = this.galaxyPackTex[8][1];
                loading = true;
                while (loading) {
                    try {
                        this.effect.loadEmitters(this.emiFile3);
                        loading = false;
                    } catch (Exception e17) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e18) {
                        }
                        loading = true;
                    }
                }
                break;
        }
        this.ParticleE = null;
        this.ParticleE = this.effect.getEmitters();
        this.cX = (float) (Gdx.graphics.getWidth() / 2);
        this.cY = (float) (Gdx.graphics.getHeight() / 2);
        this.emX = this.cX;
        this.emY = this.cY;
        this.dxx = 0.0f;
        this.effect.setPosition(this.emX, this.emY);
        loadTextures(this.galaxyTexNr, this.starTexNr);
        if (this.ParticleE != null) {
            this.ParticleEFull = new Array<>(this.effect.getEmitters());
            this.ParticleE.get(0).setSprite(new Sprite(this.galaxyTex));
            this.ParticleE.get(1).setSprite(new Sprite(this.starTex));
            if (Gdx.graphics.getHeight() < Gdx.graphics.getWidth()) {
                this.u_scale = ((float) Gdx.graphics.getHeight()) / 600.0f;
            } else {
                this.u_scale = ((float) Gdx.graphics.getHeight()) / 800.0f;
            }
            if (this.u_scale > 1.4f) {
                if (this.u_scale <= 1.8f) {
                    this.u_scale *= 0.85f;
                } else if (this.u_scale <= 2.5f) {
                    this.u_scale *= 0.85f;
                } else if (this.u_scale <= 5.0f) {
                    this.u_scale *= 0.85f;
                }
                ScaledNumericValue gs = this.ParticleE.get(0).getScale();
                float lMin = gs.getLowMin();
                float lMax = gs.getLowMax();
                float hMin = gs.getHighMin();
                float hMax = gs.getHighMax();
                this.ParticleE.get(0).getScale().setLow(this.u_scale * lMin, this.u_scale * lMax);
                this.ParticleE.get(0).getScale().setHigh(this.u_scale * hMin, this.u_scale * hMax);
                this.ParticleEFull.get(0).getScale().setLow(this.u_scale * lMin, this.u_scale * lMax);
                this.ParticleEFull.get(0).getScale().setHigh(this.u_scale * hMin, this.u_scale * hMax);
                this.ParticleE.get(1).setMaxParticleCount(150);
                this.ParticleEFull.get(1).setMaxParticleCount(150);
                if (this.u_scale <= 1.5f) {
                    ((ParticleEmitter) this.ParticleE.get(1)).getLife().setHighMax(15000.0f);
                    ((ParticleEmitter) this.ParticleEFull.get(1)).getLife().setHighMax(15000.0f);
                } else if (this.u_scale <= 1.7f) {
                    ((ParticleEmitter) this.ParticleE.get(1)).getLife().setHighMax(30000.0f);
                    ((ParticleEmitter) this.ParticleEFull.get(1)).getLife().setHighMax(30000.0f);
                } else {
                    ((ParticleEmitter) this.ParticleE.get(1)).getLife().setHighMax(50000.0f);
                    ((ParticleEmitter) this.ParticleEFull.get(1)).getLife().setHighMax(50000.0f);
                }
                ((ParticleEmitter) this.ParticleE.get(1)).getScale().setLow(2.0f, 3.0f);
                ((ParticleEmitter) this.ParticleE.get(1)).getScale().setHigh(2.0f, 4.0f);
                ((ParticleEmitter) this.ParticleEFull.get(1)).getScale().setLow(2.0f, 3.0f);
                ((ParticleEmitter) this.ParticleEFull.get(1)).getScale().setHigh(2.0f, 4.0f);
            } else {
                ((ParticleEmitter) this.ParticleE.get(1)).getScale().setLow(1.0f, 2.0f);
                ((ParticleEmitter) this.ParticleE.get(1)).getScale().setHigh(1.0f, 3.0f);
                ((ParticleEmitter) this.ParticleEFull.get(1)).getScale().setLow(1.0f, 2.0f);
                ((ParticleEmitter) this.ParticleEFull.get(1)).getScale().setHigh(1.0f, 3.0f);
            }
        }
        if (this.starsON) {
            if (this.effect.getEmitters().size == 1) {
                this.effect.getEmitters().add(this.ParticleEFull.get(1));
                this.ParticleE = this.effect.getEmitters();
                this.effect.setPosition(this.emX, this.emY);
            }
        } else if (this.effect.getEmitters().size == 2) {
            this.effect.getEmitters().removeIndex(1);
        }
        this.load = true;
    }

    private void loadTextures(int gtn, int stn) {
        boolean loading = true;
        while (loading) {
            try {
                this.galaxyTex = GalaxyPackWallpaper.getTexture(gtn, 0, false);
                this.galaxyTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                this.starTex = GalaxyPackWallpaper.getTexture(stn, 3, true);
                this.starTex.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
                loading = false;
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                    if (this.galaxyTex != null) {
                        this.galaxyTex.dispose();
                    }
                    if (this.starTex != null) {
                        this.starTex.dispose();
                    }
                    this.galaxyTex = null;
                    this.starTex = null;
                } catch (InterruptedException e2) {
                }
                loading = true;
            }
        }
    }
}
