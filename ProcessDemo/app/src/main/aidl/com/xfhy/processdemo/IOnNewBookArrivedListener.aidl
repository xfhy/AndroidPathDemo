// IOnNewBookArrivedListener.aidl
package com.xfhy.processdemo;

// Declare any non-default types here with import statements
//虽然位于相同包,但还是需要导包
import com.xfhy.processdemo.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
