package com.example.project;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Tema1 {

	static final String usersFile = "users.txt";
	static final String questionsFile = "questions.txt";
	static final String quizzesFile = "quizzes.txt";
	static final String solutionsFile = "solutions.txt";

	/** intoarce un substring dintr-un sir de caractere */
	public static String getSubstring(String str, String delim, int cont) {
		StringTokenizer p = new StringTokenizer(str, delim);
		while (cont > 0) {
			p.nextToken();
			cont--;
		}
		return p.nextToken();
	}

	public static int incrementPositiveNumber(int x) {
		if (x >= 0) {
			return ++x;
		}
		return x;
	}

	public static double calculateFrenchScore(int totalCorrectAnswers, int totalWrongAnswers, int correctAnswers, int wrongAnswers) {
		double correctPercentage = (double)correctAnswers/totalCorrectAnswers;
		double wrongPercentage = (double)wrongAnswers/totalWrongAnswers;
		return correctPercentage - wrongPercentage;
	}

	/** insereaza o linie in fisier */
	public static void insertLineInFile(String line, String fileName) {
		try (FileWriter fw = new FileWriter(fileName, true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw)) {
			out.print(line);
			out.println("");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void deleteFile(String fileName) {
		try (FileWriter fw = new FileWriter(fileName, false);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw)) {
			out.write("");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/** sterge continutul tuturor fisierelor */
	public static void cleanAllFiles() {
		Question.nrOfQuestions = 0;
		Quiz.nrOfQuizzes = 0;
		Answer.nrOfAnswers = 0;
		deleteFile(questionsFile);
		deleteFile(quizzesFile);
		deleteFile(usersFile);
		deleteFile("answers.txt");
		deleteFile(solutionsFile);
	}


	public static void main(final String[] args) {
		int i, id = 0, getAllQuestionsCont = -1, quizCont = -1, getQuizCont = -1, getAllQuizzesCont = -1;
		int getQuestionCont = -1, userCont = -1, questionCont = -1, answerCont = 0, cont = 0;
		int answerValue, getQuizDetails = -1, deleteQuiz = -1, submitQuiz = -1, getSolutions = -1;
		String username = null, password = null, text = null, type = null, p, ans;
		String answer = null, name =null, quizDetails;
		int[] questionsId = null, copie, answersId = null;
		Answer[] answers = new Answer[5];
		double quizScore;
		if(args == null) {
			System.out.print("Hello world!");
			return;
		}
		for (i = 0; i < args.length; i++) {
			if (args[i].compareTo("-create-user") == 0) {
				userCont = 0;
			} else if (userCont == 1) {
				if (args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (userCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'",1);
				}
			}

			if (args[i].compareTo("-create-question") == 0) {
				questionCont = 0;
			} else if (questionCont == 1) {
				if (args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (questionCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'",1);
				}
			} else if (questionCont == 3) {
				p = getSubstring(args[i], "'",0);
				if (p.compareTo("-text ") == 0) {
					text = getSubstring(args[i], "'",1);
				} else {
					Question.printTextError();
					return;
				}
			} else if (questionCont == 4) {
				if (args[i].charAt(1) == 't') {
					type = getSubstring(args[i], "'",1);
				}
				answerCont = 1;
			} else if (answerCont >= 1) {
				p = getSubstring(args[i], "'", 0);
				if (cont % 2 == 0) {
					ans = "-answer-" + answerCont + " ";
					if (p.compareTo(ans) == 0) {
						answer = getSubstring(args[i], "'", 1);
						if (Answer.searchAnswer(answers, answer) == 1) return;
					} else {
						Answer.printDescriptionError(answerCont);
						return;
					}
				} else {
					ans = "-answer-" + answerCont + "-is-correct ";
					if (p.compareTo(ans) == 0) {
						answerValue = Integer.parseInt(getSubstring(args[i], "'", 1));
						answers[answerCont - 1] = new Answer(answer, answerValue, type);
						answerCont++;
					} else {
						Answer.printFlagError(answerCont);
						return;
					}
				}
				cont++;
			}

			if (args[i].compareTo("-get-question-id-by-text") == 0) {
				getQuestionCont = 0;
			} else if (getQuestionCont == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (getQuestionCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'", 1);
				}
			} else if (getQuestionCont == 3) {
				if (args[i].charAt(1) == 't') {
					text = getSubstring(args[i], "'", 1);
				}
			}

			if (args[i].compareTo("-get-all-questions") == 0) {
				getAllQuestionsCont = 0;
			} else if (getAllQuestionsCont == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (getAllQuestionsCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'",1);
				}
			}

			if (args[i].compareTo("-create-quizz") == 0) {
				quizCont = 0;
			} else if (quizCont == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (quizCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'",1);
				}
			} else if (quizCont == 3) {
				if(args[i].charAt(1) == 'n') {
					name = getSubstring(args[i], "'", 1);
				}
			} else if (quizCont > 3) {
				if(args[i].charAt(1) == 'q') {
					if (questionsId == null) {
						questionsId = new int[1];
					} else {
						copie = questionsId.clone();
						questionsId = Arrays.copyOf(copie, copie.length + 1);
					}

					questionsId[cont] = Integer.parseInt(getSubstring(args[i], "'", 1));
					cont++;
				}
			}

			if (args[i].compareTo("-get-quizz-by-name") == 0) {
				getQuizCont = 0;
			} else if (getQuizCont == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (getQuizCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'",1);
				}
			} else if (getQuizCont == 3) {
				if (args[i].charAt(1) == 'n') {
					name = getSubstring(args[i], "'", 1);
				}
			}

			if (args[i].compareTo("-get-all-quizzes") == 0) {
				getAllQuizzesCont = 0;
			} else if (getAllQuizzesCont == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (getAllQuizzesCont == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'", 1);
				}
			}

			if (args[i].compareTo("-get-quizz-details-by-id") == 0) {
				getQuizDetails = 0;
			} else if (getQuizDetails == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (getQuizDetails == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'", 1);
				}
			} else if (getQuizDetails == 3) {
				if(args[i].charAt(1) == 'i') {
					id = Integer.parseInt(getSubstring(args[i], "'", 1));
				}
			}

			if (args[i].compareTo("-delete-quizz-by-id") == 0) {
				deleteQuiz = 0;
			} else if (deleteQuiz == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (deleteQuiz == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'", 1);
				}
			} else if (deleteQuiz == 3) {
				if(args[i].charAt(1) == 'i') {
					id = Integer.parseInt(getSubstring(args[i], "'", 1));
				}
			}

			if (args[i].compareTo("-submit-quizz") == 0) {
				submitQuiz = 0;
			} else if (submitQuiz == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (submitQuiz == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'", 1);
				}
			} else if (submitQuiz == 3) {
				if (args[i].charAt(1) == 'q') {
					id = Integer.parseInt(getSubstring(args[i], "'", 1));
				}
			} else if (submitQuiz >= 3) {
				if (args[i].charAt(1) == 'a') {
					if (answersId == null) {
						answersId = new int[1];
					} else {
						copie = answersId.clone();
						answersId = Arrays.copyOf(copie, copie.length + 1);
					}
					answersId[cont] = Integer.parseInt(getSubstring(args[i], "'", 1));
					cont++;
				}
			}

			if (args[i].compareTo("-get-my-solutions") == 0) {
				getSolutions = 0;
			} else if (getSolutions == 1) {
				if(args[i].charAt(1) == 'u') {
					username = getSubstring(args[i], "'", 1);
				}
			} else if (getSolutions == 2) {
				if (args[i].charAt(1) == 'p') {
					password = getSubstring(args[i], "'", 1);
				}
			}

			if (args[i].compareTo("-cleanup-all") == 0) {
					cleanAllFiles();
			}

			userCont = incrementPositiveNumber(userCont);
			questionCont = incrementPositiveNumber(questionCont);
			getQuestionCont = incrementPositiveNumber(getQuestionCont);
			getAllQuestionsCont = incrementPositiveNumber(getAllQuestionsCont);
			quizCont = incrementPositiveNumber(quizCont);
			getQuizCont = incrementPositiveNumber(getQuizCont);
			getAllQuizzesCont = incrementPositiveNumber(getAllQuizzesCont);
			getQuizDetails = incrementPositiveNumber(getQuizDetails);
			deleteQuiz = incrementPositiveNumber(deleteQuiz);
			submitQuiz = incrementPositiveNumber(submitQuiz);
			getSolutions = incrementPositiveNumber(getSolutions);

		}

		if (userCont != -1) {
			if (username == null) {
				User.printUsernameError();
				return;
			}
			if (password == null) {
				User.printPasswordError();
				return;
			}
			User u = new User(username,password);
			User.insertUserInFile(u, usersFile);
		}

		if (questionCont != -1 || getQuestionCont != -1 || getAllQuestionsCont != -1 || quizCont != -1
				|| getQuizCont != -1 || getAllQuizzesCont != -1  || getQuizDetails != -1 || deleteQuiz != -1
				|| submitQuiz != -1 || getSolutions != -1) {
			if (username == null || password == null) {
				Question.printUserError();
				return;
			}
			User u = new User(username, password);
			if (User.searchUser(u, usersFile) == -1) {
				Question.printLoginFailed();
				return;
			}

		}

		if (questionCont != -1) {

			if (Answer.checkAnswerArray(answers) != 1) {
				return;
			}
			User u = new User(username,password);
			Question q = new Question(u, text, type, answers);
			Question.insertQuestionInFile(q, questionsFile);
		}

		if (getQuestionCont != -1) {
			User u = new User(username,password);
			Question q = new Question(u, text);
			id = Question.searchQuestionByText(q, questionsFile);
			if (id != -1) {
				Question.printGetQuestionOK(id);
			} else {
				Question.printGetQuestionError();
			}
			return;
		}

		if (getAllQuestionsCont != -1) {
			User u = new User(username,password);
			Question.searchAllQuestions(u, questionsFile);
		}

		if (quizCont != -1) {
			if (Quiz.searchQuizByName(name,quizzesFile) != -1) {
				Quiz.printQuizExists();
				return;
			}
			for (int qId : questionsId) {
				if(Question.searchQuestionById(qId, questionsFile) == -1) {
					Quiz.printQuizQuestionIdError(qId);
					return;
				}
			}
			User u = new User(username,password);
			Quiz quiz = new Quiz(u, name, questionsId);
			Quiz.insertQuizInFile(quiz, quizzesFile);
		}

		if (getQuizCont != -1) {
			id = Quiz.searchQuizByName(name, quizzesFile);
			if (id != -1) {
				Quiz.printGetQuizOK(id);
			} else {
				Quiz.printGetQuizError();
			}
			return;
		}

		if (getAllQuizzesCont != -1) {
			User u = new User(username,password);
			Quiz q = new Quiz(u, name);
			Quiz.searchAllQuizzes(q, quizzesFile);
		}

		if (getQuizDetails != -1) {
			User u = new User(username,password);
			int[] v = Quiz.searchQuizById(id, quizzesFile);
			Question.searchQuestionsByIdArray(v, questionsFile);
			return;
		}

		if (deleteQuiz != -1) {
			if (id == 0) {
				Quiz.printQuizIdError();
			} else if ((Quiz.searchQuizById(id, quizzesFile) == null)) {
				Quiz.printQuizNotFoundError();
			} else {
				Quiz.deleteQuiz(id, quizzesFile);
			}
			return;
		}

		if (submitQuiz != -1) {
			if (id == 0) {
				Quiz.printQuizIdError();
			} else if ((Quiz.searchQuizById(id, quizzesFile) == null)) {
				Quiz.printQuizNotFoundError();
			} else {
				quizScore = Answer.searchAnswersByIdArray(answersId, "answers.txt");
				name = Quiz.searchQuizByIdAndGetName(id, quizzesFile);
				quizDetails = username + "'" + password + "'" + name + "'" + id + "'" + (int)quizScore + "'";
				insertLineInFile(quizDetails, solutionsFile);
				Quiz.printQuizScore(quizScore);
			}
			return;
		}

		if (getSolutions != -1) {
			User u = new User(username,password);
			Quiz.searchAllSolutions(u, solutionsFile);
		}

	}
}
