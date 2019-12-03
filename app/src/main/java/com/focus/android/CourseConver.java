package com.focus.android;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 将html字符串转化为课程数组的工具类（依赖jsoup）
 * 只能转化一种格式字符串
 */
public class CourseConver {
    public Course[] getCourses(String coursehtml){
        //将原字符串dom对象
        Document doc = Jsoup.parseBodyFragment(coursehtml);
        //根据class属性获取所有div
        Elements elements = doc.getElementsByClass("kbcontent");
        List<Course> courselist = new ArrayList<Course>();
        //遍历所有divElement element: elements
        for (int i = 0; i < elements.size(); i++) {
            //获取id值
            String id = elements.get(i).id();
            String[] idarr = id.split("-");
            //div中的文本内容
            String text = elements.get(i).text();
            //过滤空课
            if (text.indexOf(" ")<=0){ continue; }
            //div中的html内容(去除换行符)
            String html = elements.get(i).html().replaceAll("\\n","");
            //根据"--------------------- "切割(同一个id的不同课程)
            String[] allarr = html.split("---------------------");
            for(String s : allarr){
                //给course赋值
                Course course = new Course();
                course.setWeek_day(Integer.parseInt(idarr[1]));
                course.setSection_number(i/7+1);
                //如果以<br>开头，则删除该<br>
                if (s.indexOf("<br>") == 0){
                    s = s.substring(4);
                }
                //根据"<br>"切割
                String[] arr = s.split("<br>");
                int j = 0;
                course.setCourse_number(arr[j++]);
                course.setCourse_name(arr[j++]);
                while (j < arr.length) {   //可能为空的字段
                    String value = arr[j].replaceAll("<.*\">", "").replaceAll("</.*>","");  //获取html中的text
                    if (arr[j].indexOf("<font title=\"老师\">") >= 0)
                        course.setCourse_teacher(value);
                    else if (arr[j].indexOf("<font title=\"周次(节次)\">") >= 0)
                        course.setWeek(this.weektran(value));
                    else if (arr[j].indexOf("<font title=\"教室\">") >= 0)
                        course.setCourse_address(value);
                    j++;
                }
                courselist.add(course);
            }
        }
        //将list转为数组
        Course[] courses = new Course[courselist.size()];
        courselist.toArray(courses);
        return courses;
    }

    //将"1-4,6-11(周)"类似的字符串转换为int数组
    private int[] weektran(String s) {
        List<Integer> list = new ArrayList<Integer>();
        //去掉"(周)"
        String week = s.replaceAll("\\(周\\)", "");
        //通过","分割
        String[] arr = week.split(",");
        for (String str : arr){
            //通过"-"分割为(可能只有一个数字)
            String[] split = str.split("-");
            try {
                if (split.length == 2) {   //两个数字,例如 1-4
                    int begin = Integer.parseInt(split[0]);
                    int end = Integer.parseInt(split[1]);
                    for (int i = begin; i <= end; i++) {
                        list.add(i);
                    }
                }else if (split.length == 1){   //一个数字，例如12
                    list.add(Integer.parseInt(split[0]));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //list转Integer[]
        Integer[] arrinteger = new Integer[list.size()];
        list.toArray(arrinteger);
        //拆箱
        int[] arrint = new int[list.size()];
        for (int i = 0; i < arrinteger.length; i++) {
            arrint[i] = arrinteger[i];
        }
        return arrint;
    }
}
