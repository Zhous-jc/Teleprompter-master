package com.zjc.base.module.speach.download;


import com.zjc.base.module.speach.util.Stat;
import lombok.extern.slf4j.Slf4j;

/**
 * STEP 2.3 库接收识别结果
 */
@Slf4j
public class SimpleDownloader {

    public void onMessage(Result result) {
        if (!result.isHeartBeat()) {
            log.info(Stat.formatResult(result).toString());
        }
    }
}
