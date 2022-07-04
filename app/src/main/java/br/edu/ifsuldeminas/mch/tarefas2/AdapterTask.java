// Criada para teste pode deletar se quiser.
package br.edu.ifsuldeminas.mch.tarefas2;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsuldeminas.mch.tarefas2.domain.Category;
import br.edu.ifsuldeminas.mch.tarefas2.domain.Task;

public class AdapterTask extends BaseAdapter {

    private final List<Task> tasks;
    private final MainActivity mainActivity;

    public AdapterTask (List<Task> tasks, MainActivity mainActivity) {
        this.tasks = tasks;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int id) {
        return tasks.get(id);
    }

    @Override
    public long getItemId(int id) {
        return 0;
    }

    @Override
    public View getView(int id, View convertView, ViewGroup parent) {

        View view = mainActivity.getLayoutInflater().inflate(R.layout.fragment_main, parent,false);
        Task task = tasks.get(id);

        TextView description = (TextView) view.findViewById(R.id.task_description);
        TextView category = (TextView) view.findViewById(R.id.category_description);
        ImageView imagem = (ImageView) view.findViewById(R.id.lista_curso_personalizada_imagem);

        description.setText(category.toString());
        category.setText(category.toString());
        imagem.setImageResource(R.drawable.feito);

        return view;
    }
}
