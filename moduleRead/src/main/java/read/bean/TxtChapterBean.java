package read.bean;

/**
 * 章节目录所需类
 */

public class TxtChapterBean {

    //章节所属的小说(网络)
    public   String bookId;
    //章节的链接(网络)
    public String link;

    //章节名(共用)
    public  String title;

    //章节内容在文章中的起始位置(本地)
    public long start;
    //章节内容在文章中的终止位置(本地)
    public  long end;

    //所属的下载任务
    private String taskName;

    private boolean unReadable;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String id) {
        this.bookId = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public boolean isUnReadable() {
        return unReadable;
    }

    public void setUnReadable(boolean unReadable) {
        this.unReadable = unReadable;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "TxtChapter{" +
                "title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
