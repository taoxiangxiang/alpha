package com.alpha.constans;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by taoxiang on 2017/5/6.
 */
public class ParamUtil {

    private static boolean vIDNumByRegex(String idNum) {
        String curYear = "" + Calendar.getInstance().get(Calendar.YEAR);
        int y3 = Integer.valueOf(curYear.substring(2, 3));
        int y4 = Integer.valueOf(curYear.substring(3, 4));
        // 43 0103 1973 0 9 30 051 9
        return idNum.matches("^(1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|71|8[1-2])\\d{4}(19\\d{2}|20([0-" + (y3 - 1) + "][0-9]|" + y3 + "[0-" + y4
                + "]))(((0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])))\\d{3}([0-9]|x|X)$");
    }

    private static final int ID_LENGTH = 17;

    private static boolean vIDNumByCode(String idNum) {
        if (idNum.length() != 18) {
            return false;
        }
        // 系数列表
        int[] ratioArr = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 校验码列表
        char[] checkCodeList = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        // 获取身份证号字符数组
        char[] cIds = idNum.toCharArray();
        // 获取最后一位（身份证校验码）
        char oCode = cIds[ID_LENGTH];
        int[] iIds = new int[ID_LENGTH];
        int idSum = 0;// 身份证号第1-17位与系数之积的和
        int residue = 0;// 余数(用加出来和除以11，看余数是多少？)
        for (int i = 0; i < ID_LENGTH; i++) {
            iIds[i] = cIds[i] - '0';
            idSum += iIds[i] * ratioArr[i];
        }
        residue = idSum % 11;// 取得余数
        return Character.toUpperCase(oCode) == checkCodeList[residue];
    }

    public static boolean validCitizenId(String citizenId) {
        return vIDNumByCode(citizenId) && vIDNumByRegex(citizenId);
    }

    public static boolean validVehicleNO(String vehicleNO) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
        Matcher matcher = pattern.matcher(vehicleNO);
        return matcher.matches();
    }

    public static boolean validMobile(String str) {
        Pattern p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void main(String args[]) {
        String ss = "芜BAQ121";
        System.out.println(validVehicleNO(ss));
    }
}
