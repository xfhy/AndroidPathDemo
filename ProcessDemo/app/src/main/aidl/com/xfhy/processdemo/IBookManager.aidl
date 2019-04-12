// IBookManager.aidl
package com.xfhy.processdemo;

//虽然位于相同包,但还是需要导包
import com.xfhy.processdemo.Book;
import com.xfhy.processdemo.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
