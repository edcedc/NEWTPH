package com.spit.tph.Event;

import com.spit.tph.Entity.Asset;
import com.spit.tph.Entity.SPEntityP2.AssetsDetail;
import com.spit.tph.Entity.SPUser;
import com.spit.tph.Response.LevelData;

import java.util.ArrayList;
import java.util.List;

public class PendingToAdd {
    public ArrayList<LevelData> levelData = new ArrayList<>();
    public String fatherNo;
    public int level;
    public int type = -1;
    public String typeString;
    public List<AssetsDetail> assetsDetail = null;
    public List<Asset> assetList = null;

    public List<SPUser> spUsers = null;
 }
