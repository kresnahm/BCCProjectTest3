package com.example.bccprojecttest3;

public class Courses {
    private String courseName, courseMaker, courseType, ImageUrl;

    public Courses(){

    }
    public Courses(String courseName, String courseMaker, String courseType, String imageUrl) {
        this.courseName = courseName;
        this.courseMaker = courseMaker;
        this.courseType = courseType;
        ImageUrl = imageUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseMaker() {
        return courseMaker;
    }

    public void setCourseMaker(String courseMaker) {
        this.courseMaker = courseMaker;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}