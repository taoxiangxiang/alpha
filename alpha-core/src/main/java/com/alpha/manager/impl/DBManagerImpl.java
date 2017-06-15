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
            InputStream in = process.getInputStream();
            InputStreamReader input = new InputStreamReader(in,"utf8");
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            BufferedReader br = new BufferedReader(input);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            FileOutputStream fout = new FileOutputStream(savePath);
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
            writer.write(outStr);
            writer.flush();
            in.close();
            input.close();
            br.close();
            writer.close();
            fout.close();
            logger.info("MYSQL备份成功");
            return true;
        } catch (Exception e) {
            logger.error("DBManager dbBackup catch exception", e);
            return false;
        }

    }

    @Override
    public String dbRecover(String savePath) {
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec("mysql -h" + dbHost + " -P " + dbPort
                    + " -u " + dbUser + " -p" + dbPass
                    + " --default-character-set=utf8 " + dbName);
        } catch (IOException e) {
            logger.error("DBManager dbRecover catch exception", e);
            return "IO异常，请稍后重试";
        }
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(new FileInputStream(savePath), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("DBManager dbRecover catch exception", e);
            return "系统异常，请稍后重试";
        } catch (FileNotFoundException e) {
            logger.error("DBManager dbRecover catch exception", e);
            return "文件不存在，请选择正确的日期";
        }
        try {
            BufferedReader br = new BufferedReader(in);
            String inStr = null;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            OutputStream out = process.getOutputStream();
            OutputStreamWriter writer = null;
            writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(outStr);
            writer.flush();
            out.flush();
            out.close();
            br.close();
            writer.close();
        } catch (IOException e) {
            logger.error("DBManager dbRecover catch exception", e);
            return "系统异常，请稍后重试";
        }
        return "ok";
    }
}
