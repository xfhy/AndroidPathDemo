package com.xfhy.processdemo.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by feiyang on 2019/4/16 16:02
 * Description :
 */
public class TCPServerService extends Service {

    private static final String TAG = "TCPServerService";
    /**
     * 标记当前Service是否已经销毁
     */
    private boolean mIsServiceDestoryed = false;
    private static final String[] DEFINED_MESSAGES = new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {
        //一进来就开个线程
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听本地8688端口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.e(TAG, "establish tcp server failed,port:8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestoryed) {
                try {
                    //接收客户端的请求   该方法调用之后,会阻塞,直到有客户端连接
                    final Socket client = serverSocket.accept();
                    Log.w(TAG, "accept");

                    //处理客户端的请求
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //用户接收客户端的消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        Log.w(TAG, "欢迎来到聊天室");
        out.println();
        while (!mIsServiceDestoryed) {
            String str = in.readLine();
            Log.w(TAG, "msg from client:" + str);
            if (str == null) {
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(DEFINED_MESSAGES.length);
            String msg = DEFINED_MESSAGES[i];
            out.println(msg);
            Log.w(TAG, "send :" + msg);
        }

        System.out.println("client quit.");
        // 关闭流
        out.close();
        in.close();
        client.close();
    }

}
