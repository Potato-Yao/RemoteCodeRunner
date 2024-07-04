package com.potato;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSchException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final String WORK_DIR = System.getProperty("user.dir");
    public static final File CONFIG_FILE = new File(WORK_DIR, "config.json");

    public static boolean checkConfigFile() {
        return CONFIG_FILE.exists();
    }

    public static void copyConfigFile() throws IOException {
        Files.copy(ResourceUtil.getResourceAsFile("config.json").toPath(), Path.of(CONFIG_FILE.getPath()));
    }

    public static void updateConfigFile(Config config) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(CONFIG_FILE, config);
    }

    public static void checkProjectFolder(SSHUtil util, Config config) {
        try {
            util.execCommand("mkdir " + config.getProjectPath());
        } catch (JSchException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
