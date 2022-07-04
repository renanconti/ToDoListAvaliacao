package br.edu.ifsuldeminas.mch.tarefas2;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.edu.ifsuldeminas.mch.tarefas2.databinding.FragmentMainCategoryBinding;
import br.edu.ifsuldeminas.mch.tarefas2.db.CategoryDAO;
import br.edu.ifsuldeminas.mch.tarefas2.domain.Category;

public class MainCategoryFragment extends Fragment {

    private FragmentMainCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.categoryList);

        binding.categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long l) {

                Category category = (Category) binding.categoryList.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                //categoria foi serializada aqui na implementação da category
                bundle.putSerializable("category", category);

                NavController navController = Navigation.findNavController(
                        getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_MainCategoryFragment_to_CategoryFragment, bundle);
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
        updateCategorys();
    }

    private void updateCategorys() {
        CategoryDAO dao = new CategoryDAO(getContext());
        List<Category> categories = dao.listAll();

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, categories);

        binding.categoryList.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item = menu.add(R.string.delete_category);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;

                Category categorySelected = (Category) binding.categoryList.getItemAtPosition(
                        info.position);

                CategoryDAO categoryDAO = new CategoryDAO(getContext());
                try {
                    categoryDAO.delete(categorySelected);
                    Toast.makeText(getContext(), R.string.category_deleted,
                            Toast.LENGTH_SHORT).show();
                    updateCategorys();
                }catch (SQLiteConstraintException sqLiteConstraintException){
                    Toast.makeText(getContext(), R.string.error_exclusao,
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}