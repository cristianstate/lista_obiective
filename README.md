#  Lista Obiective (To-Do App)

> *"Un obiectiv fără un plan este doar o dorință."* — Antoine de Saint-Exupéry

---

##  Importanța Setării Obiectivelor în Activitatea de Zi cu Zi

Setarea și urmărirea obiectivelor reprezintă pilonul central al productivității și al dezvoltării personale. În ritmul alert al vieții moderne, gestionarea eficientă a timpului și claritatea asupra priorităților fac diferența între o zi aglomerată dar ineficientă și o zi cu adevărat împlinită.

Definirea obiectivelor zilnice aduce beneficii esențiale:
- **Focus și Claritate:** Îți direcționează atenția către sarcinile cu adevărat importante, eliminând distragerile.
- **Reducerea Stresului:** Transformă ideile și responsabilitățile dispersate într-un plan de acțiune organizat și ușor de urmărit.
- **Motivație și Satisfacție:** Bifarea fiecărui obiectiv finalizat eliberează dopamină, oferind un sentiment concret de progres și realizare.
- **Gestionarea Eficientă a Timpului:** Îți permite să evaluezi realist timpul necesar fiecărei activități.

Aplicația **Lista Obiective** a fost creată tocmai pentru a veni în sprijinul tău, oferindu-ți un instrument simplu, rapid și intuitiv pentru a-ți structura ziua și a-ți atinge toate țelurile.

---

## 📱 Despre Aplicație

**Lista Obiective** este o aplicație mobilă Android concepută pentru gestionarea eficientă a sarcinilor și obiectivelor zilnice. Interfața modernă și curată asigură o experiență plăcută de utilizare, permițându-ți să te concentrezi pe ceea ce contează cu adevărat.

###  Caracteristici Principale

- **Adăugare rapidă de obiective:** Introdu un titlu și adaugă obiectivul fie apăsând butonul *"Adaugă"*, fie direct de pe tastatură (tasta *Done / Enter*).
-  **Marcare/Debifare obiective:** Bifează sarcinile finalizate printr-o simplă atingere pe rând sau pe caseta de bifat. Textul obiectivelor finalizate este barat și estompat vizual.
-  **Ștergere facilă:** Elimină obiectivele finalizate sau renunțate printr-un singur click pe butonul de ștergere.
-  **Salvare automată locală:** Datele tale sunt salvate automat în dispozitiv și persistă chiar și după închiderea sau repornirea aplicației.
-  **Vizualizare stare listă:** Când nu ai nicio sarcină în listă, aplicația afișează un mesaj prietenos de încurajare.

---

##  Tehnologii și Arhitectură

Aplicația este dezvoltată nativ pentru Android, respectând bunele practici de dezvoltare software:

- **Limbaj de programare:** Java 11
- **Arhitectură UI:** Android Material Design & `RecyclerView` pentru performanță optimă la derularea listei
- **Persistența datelor:** `SharedPreferences` + biblioteca **Google Gson** pentru serializarea obiectelor în format JSON
- **Compatibilitate:** Android 7.0 (API 24 - Nougat) sau versiuni ulterioare
- **Testare:** Teste unitare automate scrise în **JUnit 4** pentru verificarea integrității modelului de date și a serializării

---

##  Structura Proiectului

```text
com.fitness.todoapp/
├── MainActivity.java     # Activitatea principală (interfață, logică de business, persistență)
├── TaskAdapter.java      # Adapter RecyclerView pentru gestionarea și afișarea listei de obiective
├── Task.java             # Modelul de date pentru un obiectiv (titlu, stare completare)
└── res/
    ├── layout/
    │   ├── activity_main.xml # Layout-ul ecranului principal
    │   └── item_task.xml     # Layout-ul pentru un singur element din listă
    └── values/
        ├── strings.xml       # Resurse de text localizate
        └── colors.xml        # Paleta de culori a aplicației
```

---

## Cum se Rulează Proiectul

1. **Cerințe prealabile:**
   - Android Studio (versiunea Ladybug / Koala sau mai nouă)
   - JDK 11 sau mai nou
   - SDK Android instalat (API 36)

2. **Pași de lansare:**
   1. Deschide **Android Studio**.
   2. Selectează **Open** și alege directorul proiectului `lista_obiective`.
   3. Așteaptă finalizarea sincronizării **Gradle Sync**.
   4. Selectează un emulator Android sau conectează un dispozitiv fizic prin USB (cu *USB Debugging* activat).
   5. Apasă pe butonul **Run** (sau tasta `Shift + F10`).

---

##  Rularea Testelor Unitare

Pentru a verifica funcționalitatea codului sursă prin teste unitare:
- În Android Studio, mergi la directorul `src/test/java/com/fitness/todoapp/TaskTest.java`, dă click dreapta și alege **Run 'TaskTest'**.
- Alternativ, poți rula comanda Gradle în terminal:
  ```bash
  ./gradlew test
  ```
