package fat.cc.study.bean;

import java.util.Date;

public class NoticeListItem {

    private String title;

    private String content;

    private String publisher;

    private Date publishDate;


    public NoticeListItem(String title, String content, String publisher, Date publishDate) {
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.publishDate = publishDate;
    }

    public NoticeListItem() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
