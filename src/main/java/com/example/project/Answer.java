package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Answer extends Tema1 {
    String answer, type;
    int answerValue, id;
    static int nrOfAnswers;

    Answer(String answer, int answerValue, String type) {
        this.answer = answer;
        this.answerValue = answerValue;
        this.type = type;
        nrOfAnswers++;
        this.id = nrOfAnswers;
    }

    /** insereaza numarul de raspunsuri corecte si gresite, id-urile si valoarea lor de adevar intr-un fisier*/
    public static void insertAnswersInFile(Answer[] answers, String fileName) {
        int cont0 = 0, cont1 = 0;
        for (Answer a : answers) {
            if (a != null) {
                if (a.answerValue == 0) {
                    cont0++;
                } else if (a.answerValue == 1) {
                    cont1++;
                }
            }
        }
        String answerDetails = cont1 + "'" + cont0 + "'";
        for (Answer a : answers) {
            if (a != null) {
                answerDetails += a.id + "'" + a.answerValue + "'";
            }
        }
        insertLineInFile(answerDetails, fileName);

    }

    /** cauta id-urile raspunsurilor in sistem si calculeaza scorul obtinut pe baza grilei franceze*/
    public static double searchAnswersByIdArray(int[] answersId, String fileName) {
        int id, k = 0, answerValue, totalCorrectAnswers, totalWrongAnswers;
        int correctAnswers, wrongAnswers, scoreCont = 0;
        double score = 0;
        try {
            File answers = new File(fileName);
            Scanner myReader = new Scanner(answers);
            while (myReader.hasNextLine()) {
                correctAnswers = 0;
                wrongAnswers = 0;
                String line = myReader.nextLine();
                StringTokenizer p = new StringTokenizer(line, "'");
                totalCorrectAnswers = Integer.parseInt(p.nextToken());
                totalWrongAnswers = Integer.parseInt(p.nextToken());
                while (p.hasMoreTokens()) {
                    id = Integer.parseInt(p.nextToken());
                    if (k < answersId.length && answersId[k] == id) {
                        answerValue = Integer.parseInt(p.nextToken());
                        if (answerValue == 1) {
                            correctAnswers++;
                        } else {
                            wrongAnswers++;
                        }
                        k++;
                    }
                }
                score += calculateFrenchScore(totalCorrectAnswers, totalWrongAnswers, correctAnswers, wrongAnswers);
                scoreCont++;
            }
            myReader.close();
            if (score <= 0) {
                return 0;
            } else {
                score *= 100;
                return score / scoreCont;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }
    static void printFlagError(int answerCont) {
        System.out.println("{'status':'error','message':'Answer " + answerCont + " has no answer correct flag'}");
    }

    static void printDescriptionError(int answerCont) {
        System.out.println("{'status':'error','message':'Answer " + answerCont +  " has no answer description'}");
    }

    static void printAnswerMissingError() {
        System.out.println("{'status':'error','message':'No answer provided'}");
    }

    static void printAnswerDuplicatedError() {
        System.out.println("{'status':'error','message':'Same answer provided more than once'}");
    }

    static void printOneAnswerError() {
        System.out.println("{'status':'error','message':'Only one answer provided'}");
    }

    static void printSingleAnswerError() {
        System.out.println("{'status':'error','message':'Single correct answer question has more than one correct answer'}");
    }

    /** verifica daca exista deja aceleasi raspunsuri in sistem */
    static int searchAnswer(Answer[] answers, String answer) {
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] != null) {
                if (answers[i].answer.compareTo(answer) == 0) {
                    printAnswerDuplicatedError();
                    return 1;
                }
            }
        }
        return 0;
    }

    /** verifica daca vectorul de raspunsuri este valid */
    static int checkAnswerArray(Answer[] answers) {
        int i, cont = 0, answerValueFound = 0;
        if (answers[0] == null) {
            printAnswerMissingError();
            return -2;
        }
        for (i = 0; i < answers.length; i++) {
            if (answers[i] != null) {
                if (answers[i].type.compareTo("single") == 0) {
                    if(answers[i].answerValue == 1) {
                        if(answerValueFound == 0) {
                            answerValueFound = 1;
                        } else {
                            printSingleAnswerError();
                            return 0;
                        }
                    }
                }
             cont++;
            }
        }
        if (cont == 1) {
            printOneAnswerError();
            return -1;
        }
        return 1;
    }

}
