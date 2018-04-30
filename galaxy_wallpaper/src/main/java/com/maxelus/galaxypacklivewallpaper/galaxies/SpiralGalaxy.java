package com.maxelus.galaxypacklivewallpaper.galaxies;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mybadlogic.gdx.Gdx;
import com.mybadlogic.gdx.graphics.GL10;
import com.mybadlogic.gdx.graphics.GL11;
import com.mybadlogic.gdx.graphics.Mesh;
import com.mybadlogic.gdx.graphics.PerspectiveCamera;
import com.mybadlogic.gdx.graphics.Texture;
import com.mybadlogic.gdx.graphics.Texture.TextureFilter;
import com.mybadlogic.gdx.graphics.Texture.TextureWrap;
import com.mybadlogic.gdx.graphics.g2d.TextureRegion;
import com.mybadlogic.gdx.graphics.g3d.decals.Decal;
import com.mybadlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.mybadlogic.gdx.graphics.g3d.decals.DefaultGroupStrategy;
import com.mybadlogic.gdx.math.MathUtils;
import com.maxelus.galaxypacklivewallpaper.GalaxyPackWallpaper;
import com.maxelus.galaxypacklivewallpaper.GalaxyRenderer;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidGraphicsLW;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidInputLW;

public class SpiralGalaxy extends GalaxyRenderer {
    private static final int maxWH = 1000;
    private static Vector3 rot = new Vector3();
    private long FPS = 25;
    private boolean accON;
    private float accSpeed;
    private int angle = 0;
    private float angle3 = 0.0f;
    private float angleX;
    private float angleY;
    private boolean anim = false;
    private float animSpeed;
    private float ax;
    float[] axT = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] axT2;
    private float axd;
    private int axi = 0;
    private float ay;
    float[] ayT = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] ayT2;
    private float ayd;
    private Decal back = null;
    private Decal back2;
    private DecalBatch batchDec;
    private PerspectiveCamera cam;
    private Vector3 camOrg = new Vector3();
    private Decal core = null;
    private boolean coreOn;
    private float coreSize;
    private Texture coreTex = null;
    private int coreTexNr = 0;
    private int coreTexPref = 0;
    private float cos;
    private float f110d = 0.0f;
    private float delta = 1.0f;
    Vector2 deltaV = new Vector2();
    private float dx;
    private float dx2;
    private float dy;
    private float dy2;
    int[][] galaxyPackTex = new int[][]{new int[]{8, 10, 2, 3, 17}, new int[]{7, 10, 1, 1, 18},
            new int[]{4, 10, 1, 1, 18}, new int[]{5, 10, 1, 1, 18}, new int[]{13, 14, 1, 1, 19},
            new int[]{16, 14, 1, 1, 18}, new int[]{23, 10, 2, 3, 17}, new int[]{24, 11, 1, 3, 18},
            new int[]{25, 10, 1, 3, 18}, new int[]{26, 11, 3, 3, 19}, new int[]{27, 10, 1, 1, 18},
            new int[]{28, 11, 2, 1, 17}, new int[]{31, 11, 2, 3, 17}, new int[]{32, 11, 1, 3, 18},
            new int[]{33, 11, 2, 3, 17}};
    private Texture galaxyTex = null;
    private int galaxyTexNr = 0;
    int galaxyType = 0;
    Vector2 last = new Vector2();
    private Decal light = null;
    private boolean load = false;
    Vector3 lookAt = new Vector3();
    private SharedPreferences mPrefs;
    private float maxAngleX;
    private float maxAngleY;
    private int maxStars = 200;
    private float minAngleX;
    private float minAngleY;
    private Texture novaTex = null;
    private int orient;
    private Decal plan0;
    private float plan0rZ;
    private Decal plan1;
    private float plan1rZ;
    private Decal plan2;
    private float plan2rZ;
    private Decal plan3;
    private float plan3rZ;
    private Decal plan4;
    private float plan4rZ;
    Vector3 point = new Vector3();
    private int pref = 0;
    private float f111r;
    private boolean readPref = false;
    Matrix4 rotMatrix = new Matrix4();
    private int sMax = 100;
    private float sin;
    private Mesh skyBox;
    private int skyBoxNr = 0;
    private int skyBoxPref = 0;
    private Texture skyBoxTex = null;
    private float speedPlane;
    Vector3 spoint = new Vector3();
    Matrix4 srotMatrix = new Matrix4();
    private Texture starTex = null;
    private int starTexNr = 0;
    private int starTexPref = 0;
    private Decal[] stars = null;
    private boolean starsON = true;
    Vector2 tCurr = new Vector2();
    private boolean tab = false;
    private float time;
    private float touchStartX;
    private float touchStartY;
    private boolean toutch;
    private float u_scale;
    final Vector3 xAxis = new Vector3(1.0f, 0.0f, 0.0f);
    private float xadM = 1.0f;
    private boolean xrayOn;
    private Texture xrayTex;
    private int xrayTexNr = 0;
    private int xrayTexPref = 0;
    final Vector3 yAxis = new Vector3(0.0f, 1.0f, 0.0f);
    private float yadM = 1.0f;
    final Vector3 zAxis = new Vector3(0.0f, 0.0f, 1.0f);

    public SpiralGalaxy(SharedPreferences mP, int galaxyType, boolean readPref) {
        this.galaxyType = galaxyType;
        this.readPref = readPref;
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
        this.cam = new PerspectiveCamera(67.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        if (Gdx.graphics.getWidth() < Gdx.graphics.getHeight()) {
            if (Gdx.graphics.getHeight() <= 1100) {
                this.cam.position.set(0.0f, 0.0f, -5.0f);
            } else if (Gdx.graphics.getHeight() <= 1300) {
                this.cam.position.set(0.0f, 0.0f, -6.0f);
            } else if (Gdx.graphics.getHeight() > 1300) {
                this.cam.position.set(0.0f, 0.0f, -7.0f);
            }
            this.maxAngleY = 40.0f;
            this.minAngleY = -40.0f;
            this.maxAngleX = 30.0f;
            this.minAngleX = -30.0f;
        } else {
            if (Gdx.graphics.getWidth() <= 1100) {
                this.cam.position.set(0.0f, 0.0f, -5.0f);
            } else if (Gdx.graphics.getWidth() <= 1300) {
                this.cam.position.set(0.0f, 0.0f, -6.0f);
            } else if (Gdx.graphics.getWidth() > 1300) {
                this.cam.position.set(0.0f, 0.0f, -7.0f);
            }
            this.maxAngleY = 40.0f;
            this.minAngleY = -40.0f;
            this.maxAngleX = 30.0f;
            this.minAngleX = -30.0f;
        }
        this.camOrg.set(this.cam.position);
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
        float aspect = ((float) Gdx.graphics.getWidth()) / ((float) Gdx.graphics.getHeight());
        if (aspect < 1.0f) {
            this.u_scale = (((float) Gdx.graphics.getHeight()) / 600.0f) / aspect;
        } else {
            this.u_scale = (((float) Gdx.graphics.getHeight()) / 800.0f) * aspect;
        }
        if (Gdx.graphics.getWidth() < Gdx.graphics.getHeight()) {
            this.cam.fieldOfView = 67.0f;
            this.u_scale *= 0.4f;
        } else {
            this.cam.fieldOfView = 48.0f;
            this.u_scale *= 0.5f;
        }
        if (this.u_scale < 1.0f) {
            this.u_scale = 1.0f;
        }
        this.point.set(this.cam.position);
        this.rotMatrix.setToRotation(this.xAxis, -20.0f);
        this.point.mul(this.rotMatrix);
        this.rotMatrix.setToRotation(this.yAxis, -20.0f);
        this.point.mul(this.rotMatrix);
        this.cam.position.set(this.point);
        this.cam.lookAt(0.0f, 0.0f, 0.0f);
        Gdx.input.setInputProcessor(this);
        this.batchDec = new DecalBatch(200, new DefaultGroupStrategy());
        onSharedPreferenceChanged(this.mPrefs, null);
        loadTextures(this.galaxyTexNr, this.skyBoxNr, this.coreTexNr, this.starTexNr, this.xrayTexNr);
        this.stars = new Decal[this.maxStars];
        for (int i = 0; i < this.maxStars; i++) {
            this.stars[i] = Decal.newDecal(new TextureRegion(this.starTex));
            this.stars[i].setPosition((0.5f - ((float) Math.random())) * 10.0f, (0.5f - ((float) Math.random())) * 10.0f, (0.5f - ((float) Math.random())) * 5.0f);
            this.stars[i].setScale(((((float) Math.random()) * 0.85f) + 0.15f) * 0.0018f);
            this.stars[i].setBlending(770, 1);
        }
        this.back = Decal.newDecal(new TextureRegion(this.skyBoxTex));
        this.back.setPosition(0.0f, 0.0f, 4.0f);
        this.back.setScale(0.018000001f);
        this.core = Decal.newDecal(new TextureRegion(this.coreTex));
        this.core.setPosition(0.0f, 0.0f, 0.19f);
        this.core.setScale(0.018000001f * this.coreSize);
        this.light = Decal.newDecal(new TextureRegion(this.xrayTex));
        this.light.setPosition(0.0f, 0.0f, 0.25f);
        this.core.setBlending(770, 1);
        this.light.setBlending(770, 1);
        this.light.rotateX(90.0f);
        this.plan0 = Decal.newDecal(new TextureRegion(this.galaxyTex));
        this.plan1 = Decal.newDecal(new TextureRegion(this.galaxyTex));
        this.plan2 = Decal.newDecal(new TextureRegion(this.galaxyTex));
        this.plan3 = Decal.newDecal(new TextureRegion(this.galaxyTex));
        this.plan4 = Decal.newDecal(new TextureRegion(this.galaxyTex));
        this.plan0.rotateX(15.0f);
        this.plan1.rotateY(-15.0f);
        this.plan2.rotateX(15.0f);
        this.plan3.rotateY(-15.0f);
        this.plan4.rotateX(15.0f);
        this.plan0.rotateZ(0.0f);
        this.plan1.rotateZ(-15.0f);
        this.plan2.rotateZ(25.0f);
        this.plan3.rotateZ(-35.0f);
        this.plan4.rotateZ(45.0f);
        this.plan0.setPosition(0.0f, 0.0f, 3.0f);
        this.plan1.setPosition(0.0f, 0.0f, 2.0f);
        this.plan2.setPosition(0.0f, 0.0f, 1.0f);
        this.plan3.setPosition(0.0f, 0.0f, 0.5f);
        this.plan4.setPosition(0.0f, 0.0f, 0.2f);
        this.plan0.setScale(0.018000001f);
        this.plan1.setScale(0.012f);
        this.plan2.setScale(0.006f);
        this.plan3.setScale(0.0024f);
        this.plan4.setScale(0.0018000001f);
        this.plan0.setColor(1.0f, 1.0f, 1.0f, 0.6f);
        this.plan1.setColor(1.0f, 1.0f, 1.0f, 0.7f);
        this.plan2.setColor(1.0f, 1.0f, 1.0f, 0.8f);
        this.plan3.setColor(1.0f, 1.0f, 1.0f, 0.9f);
        this.plan4.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.plan0.setBlending(770, 1);
        this.plan1.setBlending(770, 1);
        this.plan2.setBlending(770, 1);
        this.plan3.setBlending(770, 1);
        this.plan4.setBlending(770, 1);
        this.plan0.transformationOffset = new Vector2(0.0f, 0.0f);
        this.plan1.transformationOffset = new Vector2(0.0f, 0.0f);
        this.plan2.transformationOffset = new Vector2(0.0f, 0.0f);
        this.plan3.transformationOffset = new Vector2(0.0f, 0.0f);
        this.plan4.transformationOffset = new Vector2(0.0f, 0.0f);
        this.load = true;
    }

    public void dispose() {
        this.load = false;
        try {
            this.mPrefs.unregisterOnSharedPreferenceChangeListener(this);
            this.mPrefs = null;
            this.galaxyTex = null;
            this.skyBoxTex = null;
            this.coreTex = null;
            this.starTex = null;
            this.xrayTex = null;
            this.batchDec = null;
        } catch (Exception e) {
        }
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        if (this.load) {
            float aspect = ((float) Gdx.graphics.getWidth()) / ((float) Gdx.graphics.getHeight());
            if (aspect < 1.0f) {
                this.u_scale = (((float) Gdx.graphics.getHeight()) / 600.0f) / aspect;
            } else {
                this.u_scale = (((float) Gdx.graphics.getHeight()) / 800.0f) * aspect;
            }
            this.cam.viewportHeight = (float) height;
            this.cam.viewportWidth = (float) width;
            if (Gdx.graphics.getWidth() < Gdx.graphics.getHeight()) {
                this.cam.fieldOfView = 67.0f;
                this.u_scale *= 0.4f;
                this.maxAngleY = 40.0f;
                this.minAngleY = -40.0f;
                this.maxAngleX = 30.0f;
                this.minAngleX = -30.0f;
            } else {
                this.cam.fieldOfView = 48.0f;
                this.u_scale *= 0.9f;
                this.maxAngleY = 40.0f;
                this.minAngleY = -40.0f;
                this.maxAngleX = 30.0f;
                this.minAngleX = -30.0f;
            }
            if (this.u_scale < 1.0f) {
                this.u_scale = 1.0f;
            }
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

    public void resume() {
        switch (this.pref) {
            case 4:
                setFPS(this.FPS);
                break;
        }
        this.load = false;
        loadTextures(this.galaxyTexNr, this.skyBoxNr, this.coreTexNr, this.starTexNr, this.xrayTexNr);
        this.load = true;
        this.pref = 0;
    }

    public void render() {
        if (this.load) {
            int i;
            this.delta = Math.max(Gdx.graphics.getDeltaTime(), 0.01f);
            GL11 gl = Gdx.graphics.getGL11();
            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            gl.glClear(16384);
            gl.glEnable(GL10.GL_COLOR_MATERIAL);
            gl.glEnable(3553);
            gl.glDisable(2929);
            gl.glDepthMask(false);
            this.angle++;
            if (this.angle >= 360) {
                this.angle = 0;
            }
            this.sin = MathUtils.sinDeg((float) this.angle);
            this.cos = MathUtils.cosDeg((float) this.angle);
            this.time += this.delta * 0.5f;
            if (this.anim) {
                this.point.set(this.cam.position);
            } else {
                this.dx = 0.0f;
                this.dy = 0.0f;
            }
            if (this.dx != 0.0f) {
                this.angleY += this.dx * this.delta;
                this.dx *= (1.0f - this.delta) * 0.99f;
                if (this.angleY > this.maxAngleY) {
                    this.angleY = this.maxAngleY;
                }
                if (this.angleY < this.minAngleY) {
                    this.angleY = this.minAngleY;
                }
                if (Math.abs(this.dx) < 0.01f) {
                    this.dx = 0.0f;
                }
            }
            if (this.dy != 0.0f) {
                this.angleX += this.dy * this.delta;
                this.dy *= (1.0f - this.delta) * 0.99f;
                if (this.angleX > this.maxAngleX) {
                    this.angleX = this.maxAngleX;
                }
                if (this.angleX < this.minAngleX) {
                    this.angleX = this.minAngleX;
                }
                if (Math.abs(this.dy) < 0.01f) {
                    this.dy = 0.0f;
                }
            }
            this.point.set(this.camOrg);
            if (this.accON) {
                this.ax = Gdx.input.getAccelerometerX();
                this.ay = Gdx.input.getAccelerometerY();
                this.axT[this.axi] = this.ax;
                this.ayT[this.axi] = this.ay;
                this.axi++;
                if (this.axi > 19) {
                    this.axi = 0;
                }
                this.axd = 0.0f;
                this.ayd = 0.0f;
                for (i = 0; i < 20; i++) {
                    this.axd += this.axT2[i];
                    this.ayd += this.ayT2[i];
                }
            } else {
                this.axd = 0.0f;
                this.ayd = 0.0f;
            }
            this.rotMatrix.setToRotation(this.yAxis, this.angleY + (this.axd * this.accSpeed));
            this.point.mul(this.rotMatrix);
            this.rotMatrix.setToRotation(this.xAxis, this.angleX + (this.ayd * this.accSpeed));
            this.point.mul(this.rotMatrix);
            this.cam.position.set(this.point);
            this.cam.lookAt(this.cos * 0.03f, this.sin * 0.03f, (-this.sin) * 0.03f);
            this.cam.update();
            this.cam.apply(gl);
            if (this.coreOn) {
                this.core.lookAt(this.cam.position, this.cam.position);
                this.core.setScale((0.018f * (1.0f - (this.sin / 3.0f))) * this.coreSize);
                this.core.rotateZ((-this.sin) * 10.0f);
            }
            this.back.lookAt(this.cam.position, this.cam.up);
            this.back.rotateZ(-this.angle3);
            if (this.galaxyType == 5) {
                this.plan0.transformationOffset.x = (this.sin * 0.01f) * this.speedPlane;
                this.plan0.transformationOffset.y = ((-this.sin) * 0.01f) * this.speedPlane;
                this.plan1.transformationOffset.x = ((-this.sin) * 0.03f) * this.speedPlane;
                this.plan1.transformationOffset.y = ((-this.sin) * 0.04f) * this.speedPlane;
                this.plan2.transformationOffset.x = (this.sin * 0.04f) * this.speedPlane;
                this.plan2.transformationOffset.y = -0.5f + (((-this.sin) * 0.03f) * this.speedPlane);
                this.plan3.transformationOffset.x = ((-this.sin) * 0.05f) * this.speedPlane;
                this.plan3.transformationOffset.y = 0.5f + ((this.sin * 0.03f) * this.speedPlane);
                this.plan4.transformationOffset.x = 0.5f + ((this.sin * 0.03f) * this.speedPlane);
                this.plan4.transformationOffset.y = ((-this.sin) * 0.07f) * this.speedPlane;
            } else {
                this.plan0.transformationOffset.x = (this.sin * 0.01f) * this.speedPlane;
                this.plan0.transformationOffset.y = (this.sin * 0.02f) * this.speedPlane;
                this.plan1.transformationOffset.x = ((-this.sin) * 0.02f) * this.speedPlane;
                this.plan1.transformationOffset.y = ((-this.sin) * 0.03f) * this.speedPlane;
                this.plan2.transformationOffset.x = (this.sin * 0.03f) * this.speedPlane;
                this.plan2.transformationOffset.y = ((-this.sin) * 0.04f) * this.speedPlane;
                this.plan3.transformationOffset.x = ((-this.sin) * 0.04f) * this.speedPlane;
                this.plan3.transformationOffset.y = (this.sin * 0.05f) * this.speedPlane;
                this.plan4.transformationOffset.x = (this.sin * 0.05f) * this.speedPlane;
                this.plan4.transformationOffset.y = (this.sin * 0.06f) * this.speedPlane;
            }
            this.plan0.rotateZ(this.plan0rZ * this.delta);
            this.plan1.rotateZ(this.plan1rZ * this.delta);
            this.plan2.rotateZ(this.plan2rZ * this.delta);
            this.plan3.rotateZ(this.plan3rZ * this.delta);
            this.plan4.rotateZ(this.plan4rZ * this.delta);
            this.plan0.rotateY((this.sin * 1.0f) * this.delta);
            this.plan1.rotateX(((-this.cos) * 1.0f) * this.delta);
            this.plan2.rotateY((this.sin * 1.0f) * this.delta);
            this.plan3.rotateX(((-this.cos) * 1.0f) * this.delta);
            this.plan4.rotateY((this.sin * 1.0f) * this.delta);
            this.batchDec.add(this.back);
            this.batchDec.add(this.plan0);
            this.batchDec.add(this.plan1);
            this.batchDec.add(this.plan2);
            this.batchDec.add(this.plan3);
            this.batchDec.add(this.plan4);
            if (this.coreOn) {
                this.batchDec.add(this.core);
            }
            if (this.xrayOn) {
                TextureRegion tr = this.light.getTextureRegion();
                tr.scroll(this.sin * 0.02f, 0.0f);
                this.light.setTextureRegion(tr);
                this.light.setScale(0.0015f, (0.08f * this.sin) + 0.02f);
                this.batchDec.add(this.light);
            }
            if (this.starsON) {
                for (i = 0; i < this.sMax; i++) {
                    this.spoint.set(this.stars[i].getPosition());
                    this.srotMatrix.setToRotation(this.zAxis, ((-this.delta) * 2.0f) * this.speedPlane);
                    this.spoint.mul(this.srotMatrix);
                    this.stars[i].setPosition(this.spoint.x, this.spoint.y, this.spoint.z);
                    if (this.cam.frustum.sphereInFrustum(this.stars[i].getPosition(), 0.01f)) {
                        this.stars[i].lookAt(this.cam.position, this.cam.position);
                        if (i % 5 == 0) {
                            this.stars[i].setScale((0.001f * ((float) Math.cos((double) ((this.time * 6.0f) + this.stars[i].getZ())))) + 0.001f);
                        }
                        this.batchDec.add(this.stars[i]);
                    }
                }
            }
            this.angle3 -= 0.05f;
            if (this.angle3 <= -360.0f) {
                this.angle3 = 0.0f;
            }
            this.batchDec.flush();
        }
    }

    public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
    }

    public void setIsPreview(boolean isPreview) {
    }

    public void touchDrop(int x, int y) {
    }

    public void touchTap(int x, int y) {
    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        if (this.load) {
            this.touchStartX = (float) x;
            this.touchStartY = (float) y;
            this.toutch = true;
            this.last.set((float) x, (float) y);
        }
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        if (this.load && pointer == 0) {
            float dyy = ((((float) y) - this.touchStartY) / (this.u_scale * 25.0f)) * this.animSpeed;
            this.dx += ((((float) x) - this.touchStartX) / (this.u_scale * 25.0f)) * this.animSpeed;
            this.dy += dyy;
            this.touchStartX = (float) x;
            this.touchStartY = (float) y;
        }
        return false;
    }

    public boolean touchMoved(int x, int y) {
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    private void setFPS(long ms) {
        ((AndroidGraphicsLW) Gdx.graphics).setMSSleep(ms);
    }

    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        String fps;
        if (key != null) {
            if (key.equalsIgnoreCase("battery_pref")) {
                this.pref = 4;
                fps = sp.getString(key, "");
                if (fps == "") {
                    if (this.tab) {
                        fps = "25";
                    } else {
                        fps = "1";
                    }
                }
                this.FPS = Long.valueOf(fps);
                if (this.tab) {
                    this.FPS += 10;
                }
            } else if (key.equalsIgnoreCase("anim_pref")) {
                this.pref = 3;
                this.anim = sp.getBoolean(key, true);
            } else if (key.equalsIgnoreCase("galaxy_back_pref")) {
                this.skyBoxPref = Integer.parseInt(sp.getString(key, "-1"));
                if (this.skyBoxPref >= 0) {
                    this.skyBoxNr = getSkyBox(this.skyBoxPref);
                } else {
                    this.skyBoxNr = this.galaxyPackTex[this.galaxyType][1];
                }
            } else if (key.equalsIgnoreCase("galaxy_core_pref")) {
                this.coreTexPref = Integer.parseInt(sp.getString(key, "-1"));
                if (this.coreTexPref >= 0) {
                    this.coreTexNr = getCoreTex(this.coreTexPref);
                } else {
                    this.coreTexNr = this.galaxyPackTex[this.galaxyType][2];
                }
            } else if (key.equalsIgnoreCase("galaxy_stars_pref")) {
                this.starTexPref = Integer.parseInt(sp.getString(key, "-1"));
                if (this.starTexPref >= 0) {
                    this.starTexNr = getStarTex(this.starTexPref);
                } else {
                    this.starTexNr = this.galaxyPackTex[this.galaxyType][3];
                }
            } else if (key.equalsIgnoreCase("galaxy_stars_count_pref")) {
                this.sMax = Integer.parseInt(sp.getString(key, "200"));
                for (int i = 0; i < this.maxStars; i++) {
                    if (this.stars != null) {
                        this.stars[i].setPosition((0.5f - ((float) Math.random())) * 10.0f, (0.5f - ((float) Math.random())) * 10.0f, (0.5f - ((float) Math.random())) * 5.0f);
                    }
                }
            } else if (key.equalsIgnoreCase("galaxy_speed_pref")) {
                this.speedPlane = Float.parseFloat(sp.getString(key, "1.0f"));
                this.plan0rZ = -2.0f * this.speedPlane;
                this.plan1rZ = -4.0f * this.speedPlane;
                this.plan2rZ = -8.0f * this.speedPlane;
                this.plan3rZ = -12.0f * this.speedPlane;
                this.plan4rZ = -16.0f * this.speedPlane;
            } else if (key.equalsIgnoreCase("galaxy_core_box_pref")) {
                this.coreOn = sp.getBoolean(key, true);
                if (this.coreOn) {
                    this.xrayOn = sp.getBoolean("galaxy_xray_box_pref", true);
                } else {
                    this.xrayOn = false;
                }
            } else if (key.equalsIgnoreCase("galaxy_stars_box_pref")) {
                this.starsON = sp.getBoolean("galaxy_stars_box_pref", true);
            } else if (key.equalsIgnoreCase("galaxy_core_size_pref")) {
                this.coreSize = Float.parseFloat(sp.getString(key, "1.0f"));
            } else if (key.equalsIgnoreCase("galaxy_xray_box_pref")) {
                this.xrayOn = sp.getBoolean(key, true);
            } else if (key.equalsIgnoreCase("galaxy_xray_pref")) {
                this.xrayTexPref = Integer.parseInt(sp.getString(key, "-1"));
                if (this.xrayTexPref >= 0) {
                    this.xrayTexNr = getXrayTex(this.xrayTexPref);
                } else {
                    this.xrayTexNr = this.galaxyPackTex[this.galaxyType][4];
                }
            }
            if (!key.equalsIgnoreCase("galaxy_pref")) {
                if (key.equalsIgnoreCase("acc_pref")) {
                    this.accON = sp.getBoolean("acc_pref", true);
                    if (this.accON) {
                        ((AndroidInputLW) Gdx.input).registerSensorListeners();
                        ((AndroidInputLW) Gdx.input).config.useAccelerometer = true;
                        return;
                    }
                    ((AndroidInputLW) Gdx.input).unregisterSensorListeners();
                    ((AndroidInputLW) Gdx.input).config.useAccelerometer = false;
                    return;
                } else if (key.equalsIgnoreCase("accSpeed_pref")) {
                    setAccSpeed(Integer.parseInt(sp.getString("accSpeed_pref", "2")));
                    return;
                } else if (key.equalsIgnoreCase("anim_speed_pref")) {
                    this.animSpeed = Float.parseFloat(sp.getString("anim_speed_pref", "1.0f"));
                    return;
                } else {
                    return;
                }
            }
            return;
        }
        this.anim = sp.getBoolean("anim_pref", true);
        this.animSpeed = Float.parseFloat(sp.getString("anim_speed_pref", "1.0f"));
        this.accON = sp.getBoolean("acc_pref", true);
        if (this.accON) {
            ((AndroidInputLW) Gdx.input).registerSensorListeners();
            ((AndroidInputLW) Gdx.input).config.useAccelerometer = true;
        } else {
            ((AndroidInputLW) Gdx.input).unregisterSensorListeners();
            ((AndroidInputLW) Gdx.input).config.useAccelerometer = false;
        }
        this.galaxyTexNr = this.galaxyPackTex[this.galaxyType][0];
        this.skyBoxNr = this.galaxyPackTex[this.galaxyType][1];
        this.coreTexNr = this.galaxyPackTex[this.galaxyType][2];
        this.starTexNr = this.galaxyPackTex[this.galaxyType][3];
        this.xrayTexNr = this.galaxyPackTex[this.galaxyType][4];
        setAccSpeed(Integer.parseInt(sp.getString("accSpeed_pref", "2")));
        if (this.readPref) {
            this.skyBoxPref = Integer.parseInt(sp.getString("galaxy_back_pref", "-1"));
            this.coreTexPref = Integer.parseInt(sp.getString("galaxy_core_pref", "-1"));
            this.starTexPref = Integer.parseInt(sp.getString("galaxy_stars_pref", "-1"));
            this.xrayTexPref = Integer.parseInt(sp.getString("galaxy_xray_pref", "-1"));
            if (this.skyBoxPref >= 0) {
                this.skyBoxNr = getSkyBox(this.skyBoxPref);
            } else {
                this.skyBoxNr = this.galaxyPackTex[this.galaxyType][1];
            }
            if (this.coreTexPref >= 0) {
                this.coreTexNr = getCoreTex(this.coreTexPref);
            } else {
                this.coreTexNr = this.galaxyPackTex[this.galaxyType][2];
            }
            if (this.starTexPref >= 0) {
                this.starTexNr = getStarTex(this.starTexPref);
            } else {
                this.starTexNr = this.galaxyPackTex[this.galaxyType][3];
            }
            if (this.xrayTexPref >= 0) {
                this.xrayTexNr = getXrayTex(this.xrayTexPref);
            } else {
                this.xrayTexNr = this.galaxyPackTex[this.galaxyType][4];
            }
        } else {
            Editor e = this.mPrefs.edit();
            e.putString("galaxy_back_pref", String.valueOf(getSkyBoxPrefNr(this.galaxyPackTex[this.galaxyType][1])));
            e.putString("galaxy_core_pref", String.valueOf(getCorePrefNr(this.galaxyPackTex[this.galaxyType][2])));
            e.putString("galaxy_stars_pref", String.valueOf(getStarPrefNr(this.galaxyPackTex[this.galaxyType][3])));
            e.putString("galaxy_xray_pref", String.valueOf(getXrayPrefNr(this.galaxyPackTex[this.galaxyType][4])));
            e.commit();
        }
        this.sMax = Integer.parseInt(sp.getString("galaxy_stars_count_pref", "200"));
        this.starsON = sp.getBoolean("galaxy_stars_box_pref", true);
        this.speedPlane = Float.parseFloat(sp.getString("galaxy_speed_pref", "1.0f"));
        this.plan0rZ = -2.0f * this.speedPlane;
        this.plan1rZ = -4.0f * this.speedPlane;
        this.plan2rZ = -8.0f * this.speedPlane;
        this.plan3rZ = -12.0f * this.speedPlane;
        this.plan4rZ = -16.0f * this.speedPlane;
        this.coreOn = sp.getBoolean("galaxy_core_box_pref", true);
        this.coreSize = Float.parseFloat(sp.getString("galaxy_core_size_pref", "1.0f"));
        if (this.coreOn) {
            this.xrayOn = sp.getBoolean("galaxy_xray_box_pref", true);
        } else {
            this.xrayOn = false;
        }
        fps = sp.getString("battery_pref", "");
        if (fps == "") {
            if (this.tab) {
                fps = "25";
            } else {
                fps = "1";
            }
        }
        this.FPS = Long.valueOf(fps);
        if (this.tab) {
            this.FPS += 10;
        }
        setFPS(this.FPS);
    }

    private void setAccSpeed(int nr) {
        switch (nr) {
            case 0:
                this.accSpeed = 0.03f;
                return;
            case 1:
                this.accSpeed = 0.05f;
                return;
            case 2:
                this.accSpeed = 0.08f;
                return;
            default:
                return;
        }
    }

    private int getStarTex(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 3;
            default:
                return 2;
        }
    }

    private int getStarPrefNr(int i) {
        switch (i) {
            case 2:
                return 0;
            case 3:
                return 2;
            default:
                return 1;
        }
    }

    private int getCoreTex(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 3;
            default:
                return 2;
        }
    }

    private int getCorePrefNr(int i) {
        switch (i) {
            case 2:
                return 0;
            case 3:
                return 2;
            default:
                return 1;
        }
    }

    private int getSkyBox(int i) {
        switch (i) {
            case 1:
                return 10;
            case 2:
                return 12;
            case 3:
                return 14;
            case 4:
                return 15;
            default:
                return 11;
        }
    }

    private int getSkyBoxPrefNr(int i) {
        switch (i) {
            case 11:
                return 0;
            case 12:
                return 2;
            case 14:
                return 3;
            case 15:
                return 4;
            default:
                return 1;
        }
    }

    private int getXrayTex(int i) {
        switch (i) {
            case 1:
                return 18;
            case 2:
                return 19;
            default:
                return 17;
        }
    }

    private int getXrayPrefNr(int i) {
        switch (i) {
            case 18:
                return 1;
            case 19:
                return 2;
            default:
                return 0;
        }
    }

    public void setType(int typ) {
        this.galaxyType = typ;
        this.galaxyTexNr = this.galaxyPackTex[typ][0];
        this.skyBoxNr = this.galaxyPackTex[typ][1];
        this.coreTexNr = this.galaxyPackTex[typ][2];
        this.starTexNr = this.galaxyPackTex[typ][3];
        this.xrayTexNr = this.galaxyPackTex[typ][4];
        Editor e = this.mPrefs.edit();
        e.putString("galaxy_back_pref", String.valueOf(getSkyBoxPrefNr(this.galaxyPackTex[typ][1])));
        e.putString("galaxy_core_pref", String.valueOf(getCorePrefNr(this.galaxyPackTex[typ][2])));
        e.putString("galaxy_stars_pref", String.valueOf(getStarPrefNr(this.galaxyPackTex[typ][3])));
        e.putString("galaxy_xray_pref", String.valueOf(getXrayPrefNr(this.galaxyPackTex[typ][4])));
        e.commit();
        for (int i = 0; i < 100; i++) {
            if (this.stars != null) {
                this.stars[i].setPosition((0.5f - ((float) Math.random())) * 8.0f, (0.5f - ((float) Math.random())) * 8.0f, (0.5f - ((float) Math.random())) * 4.0f);
            }
        }
        this.load = false;
        loadTextures(this.galaxyTexNr, this.skyBoxNr, this.coreTexNr, this.starTexNr, this.xrayTexNr);
        this.load = true;
    }

    private void loadTextures(int gtn, int btn, int ctn, int stn, int xtn) {
        boolean loading = true;
        while (loading) {
            try {
                this.galaxyTex = GalaxyPackWallpaper.getTexture(gtn, 0, false);
                this.galaxyTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                this.skyBoxTex = GalaxyPackWallpaper.getTexture(btn, 1, false);
                this.skyBoxTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                this.coreTex = GalaxyPackWallpaper.getTexture(ctn, 2, true);
                this.coreTex.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
                this.starTex = GalaxyPackWallpaper.getTexture(stn, 3, true);
                this.starTex.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
                this.xrayTex = GalaxyPackWallpaper.getTexture(xtn, 4, true);
                this.xrayTex.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
                this.xrayTex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
                loading = false;
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                    if (this.galaxyTex != null) {
                        this.galaxyTex.dispose();
                    }
                    if (this.skyBoxTex != null) {
                        this.skyBoxTex.dispose();
                    }
                    if (this.coreTex != null) {
                        this.coreTex.dispose();
                    }
                    if (this.starTex != null) {
                        this.starTex.dispose();
                    }
                    if (this.xrayTex != null) {
                        this.xrayTex.dispose();
                    }
                    this.galaxyTex = null;
                    this.skyBoxTex = null;
                    this.coreTex = null;
                    this.starTex = null;
                    this.xrayTex = null;
                } catch (InterruptedException e2) {
                }
                loading = true;
            }
        }
    }
}
