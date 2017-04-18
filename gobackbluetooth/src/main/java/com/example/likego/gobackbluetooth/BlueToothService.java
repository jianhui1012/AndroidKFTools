package com.example.likego.gobackbluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 蓝牙服务类
 */
public class BlueToothService extends Service {

    //打印日志TAG
    private static final String TAG = "BLUETOOTHSERVICE";

    /**************
     * service 命令
     *********/
    static final int CMD_STOP_SERVICE = 0x01;// 停止服务
    static final int CMD_SEND_DATA = 0x02;// 发送数据
    static final int CMD_SYSTEM_EXIT = 0x03;// 退出程序
    static final int CMD_SHOW_TOAST = 0x04;// 界面上显示toast
    static final int CMD_START_SERVICE = 0x05;// 开始连接
    static final int CMD_RETURN_DATA = 0x06;// 返回数据
    static final int CMD_STATE_CHANGE = 0x07;// 状态改变
    static final int CMD_RESTART_SERVICE = 0x08;// 重新连接

    //连接中，未连接，已连接-0，1，2
    public static final int CONNECTING = 0;
    public static final int NOTCONNECT = 1;
    public static final int CONNECTED = 2;

    public static final String ActivityInterFilter = "com.example.likego.gobackbluetooth.activity";


    //远程设备蓝牙模块的UUID
    private static final UUID COMMON_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private HandleActivityReceiver handleActivityReceiver = null;
    private int mState;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private InputStream inStream = null;
    private BluetoothAdapter mAdapter;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //注册接受广播接收器
        if (null != handleActivityReceiver) {
            handleActivityReceiver = new HandleActivityReceiver();
            registerReceiver(handleActivityReceiver, new IntentFilter(ActivityInterFilter));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != handleActivityReceiver)
            unregisterReceiver(handleActivityReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //处理Activity信息的广播接收器
    private class HandleActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ActivityInterFilter)) {
                int cmd = intent.getIntExtra("cmd", -1);// 获取Extra信息
                if (cmd == CMD_STOP_SERVICE) {// 停止服务

                }

                if (cmd == CMD_SEND_DATA) {// 发送数据
                    byte[] value = intent.getByteArrayExtra("wirtedata");

                }
                if (cmd == CMD_START_SERVICE)// 开启连接服务
                {
                    String address = intent.getStringExtra("address");
                    mAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (null != mAdapter) {
                        mAdapter.cancelDiscovery();
                    }

                }
            }
        }
    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                            COMMON_UUID);
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(
                            COMMON_UUID);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
            mState = CONNECTING;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }

                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BlueToothService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            mState = CONNECTED;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (mState == CONNECTED) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);

                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity

            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice, String mSocketType) {

    }

    public synchronized void connect(BluetoothDevice device, boolean secure) {
        Log.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        // Update UI title

    }

    private synchronized void setState(int state) {
        Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        sendConnectionState(state);
    }

    private void sendConnectionState(int state) {

    }
}
