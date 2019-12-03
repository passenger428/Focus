package com.focus.android;

import java.util.Arrays;

/**
 * 存在课程数据的bean对象
 */
public class Course {
    private String course_name;    //课程名
    private String course_number;  //课程编号
    private String course_teacher; //老师
    private String course_address; //教室
    private int week_day;          //星期几
    private int section_number;    //某天的第几节课（1-5）
    private int []week;            //该课程在第几周上（例1-4,6-12(周)）
    public static int numOfcourse;

    //构造函数
    public Course() {
    }

    public Course(String course_name, String course_number, String course_teacher, String course_address, int week_day, int section_number, int[] week) {
        this.course_name = course_name;
        this.course_number = course_number;
        this.course_teacher = course_teacher;
        this.course_address = course_address;
        this.week_day = week_day;
        this.section_number = section_number;
        this.week = week;
    }

    //get，set方法
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public String getCourse_teacher() {
        return course_teacher;
    }

    public void setCourse_teacher(String course_teacher) {
        this.course_teacher = course_teacher;
    }

    public String getCourse_address() {
        return course_address;
    }

    public void setCourse_address(String course_address) {
        this.course_address = course_address;
    }

    public int getWeek_day() {
        return week_day;
    }

    public void setWeek_day(int week_day) {
        this.week_day = week_day;
    }

    public int getSection_number() {
        return section_number;
    }

    public void setSection_number(int section_number) {
        this.section_number = section_number;
    }

    public int[] getWeek() {
        return week;
    }

    public void setWeek(int[] week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_name='" + course_name + '\'' +
                ", course_number='" + course_number + '\'' +
                ", course_teacher='" + course_teacher + '\'' +
                ", course_address='" + course_address + '\'' +
                ", week_day=" + week_day +
                ", section_number=" + section_number +
                ", week=" + Arrays.toString(week) +
                '}';
    }
}
