package com.potato;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SSHUtil {
    private final Session session;

    public SSHUtil(String hostName, String hostIp, String passwd, int port) throws JSchException {
        JSch jSch = new JSch();
        session = jSch.getSession(hostName, hostIp, port);
        session.setPassword(passwd);
        session.setConfig("StrictHostKeyChecking", "no");
    }

    public SSHUtil(String hostName, String hostIp, String passwd) throws JSchException {
        this(hostName, hostIp, passwd, 22);
    }

    public void connect() throws JSchException {
        session.connect();
    }

    public void disconnect() throws JSchException {
        session.disconnect();
    }

    public void execCommand(String command) throws JSchException, IOException {
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        channel.connect();

        InputStream in = channel.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel.disconnect();
    }

    public void transferFile(String src, String dest) throws JSchException, SftpException {
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();

        channel.put(src, dest);

        channel.disconnect();
    }
}
