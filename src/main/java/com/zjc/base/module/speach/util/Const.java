package com.zjc.base.module.speach.util;

public interface Const {

    /* 下面2个是鉴权信息 ,具体参数在sendStartFrame() 方法内 */
    int APPID = 25580331;

    String APPKEY = "kYUS3g4wS8luZkEPsIyDKr5C";

    String SECRETKEY = "vUP0zduOG6rZfIS26zCRARemCHcSMId7";

    /* dev_pid 是语言模型 ， 可以修改为其它语言模型测试，如远场普通话 19362*/
    int DEV_PID = 15372;

    /* 可以改为wss:// */
    String URI = "ws://153.3.236.16/realtime_asr";
}
