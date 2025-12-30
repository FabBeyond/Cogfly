package dev.ambershadow.cogfly.util;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.loader.ModData;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {

    public static Path getSavePath() {
        String home = System.getProperty("user.home");
        return switch (OperatingSystem.current()){
            case MAC -> Paths.get(home + "/Library/Application Support/unity.Team-Cherry.Silksong/");
            case LINUX -> Paths.get(home + "/.config/unity3d/Team Cherry/Hollow Knight Silksong/");
            case WINDOWS -> Paths.get(home + "\\AppData\\LocalLow\\Team Cherry\\Hollow Knight Silksong\\");
            case OTHER -> Paths.get("");
        };
    }

    public static String getGameExecutable(){
        return switch (OperatingSystem.current()){
            case WINDOWS -> "Hollow Knight Silksong.exe";
            case LINUX -> "Hollow Knight Silksong.x86_64";
            case MAC -> "run_bepinex.sh";
            default -> "";
        };
    }

    public static void openSavePath(){
        Path savePath = getSavePath();
        if (savePath.toString().isEmpty() || savePath.toString().isBlank())
            return;
        if (new File(savePath.toString()).exists()){
            File file = new File(savePath.toString());
            if (!file.isDirectory())
                return;
            String[] files =  file.list();
            if (files == null || files.length == 0)
                return;
            URI open = savePath.resolve(files[0]).toUri();
            if (!(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)))
                return;
            try {
                Desktop.getDesktop().browse(open);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void openGamePath() {
        if (!(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)))
            return;
        try {
            Desktop.getDesktop().browse(Paths.get(Cogfly.settings.gamePath).toUri());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openProfilePath(Profile profile) {
        if (!(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)))
            return;
        try {
            Desktop.getDesktop().browse(profile.getPath().toUri());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void pickFolderAsync(Consumer<Path> callback){
        Platform.runLater(() -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Select a folder");
            File folder = chooser.showDialog(null);
            if (folder != null) {
                String path = folder.getAbsolutePath();
                callback.accept(Paths.get(path));
            }
        });
    }

    public static void pickFileAsync(Consumer<Path> callback, FileChooser.ExtensionFilter... filters){
        Platform.runLater(() -> {
            Stage stage = new Stage();
            JFXPanel dummy = new JFXPanel();
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(filters);
            chooser.setTitle("Select a file");
            File file = chooser.showOpenDialog(null);
            if (file != null) {
                String path = file.getAbsolutePath();
                callback.accept(Paths.get(path));
            }
        });
    }

    public static void downloadAndExtract(URL url, String output){
        downloadAndExtract(url, Paths.get(output));
    }
    public static void downloadAndExtract(URL url, Path output){
        try (ZipInputStream zis = new ZipInputStream(url.openStream())) {
            Files.createDirectories(output);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path outputPath = output.resolve(entry.getName()).normalize();
                if (!outputPath.startsWith(output)) {
                    throw new IOException("Bad zip entry: " + entry.getName());
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(outputPath);
                } else {
                    Files.createDirectories(outputPath.getParent());
                    try (OutputStream os = Files.newOutputStream(outputPath)) {
                        zis.transferTo(os);
                    }
                }

            }
            zis.closeEntry();
        } catch (IOException ignored){}
    }

    public static void removeMod(ModData mod, Profile profile){
        profile.getInstalledMods().remove(mod);
        Path path = profile.getPluginsPath()
                .resolve(mod.getName() + "/");
        try(Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeMod(ModData mod){
        removeMod(mod, ProfileManager.getCurrentProfile());
    }

    public static void downloadMod(ModData mod, Profile profile){
        profile.getInstalledMods().add(mod);
        for (String dep : mod.getDependencies()) {
            if (dep.contains("BepInExPack"))
                continue;
            ModData m = getModFromDependency(dep);
            if (m != null && !m.isInstalled())
                CompletableFuture.runAsync(() -> downloadMod(m, profile));
        }
        Path path = profile.getPluginsPath()
                .resolve(mod.getName() + "/");
        downloadAndExtract(mod.getDownloadUrl(), path);
    }

    public static void downloadMod(ModData mod){
        downloadMod(mod, ProfileManager.getCurrentProfile());
    }
    private static ModData getModFromDependency(String dependency){
        for (ModData mod : Cogfly.mods) {
            String combined = mod.getAuthor() + "-" + mod.getName();
            if (dependency.contains(combined))
                return mod;
        }
        return null;
    }

    public static void copyFile(Path path){

        List<String> command = new ArrayList<>();
        String file = path.toAbsolutePath().toString();

        switch (OperatingSystem.current()) {
            case WINDOWS:
                command.add("powershell.exe");
                command.add("Set-Clipboard");
                command.add("-Path");
                command.add("'" + file + "'");
                break;
            case MAC:
                command.add("osascript");
                command.add("-e");
                command.add("set the clipboard to (POSIX file \"" + file + "\")");
                break;
            case LINUX:
                String[][] cmds = {
                        {"bash", "-c", "echo \"" + file + "\" | xclip -selection clipboard -t text/uri-list"},
                        {"bash", "-c", "echo \"" + file + "\" | xsel --clipboard --input"},
                        {"bash", "-c", "echo \"" + file + "\" | wl-copy"}
                };
                for (String[] cmd : cmds) {
                    ProcessBuilder builder = new ProcessBuilder(cmd);
                    try {
                        builder.inheritIO().start().waitFor();
                        return;
                    } catch (Exception ignored) {}
                }
                break;
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            pb.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyString(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }

    public static void replaceLineInFile(Path file, String line, String replacement){
        try (InputStream in = Files.newInputStream(file)){
            String content = new String(in.readAllBytes());
            content = content.replace(line, replacement);
            Files.writeString(file, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replaceEntireLine(Path file, String startOfLine, String replacement){
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : new ArrayList<>(lines)) {
                if (line.startsWith(startOfLine)){
                    lines.set(lines.indexOf(line), replacement);
                }
            }
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void launchModdedGame(Profile profile){
        Cogfly.launchGameAsync(profile.getBepInExPath().toString());
    }
    public enum OperatingSystem {
        WINDOWS, MAC, LINUX, OTHER;

        public static OperatingSystem current() {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) return WINDOWS;
            if (os.contains("mac")) return MAC;
            if (os.contains("nix") || os.contains("nux")) return LINUX;
            return OTHER;
        }
    }
}