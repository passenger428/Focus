package com.focus.android;

import androidx.annotation.Nullable;

public class Add_class_message {
    private String class_name;
    private String class_teacher;
    private String class_room;
    private String class_time;
    private String class_day;
    private String class_weeks;

    public Add_class_message(String[] class_message_group) {
        class_name = class_message_group[0];
        class_teacher = class_message_group[1];
        class_room = class_message_group[2];
        class_time = class_message_group[3];
        class_day = class_message_group[4];
        class_weeks = class_message_group[5];
    }

    public String gors_Class_name(boolean oredit, @Nullable String new_message) {
        if (new_message == null || new_message.equals("")) {
            oredit = false;
        }
        if (oredit) {
            class_name = new_message;
        }
        return class_name;
    }

    public String gors_Class_teacher(boolean oredit, @Nullable String new_message) {
        if (new_message == null || new_message.equals("")) {
            oredit = false;
        }
        if (oredit) {
            class_teacher = new_message;
        }
        return class_teacher;
    }

    public String gors_Class_room(boolean oredit, @Nullable String new_message) {
        if (new_message == null || new_message.equals("")) {
            oredit = false;
        }
        if (oredit) {
            class_room = new_message;
        }
        return class_room;
    }

    public int gors_Class_time(boolean oredit, @Nullable String new_message) {
        if (new_message == null || new_message.equals("")) {
            oredit = false;
        }
        if (oredit) {
            class_time = new_message;
        }
        if(class_time.equals("") || class_time == null){
            class_time = "1";
        }
        return Integer.parseInt(class_time);
    }

    public int gors_Class_day(boolean oredit, @Nullable String new_message) {
        if (new_message == null || new_message.equals("")) {
            oredit = false;
        }
        if (oredit) {
            class_day = new_message;
        }
        if(class_day.equals("") || class_day == null){
            class_day = "1";
        }
        return Integer.parseInt(class_day);
    }

    public String gors_Class_weeks(boolean oredit, @Nullable String new_message) {
        if (new_message == null || new_message.equals("")) {
            oredit = false;
        }
        if (oredit) {
            class_weeks = new_message;
        }
        return class_weeks;
    }


}
