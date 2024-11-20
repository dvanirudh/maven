package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Student {
        static final Logger log = LogManager.getLogger(Student.class);
    static Scanner sc = new Scanner(System.in);

    Map<Integer, String> studentInfo = new HashMap<>();
    Map<Integer, Map<String, Integer>> studentSubjects = new HashMap<>();

    public void data() {
        log.info("Do you have student data: ");
        Stream.of(sc.next()).filter(input -> input.equalsIgnoreCase("yes"))
                .findFirst()
                .ifPresentOrElse(
                        input -> studentData(),
                        () -> System.out.println("Bye!"));
        log.info("After giving the Student's Data it gives 'Bye!' statement");
    }

    private void studentData() {
        log.info("Enter the Student name --> ");
        String name = sc.next();
        log.info("Enter the Student ID --> ");
        int ID = sc.nextInt();

        studentInfo.put(ID, name);

        System.out.print("Do you want to add subject details --> ");
        Stream.of(sc.next()).filter(input -> input.equalsIgnoreCase("yes"))
                .findFirst()
                .ifPresentOrElse(
                        input -> addSubjects(ID),
                        () -> display());

        log.info("If yes the you can give Subject Details.");
    }

    private void addSubjects(int ID) {
        Map<String, Integer> subjectsMarks = new HashMap<>();

        do {
            log.info("Enter the subject name --> ");
            String subject = sc.next();
            log.info("Enter marks for " + subject + " --> ");
            int marks = Stream.generate( () -> {
                        return sc.nextInt();
                    }).filter(m -> m>=0 && m<=100)
                    .findFirst()
                    .get();
            log.info(" Above marks has been validated, Give marks upto 100 not more than than ");

            subjectsMarks.put(subject, marks);
            log.info(" Do you want to add another subject --> ");
        }
        while (!sc.next().equalsIgnoreCase("no"));
        studentSubjects.put(ID, subjectsMarks);

        log.info(" Do you want to add another student --> ");
        Stream.of(sc.next()).filter(input -> input.equalsIgnoreCase("yes"))
                .findFirst()
                .ifPresentOrElse(
                        input -> addSubjects(ID),
                        () -> display());   }

    private List<String> gatherAllSubjects() {
        return studentSubjects.values().stream()
                .flatMap(subjects -> subjects.keySet().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    private void display() {
        List<String> subjectList = gatherAllSubjects();

        log.info(" \n " + " Details below will be displayed --> ");
        System.out.printf("\n"+"%-30s %-10s", " Name", "ID");
        subjectList.forEach(subject -> System.out.printf("%-20s", subject));
        System.out.println();

        studentInfo.entrySet().stream()
                .forEach(entry -> {
                    int ID = entry.getKey();
                    String name = entry.getValue();
                    System.out.printf("%-30s %-10d", name, ID);
                    Map<String, Integer> subjects = studentSubjects.getOrDefault(ID, Collections.emptyMap());

                    subjectList.forEach(subject -> {
                        String mark = subjects.getOrDefault(subject, -1) == -1 ? "N/A" : String.valueOf(subjects.get(subject));
                        System.out.printf("%-20s", mark);
                    });
                    System.out.println();
                });
    }
}
