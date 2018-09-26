package com.ist.lwp.koipond.models;

import com.ist.lwp.koipond.Config;
import com.ist.lwp.koipond.datareader.SharedPreferenceHelper;
import com.ist.lwp.koipond.models.KoiModel.KoiSize;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PondModel {

  public int id;
  private List<KoiModel> koiModels;
  private List<PondEditListener> listeners = new ArrayList<>();

  public enum Opcode {
    ADD,
    REMOVE,
    EDIT
  }

  public interface PondEditListener {
    void sendPondEditEvent(PropsType propsType, Opcode opcode, Object obj);
  }

  public enum PropsType {
    KOI,
    PLANTS
  }

  private void initKoiModels() {
    this.koiModels = new ArrayList<>();
    for (String data : SharedPreferenceHelper.getInstance()
        .getStringSet(PreferenceChangedType.KOISET.toString(), Config.KOI_DATASET)) {
      String[] array = data.split("\\|");
      KoiModel model = new KoiModel(Integer.parseInt(array[0]));
      model.fromDB = true;
      model.species = array[1];

      if (array[2].equalsIgnoreCase("BIG")) {
        model.size = KoiSize.BIG;
      }
      if (array[2].equalsIgnoreCase("MEDIUM")) {
        model.size = KoiSize.MEDIUM;
      }
      if (array[2].equalsIgnoreCase("SMALL")) {
        model.size = KoiSize.SMALL;
      }
      model.pondId = Integer.parseInt(array[3]);
      addKoiModel(model);
    }
  }

  public void reset() {
    List<KoiModel> clonedKoiModels = new ArrayList<>();
    for (KoiModel model : this.koiModels) {
      clonedKoiModels.add(model);
    }
    for (KoiModel model2 : clonedKoiModels) {
      removeKoiModel(model2);
    }
    SharedPreferenceHelper.getInstance()
        .putStringSet(PreferenceChangedType.KOISET.toString(), null);
    initKoiModels();
  }

  public PondModel(int id) {
    this.id = id;
    initKoiModels();
  }

  public void addPondEditListener(PondEditListener listener) {
    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener);
    }
  }

  public void removePondEditListener(PondEditListener listener) {
    if (this.listeners.contains(listener)) {
      this.listeners.remove(listener);
    }
  }

  public void addKoiModel(KoiModel koiModel) {
    if (!this.koiModels.contains(koiModel)) {
      this.koiModels.add(koiModel);
      if (!koiModel.fromDB) {
        koiModel.saveIntoPond();
      }
      for (PondEditListener listener : this.listeners) {
        listener.sendPondEditEvent(PropsType.KOI, Opcode.ADD, koiModel);
      }
    }
  }

  public void removeKoiModel(KoiModel koiModel) {
    if (this.koiModels.contains(koiModel)) {
      this.koiModels.remove(koiModel);
      koiModel.vanishFromPond();
      for (PondEditListener listener : this.listeners) {
        listener.sendPondEditEvent(PropsType.KOI, Opcode.REMOVE, koiModel);
      }
    }
  }

  public void updateKoiSize(KoiModel koiModel, KoiSize size) {
    if (this.koiModels.contains(koiModel)) {
      koiModel.updateSize(size);
      for (PondEditListener listener : this.listeners) {
        listener.sendPondEditEvent(PropsType.KOI, Opcode.EDIT, koiModel);
      }
    }
  }

  public void updateKoiSpecies(KoiModel koiModel, String species) {
    if (this.koiModels.contains(koiModel)) {
      koiModel.updateSpecies(species);
      for (PondEditListener listener : this.listeners) {
        listener.sendPondEditEvent(PropsType.KOI, Opcode.EDIT, koiModel);
      }
    }
  }

  public List<KoiModel> getKoiModels() {
    return this.koiModels;
  }

  public HashSet<String> getKoiSpecies() {
    HashSet<String> koiSpecies = new HashSet<>();
    for (KoiModel koiModel : this.koiModels) {
      koiSpecies.add(koiModel.species);
    }
    return koiSpecies;
  }
}
