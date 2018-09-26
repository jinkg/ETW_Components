package com.ist.lwp.koipond.koi3d;

import com.ist.lwp.koipond.models.KoiModel;
import com.ist.lwp.koipond.models.KoiModel.KoiSize;
import com.ist.lwp.koipond.models.PondsManager;
import com.ist.lwp.koipond.natives.NativeKoi3DRenderer;
import com.ist.lwp.koipond.waterpond.MainRenderer;

public class Koi3DRenderer {
    private NativeKoi3DRenderer nativeKoiRenderer = new NativeKoi3DRenderer();

    public Koi3DRenderer(MainRenderer mainRenderer) {
        for (KoiModel model : PondsManager.getInstance().getCurrentPond().getKoiModels()) {
            addKoi(model);
        }
    }

    public void addKoi(KoiModel koiModel) {
        this.nativeKoiRenderer.addKoi(koiModel.getId(), koiModel.species, sizeToInt(koiModel.size));
    }

    public void removeKoi(KoiModel koiModel) {
        this.nativeKoiRenderer.removeKoi(koiModel.getId());
    }

    public void updateKoi(KoiModel koiModel) {
        this.nativeKoiRenderer.updateKoi(koiModel.getId(), koiModel.species, sizeToInt(koiModel.size));
    }

    private int sizeToInt(KoiSize size) {
        switch (size) {
            case SMALL:
                return 0;
            case MEDIUM:
                return 1;
            case BIG:
                return 2;
            default:
                return 1;
        }
    }

    private void addSampleKoi() {
        KoiModel sample1 = new KoiModel();
        sample1.species = KoiModel.SPECIES_KOIB1;
        addKoi(sample1);
        KoiModel sample2 = new KoiModel();
        sample2.species = KoiModel.SPECIES_KOIB2;
        addKoi(sample2);
    }

    public void dispose() {
    }
}
