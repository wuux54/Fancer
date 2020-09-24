package com.read.bean;

import java.util.List;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/9/16
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class ddd {

    /**
     * ok : true
     * mixToc : {"_id":"5c2c76636540b6913e8e99a7","book":"5c2c76636540b6913e8e99a7","chapters":[{"title":"暂无版权","link":"http://www.honeypot.com/xiaoshuo/410/402169/1.html","unreadble":false}],"chaptersUpdated":"2019-05-05T15:40:18.809Z","chaptersCount1":1,"updated":"2019-05-05T15:40:18.809Z"}
     */

    private boolean ok;
    private MixTocBean mixToc;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public MixTocBean getMixToc() {
        return mixToc;
    }

    public void setMixToc(MixTocBean mixToc) {
        this.mixToc = mixToc;
    }

    public static class MixTocBean {
        /**
         * _id : 5c2c76636540b6913e8e99a7
         * book : 5c2c76636540b6913e8e99a7
         * chapters : [{"title":"暂无版权","link":"http://www.honeypot.com/xiaoshuo/410/402169/1.html","unreadble":false}]
         * chaptersUpdated : 2019-05-05T15:40:18.809Z
         * chaptersCount1 : 1
         * updated : 2019-05-05T15:40:18.809Z
         */

        private String _id;
        private String book;
        private String chaptersUpdated;
        private int chaptersCount1;
        private String updated;
        private List<ChaptersBean> chapters;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public String getChaptersUpdated() {
            return chaptersUpdated;
        }

        public void setChaptersUpdated(String chaptersUpdated) {
            this.chaptersUpdated = chaptersUpdated;
        }

        public int getChaptersCount1() {
            return chaptersCount1;
        }

        public void setChaptersCount1(int chaptersCount1) {
            this.chaptersCount1 = chaptersCount1;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public List<ChaptersBean> getChapters() {
            return chapters;
        }

        public void setChapters(List<ChaptersBean> chapters) {
            this.chapters = chapters;
        }

        public static class ChaptersBean {
            /**
             * title : 暂无版权
             * link : http://www.honeypot.com/xiaoshuo/410/402169/1.html
             * unreadble : false
             */

            private String title;
            private String link;
            private boolean unreadble;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public boolean isUnreadble() {
                return unreadble;
            }

            public void setUnreadble(boolean unreadble) {
                this.unreadble = unreadble;
            }
        }
    }
}
