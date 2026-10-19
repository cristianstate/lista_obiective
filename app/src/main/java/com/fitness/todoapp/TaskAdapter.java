package com.fitness.todoapp;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter pentru RecyclerView responsabil cu legarea datelor din lista de obiective (taskList)
 * de elementele vizuale din interfața utilizator (item_task.xml).
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    /**
     * Interfață de comunicare (callback) pentru gestionarea acțiunilor utilizatorului
     * (ștergere și bifare/debifare obiectiv).
     */
    public interface OnTaskActionListener {
        /**
         * Apelată când utilizatorul apasă pe butonul de ștergere al unui obiectiv.
         * @param position Poziția elementului în listă.
         */
        void onTaskDeleted(int position);

        /**
         * Apelată când utilizatorul bifează sau debifează caseta unui obiectiv.
         * @param position Poziția elementului în listă.
         * @param isChecked Noua stare a casetei de bifare (true/false).
         */
        void onTaskChecked(int position, boolean isChecked);
    }

    // Lista de obiective de afișat
    private List<Task> taskList;
    
    // Listener pentru evenimentele declanșate pe elemente
    private OnTaskActionListener listener;

    /**
     * Constructorul adapter-ului.
     * @param taskList Lista inițială de obiective.
     * @param listener Listener-ul care va procesa acțiunile pe elemente.
     */
    public TaskAdapter(List<Task> taskList, OnTaskActionListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflația (crearea) layout-ului XML pentru un singur element (item_task.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Obținerea obiectivului de la poziția curentă
        Task task = taskList.get(position);

        // Eliminăm temporar listener-ul pentru a evita apelarea nedorită în timpul reciclării
        holder.checkBox.setOnCheckedChangeListener(null);

        // Setarea textului și a stării bifate
        holder.textViewTask.setText(task.getTitle());
        holder.checkBox.setChecked(task.isCompleted());

        // Aplicarea stilului vizual dacă obiectivul este finalizat (text tăiat și opacitate redusă)
        if (task.isCompleted()) {
            holder.textViewTask.setPaintFlags(
                    holder.textViewTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewTask.setAlpha(0.5f);
        } else {
            // Eliminarea stilului de tăiere a textului dacă obiectivul nu este finalizat
            holder.textViewTask.setPaintFlags(
                    holder.textViewTask.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewTask.setAlpha(1.0f);
        }

        // Listener pentru schimbarea stării casetei de bifat (CheckBox)
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && listener != null) {
                listener.onTaskChecked(pos, isChecked);
            }
        });

        // Apăsarea pe întregul rând permite bifarea/debifarea obiectivului
        holder.itemView.setOnClickListener(v -> holder.checkBox.toggle());

        // Listener pentru butonul de ștergere
        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && listener != null) {
                listener.onTaskDeleted(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returnează numărul total de elemente din listă
        return taskList.size();
    }

    /**
     * Clasă ViewHolder interioară care păstrează referințele către componentele grafice ale fiecărui element din listă.
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textViewTask;
        ImageButton btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            // Identificarea elementelor din interfață prin ID-urile din item_task.xml
            checkBox = itemView.findViewById(R.id.checkBoxTask);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
