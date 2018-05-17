package com.example.user.jolp_v0;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class DayInfo {

    int importance;
    int color;
    CalendarDay cal;

    public DayInfo(int importance, int color, int year, int month, int day) {
        this.importance = importance;
        this.color = color;
        this.cal = CalendarDay.from(year,month-1,day);
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public CalendarDay getCal() {
        return cal;
    }

    public void setCal(CalendarDay cal) {
        this.cal = cal;
    }
}
