# Tema1-POO-QuizzesGenerator

Tema presupune implementarea unui generator simplu de chestionare. Utilizatorii acestui program se vor autentifica în aplicație la orice apel din sistem, în afară de acela de creare de utilizator. Acestia pot crea întrebări (cu un singur răspuns corect, sau cu mai multe răspunsuri corecte), crea chestionare în baza întrebărilor adăugate anterior și vor putea răspunde chestionarelor celorlalți, doar o singură dată.

Utilizatorul interactioneaza cu generatorul de chestionare prin intermediul argumentelor transmise ca parametru al metodei main. Ca sa ne asiguram ca programul functioneaza cum trebuie, am creat mai multe metode ce printeaza pe ecran un feedback despre executarea comenzii (diferite erori sau realizarea cu succes a actiunii). Metodele ce se ocupa de cautarea, extragerea si inserarea intrebarilor, utilizatorilor sau chestionarelor sunt definite in mai multe clase (User, Question, Answer si Quiz).

Pentru a executa comanda, se parcurge vectorul de Stringuri printr-un for. Se extrage fiecare input al comenzii cu ajutorul metodei getSubstring. Informatiile se afla mereu intre doua apostrofuri (exemplu: 'my_username'), iar getSubstring imparte un String in mai multe substringuri, delimitate de apostrof si returneaza al catelea substring dorim (similar cu strtok din limbajul C).

La adaugarea unui user in sistem, se deschide fisierul "users.txt" unde se cauta un user existent cu acelasi nume. Nu ne dorim sa avem utilizatori cu nume identice, intrucat acestia sunt unici. In cazul in care nu exista un user cu acelasi nume, se introduc datele noului user pe ultima linie.

Intrebarile pot fi create numai de utilizatorii deja introdusi in sistem. Se cauta userul care creeaza intrebarea in fisier, iar daca acesta nu exista apare o eroare de login si programul se opreste. Fiecare intrebare are un id unic pentru a fi mai usor identificata, precum si raspunsurile acesteia. Toate intrebarile din fisier trebuie sa fie diferite, iar atunci cand se incearca adaugarea uneia in "questions.txt", se verifica daca se mai afla in el o intrebare identica (cu acelasi text). Raspunsurile, ca input, sunt reprezentate de raspunsul in sine si valoarea de adevar a acestuia ("Yes", "1"). Pentru a avea o implementare cat mai compacta am creat un vector de obiecte de tip Answer ce contine datele mentionate anterior. Atunci cand este apelat "–get-question-id-by-text" se afiseaza id-ul unei intrebari in functie de textul acesteia. Daca se apeleaza "–get-all-questions", sunt afisate toate intrebarile din sistem create de userul respectiv.

Crearea unui quiz grupeaza mai multe intrebari ale unui utilizator intr-un singur chestionar. Toate chestionarele sunt diferite (au id unic), se afla in fisierul "quizzes.txt" si contin intrebarile din "questions.txt" alaturi de utilizatorii din "users.txt". Atunci cand se cere stergerea unui chestionar, se copiaza restul quizurilor (fara cel pe care vrem sa il stergem) intr-un fisier temporar ("quizzes.tmp"), se sterge fisierul "quizzes.txt" si se redenumeste cel temporar ("quizzes.tmp" devine "quizzes.txt"). Rezolvarea unui quiz implica raspunderea intrebarilor de catre utilizatorul generatorului de chesionare. Astfel, quizurile rezolvate sunt salvate in fisierul "solutions.txt" alaturi de scorul obtinut de utilizator (scor bazat pe grila franceza).

Ce alte cazuri limită ați mai trata în această aplicație?

delogarea unui utilizator din sistem dupa un anumit interval de timp (insemnand ca datele sale inca sunt salvate, doar ca nu pot fi accesate, afisate)
stergerea chestionarului din sistem dupa ce acesta a fost rezolvat si submited in "solutions.txt"
eliberarea unui id atunci cand un quiz este sters si atribuirea acestui id urmatorului quiz creat
Cum ați refactoriza comenzile și răspunsurile din aplicație?

optiunile dintr-o comanda sa se poata trimita in orice ordine (exemplu: "–create-user -p ‘my_password’ -u ‘my_username’")
sa se poata crea mai multi utilizatori dintr-o singura comanda (exemplu: "–create-users -u ‘my_username1’ -p ‘my_password1 -u ‘my_username2’ -p ‘my_password2"
posibilitatea crearii unei intrebari fara primirea raspunsurilor ei, iar acestea pot fi adaugate ulterior in sistem
