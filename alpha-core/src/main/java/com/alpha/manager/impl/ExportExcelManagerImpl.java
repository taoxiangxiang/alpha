package com.alpha.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.ExportExcelManager;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/21.
 */
@Component("exportExcelManager")
public class ExportExcelManagerImpl implements ExportExcelManager {

    private final Logger logger = LoggerFactory.getLogger(ExportExcelManagerImpl.class);

    public XSSFWorkbook exportExcel(List<String> titleNameList, List<String> fieldNameList, List dataList) {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet();
        XSSFRow title = sheet.createRow(0);
        XSSFCellStyle titleStyle = book.createCellStyle();
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//设置居中
        XSSFFont font = book.createFont();
        font.setBold(true);//设置加粗
        titleStyle.setFont(font);
        if (titleNameList == null) return book;
        for (int i = 0; i < titleNameList.size(); i++) {
            XSSFCell mainName = title.createCell(i);
            mainName.setCellValue(titleNameList.get(i));
            mainName.setCellStyle(titleStyle);
        }
        int i = 1;
        for(Object o :dataList){
            //添加一行
            XSSFRow row = sheet.createRow(i);
            i++;
            object2Row(fieldNameList, o, row);
        }

        return book;
    }

    private void object2Row(List<String> fieldNameList, Object o, XSSFRow row) {
        if (fieldNameList == null) return;
        for(int i = 0; i < fieldNameList.size(); i++) {
            Object object = getFieldValueByName(fieldNameList.get(i), o);
            String value = "";
            if (object == null) {
                value = "";
            } else if (object instanceof Date) {
                value = CalendarUtil.toString((Date)object, CalendarUtil.TIME_PATTERN);
            } else if (object instanceof Boolean) {
                if (((Boolean) object)) {
                    value = "是";
                } else {
                    value = "否";
                }
            } else {
                value = String.valueOf(object);
            }
//            String value = (object == null ? "" : ((object instanceof Date) ? CalendarUtil.toString((Date)object, CalendarUtil.TIME_PATTERN) : String.valueOf(object)));
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(value);
        }
    }

    private Object getFieldValueByName(String filedName, Object o) {
        try {
            String[] filedNameArray = filedName.split("\\.");
            for (String innerFieldName : filedNameArray) {
                Class clazz = o.getClass();
                PropertyDescriptor pd = new PropertyDescriptor(innerFieldName, clazz);
                Method method = pd.getReadMethod();
                o = method.invoke(o);
            }
            return o;
        } catch (Exception e) {
            logger.error("ExportExcelManagerImpl catch exception, no such getter method，filedName＝" + filedName + ",o=" + JSON.toJSONString(o), e);
            return null;
        }
    }
}
