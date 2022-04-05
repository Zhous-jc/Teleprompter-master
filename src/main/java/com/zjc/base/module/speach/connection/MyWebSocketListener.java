package com.zjc.base.module.speach.connection;

import cn.hutool.json.JSONException;
import com.zjc.base.module.speach.download.Result;
import com.zjc.base.module.speach.download.SimpleDownloader;
import com.zjc.base.module.speach.upload.AbstractUploader;
import com.zjc.base.module.speach.util.Stat;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MyWebSocketListener extends WebSocketListener {

    private AbstractUploader uploader;

    private SimpleDownloader downloader;

    private AtomicBoolean isClosed;

    private Stat stat;

    /**
     * @param uploader   发送数据类，上传参数和音频内容
     * @param downloader 接收数据类，获取识别结果
     */
    public MyWebSocketListener(AbstractUploader uploader, SimpleDownloader downloader) {
        isClosed = new AtomicBoolean(false); // 是否
        stat = uploader.getStat(); //一些统计数据

        this.uploader = uploader;
        this.downloader = downloader;
        stat.updateBeforeConnectTime();
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        stat.updateOnOpenTime();

        // 这里千万别阻塞，包括这个类其它回调
        new Thread(() -> {
            try {
                uploader.execute(webSocket);
            } catch (JSONException e) {
                log.info("upload " + e);
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        // 这里千万别阻塞，包括这个类其它回调
        Result result;
        try {
            // 将json解析为Result类
            result = new Result(text);
        } catch (JSONException e) {
            log.info("receive json parse error: " + e);
            e.printStackTrace();
            return;
        }
        if (result.isHeartBeat()) {
            //log.info("receive heartbeat: " + text);
        } else {
            //log.info("receive text: " + text);
        }
        if (result.isFin()) {
            stat.addResult(result);
        }
        downloader.onMessage(result);

    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        // 这里千万别阻塞，包括这个类其它回调
        log.info("websocket closed: " + code + " | " + reason);
        log.info(stat.toReportString());
        setClosed();
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        // 这里千万别阻塞，包括这个类其它回调
        log.info("websocket event closing :" + code + " | " + reason);
        webSocket.close(1000, "");
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        // 这里千万别阻塞，包括这个类其它回调
        log.info("websocket failure :" + t);
        setClosed();
    }

    private void setClosed() {
        isClosed.set(true);
        uploader.setClosed();
    }


    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        super.onMessage(webSocket, bytes);
        log.info("receive binary unexpected: " + bytes.size());
        // never happen
    }

    public boolean isClosed() {
        return isClosed.get();
    }
}
