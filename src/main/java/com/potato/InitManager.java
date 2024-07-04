package com.potato;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InitManager {
    public static void hint(ResourceBundle bundle, Config config) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(bundle.getString("set_python"));
        config.setPythonCommand(scanner.nextLine());
        System.out.println(bundle.getString("set_host_name"));
        config.setHostName(scanner.nextLine());
        System.out.println(bundle.getString("set_host_ip"));
        config.setHostIp(scanner.nextLine());
        System.out.println(bundle.getString("set_host_passwd"));
        config.setHostPasswd(scanner.nextLine());

        try {
            ConfigManager.updateConfigFile(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
