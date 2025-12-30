package dev.ambershadow.cogfly.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.util.ProfileManager;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModData {

    public static ModData getModAtVersion(String fullName, String version){
        Optional<JsonObject> mod = rawModData.stream()
                .filter(obj -> obj.get("full_name")
                        .getAsString().equals(fullName)).findFirst();
        if (mod.isEmpty())
            return null;
        JsonObject parent = mod.get();
        JsonArray versions = parent.get("versions").getAsJsonArray();
        final JsonObject[] targetVersion = {null};
        for (JsonElement ver : versions) {
            if (ver.getAsJsonObject().get("version_number")
                    .getAsString().equals(version)) {
                targetVersion[0] = ver.getAsJsonObject();
                break;
            }
        }
        if (targetVersion[0] == null)
            return null;
        return new ModData(parent, targetVersion[0]);
    }

    public static ModData getMod(String fullName){
        return Cogfly.mods.stream().filter(mod -> mod.getFullName().equals(fullName)).findFirst().orElse(null);
    }
    static List<JsonObject> rawModData = new ArrayList<>();
    private final JsonObject rawObj;
    private final String name;
    private final String fullName;
    private final String author;
    private URL downloadUrl;
    private final List<String> dependencies;
    private final String versionNumber;
    private final String description;

    private final String dateCreated;
    private final String dateModified;
    private int totalDownloads;
    private final URI packageUrl;
    private final URI websiteUrl;

    private ModData(JsonObject parentObject, JsonObject version){
        rawObj = parentObject;
        name = parentObject.get("name").getAsString();
        author = parentObject.get("owner").getAsString();
        fullName = parentObject.get("full_name").getAsString();
        dependencies = new ArrayList<>();
        totalDownloads = 0;
        dateCreated = parentObject.get("date_created").getAsString();
        try {
            downloadUrl = URL.of(URI.create(version.get("download_url").getAsString()), null);
        } catch (MalformedURLException e){
            downloadUrl = null;
            e.printStackTrace();
        }
        packageUrl = URI.create(parentObject.get("package_url").getAsString());
        String website = version.get("website_url").getAsString();
        websiteUrl = website.isEmpty() ? null : URI.create(website);
        JsonArray dependencies = version.get("dependencies").getAsJsonArray();
        dependencies.forEach(dep -> {
            if (dep.getAsString().contains("BepInExPack") || dep.getAsString().trim().isEmpty())
                return;
            this.dependencies.add(dep.getAsString());
        });
        dateModified = version.get("date_created").getAsString();
        this.versionNumber = version.get("version_number").getAsString();
        description = version.get("description").getAsString();

        parentObject.get("versions").getAsJsonArray()
        .forEach(v -> {
            totalDownloads += v.getAsJsonObject().get("downloads").getAsInt();
        });
    }
    public ModData(JsonObject parentObject){
        JsonArray versions = parentObject.get("versions").getAsJsonArray();
        JsonObject latestVersion = versions.get(0).getAsJsonObject();
        this(parentObject, latestVersion);
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public String getAuthor() {
        return author;
    }

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public String getName() {
        return name;
    }
    public String getFullName(){
        return fullName;
    }
    public String getVersionNumber() {
        return versionNumber;
    }
    public String getDescription() {
        return description;
    }
    public String getDateCreated() {
        return dateCreated;
    }
    public String getDateModified() {
        return dateModified;
    }
    public int getTotalDownloads(){
        return totalDownloads;
    }
    public URI getPackageUrl(){
        return packageUrl;
    }
    public URI getWebsiteUrl(){
        return websiteUrl;
    }
    public boolean isInstalled(){
        return ProfileManager.getCurrentProfile().getInstalledMods()
                .stream().anyMatch(m ->
                m.getFullName().equals(getFullName()));
    }
    public boolean isOutdated(){
        if (!isInstalled())
            return false;
        return !versionNumber.equals(
                rawObj.get("versions").getAsJsonArray()
                        .get(0).getAsJsonObject()
                        .get("version_number").getAsString()
        );
    }
}
