package com.focus.android;

public class Course {
    public String course_name;
    public String course_number;
    public String course_teacher;
    public String course_address;
    public int week_day;
    public int section_number;
    public int []week;
    public static int numOfcourse;
    //构造函数
    Course(String Course_name,String Course_number,String Course_teacher,String Course_address,int Week_day,int Section_number,int[] Week){
        course_name = Course_name;
        course_number = Course_number;
        course_teacher = Course_teacher;
        course_address = Course_address;
        week_day = Week_day;
        section_number = Section_number;
        week = Week;
    }
    Course(){

    }
    //数据解析函数
    public static Course[] getCourses(String Information){
        Course []courses = new Course[50];
        for (int i = 0;i<50;i++){
            courses[i] = new Course();
        }
        courses[0] = new Course("计算机组成原理","19040148a-1","张静B",
                "东区教学楼3-107",1,1,new int[]{1,2,3,4,6,7,8,9,10,11,12,13,14});
        courses[1] = new Course("Windows系统编程","19040156b-1","景国良",
                "东区教学楼3-207",2,1,new int[]{2,3,4,6,7,8,9,10,11});
        courses[2] = new Course("计算机组成原理","19040148a-1","张静B",
                "东区教学楼3-107",3,1,new int[]{1,2,3,4,6,7,8,9,10,11,12,13,14});
        courses[3] = new Course("计算机网络","19040149a-1","滕玮",
                "东区教学楼3-107",4,1,new int[]{2,3,4,6,7,8,9,10,11,12,13,14});
        courses[4] = new Course("计算机组成原理","19040148a-1","张静B",
                "东区教学楼3-105",5,1,new int[]{1,2,3,4,6,7,8,9,10,11,12,13,14});
        courses[5] = new Course("数据库系统基础","19040150a-1","段先华",
                "东区教学楼3-205",1,2,new int[]{1,2,3,4,6,7,8,9,10,11,12,13,14});
        courses[6] = new Course("计算机专业英语","19040163b-1","王丽娟",
                "东区教学楼3-107",2,2,new int[]{1,2,3,4,6,7,8,9,10,11,12,13,14});
        courses[7] = new Course("数据库系统基础","19040150a-1","段先华",
                "东区教学楼3-205",3,2,new int[]{1,2,3,4,6,7,8,9,10,11,12,13,14});
        courses[8] = new Course("计算方法","19040160b-1","高尚",
                "东区综合楼D-409",4,2,new int[]{6,8});
        courses[9] = new Course("数据库系统基础","19040150a-1","段先华",
                "东区教学楼3-105",5,2,new int[]{9,10,11,12,13,14});
        courses[10] = new Course("Windows系统编程","19040156b-1","景国良",
                "东区实验楼11-387",5,2,new int[]{1,2,3,4,6});
        courses[11] = new Course("信息安全技术","19040162b-1","朱文正",
                "东区综合楼D-401",3,3,new int[]{2,3,4,6,7,8,9,10,11,12,13,14});
        numOfcourse = 12;
        return courses;
    }
}
