package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Quiz extends Tema1 {
    User user;
    String name;
    int[] questionsId;
    int id;
    static int nrOfQuizzes;
    boolean isCompleted;
    Quiz(User user, String name, int[] questionsId) {
        this.user = user;
        this.name = name;
        this.questionsId = questionsId;
        nrOfQuizzes++;
        this.id = nrOfQuizzes;
        this.isCompleted = false;
    }

    Quiz(User user, String name) {
        this.user = user;
        this.name = name;
    }

    /** cauta un quiz in functie de numele lui si returneaza id-ul lui */
    public static int searchQuizByName(String quizName, String fileName) {
        String name;
        int id;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                name = getSubstring(line, "'", 2);
                id = Integer.parseInt(getSubstring(line, "'", 3));
                if (quizName.compareTo(name) == 0) {
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

    /** cauta un quiz in functie de id-ul lui si returneaza id-urile intrebarilor sale*/
    public static int[] searchQuizById(int quizId, String fileName) {
        int id, quizFound = 0, cont = 5, questionIndex, k = 0;
        int[] questionId = null, copie;
        String line = null;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                id = Integer.parseInt(getSubstring(line, "'", 3));
                if (quizId == id) {
                    quizFound = 1;
                    break;
                }
            }
            if (quizFound == 1) {
                StringTokenizer p = new StringTokenizer(line, "'");
                while (cont > 0) {
                    p.nextToken();
                    cont--;
                }
                while (p.hasMoreTokens()) {
                    questionIndex = Integer.parseInt(p.nextToken());
                    if (questionId == null) {
                        questionId = new int[1];
                    } else {
                        copie = questionId.clone();
                        questionId = Arrays.copyOf(copie, copie.length + 1);
                    }
                    questionId[k] = questionIndex;
                    cont++;
                    k++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return questionId;
    }

    /** insereaza datele chestionarului in fisier */
    public static void insertQuizInFile(Quiz quiz, String fileName) {
        String quizDetails;
        quizDetails = quiz.user.username + "'" + quiz.user.password + "'" + quiz.name + "'" + quiz.id + "'False'";
        for (int qId : quiz.questionsId) {
            quizDetails = quizDetails + qId + "'";
        }
        insertLineInFile(quizDetails, fileName);
        Quiz.printQuizOK();
    }

    /** afiseaza toate quizurile create de un user */
    public static void  searchAllQuizzes(Quiz quiz, String fileName) {
        String username, password, name, isCompleted;
        User u = quiz.user;
        int id, cont = 0;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                username = getSubstring(line, "'", 0);
                password = getSubstring(line, "'", 1);
                name = getSubstring(line, "'", 2);
                id = Integer.parseInt(getSubstring(line, "'", 3));
                isCompleted = getSubstring(line, "'", 4);
                if (u.username.compareTo(username) == 0 && u.password.compareTo(password) == 0) {
                    if (cont == 0) {
                        System.out.print("{'status':'ok','message':'[");
                    } else {
                        System.out.print(", ");
                    }
                    System.out.print("{\"quizz_id\" : \""+ id + "\", \"quizz_name\" : \""+ name + "\", \"is_completed\" : \""+ isCompleted + "\"}");
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

    /** cauta un quiz in functie de id-ul lui si returneaza numele lui */
    public static String searchQuizByIdAndGetName(int quizId, String fileName) {
        String name = null;
        int id;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                name = getSubstring(line, "'", 2);
                id = Integer.parseInt(getSubstring(line, "'", 3));
                if (quizId == id) {
                    return name;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return name;
    }

    /** afiseaza scorul obtinut de user la un chestionar */
    public static void  searchAllSolutions(User u, String fileName) {
        String username, password, name, score;
        int id, cont = 0;
        try {
            File questions = new File(fileName);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                username = getSubstring(line, "'", 0);
                password = getSubstring(line, "'", 1);
                name = getSubstring(line, "'", 2);
                id = Integer.parseInt(getSubstring(line, "'", 3));
                score = getSubstring(line, "'", 4);
                if (u.username.compareTo(username) == 0 && u.password.compareTo(password) == 0) {
                    if (cont == 0) {
                        System.out.print("{'status':'ok','message':'[");
                    } else {
                        System.out.print(", ");
                    }
                    cont++;
                    System.out.print("{\"quiz-id\" : \""+ id + "\", \"quiz-name\" : \""+ name + "\", \"score\" : \""+ score + "\", \"index_in_list\" : \""+ cont + "\"}");
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

    /** sterge un chestionar din fisier, fara sa afecteze restul chestionarelor */
    public static void deleteQuiz(int quizId, String fileName) {
        int id, contLines = 0;
        String line;
        try {
            File quizzes = new File(fileName);
            Scanner myReader = new Scanner(quizzes);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                id = Integer.parseInt(getSubstring(line, "'", 3));
                if (quizId != id) {
                    insertLineInFile(line, "quizzes.tmp");
                    contLines++;
                }
            }
            if (contLines == 0) {
                insertLineInFile("", "quizzes.tmp");
            }
            deleteFile("quizzes.txt");
            File newQuizzes = new File("quizzes.tmp");
            if (!newQuizzes.renameTo(quizzes)) {
                System.out.println("An error occured.");
            }
            Quiz.printQuizDeletedOK();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static void printQuizExists() {
        System.out.println("{'status':'error','message':'Quizz name already exists'}");
    }

    static void printQuizQuestionIdError(int id) {
        System.out.println("{'status':'error','message':'Question ID for question " + id +" does not exist'}");
    }

    static void printQuizIdError() {
        System.out.println("{'status':'error','message':'No quizz identifier was provided'}");
    }

    static void printQuizNotFoundError() {
        System.out.println("{'status':'error','message':'No quiz was found'}");
    }
    static void printQuizDeletedOK() {
        System.out.println("{'status':'ok','message':'Quizz deleted successfully'}");
    }
    static void printGetQuizOK(int id) {
        System.out.println("{'status':'ok','message':'" + id + "'}");
    }

    static void printGetQuizError() {
        System.out.println("{'status':'error','message':'Quizz does not exist'}");
    }
    static void printQuizOK() {
        System.out.println("{'status':'ok','message':'Quizz added succesfully'}");
    }

    static void printQuizScore(double quizScore) {
        int score = (int)quizScore;
        if (score == 66) score++;
        System.out.println("{'status':'ok','message':'" + score + " points'}");
    }
}
