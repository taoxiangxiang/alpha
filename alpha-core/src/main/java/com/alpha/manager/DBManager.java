package com.alpha.manager;

/**
 * Created by taoxiang on 2017/5/14.
 */
public interface DBManager {

    boolean dbBackup(String savePath);

    String dbRecover(String savePath);
}
