// IBinderPool.aidl
package com.xfhy.processdemo;

// Declare any non-default types here with import statements

interface IBinderPool {

    //根据binderCode查询该使用哪个 IBinder
    IBinder queryBinder(int binderCode);

}
