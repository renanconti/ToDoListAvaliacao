package br.edu.ifsuldeminas.mch.tarefas2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import br.edu.ifsuldeminas.mch.tarefas2.databinding.FragmentTaskBinding;
import br.edu.ifsuldeminas.mch.tarefas2.db.CategoryDAO;
import br.edu.ifsuldeminas.mch.tarefas2.db.TaskDAO;
import br.edu.ifsuldeminas.mch.tarefas2.domain.Category;
import br.edu.ifsuldeminas.mch.tarefas2.domain.Task;

public class TaskFragment extends Fragment {

    private Task task;
    private FragmentTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CategoryDAO dao = new CategoryDAO(getContext());
        List<Category> categories = dao.listAll();

        Spinner spinner = getActivity().findViewById(R.id.spinner);
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        binding.buttonSaveTaskId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText descriptionTIET = binding.taskDescription;

                Category category = (Category) spinner.getSelectedItem();
                //Verificar se existe categoria salva para a tarefa
                if (category == null){
                    Toast.makeText(getContext(), R.string.task_category_not_empty,
                            Toast.LENGTH_SHORT).show();
                // Se não existir, passar a verificação de tarefa
                }else{
                String description = descriptionTIET.getText().toString();
                description = description != null ? description : "";

                //verificação do nome da descrição para não ser vazia
                if(description.equals("")){
                    Toast.makeText(getContext(), R.string.task_description_empty,
                            Toast.LENGTH_SHORT).show();
                } else {
                    //Se para caso a task seja vazia, salvar com o dao
                    if(task == null) {
                        Task task = new Task(0, description, category);

                        TaskDAO dao = new TaskDAO(getContext());
                        dao.save(task);

                        Toast.makeText(getContext(), R.string.task_saved,
                                Toast.LENGTH_SHORT).show();
                    //update da task
                    } else {
                        task.setDescription(description);
                        task.setCategory(category);

                        TaskDAO dao = new TaskDAO(getContext());
                        dao.update(task);

                        Toast.makeText(getContext(), R.string.task_updated,
                                Toast.LENGTH_SHORT).show();
                    }
                }}

                //Caso não atenda as entradas retornar para a pagina de tarefas
                NavController navController = Navigation.findNavController(
                        getActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.action_TaskFragment_to_MainFragment);
                navController.popBackStack(R.id.TaskFragment, true);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        Object taskArguments = null;
        if(getArguments() != null){
            taskArguments = getArguments().getSerializable("task");
        }
        if (taskArguments != null) {
            task = (Task) taskArguments;
            TextInputEditText taskDescription = binding.taskDescription;
            taskDescription.setText(task.getDescription());
        }
    }
}