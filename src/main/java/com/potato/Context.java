package com.potato;

public class Context {
    private final OsType currentOs;

    public Context(OsType currentOs) {
        this.currentOs = currentOs;
    }

    public Context() {
        this(OsType.Windows);
    }

    public String osSeparator() {
        return currentOs == OsType.Windows ? "\\" : "/";
    }

    public String destSeparator() {
        return currentOs == OsType.Windows ? "/" : "\\";
    }

    public String replaceToDest(String src) {
        return src.replace(osSeparator(), destSeparator());
    }
}
