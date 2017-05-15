package com.alpha.manager.impl;

import com.alpha.manager.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by taoxiang on 2017/5/14.
 */
@Component("dBManager")
public class DBManagerImpl implements DBManager {

    private static final String dbHost = "localhost";
    private static final String dbPort = "3306";
    private static final String dbUser = "root";
    private static final String dbPass = "admin1234";
    private static final String dbName = "carbase";
    private static final Logger logger = LoggerFactory.getLogger(DBManagerImpl.class);

    @Override
    public boolean dbBackup(String savePath) {
        try {
            Runtime runtime = Runtime.getRuntime();
            // -u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字
            Process process = runtime.exec("mysqldump -h " + dbHost + " -P "
                    + dbPort + " -u " + dbUser + " -p" + dbPass + " " + dbName);
            InputStream inputStream = process.getInputStream();// 得到输入流，写成.sql文件
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\r\n");
            }
            s = sb.toString();
            File file = new File(savePath);
            boolean res = file.getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(s.getBytes());
            fileOutputStream.close();
            br.close();
            reader.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            logger.error("DBManager dbBackup catch exception", e);
            return false;
        }

    }

    @Override
    public boolean dbRecover(String savePath) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mysql -h" + dbHost + " -P " + dbPort
                    + " -u " + dbUser + " -p" + dbPass
                    + " --default-character-set=utf8 " + dbName);
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(savePath)));
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                sb.append(str).append("\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
            return true;
        } catch (Exception e) {
            logger.error("DBManager dbRecover catch exception", e);
            return false;
        }
    }
}
