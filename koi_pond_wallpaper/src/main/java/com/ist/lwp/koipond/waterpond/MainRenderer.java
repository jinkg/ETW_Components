package com.ist.lwp.koipond.waterpond;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ist.lwp.koipond.baits.BaitsRenderer;
import com.ist.lwp.koipond.koi3d.Koi3DRenderer;
import com.ist.lwp.koipond.models.BaitsManager;
import com.ist.lwp.koipond.models.KoiModel;
import com.ist.lwp.koipond.models.PondModel.Opcode;
import com.ist.lwp.koipond.models.PondModel.PondEditListener;
import com.ist.lwp.koipond.models.PondModel.PropsType;
import com.ist.lwp.koipond.models.PondsManager;
import com.ist.lwp.koipond.natives.NativeMainRenderer;
import com.ist.lwp.koipond.plants.PlantsRenderer;
import com.ist.lwp.koipond.resource.TextureMananger;
import com.ist.lwp.koipond.resource.TextureMananger.OnThemeTextureUpdated;
import com.ist.lwp.koipond.school.SchoolRenderer;
import com.ist.lwp.koipond.waterpond.PreferencesManager.OnPreferenceChangedListener;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;

public final class MainRenderer implements InputProcessor, Screen, OnThemeTextureUpdated, PondEditListener, OnPreferenceChangedListener {
    public final BaitsRenderer baitsRenderer;
    public final BottomRenderer bottomRenderer;
    private boolean doubleTapped;
    public final FadeRenderer fadeRenderer;
    private float gryoRotation = Float.MAX_VALUE;
    private Matrix4 gyroMatrix = new Matrix4();
    private final Vector3 gyroVector = new Vector3();
    private boolean isPreview;
    public final Koi3DRenderer koi3dRenderer;
    private Vector2 lastTapPoint;
    private long lastTapTime;
    private NativeMainRenderer nativeMainRenderer = new NativeMainRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public final PlantsRenderer plantsRenderer;
    public final PowerSaver powerSaver;
    public final RainDropsRenderer rainDropsRenderer;
    public final RefractionsRenderer refractionsRenderer;
    public final Vector3 rightTop;
    public final RipplesRenderer ripplesRenderer;
    public final SceneFBORenderer sceneFBORenderer;
    public final SchoolRenderer schoolRenderer;
    public final SurfaceRenderer surfaceRenderer;

    public MainRenderer() {
        float visibleWidth;
        float visibleHeight;
        Gdx.input.setInputProcessor(this);
        PondsManager.getInstance().getCurrentPond().addPondEditListener(this);
        TextureMananger.getInstance().addThemeChangedListener(this);
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        preferencesManager.addPreferenceChangedListener(this);
        this.nativeMainRenderer.setGyroEnabled(preferencesManager.gyroEnabled);
        this.lastTapTime = 0;
        this.doubleTapped = false;
        this.lastTapPoint = new Vector2();
        Vector2 windowSize = new Vector2((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        float aspectRatio = windowSize.x / windowSize.y;
        this.rightTop = new Vector3(1.0f, 1.0f, 0.0f);
        Vector3 indentedDimension = new Vector3(this.rightTop.x - (2.0f * 0.05f), this.rightTop.y - (2.0f * 0.05f), 0.0f);
        Vector3 visibleDimension = new Vector3();
        if (aspectRatio > 1.0f) {
            visibleWidth = indentedDimension.x;
            visibleHeight = visibleWidth / aspectRatio;
        } else {
            visibleHeight = indentedDimension.y;
            visibleWidth = visibleHeight * aspectRatio;
        }
        visibleDimension.set(visibleWidth, visibleHeight, 0.0f);
        Vector3 sceneDimension = new Vector3(visibleDimension.x + (2.0f * 0.05f), visibleDimension.y + (2.0f * 0.05f), 0.0f);
        Vector3 scenePixelSize = new Vector3((float) Math.round((windowSize.x * sceneDimension.x) / visibleDimension.x), (float) Math.round((windowSize.y * sceneDimension.y) / visibleDimension.y), 0.0f);
        this.powerSaver = new PowerSaver();
        this.rainDropsRenderer = new RainDropsRenderer(this);
        this.plantsRenderer = new PlantsRenderer();
        this.baitsRenderer = new BaitsRenderer();
        this.fadeRenderer = new FadeRenderer();
        this.koi3dRenderer = new Koi3DRenderer(this);
        this.ripplesRenderer = new RipplesRenderer(this);
        this.bottomRenderer = new BottomRenderer();
        this.refractionsRenderer = new RefractionsRenderer();
        this.schoolRenderer = new SchoolRenderer();
        this.sceneFBORenderer = new SceneFBORenderer(scenePixelSize.x, scenePixelSize.y);
        this.surfaceRenderer = new SurfaceRenderer(this);
    }

    private void updateGyroInfo() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        if (preferencesManager.gyroEnabled) {
            float rotation = (float) Gdx.input.getRotation();
            if (rotation != this.gryoRotation) {
                this.gyroMatrix.rotate(Vector3.Z, rotation);
                this.gryoRotation = rotation;
            }
            float x = Gdx.input.getAccelerometerX();
            float y = Gdx.input.getAccelerometerY();
            float z = Gdx.input.getAccelerometerZ();
            if (Gdx.input.getNativeOrientation() == Orientation.Portrait) {
                this.gyroVector.set(x, y, z);
            } else {
                this.gyroVector.set(-y, x, z);
            }
            this.gyroVector.mul(this.gyroMatrix);
            this.gyroVector.scl(preferencesManager.interGyroSensitivityPercent);
            return;
        }
        this.gyroVector.set(Vector3.Zero);
    }

    public final void show() {
    }

    public final void render(float deltaTime) {
        updateGyroInfo();
        this.nativeMainRenderer.preRender(deltaTime, this.gyroVector);
        Gdx.graphics.getGL20().glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.rainDropsRenderer.render();
        this.ripplesRenderer.render(deltaTime);
        this.sceneFBORenderer.render(deltaTime);
        this.surfaceRenderer.render();
        this.fadeRenderer.render(deltaTime);
        this.nativeMainRenderer.postRender();
        this.powerSaver.sleep();
    }

    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean z = false;
        this.ripplesRenderer.wakeUp();
        this.nativeMainRenderer.touchDown(screenX, screenY, pointer);
        boolean inRange = false;
        long now = System.currentTimeMillis();
        if (now - this.lastTapTime >= 1000 || this.lastTapPoint.dst((float) screenX, (float) screenY) >= 50.0f) {
            this.doubleTapped = false;
        } else {
            inRange = true;
            if (!this.doubleTapped) {
                z = true;
            }
            this.doubleTapped = z;
        }
        this.lastTapTime = now;
        this.lastTapPoint.set((float) screenX, (float) screenY);
        if (this.doubleTapped && inRange && PreferencesManager.getInstance().feedKoi) {
            if (BaitsManager.getInstance().getBaitsNum() > 0) {
                this.baitsRenderer.addBait(screenX, screenY, pointer);
                BaitsManager.getInstance().consume();
            }
        }
        return true;
    }

    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.ripplesRenderer.wakeUp();
        this.nativeMainRenderer.touchUp(screenX, screenY, pointer);
        return true;
    }

    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        this.ripplesRenderer.wakeUp();
        this.nativeMainRenderer.touchMove(screenX, screenY, Gdx.input.getDeltaX(pointer), Gdx.input.getDeltaY(pointer), pointer);
        return true;
    }

    public void updateSurfaceCameraDestin(float xOffsetPercent, float yOffsetPercent) {
        if (PreferencesManager.getInstance().pagePan && !this.isPreview) {
            this.nativeMainRenderer.updateSurfaceCameraDestin(xOffsetPercent, yOffsetPercent);
        }
    }

    public final void resume() {
        TextureMananger.getInstance().updateKoiTextures();
        if (TextureMananger.getInstance().isThemeTexturesDirty()) {
            this.fadeRenderer.animate();
        }
        this.powerSaver.reset();
        this.ripplesRenderer.wakeUp();
        for (int pointer = 0; pointer < 5; pointer++) {
            this.nativeMainRenderer.touchUp(0, 0, pointer);
        }
    }

    public final void dispose() {
        this.ripplesRenderer.dispose();
        this.bottomRenderer.dispose();
        this.refractionsRenderer.dispose();
        this.schoolRenderer.dispose();
        this.sceneFBORenderer.dispose();
        this.surfaceRenderer.dispose();
        this.koi3dRenderer.dispose();
        this.powerSaver.dispose();
        TextureMananger.getInstance().removeThemeChangedListener(this);
        PreferencesManager.getInstance().removePreferenceChangedListener(this);
        PondsManager.getInstance().getCurrentPond().removePondEditListener(this);
    }

    public final void hide() {
    }

    public final void pause() {
    }

    public void resize(int width, int height) {
    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    public void onThemeTextureUpdated() {
        this.bottomRenderer.onThemeTextureUpdated();
        this.refractionsRenderer.onThemeTextureUpdated();
        this.surfaceRenderer.onThemeTextureUpdated();
        this.schoolRenderer.onThemeTextureUpdated();
        this.plantsRenderer.onThemeTextureUpdated();
        PondsManager.getInstance().getCurrentPond().addPondEditListener(this);
    }

    public void sendPondEditEvent(PropsType type, Opcode code, Object model) {
        switch (code) {
            case ADD:
                switch (type) {
                    case KOI:
                        this.koi3dRenderer.addKoi((KoiModel) model);
                        return;
                    case PLANTS:
                        return;
                    default:
                        return;
                }
            case REMOVE:
                switch (type) {
                    case KOI:
                        this.koi3dRenderer.removeKoi((KoiModel) model);
                        return;
                    case PLANTS:
                        return;
                    default:
                        return;
                }
            case EDIT:
                switch (type) {
                    case KOI:
                        this.koi3dRenderer.updateKoi((KoiModel) model);
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    public void onPreviewStateChanged(boolean isPreview) {
        this.isPreview = isPreview;
        updatePanMode();
    }

    public void onPreferenceChanged(PreferenceChangedType type) {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        switch (type) {
            case GYROENABLE:
                this.nativeMainRenderer.setGyroEnabled(preferencesManager.gyroEnabled);
                return;
            case TOUCHPAN:
                updatePanMode();
                return;
            case PAGEPAN:
                updatePanMode();
                return;
            default:
                return;
        }
    }

    private void updatePanMode() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        boolean panMode = false;
        if (this.isPreview) {
            if (preferencesManager.touchPan || preferencesManager.pagePan) {
                panMode = true;
            }
        } else if (preferencesManager.touchPan) {
            panMode = true;
        }
        this.nativeMainRenderer.setPanEnabled(panMode);
    }
}
