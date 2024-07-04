package com.potato;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSchException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, JSchException {
        ObjectMapper mapper = new ObjectMapper();
//        Locale locale = new Locale("zh", "CN");
        Locale locale = new Locale("en", "US");
        ResourceBundle hintBundle = ResourceBundle.getBundle("hint", locale);

        if (args.length == 0) {
            zeroArgs(hintBundle);
        } else if (args[0].equals("init")) {
            initMode(hintBundle, new Config());
        } else {
            switchMode(hintBundle, args);
        }

////            File localFile = ResourceUtil.getResourceAsFile(localFilePath);
////            util.transferFile(localFile.getAbsolutePath(), remoteFilePath);
////            util.execCommand("python3 " + remoteFilePath);
    }

    private static void zeroArgs(ResourceBundle bundle) {
        System.out.println(bundle.getString("description") + Config.VERSION);
    }

    private static void initMode(ResourceBundle bundle, Config config) {
        InitManager.hint(bundle, config);
    }

    private static void switchMode(ResourceBundle bundle, String[] args) throws IOException, JSchException {
        if (!ConfigManager.checkConfigFile()) {
            System.out.println(bundle.getString("turn_to_init"));
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals("d")) {
                ConfigManager.copyConfigFile();
            } else if (scanner.nextLine().equals("i")) {
                initMode(bundle, new Config());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(ConfigManager.CONFIG_FILE, Config.class);
        SSHUtil sshUtil = new SSHUtil(config.getHostName(), config.getHostIp(), config.getHostPasswd());
        sshUtil.connect();
        ConfigManager.checkProjectFolder(sshUtil, config);

        switch (args[0]) {
            case "trans":
                TransManager manager = new TransManager(config, sshUtil);
                File src = new File(args[1]);
                manager.transfer(src);
                break;
            case "exec":
                break;
            case "test":
                System.out.println(config.getProjectName());
                break;
            default:
                System.out.println(bundle.getString("unknown_command"));
        }

        sshUtil.disconnect();
    }
}