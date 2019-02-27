package fat.cc.study.bean;

public class CourseFilesListItem {

    private String fileName;

    private String fileUri;

    public CourseFilesListItem() {
    }

    public CourseFilesListItem(String fileName, String fileUri) {
        this.fileName = fileName;
        this.fileUri = fileUri;
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


}
