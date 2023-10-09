package edu.uncc.inclass08;

// mBinding.textViewCourseHours.setText(grade.ge);
//         mBinding.textViewCourseLetterGrade.setText();
//         mBinding.textViewCourseName.setText();
//         mBinding.textViewCourseNumber.setText();

public class Grade {
    String  CourseLetterGrade, CourseName, CourseNumber;
    Double CourseHours;

    public Grade(Double courseHours, String courseLetterGrade, String courseName, String courseNumber) {
        CourseHours = courseHours;
        CourseLetterGrade = courseLetterGrade;
        CourseName = courseName;
        CourseNumber = courseNumber;
    }

    public Grade() {
    }

    public Double getCourseHours() {
        return CourseHours;
    }

    public void setCourseHours(Double courseHours) {
        CourseHours = courseHours;
    }

    public String getCourseLetterGrade() {
        return CourseLetterGrade;
    }

    public void setCourseLetterGrade(String courseLetterGrade) {
        CourseLetterGrade = courseLetterGrade;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseNumber() {
        return CourseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        CourseNumber = courseNumber;
    }


}
