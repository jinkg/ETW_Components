package com.ist.lwp.koipond.models;

import com.ist.lwp.koipond.datareader.SharedPreferenceHelper;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;
import java.util.LinkedHashSet;
import java.util.List;

public class KoiModel {
    public static final String SPECIES_KOIA1 = "KOIA1";
    public static final String SPECIES_KOIA2 = "KOIA2";
    public static final String SPECIES_KOIA3 = "KOIA3";
    public static final String SPECIES_KOIA4 = "KOIA4";
    public static final String SPECIES_KOIA5 = "KOIA5";
    public static final String SPECIES_KOIA6 = "KOIA6";
    public static final String SPECIES_KOIA7 = "KOIA7";
    public static final String SPECIES_KOIA8 = "KOIA8";
    public static final String SPECIES_KOIB1 = "KOIB1";
    public static final String SPECIES_KOIB2 = "KOIB2";
    public static final String SPECIES_KOIB3 = "KOIB3";
    public static final String SPECIES_KOIB4 = "KOIB4";
    public static final String SPECIES_KOIB5 = "KOIB5";
    public static final String SPECIES_KOIB6 = "KOIB6";
    public static final String SPECIES_KOIB7 = "KOIB7";
    public static final String SPECIES_KOID01 = "KOID01";
    public static final String SPECIES_KOID02 = "KOID02";
    public static final String SPECIES_KOID03 = "KOID03";
    public static final String SPECIES_KOID04 = "KOID04";
    public static final String SPECIES_KOID05 = "KOID05";
    public static final String SPECIES_KOID06 = "KOID06";
    public static final String SPECIES_KOID07 = "KOID07";
    public static final String SPECIES_KOID08 = "KOID08";
    public static final String SPECIES_KOID09 = "KOID09";
    public static final String SPECIES_KOID10 = "KOID10";
    public static final String SPECIES_KOID11 = "KOID11";
    public static final String SPECIES_KOID12 = "KOID12";
    public static final String SPECIES_KOID13 = "KOID13";
    public static final String SPECIES_KOID14 = "KOID14";
    public static final String SPECIES_KOID15 = "KOID15";
    public static final String SPECIES_KOID16 = "KOID16";
    public static final LinkedHashSet<String> validSpecies = new LinkedHashSet<String>() {
        private static final long serialVersionUID = 1;

        {
            add(KoiModel.SPECIES_KOIA1);
            add(KoiModel.SPECIES_KOIB4);
            add(KoiModel.SPECIES_KOIB3);
            add(KoiModel.SPECIES_KOIA6);
            add(KoiModel.SPECIES_KOIA8);
            add(KoiModel.SPECIES_KOIB1);
            add(KoiModel.SPECIES_KOIB2);
            add(KoiModel.SPECIES_KOIB5);
            add(KoiModel.SPECIES_KOIB6);
            add(KoiModel.SPECIES_KOIB7);
            add(KoiModel.SPECIES_KOID03);
            add(KoiModel.SPECIES_KOID04);
            add(KoiModel.SPECIES_KOID05);
            add(KoiModel.SPECIES_KOID06);
            add(KoiModel.SPECIES_KOID07);
            add(KoiModel.SPECIES_KOID08);
            add(KoiModel.SPECIES_KOID09);
            add(KoiModel.SPECIES_KOID10);
            add(KoiModel.SPECIES_KOID11);
            add(KoiModel.SPECIES_KOID12);
            add(KoiModel.SPECIES_KOID13);
            add(KoiModel.SPECIES_KOID14);
            add(KoiModel.SPECIES_KOID15);
            add(KoiModel.SPECIES_KOID16);

        }
    };
    public boolean fromDB;
    protected int id;
    public int pondId;
    public KoiSize size;
    public String species;

    public enum KoiSize {
        BIG,
        MEDIUM,
        SMALL
    }

    public KoiModel(int recommended) {
        this.species = SPECIES_KOIA1;
        this.size = KoiSize.BIG;
        this.fromDB = false;
        this.pondId = 0;
        KoiManager koiManager = KoiManager.getInstance();
        this.id = koiManager.allocID(recommended);
        koiManager.koiModels.put(this.id, this);
    }

    public KoiModel() {
        this(-1);
    }

    public int getId() {
        return this.id;
    }

    public void saveIntoPond() {
        updateDB();
    }

    public void vanishFromPond() {
        updateDB();
    }

    public void updateSpecies(String species) {
        this.species = species;
        updateDB();
    }

    public void updateSize(KoiSize size) {
        this.size = size;
        updateDB();
    }

    public String toString() {
        return this.id + "|" + this.species + "|" + this.size.toString() + "|" + this.pondId;
    }

    private void updateDB() {
        List<KoiModel> koiModels = PondsManager.getInstance().getCurrentPond().getKoiModels();
        LinkedHashSet<String> koiInPond = new LinkedHashSet<>();
        for (KoiModel koiModel : koiModels) {
            koiInPond.add(koiModel.toString());
        }
        SharedPreferenceHelper.getInstance().putStringSet(PreferenceChangedType.KOISET.toString(), koiInPond);
    }
}
