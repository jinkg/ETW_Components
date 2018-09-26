package com.ist.lwp.koipond.waterpond;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.ist.lwp.koipond.natives.NativeSceneFBORenderer;
import com.ist.lwp.koipond.waterpond.PreferencesManager.OnPreferenceChangedListener;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;

public class SceneFBORenderer extends FBOWrapper implements OnPreferenceChangedListener {
    private final NativeSceneFBORenderer nativeSceneFBORenderer = new NativeSceneFBORenderer();

    public SceneFBORenderer(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        preferencesManager.addPreferenceChangedListener(this);
        this.nativeSceneFBORenderer.setSchoolVisible(preferencesManager.schoolEnabled);
        this.nativeSceneFBORenderer.setPlantsVisible(preferencesManager.showFloatage);
        create(Format.RGB565);
    }

    public final void dispose() {
        super.dispose();
        PreferencesManager.getInstance().removePreferenceChangedListener(this);
    }

    public final void render(float deltaTime) {
        begin();
        this.nativeSceneFBORenderer.render(deltaTime);
        end();
    }

    public void onPreferenceChanged(PreferenceChangedType type) {
        switch (type) {
            case SHOWSCHOOL:
                this.nativeSceneFBORenderer.setSchoolVisible(PreferencesManager.getInstance().schoolEnabled);
                return;
            case SHOWFLOATAGE:
                this.nativeSceneFBORenderer.setPlantsVisible(PreferencesManager.getInstance().showFloatage);
                return;
            default:
                return;
        }
    }
}
