package read.callback;

import read.bean.BookBean;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/9/15
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public interface ItfBookEventCallback {

//    /**
//     * 页面内点击
//     * @param type
//     * @param view
//     */
//    void viewClick(int type, View view);

    /**
     * 阅读记录返回监听
     * @param id
     * @return
     */
    BookBean getReadRecord(String id);

    /**
     * 阅读存储
     *
     * @param mBookBean 书籍
     */
    void saveBookBean(BookBean mBookBean);

    /**
     * 章节缓存
     */
    void downLoadCache();


}
