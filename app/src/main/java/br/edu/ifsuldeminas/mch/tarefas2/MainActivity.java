package br.edu.ifsuldeminas.mch.tarefas2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import br.edu.ifsuldeminas.mch.tarefas2.databinding.ActivityMainBinding;
import br.edu.ifsuldeminas.mch.tarefas2.domain.Category;
import br.edu.ifsuldeminas.mch.tarefas2.domain.Task;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (navController.getCurrentDestination().getId() == R.id.MainFragment) {
                    navController.navigate(R.id.action_MainFragment_to_TaskFragment);
                }
                if (navController.getCurrentDestination().getId() == R.id.MainCategoryFragment) {
                    navController.navigate(R.id.action_MainCategoryFragment_to_CategoryFragment);
                }
            }
        });

        navController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController navController,
                                                     @NonNull NavDestination navDestination,
                                                     @Nullable Bundle bundle) {
                        binding.fab.setVisibility(FloatingActionButton.VISIBLE);
                        if (navController.getCurrentDestination().getId() != R.id.MainFragment && navController.getCurrentDestination().getId() != R.id.MainCategoryFragment) {
                            binding.fab.setVisibility(FloatingActionButton.INVISIBLE);
                        }
                    }

                });


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
//Navegando entre MainFragment e MainCategoryFragment, caso ID do action for igual
        if (id == R.id.action_settings) {
            if (navController.getCurrentDestination().getId() == R.id.MainFragment){
                navController.navigate(R.id.action_MainFragment_to_MainCategoryFragment);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}