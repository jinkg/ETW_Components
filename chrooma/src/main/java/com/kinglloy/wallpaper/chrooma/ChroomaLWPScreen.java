package com.kinglloy.wallpaper.chrooma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.Iterator;

public class ChroomaLWPScreen implements Screen {
    private static final float GRAVITY_EARTH = 10.0f;
    private static final String TAG = "SCREEN";
    private Array<Table> bgCards;
    private Texture card;
    private float forceThreshold = 3.0f;
    private float lastPitch;
    private float lastRoll;
    private ChroomaLWP lwp;
    private Color[] oldPalette;
    private Color[] palette;
    private GamePreferences prefs = GamePreferences.instance;
    private float shakeTimer = 0.0f;
    private Stage stage;
    private float startingRoll;
    private float time = 0.0f;
    private long timeSinceSwitchOff;

    public ChroomaLWPScreen(ChroomaLWP lwp) {
        this.lwp = lwp;
    }

    public void show() {
        this.stage = new Stage(new ExtendViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        this.bgCards = new Array();
        setUp();
    }

    private void setUp() {
        if (this.card == null) {
            this.card = new Texture(this.prefs.getShape());
            this.card.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            this.palette = Palette.getRandom();
        }
        this.stage.addActor(buildCardLayer());
        setDefaultRollValue();
    }

    private Table buildCardLayer() {
        Table layer = new Table();
        int breathSpeed = this.prefs.getBreathAnimationSpeed();
        float timeOfMovement = Constants.BASETIME / ((float) Math.max(1, breathSpeed));
        float timeForComing = Constants.BASETIMESTARTING / ((float) Math.max(1, this.prefs.getStartingAnimationSpeed()));
        float tileSize = (((Constants.VIEWPORT_GUI_WIDTH / 3.0f) * ((float) this.prefs.getTileSize())) / 100.0f) + (Constants.VIEWPORT_GUI_WIDTH / GRAVITY_EARTH);
        float sizeFactor = (Constants.VIEWPORT_GUI_WIDTH / tileSize) * 2.0f;
        for (int i = 4; i >= 0; i--) {
            Table bgTable = new Table();
            Actor bgCard = new Image(this.card);
            float size = tileSize * ((float) (i + 1));
            bgTable.setSize(size, size);
            bgTable.setPosition(Constants.VIEWPORT_GUI_WIDTH / 2.0f, Constants.VIEWPORT_GUI_HEIGHT / 2.0f, 1);
            bgTable.add(bgCard).fill().expand();
            layer.addActor(bgTable);
            if (this.prefs.breathAnimation) {
                bgTable.addAction(Actions.sequence(Actions.delay(0.8f * ((float) i)), Actions.forever(Actions.parallel(Actions.sequence(Actions.sizeBy(Constants.VIEWPORT_GUI_WIDTH / sizeFactor, Constants.VIEWPORT_GUI_WIDTH / sizeFactor, timeOfMovement, Interpolation.sine), Actions.sizeBy((-Constants.VIEWPORT_GUI_WIDTH) / sizeFactor, (-Constants.VIEWPORT_GUI_WIDTH) / sizeFactor, timeOfMovement, Interpolation.sine)), Actions.sequence(Actions.moveBy(((-Constants.VIEWPORT_GUI_WIDTH) / sizeFactor) / 2.0f, ((-Constants.VIEWPORT_GUI_WIDTH) / sizeFactor) / 2.0f, timeOfMovement, Interpolation.sine), Actions.moveBy((Constants.VIEWPORT_GUI_WIDTH / sizeFactor) / 2.0f, (Constants.VIEWPORT_GUI_WIDTH / sizeFactor) / 2.0f, timeOfMovement, Interpolation.sine))))));
            }
            if (this.prefs.startingAnimation) {
                bgTable.setSize(0.0f, 0.0f);
                bgTable.setPosition(Constants.VIEWPORT_GUI_WIDTH / 2.0f, Constants.VIEWPORT_GUI_HEIGHT / 2.0f, 1);
                if (this.prefs.isGyroEnabled()) {
                    bgTable.addAction(Actions.sequence(Actions.delay(0.1f * ((float) i)), Actions.sizeTo(size, size, timeForComing, Interpolation.swingOut)));
                } else {
                    bgTable.addAction(Actions.sequence(Actions.delay(0.1f * ((float) i)), Actions.parallel(Actions.sizeTo(size, size, timeForComing, Interpolation.swingOut), Actions.moveToAligned(Constants.VIEWPORT_GUI_WIDTH / 2.0f, Constants.VIEWPORT_GUI_HEIGHT / 2.0f, 1, timeForComing, Interpolation.swingOut))));
                }
            }
            if (this.oldPalette != null) {
                bgCard.setColor(this.oldPalette[i]);
                bgCard.addAction(Actions.color(this.palette[i], 1.0f, Interpolation.circleOut));
            } else {
                bgCard.setColor(this.palette[i]);
            }
            this.bgCards.add(bgTable);
        }
        this.lwp.getActionResolver().setActionLauncher(Palette.getActualPaletteNumber());
        return layer;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(this.palette[0].r / 3.0f, this.palette[0].g / 3.0f, this.palette[0].b / 3.0f, 1.0f);
        Gdx.gl.glClear(16384);
        this.stage.act();
        this.stage.draw();
        this.time += delta;
        if (this.prefs.getTimeLimit() != 0.0f && this.time > this.prefs.getTimeLimit()) {
            restartCardLayer();
            setDefaultRollValue();
        }
        if (this.shakeTimer < 0.0f && this.prefs.shakeToChange() && checkShake()) {
            restartCardLayer();
            this.shakeTimer = 0.5f;
        }
        if (this.prefs.isGyroEnabled()) {
            updateGyro();
        }
        if (this.shakeTimer >= 0.0f) {
            this.shakeTimer -= Gdx.graphics.getDeltaTime();
        }
    }

    private boolean checkShake() {
        float xGrav = Gdx.input.getAccelerometerX() / GRAVITY_EARTH;
        float yGrav = Gdx.input.getAccelerometerY() / GRAVITY_EARTH;
        float zGrav = Gdx.input.getAccelerometerZ() / GRAVITY_EARTH;
        return ((float) Math.sqrt((double) (((xGrav * xGrav) + (yGrav * yGrav)) + (zGrav * zGrav)))) > this.forceThreshold;
    }

    private void updateGyro() {
        float sensibility = (float) (((this.prefs.getParallaxSensibility() * 6) / 100) + 2);
        float pitch = MathUtils.lerp(this.lastPitch, (Gdx.input.getAccelerometerZ() / GRAVITY_EARTH) - this.startingRoll, Gdx.graphics.getDeltaTime() * sensibility);
        MathUtils.clamp(pitch, -1.0f, 1.0f);
        float roll = MathUtils.lerp(this.lastRoll, Gdx.input.getAccelerometerX() / GRAVITY_EARTH, Gdx.graphics.getDeltaTime() * sensibility);
        MathUtils.clamp(roll, -1.0f, 1.0f);
        float parallaxSpan = (Constants.VIEWPORT_GUI_WIDTH / 6.0f) + (((Constants.VIEWPORT_GUI_WIDTH / 3.0f) * ((float) this.prefs.getParallaxSensibility())) / 100.0f);
        Iterator it = this.bgCards.iterator();
        while (it.hasNext()) {
            Table t = (Table) it.next();
            int index = this.bgCards.size - this.bgCards.indexOf(t, false);
            if (Constants.VIEWPORT_GUI_HEIGHT >= Constants.VIEWPORT_GUI_WIDTH) {
                t.setPosition((Constants.VIEWPORT_GUI_WIDTH / 2.0f) + ((parallaxSpan * roll) * ((1.0f / ((float) this.bgCards.size)) * ((float) index))), (Constants.VIEWPORT_GUI_HEIGHT / 2.0f) - ((parallaxSpan * pitch) * ((1.0f / ((float) this.bgCards.size)) * ((float) index))), 1);
            } else {
                t.setPosition((Constants.VIEWPORT_GUI_WIDTH / 2.0f) + ((parallaxSpan * pitch) * ((1.0f / ((float) this.bgCards.size)) * ((float) index))), (Constants.VIEWPORT_GUI_HEIGHT / 2.0f) - ((parallaxSpan * roll) * ((1.0f / ((float) this.bgCards.size)) * ((float) index))), 1);
            }
        }
        this.lastPitch = pitch;
        this.lastRoll = roll;
    }

    private void setDefaultRollValue() {
        this.startingRoll = Gdx.input.getAccelerometerZ() / GRAVITY_EARTH;
        this.lastRoll = -this.startingRoll;
    }

    public void resize(int width, int height) {
        Constants.VIEWPORT_GUI_WIDTH = (float) Gdx.graphics.getWidth();
        Constants.VIEWPORT_GUI_HEIGHT = (float) Gdx.graphics.getHeight();
        this.stage.dispose();
        show();
    }

    public void pause() {
        this.stage.clear();
    }

    public void resume() {
        if (this.prefs.getTimeLimit() == 0.0f || this.prefs.optionsSet) {
            restartCardLayer();
            this.prefs.optionsSet = false;
        }
        setDefaultRollValue();
        this.forceThreshold = 2.0f + ((3.0f * ((float) this.prefs.getGyroSensibility())) / 100.0f);
        this.timeSinceSwitchOff = TimeUtils.timeSinceMillis(this.prefs.getCurrentTime()) / 1000;
        this.time += (float) this.timeSinceSwitchOff;
    }

    private void restartCardLayer() {
        this.stage.clear();
        this.card.dispose();
        Gdx.app.debug(TAG, Texture.getManagedStatus());
        this.card = new Texture(this.prefs.getShape());
        this.card.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.oldPalette = this.palette;
        this.palette = Palette.getRandom();
        this.time = 0.0f;
        this.bgCards.clear();
        this.stage.addActor(buildCardLayer());
    }

    public void hide() {
    }

    public void dispose() {
    }
}
