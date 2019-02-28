package fat.cc.study.bean;

public class CourseFilesListItem {

    private String fileName;

    private String fileUri;

    private String fileType;

    public CourseFilesListItem() {
    }

    public CourseFilesListItem(String fileName, String fileUri,String fileType) {
        this.fileName = fileName;
        this.fileUri = fileUri;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
