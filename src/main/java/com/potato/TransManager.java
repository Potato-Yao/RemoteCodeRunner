package com.potato;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;

public class TransManager {
    private final Config config;
    private final SSHUtil util;

    public TransManager(Config config, SSHUtil util) {
        this.config = config;
        this.util = util;
    }

    public void transfer(File src) {
        String srcPath = src.getAbsolutePath();

        int index;
        index = srcPath.lastIndexOf(config.CURRENT_OS.osSeparator() + config.getProjectName() + config.CURRENT_OS.osSeparator());
        String path = srcPath.substring(index + 1 + config.getProjectName().length() + 1);
        path = config.CURRENT_OS.replaceToDest(path);

        String dest = config.getRemotePath() + config.DEST_OS.osSeparator() + config.getProjectName() + config.DEST_OS.osSeparator() + path;
        index = dest.lastIndexOf(config.DEST_OS.osSeparator());
        String purePath = dest.substring(0, index + 1);

        try {
            util.execCommand("mkdir " + purePath);
            util.transferFile(srcPath, dest);
        } catch (JSchException | SftpException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
