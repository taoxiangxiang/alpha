package com.alpha.domain;


import java.util.List;

import java.util.ArrayList;

/**
 * Created by taoxiang on 2017/4/15.
 */
public class CommonDO {

    public static void main(String args[]) {
        String model = "VehicleAccidentDO";
        String modelMap = "VehicleAccidentMap";
        String table = "vehicle_accident";
        List<String> property = new ArrayList<String>();
        property.add("vehicleNO");
        property.add("team");
        property.add("accidentDate");
        property.add("driverId");
        property.add("driverName");
        property.add("accidentAddress");
        property.add("accidentDesc");
        property.add("result");
        property.add("dealDesc");
        property.add("liablePerson");
        property.add("maintainAddress");
        property.add("liableDesc");
        property.add("money");
        property.add("file");
        property.add("remark");
        property.add("attribute");
//        property.add("attribute");
//        property.add("applicationDepartment");
//        property.add("applicantPhone");
//        property.add("personNumber");
//        property.add("startPlace");
//        property.add("endPlace");
//        property.add("endPlaceType");
//        property.add("contacts");
//        property.add("contactsPhone");
//        property.add("remark");
//        property.add("file");
//        property.add("status");
//        property.add("attribute");
        property.add("gmtCreate");
        property.add("gmtModified");
        List<String> column = new ArrayList<String>();
        column.add("vehicle_no");
        column.add("team");
        column.add("accident_date");
        column.add("driver_id");
        column.add("driver_name");
        column.add("accident_address");
        column.add("accident_desc");
        column.add("result");
        column.add("deal_desc");
        column.add("liable_person");
        column.add("maintain_address");
        column.add("liable_desc");
        column.add("money");
        column.add("file");
        column.add("remark");
        column.add("attribute");
//        column.add("applicant");
//        column.add("application_department");
//        column.add("applicant_phone");
//        column.add("person_number");
//        column.add("start_place");
//        column.add("end_place");
//        column.add("end_place_type");
//        column.add("contacts");
//        column.add("contacts_phone");
//        column.add("remark");
//        column.add("file");
//        column.add("status");
//        column.add("attribute");
        column.add("gmt_create");
        column.add("gmt_modified");
        System.out.println("<resultMap id=\"" + modelMap + "\" type=\"" + model + "\">");
        System.out.println("<id property=\"id\" column=\"id\" />");
        for (int i = 0; i < property.size();i++) {
            System.out.println("<result property=\"" + property.get(i) + "\" column=\"" + column.get(i) + "\" />");
        }

        System.out.println("</resultMap>");
        System.out.println();
        System.out.println("<insert id=\"insert\" parameterType=\"" + model + "\">");
        System.out.print("insert into "+table+" (id");
        for (String str : column) {
            System.out.print("," + str);
        }
        System.out.println(")");
        System.out.print("VALUES (null");
        for (String str : property) {
            if (str.equals("gmtCreate") || str.equals("gmtModified")) {
                str = "now()";
                System.out.print(",now()");
            } else {
                System.out.print(",#{" + str + "}");
            }
        }
        System.out.println(")");
        System.out.println("</insert>");
        System.out.println();
        System.out.println("<update id=\"update\" parameterType=\"" + model + "\">");
        System.out.println("update " + table);
        System.out.println("set");
        for (int i = 0; i< property.size()-2;i++) {
            System.out.println("<if test=\"" + property.get(i) +"!=null\">" + column.get(i) + "=#{" + property.get(i) +"},</if>");
        }
        System.out.println("gmt_modified = now()");
        System.out.println("where id = #{id}");
        System.out.println("</update>");
    }


    //    <resultMap id="DepartmentMap" type="DepartmentDO">
//        <id property="id" column="id" />
//        <result property="departmentName" column="department_name" />
//        <result property="belongDepartmentName" column="belong_department_name" />
//        <result property="departmentContact" column="department_contact" />
//        <result property="departmentContactPhone" column="department_contact_phone" />
//        <result property="departmentAddress" column="department_address" />
//        <result property="status" column="status" />
//        <result property="attribute" column="attribute" />
//        <result property="gmtCreate" column="gmt_create" />
//        <result property="gmtModified" column="gmt_modified" />
//    </resultMap>
//
//    <insert id="insert" parameterType="DepartmentDO">
//    insert into department (id,department_name,belong_department_name,department_contact,
//                            department_contact_phone,department_address,status,attribute,gmt_create,gmt_modified)
//    VALUES (null,#{departmentName},#{belongDepartmentName},#{departmentContact},#{departmentContactPhone},
//            #{departmentAddress},#{status},#{attribute},now(),now())
//    </insert>

//    <update id="update" parameterType="DepartmentDO">
//    update department
//    set
//            <if test="departmentName!=null">department_name=#{departmentName},</if>
//        <if test="belongDepartmentName!=null">belong_department_name=#{belongDepartmentName},</if>
//        <if test="departmentContact!=null">department_contact=#{departmentContact},</if>
//        <if test="departmentContactPhone!=null">department_contact_phone=#{departmentContactPhone},</if>
//        <if test="departmentAddress!=null">department_address=#{departmentAddress},</if>
//        <if test="status!=null">status=#{status},</if>
//        <if test="attribute!=null">attribute=#{attribute},</if>
//    gmt_modified = now()
//    where id = #{id}
//    </update>

}
