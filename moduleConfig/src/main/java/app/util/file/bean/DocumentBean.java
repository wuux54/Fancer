package app.util.file.bean;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/10
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class DocumentBean {
    private String displayName;
    private String mimeType;
    private String path;
    private long size;
    private long modified_date;
    private int doc_type;

    private boolean selected ;

    public DocumentBean(int id, String displayName, String mimeType,
                        String path, long size, long modified_date, int doc_type) {
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.modified_date = modified_date;
        this.doc_type = doc_type;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getModified_date() {
        return modified_date;
    }

    public void setModified_date(long modified_date) {
        this.modified_date = modified_date;
    }

    public int getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(int doc_type) {
        this.doc_type = doc_type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
