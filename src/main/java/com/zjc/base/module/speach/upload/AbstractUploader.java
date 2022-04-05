package com.zjc.base.module.speach.upload;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.zjc.base.module.speach.util.Const;
import com.zjc.base.module.speach.util.Stat;
import com.zjc.base.module.speach.util.Util;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import okio.ByteString;

import java.io.InputStream;

/**
 * 上传类
 * STEP 2. 连接成功后发送数据
 * STEP 2.1 发送发送开始参数帧
 * STEP 2.2 实时发送音频数据帧
 * STEP 2.4 发送结束帧
 */
@Slf4j
public abstract class AbstractUploader {

    /**
     * 输入的音频流
     */
    InputStream inputStream;

    volatile boolean isClosed = false;

    private Stat stat;

    AbstractUploader(InputStream inputStream, Stat stat) {
        this.inputStream = inputStream;
        this.stat = stat;
    }

    /**
     * WebSocket库建立连接后的回调
     * STEP 2. 连接成功后发送数据
     *
     * @param webSocket WebSocket类
     */
    public void execute(WebSocket webSocket) throws JSONException {
        log.info("begin to send");
        if (isClosed) {
            log.debug("websocket is closed, stop transferring frames ");
            return;
        }
        // 2.1 发送发送开始参数帧
        sendStartFrame(webSocket);
        stat.updateAfterStartFrameTime();

        // 2.2 实时发送音频数据帧
        sendAudioFrames(webSocket);

        if (isClosed) {
            log.debug("websocket is closed, stop transferring frames ");
            return;
        }

        // STEP 2.4 发送结束帧
        sendFinishFrame(webSocket);
        stat.updateAfterFinishFrameTime();
    }

    public Stat getStat() {
        return stat;
    }

    public void setClosed() {
        isClosed = true;
    }


    /**
     * 2.1 发送发送开始参数帧
     *
     * @param webSocket WebSocket 类
     * @throws JSONException Json解析错误
     */
    protected void sendStartFrame(WebSocket webSocket) throws JSONException {
        JSONObject params = new JSONObject();

        params.put("appid", Const.APPID);
        params.put("appkey", Const.APPKEY);

        params.put("dev_pid", Const.DEV_PID);
        params.put("cuid", "self_defined_server_id_like_mac_address");

        params.put("format", "pcm");
        params.put("sample", 16000);

        JSONObject json = new JSONObject();
        json.put("type", "START");
        json.put("data", params);

        log.info("send start FRAME:" + json.toString());
        webSocket.send(json.toString());
    }


    /**
     * STEP 2.2 实时发送音频数据帧
     *
     * @param webSocket WebSocket 类
     */
    protected abstract void sendAudioFrames(WebSocket webSocket);

    /**
     * STEP 2.4 发送结束帧
     *
     * @param webSocket WebSocket类
     * @throws JSONException Json错误
     */
    protected void sendFinishFrame(WebSocket webSocket) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", "FINISH");
        log.info("send FINISH FRAME:" + json.toString());
        webSocket.send(json.toString());
    }

    /**
     * 发送取消帧，websocket连接会立即断开
     *
     * @param webSocket WebSocket类
     * @throws JSONException Json错误
     */
    protected void sendCancelFrame(WebSocket webSocket) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", "CANCEL");
        log.info("send CANCEL FRAME:" + json.toString());
        webSocket.send(json.toString());
    }


    protected int sendBytes(WebSocket webSocket, byte[] buffer) {
        return sendBytes(webSocket, buffer, buffer.length);
    }

    /**
     * 发送二进制帧
     *
     * @param webSocket WebSocket类
     * @param buffer    二进制
     * @param size
     * @return
     */
    protected int sendBytes(WebSocket webSocket, byte[] buffer, int size) {
        if (size > 0) {
            ByteString bytesToSend = ByteString.of(buffer, 0, size);
            webSocket.send(bytesToSend);
            return Util.bytesToTime(size);
        } else if (size == 0) {
            log.debug("read size is 0");
            return 100;
        }
        return 0;
    }
}
