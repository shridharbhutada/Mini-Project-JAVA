import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

public class Student {
    private String firstName;
    private String lastName;
    private String id;
    private List<String> courses;
    private BigDecimal tuition;
    private Scanner keyboard = new Scanner(System.in);
    private Student(String fName, String lastName) {
        this.firstName = fName;
        this.lastName = lastName;
    }

    private Student() {

    }

    //Getters and Setters
    private BigDecimal getTuition() { return tuition; }

    private void setTuition(BigDecimal money) {
        this.tuition = money;
    }

    private String getName() { return firstName + " " +  lastName; }


    private void setFirstName(String firstName) { this.firstName = firstName; }

    private void setLastName(String lastName) { this.lastName = lastName; }

    private String getId() { return id; }

    private void setId(String id) { this.id = id; }											//this Constructor
				
    private List<String> getCourses() { return courses; }

    private void setCourses(List<String> courses) { this.courses = courses; }

    /**
     * Creates a id using a number from 1 - 4 given by the user and a random string of length 4.
     */
    private void makeID()
    {
        String grade;
        boolean checked = false;

        while (!checked)
        {
            System.out.println("Enter your school year 1. UG, 2. PG, 3.Sciencetist and 4. Ph D/Other ");
            grade = keyboard.nextLine();
            if (grade.length() == 1 && Integer.parseInt(grade) > 0 && Integer.parseInt(grade) < 5)
            {
                setId(grade.concat(randomString()));
                checked = true;
            } else {
                System.out.println("The input you enter is incorrect please try again");
            }
        }

    }

    /**
     * Returns a randomly generated 4 character string that will combined with a number entered by the user to make the student id.
     *
     * @return The four character random string
     */
    private String randomString()
    {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        int great = AB.length();
        int temp;
        String codeword = "";
        for (int i = 0; i < 4; i++)
        {
            temp = (int) (random.nextFloat() * great);
            codeword = codeword.concat(Character.toString(AB.charAt(temp)));
        }
        return codeword;
    }

    /**
     * A payment system that allows the user to make multiple payments on their tuition
     */
    private void payForCourses()
    {
        String answer;
        BigDecimal payment;
        BigDecimal moneyLeftOver;

        while (getTuition().compareTo(BigDecimal.ZERO) > 0)
        {
            System.out.println("Your current balance is $" + getTuition());
            System.out.println("Do you want pay off you balance right now");

            answer = keyboard.nextLine();

            if (answer.toLowerCase().equals("yes"))
            {
                System.out.println("How much would you like to pay right now");

                if (keyboard.hasNextBigDecimal())
                {
                    payment = keyboard.nextBigDecimal();
                    payment = payment.setScale(2, RoundingMode.HALF_UP);
                    keyboard.nextLine();
                    if ((payment.compareTo(BigDecimal.ZERO) > 0) && payment.compareTo(getTuition()) <= 0)
                    {
                        moneyLeftOver = getTuition().subtract(payment);
                        setTuition(moneyLeftOver);
                    } else if (payment.compareTo(getTuition()) > 0) {
                        System.out.println("The value you have given is greater than your tuition");
                    } else if (payment.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("You gave an negative number as a payment value. Please enter a positive value next time");
                    }

                } else {
                    keyboard.nextLine();
                    System.out.println("You entered the wrong input so please input a number next time.");
                }

            } else if (answer.toLowerCase().equals("no")) {
                break;
            } else {
                System.out.println("You gave the wrong input either enter yes or no");
            }
        }
    }

    /**
     * Gives the student the class they entered the corresponding number for a class
     *
     **/
    private void chooseCourses(List<String> classes, int courseNumber)
    {
        switch (courseNumber)
        {
            case 1:
                if (checkDups(classes, "Python 101"))
                    classes.add("Python 101");
                break;
            case 2:
                if (checkDups(classes, "Data Science 101"))
                    classes.add("Data Science 101");
                break;
            case 3:
                if (checkDups(classes, "Maths 101"))
                    classes.add("Maths 101");
                break;
            case 4:
                if (checkDups(classes, "CCNA 101"))
                    classes.add("CCNA 101");
                break;
            case 5:
                if (checkDups(classes, "Computer Science 101"))
                    classes.add("Computer Science 101");
                break;
            default:
                System.out.println("You gave the wrong input");
                break;
        }
    }

    /**
     * Allows the user to add classes keeping track of classes they already added and setting the new tuition the user has.
     */
    private void addCourses()
    {
        List<String> classes = new LinkedList<>();
        setCourses(classes);

        String answer;
        int nextCourse;
        BigDecimal size;
        BigDecimal cost;

        System.out.println("Do you want to add any courses? yes or no");
        answer = keyboard.nextLine();
        while (!answer.toLowerCase().equals("no"))
        {
            if (answer.toLowerCase().equals("yes"))
            {
                System.out.println("Which classes would you like to add now? Please choose from the following selection. " +
                        "Choose the number for the courses");
                System.out.println("1. Python 101");
                System.out.println("2. Data Science 101");
                System.out.println("3. Maths 101");
                System.out.println("4. CCNA 101");
                System.out.println("5. Computer Science 101");

                if (keyboard.hasNextInt())
                {
                    nextCourse = keyboard.nextInt();
                    keyboard.nextLine();
                    chooseCourses(classes, nextCourse);

                } else {
                    System.out.println("You put in the wrong input: Enter a number 1 - 5 for each class");
                    keyboard.nextLine();
                }

            } else {
                System.out.println("You put in the wrong input: Enter either yes or no next time");
            }

            System.out.println("Do you want to add any more courses?");
            answer = keyboard.nextLine();
        }
        size = new BigDecimal(classes.size());
        cost = new BigDecimal(600);

        cost = cost.multiply(size);
        setTuition(cost);
    }

    /**
     * Make sure every class in a given list in unique.
    
     */
    private boolean checkDups(List<String> list, String word)
    {
        for (String temp : list)
        {
            if (word.equals(temp))
            {
                System.out.println("You are already enrolled in that course");
                return false;
            }
        }
        return true;
    }

    /**
     * Prints out each student's name, id, courses, and the current balance for tuition
     *
     * All the students enrolled and in the list
     */
    private void displayInfo(Student[] studentList)
    {
        for (Student student : studentList)
        {
            System.out.println("Student Name: " + getName());
            System.out.println("Student ID: " + student.getId());

            if (student.getCourses().size() > 0) {
                System.out.println("Student's Current Courses:" + student.getCourses());
            } else {
                System.out.println("Student's Current Courses: The student isn't enrolled in any courses");
            }
            System.out.println("Student's Current Balance: $" + student.getTuition());
            System.out.println("------------------------------------------------------");
        }

    }

    public static void main(String[] args) {
        try {
            int size;
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Please enter the number of students you wish to add to the system");
            size = keyboard.nextInt();
            keyboard.nextLine();

            Student[] students = new Student[size];
            Student student;
            String firstName = "";
            String lastName = "";

            for (int i = 0; i < size; i++)
            {
                student = new Student(firstName, lastName);
                students[i] = student;

                System.out.println("Please enter your first name for Student ");
                firstName = keyboard.nextLine();
                student.setFirstName(firstName);

                System.out.println("Please enter your last name");
                lastName = keyboard.nextLine();
                student.setLastName(lastName);

                student.makeID();
                student.addCourses();
                student.payForCourses();

                if (i == size - 1)
                    student.displayInfo(students);
            }
        } catch (NegativeArraySizeException e) {
            System.out.println("You can't use a negative number for size");

        }
    }
}