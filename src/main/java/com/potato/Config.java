package com.potato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    @JsonIgnore
    public static final String VERSION = "b0.0.1";

    @JsonIgnore
    public final Context CURRENT_OS = new Context();
    @JsonIgnore
    public final Context DEST_OS = new Context(OsType.Unix);

    @JsonIgnore
    private String projectName;
    @JsonProperty("python_command")
    private String pythonCommand;
    @JsonProperty("host_name")
    private String hostName;
    @JsonProperty("host_ip")
    private String hostIp;
    @JsonProperty("host_passwd")
    private String hostPasswd;
    @JsonProperty("remote_path")
    private String remotePath = "/home/potato-ubuntu/code/remote-code";

    public Config() {
        String name = System.getProperty("user.dir");
        int index = name.lastIndexOf(CURRENT_OS.osSeparator());

        name = name.substring(index + 1);

        projectName = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPythonCommand() {
        return pythonCommand;
    }

    public void setPythonCommand(String pythonCommand) {
        this.pythonCommand = pythonCommand;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostPasswd() {
        return hostPasswd;
    }

    public void setHostPasswd(String hostPasswd) {
        this.hostPasswd = hostPasswd;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getProjectPath() {
        return remotePath + DEST_OS.osSeparator() + projectName;
    }
}
