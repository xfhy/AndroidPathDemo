// ISecurityCenter.aidl
package com.xfhy.processdemo;

// Declare any non-default types here with import statements
//接口提供加解密 功能
interface ISecurityCenter {

    String encrypt(String content);
    String decrypt(String password);
}
