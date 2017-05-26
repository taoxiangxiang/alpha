package com.alpha.manager;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * Created by taoxiang on 2017/5/21.
 */
public interface ExportExcelManager {

    XSSFWorkbook exportExcel(List<String> titleNameList, List<String> fieldNameList, List dataList);
}
