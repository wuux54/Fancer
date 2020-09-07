package com.read.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.read.utils.StringUtils;

import java.util.List;

/**
 * 收藏的书籍
 */
@Entity
public class CollBookBean implements Parcelable {

    public static final int STATUS_UNCACHE = 0; //未缓存
    public static final int STATUS_CACHING = 1; //正在缓存
    public static final int STATUS_CACHED = 2;  //已经缓存
    /**
     * _id : 53663ae356bdc93e49004474
     * title : 逍遥派
     * author : 白马出淤泥
     * shortIntro : 金庸武侠中有不少的神秘高手，书中或提起名字，或不曾提起，总之他们要么留下了绝世秘笈，要么就名震武林。 独孤九剑的创始者，独孤求败，他真的只创出九剑吗？ 残本葵花...
     * cover : /cover/149273897447137
     * hasCp : true
     * latelyFollower : 60213
     * retentionRatio : 22.87
     * updated : 2017-05-07T18:24:34.720Z
     * <p>
     * chaptersCount : 1660
     * lastChapter : 第1659章 朱长老
     */
    @PrimaryKey
    private String _id; // 本地书籍中，path 的 md5 值作为本地书籍的 id
    private String title;
    private String author;
    private String shortIntro;
    private String cover; // 在本地书籍中，该字段作为本地文件的路径
    private boolean hasCp;
    private int latelyFollower;
    private double retentionRatio;
    //最新更新日期
    private String updated;
    //最近阅读日期
    private String lastRead;
    private int chaptersCount;
    private String lastChapter;
    //是否更新或未阅读
    private boolean isUpdate = true;
    //是否是本地文件
    private boolean isLocal = false;

    //    @ToMany(referencedJoinProperty = "bookId")
    private List<BookChapterBean> bookChapterList;
//    /** Used to resolve relations */
//    @Generated(hash = 2040040024)
//    private transient DaoSession daoSession;
//    /** Used for active entity operations. */
//    @Generated(hash = 1552163441)
//    private transient CollBookBeanDao myDao;

    public CollBookBean(String _id, String title, String author, String shortIntro, String cover,
                        boolean hasCp, int latelyFollower, double retentionRatio, String updated, String lastRead,
                        int chaptersCount, String lastChapter, boolean isUpdate, boolean isLocal) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.shortIntro = shortIntro;
        this.cover = cover;
        this.hasCp = hasCp;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
        this.updated = updated;
        this.lastRead = lastRead;
        this.chaptersCount = chaptersCount;
        this.lastChapter = lastChapter;
        this.isUpdate = isUpdate;
        this.isLocal = isLocal;
    }

    public CollBookBean() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return StringUtils.convertCC(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return StringUtils.convertCC(author);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortIntro() {
        return StringUtils.convertCC(shortIntro);
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return StringUtils.convertCC(cover);
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public double getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return StringUtils.convertCC(updated);
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getLastChapter() {
        return StringUtils.convertCC(lastChapter);
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean getHasCp() {
        return this.hasCp;
    }

    public boolean getIsUpdate() {
        return this.isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getLastRead() {
        return StringUtils.convertCC(lastRead);
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public void setBookChapters(List<BookChapterBean> beans) {
        bookChapterList = beans;
        for (BookChapterBean bean : bookChapterList) {
            bean.setBookId(get_id());
        }
    }

    public List<BookChapterBean> getBookChapters() {
        return bookChapterList;
    }


    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.shortIntro);
        dest.writeString(this.cover);
        dest.writeByte(this.hasCp ? (byte) 1 : (byte) 0);
        dest.writeInt(this.latelyFollower);
        dest.writeDouble(this.retentionRatio);
        dest.writeString(this.updated);
        dest.writeString(this.lastRead);
        dest.writeInt(this.chaptersCount);
        dest.writeString(this.lastChapter);
        dest.writeByte(this.isUpdate ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
    }

    protected CollBookBean(Parcel in) {
        this._id = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.shortIntro = in.readString();
        this.cover = in.readString();
        this.hasCp = in.readByte() != 0;
        this.latelyFollower = in.readInt();
        this.retentionRatio = in.readDouble();
        this.updated = in.readString();
        this.lastRead = in.readString();
        this.chaptersCount = in.readInt();
        this.lastChapter = in.readString();
        this.isUpdate = in.readByte() != 0;
        this.isLocal = in.readByte() != 0;
    }

    public static final Creator<CollBookBean> CREATOR = new Creator<CollBookBean>() {
        @Override
        public CollBookBean createFromParcel(Parcel source) {
            return new CollBookBean(source);
        }

        @Override
        public CollBookBean[] newArray(int size) {
            return new CollBookBean[size];
        }
    };
}