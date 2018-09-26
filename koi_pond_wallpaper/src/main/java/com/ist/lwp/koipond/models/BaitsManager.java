package com.ist.lwp.koipond.models;

import com.ist.lwp.koipond.datareader.SharedPreferenceHelper;

public class BaitsManager {
    private static final int DEFAULT_BAITS_NUM = 99;
    private static BaitsManager sInstance;
    private final String AWARD_TIMESTAMP = "AWARD_TIMESTAMP";
    private final String BAITS_NUM = "BAITS_NUM";
    private final String BANNER_TIMESTAMP = "BANNER_TIMESTAMP";
    private long awardTimestamp =
        Long.valueOf(SharedPreferenceHelper.getInstance().getString("AWARD_TIMESTAMP", "0"));
    private int baitsNum;
    private long bannerTimestamp =
        Long.valueOf(SharedPreferenceHelper.getInstance().getString("BANNER_TIMESTAMP", "0"));
    private boolean infiniteBaits = true;

    public enum BaitQuantity {
        FEW,
        MEDIUM,
        BONUS
    }

    private BaitsManager() {
        if (this.infiniteBaits) {
            this.baitsNum = Integer.MAX_VALUE;
        } else {
            this.baitsNum = SharedPreferenceHelper.getInstance().getInteger("BAITS_NUM", 99);
        }
    }

    public static BaitsManager getInstance() {
        if (sInstance == null) {
            sInstance = new BaitsManager();
        }
        return sInstance;
    }

    public void consume() {
        if (!this.infiniteBaits) {
            if (this.baitsNum <= 0) {
                this.baitsNum = 0;
                return;
            }
            this.baitsNum--;
            saveBaitsNum();
        }
    }

    public int getBaitsNum() {
        return this.baitsNum;
    }

    public void setBaitsNum(int number) {
        if (!this.infiniteBaits) {
            this.baitsNum = number;
            saveBaitsNum();
        }
    }

    public void setInfiniteBaits() {
        this.infiniteBaits = true;
        this.baitsNum = Integer.MAX_VALUE;
    }

    private void saveBaitsNum() {
        SharedPreferenceHelper.getInstance().putInteger("BAITS_NUM", this.baitsNum);
    }

    public void dispose() {
        sInstance = null;
    }

    public int award(BaitQuantity quantity) {
        int awardCount;
        switch (quantity) {
            case FEW:
                awardCount = (int) ((Math.random() * 5.0d) + 5.0d);
                break;
            case MEDIUM:
                awardCount = (int) ((Math.random() * 10.0d) + 10.0d);
                break;
            case BONUS:
                awardCount = 90;
                break;
            default:
                awardCount = (int) ((Math.random() * 5.0d) + 5.0d);
                break;
        }
        setBaitsNum(this.baitsNum + awardCount);
        return awardCount;
    }

    public long getAwardTimestamp() {
        return this.awardTimestamp;
    }

    public void setAwardTimestamp(long timestamp) {
        this.awardTimestamp = timestamp;
        SharedPreferenceHelper.getInstance().putString("AWARD_TIMESTAMP", String.valueOf(this.awardTimestamp));
    }

    public long getBannerTimestamp() {
        return this.bannerTimestamp;
    }

    public void setBannerTimestamp(long timestamp) {
        this.bannerTimestamp = timestamp;
        SharedPreferenceHelper.getInstance().putString("BANNER_TIMESTAMP", String.valueOf(this.bannerTimestamp));
    }
}
