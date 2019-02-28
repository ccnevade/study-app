package fat.cc.study.common;

public enum FileType {

    html("text/html"),
    image("image"),
    pdf("application/pdf"),
    plain("text/plain"),
    audio("audio/*"),
    video("video/*"),
    chm("application/x-chm"),
    msword("application/msword"),
    msexcel("application/vnd.ms-excel"),
    msppt("application/vnd.ms-powerpoint"),
    apk("application/vnd.android.package-archive")
    ;

    private final String fileType;

    FileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
