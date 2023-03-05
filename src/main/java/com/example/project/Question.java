package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Question extends Tema1 {
    int id;
    static int nrOfQuestions;
    User user;
    String text, type;
    Answer[] answers;

    Question(User user, String text, String type, Answer[] answers) {
        this.user = user;
        this.text = text;
        this.type = type;
        this.answers = answers;
        nrOfQuestions++;
        this.id = nrOfQuestions;
    }

    Question(User user, String text) {
        this.user = user;
        this.text = text;
    }

    /** cauta o intrebare in fisier in functie de textul ei si ii returneaza id-ul */
    public static int searchQuestionByText(Question q, String fileName) {
        String text;
        int id;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                text = getSubstring(line, "'", 2);
                id =  Integer.parseInt(getSubstring(line,"'", 4));
                if (q.text.compareTo(text) == 0) {
                    return id;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }

    /** cauta o intrebare in fisier in functie de id-ul ei si ii returneaza id-ul */
    public static int searchQuestionById(int qId, String fileName) {
        int id;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                id =  Integer.parseInt(getSubstring(line,"'", 4));
                if (qId == id) {
                    return id;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }

    /** afiseaza taote intrebarile cu id-ul din vectorul qId */
    public static void searchQuestionsByIdArray(int[] qId, String fileName) {
        int id, answerId1, answerId2, cont = 0;
        String text, type, answer1, answer2;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                text = getSubstring(line,"'", 2);
                type = getSubstring(line,"'", 3);
                answerId1 = Integer.parseInt(getSubstring(line,"'", 5));
                answer1 = getSubstring(line,"'", 6);
                answerId2 = Integer.parseInt(getSubstring(line,"'", 8));
                answer2 = getSubstring(line,"'", 9);
                id =  Integer.parseInt(getSubstring(line,"'", 4));
                if (qId[cont] == id) {
                    if (cont == 0) {
                        System.out.print("{'status':'ok','message':'[");
                    } else {
                        System.out.print(", ");
                    }
                    System.out.print("{\"question-name\":\"" + text + "\", \"question_index\":\"" + qId[cont] + "\", \"question_type\":\"" + type + "\", \"answers\":\"");
                    System.out.print("[{\"answer_name\":\"" + answer1 + "\", \"answer_id\":\"" + answerId1 + "\"}, {\"answer_name\":\"" + answer2 + "\", \"answer_id\":\""+ answerId2 + "\"}]\"}");
                    cont++;
                }
            }
            System.out.println("]'}");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /** insereaza datele unei intrebari alaturi de raspunsurile acesteia in fisier*/
    public static void insertQuestionInFile(Question q, String fileName) {
        if (Question.searchQuestionByText(q,fileName) != -1) {
            Question.printQuestionExists();
            return;
        }
        String questionDetails, answerDetails;
        questionDetails = q.user.username + "'" + q.user.password + "'" + q.text + "'" + q.type + "'" + q.id + "'";
        for (Answer a : q.answers) {
            if (a != null) {
                answerDetails = a.id + "'" + a.answer + "'" + a.answerValue + "'";
                questionDetails = questionDetails + answerDetails;
            }
        }
        Answer.insertAnswersInFile(q.answers, "answers.txt");
        insertLineInFile(questionDetails, "questions.txt");
        Question.printQuestionOK();

    }

    /** afiseaza toate intrebarile create de user */
    public static void searchAllQuestions(User u, String fileName) {
        String username, password, text;
        int id;
        int cont = 0;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                username = getSubstring(line, "'", 0);
                password = getSubstring(line, "'", 1);
                text = getSubstring(line, "'", 2);
                id = Integer.parseInt(getSubstring(line, "'", 4));
                if (u.username.compareTo(username) == 0 && u.password.compareTo(password) == 0) {
                    if (cont == 0) {
                        System.out.print("{'status':'ok','message':'[");
                    } else {
                        System.out.print(", ");
                    }
                    System.out.print("{\"question_id\" : \""+ id + "\", \"question_name\" : \""+ text + "\"}");
                    cont++;
                }

            }
            if (cont != 0) {
                System.out.println("]'}");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static void printQuestionExists() {
        System.out.println("{'status':'error','message':'Question already exists'}");
    }

    static void printUserError() {
        System.out.println("{'status':'error','message':'You need to be authenticated'}");
    }

    static void printTextError() {
        System.out.println("{'status':'error','message':'No question text provided'}");
    }
    static void printLoginFailed() {
        System.out.println("{'status':'error','message':'Login failed'}");
    }

    static void printGetQuestionOK(int id) {
        System.out.println("{'status':'ok','message':'" + id + "'}");
    }

    static void printGetQuestionError() {
        System.out.println("{'status':'error','message':'Question does not exist'}");
    }

    static void printQuestionOK() {
        System.out.println("{'status':'ok','message':'Question added successfully'}");
    }

}
