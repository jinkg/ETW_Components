package com.ist.lwp.koipond;

import java.util.LinkedHashSet;

/**
 * YaLin
 * On 2018/9/26.
 */
public class Config {
  public static boolean FISH_SCHOOL = true;
  public static boolean GYROENABLE = true;
  public static boolean SHOWFLOATAGE = true;

  public static String CURRENTTHEME = "Pebble";

  public static final LinkedHashSet<String> KOI_DATASET = new LinkedHashSet<String>() {
    private static final long serialVersionUID = 1;

    {
      add("0|KOIA1|BIG|0");
      add("1|KOIA8|BIG|0");
      add("2|KOIB1|MEDIUM|0");
      add("3|KOIB3|MEDIUM|0");
      add("4|KOIB5|SMALL|0");
      add("5|KOIB6|SMALL|0");
      add("6|KOIB7|MEDIUM|0");
      add("7|KOIB4|MEDIUM|0");
      add("8|KOIA6|MEDIUM|0");
      //add("1|KOIA1|SMALL|0");
      //add("2|KOIB4|SMALL|0");
      //add("3|KOIB3|SMALL|0");
      //add("4|KOIA7|MEDIUM|0");
      //add("5|KOIA2|MEDIUM|0");
      //add("6|KOIA3|MEDIUM|0");
      //add("7|KOIA4|MEDIUM|0");
      //add("8|KOIA5|MEDIUM|0");
      //add("9|KOIA6|MEDIUM|0");
    }
  };
}
