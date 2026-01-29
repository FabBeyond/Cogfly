package dev.ambershadow.cogfly.asset;

import dev.ambershadow.cogfly.Cogfly;
public class Assets {
    private static CogflyAsset getAsset(String path) {
        return new CogflyAsset(Cogfly.getResource("/assets/" + path));
    }

    public static CogflyAsset icon = getAsset("icon.png");
    public static CogflyAsset openSaves = getAsset("openSaves.png");
    public static CogflyAsset centralIcon = getAsset("cogfly_art.png");
    public static CogflyAsset discord = getAsset("Discord-Symbol-White.png");
    public static CogflyAsset github = getAsset("Github_Invertocat_White.png");
    public static CogflyAsset silksongIcon = getAsset("silksong64x64.png");
}
