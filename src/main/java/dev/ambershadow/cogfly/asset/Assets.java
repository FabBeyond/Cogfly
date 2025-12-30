package dev.ambershadow.cogfly.asset;

import dev.ambershadow.cogfly.Cogfly;
public class Assets {
    private static CogflyAsset getAsset(String path) {
        return new CogflyAsset(Cogfly.getResource("/assets/" + path));
    }

    public static CogflyAsset icon = getAsset("icon.png");
    public static CogflyAsset openSaves = getAsset("openSaves.png");
    public static CogflyAsset copy = getAsset("copyLog.png");
    public static CogflyAsset silksongIcon = getAsset("silksong64x64.png");
}
