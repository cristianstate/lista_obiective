package com.fitness.todoapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Activitatea principală a aplicației "Lista obiective".
 * Gestionează interfața utilizator, adăugarea/ștergerea/bifarea obiectivelor
 * și persistența datelor locale folosind SharedPreferences și biblioteca Gson.
 */
public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskActionListener {

    // Componente ale interfeței grafice (UI)
    private EditText editTextTask;
    private RecyclerView recyclerView;
    private TextView textViewEmpty;
    private TaskAdapter adapter;

    // Structuri de date și obiecte pentru persistență
    private List<Task> taskList;
    private SharedPreferences prefs;
    private Gson gson;

    // Constante folosite pentru salvarea în SharedPreferences
    private static final String PREFS_NAME = "TodoPrefs";
    private static final String KEY_TASKS = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inițializarea componentelor de stocare locală
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        gson = new Gson();

        // Conectarea componentelor din XML cu variabilele din cod
        editTextTask = findViewById(R.id.editTextTask);
        recyclerView = findViewById(R.id.recyclerViewTasks);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        Button btnAdd = findViewById(R.id.btnAdd);

        // Încărcarea obiectivelor salvate anterior din SharedPreferences
        taskList = loadTasks();

        // Configurarea RecyclerView și a adapter-ului
        adapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Actualizarea stării inițiale a interfeței (afișare mesaj listă goală dacă nu există obiective)
        updateEmptyState();

        // Configurarea evenimentului de click pentru butonul "Adaugă"
        btnAdd.setOnClickListener(v -> addTask());

        // Adăugarea posibilității de a introduce un obiectiv apăsând tasta "Done/Enter" pe tastatură
        editTextTask.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTask();
                return true;
            }
            return false;
        });
    }

    /**
     * Logica de adăugare a unui nou obiectiv în listă.
     */
    private void addTask() {
        String title = editTextTask.getText().toString().trim();

        // Validare: se verifică dacă utilizatorul a introdus text
        if (title.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_empty_input), Toast.LENGTH_SHORT).show();
            return;
        }

        // Adăugarea noului obiectiv în listă și notificarea adapter-ului
        taskList.add(new Task(title));
        adapter.notifyItemInserted(taskList.size() - 1);

        // Reinițializarea câmpului de text, salvarea datelor și actualizarea stării
        editTextTask.setText("");
        saveTasks();
        updateEmptyState();

        // Derularea automată a listei până la ultimul element adăugat
        recyclerView.scrollToPosition(taskList.size() - 1);
    }

    /**
     * Metodă apelată când un obiectiv este șters prin intermediul adapter-ului.
     * @param position Poziția obiectivului șters.
     */
    @Override
    public void onTaskDeleted(int position) {
        if (position < 0 || position >= taskList.size()) {
            return;
        }
        taskList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, taskList.size() - position);
        saveTasks();
        updateEmptyState();
        Toast.makeText(this, getString(R.string.toast_task_deleted), Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodă apelată când starea de bifare a unui obiectiv se modifică.
     * @param position Poziția obiectivului modificat.
     * @param isChecked Noua stare (completat sau necompletat).
     */
    @Override
    public void onTaskChecked(int position, boolean isChecked) {
        if (position < 0 || position >= taskList.size()) {
            return;
        }
        taskList.get(position).setCompleted(isChecked);
        adapter.notifyItemChanged(position);
        saveTasks();
    }

    /**
     * Salvează lista curentă de obiective în SharedPreferences în format JSON.
     */
    private void saveTasks() {
        String json = gson.toJson(taskList);
        prefs.edit().putString(KEY_TASKS, json).apply();
    }

    /**
     * Încarcă lista de obiective din SharedPreferences.
     * @return Lista de obiective salvată sau o listă goală dacă nu există date.
     */
    private List<Task> loadTasks() {
        try {
            String json = prefs.getString(KEY_TASKS, null);
            if (json == null) return new ArrayList<>();

            // Definirea tipului generic pentru deserializarea JSON prin Gson
            Type type = new TypeToken<List<Task>>() {}.getType();
            List<Task> loaded = gson.fromJson(json, type);

            return loaded != null ? loaded : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Actualizează vizibilitatea elementelor pe ecran în funcție de prezența sau absența obiectivelor din listă.
     */
    private void updateEmptyState() {
        if (taskList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
