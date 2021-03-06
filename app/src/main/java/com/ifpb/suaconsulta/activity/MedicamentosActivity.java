package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.activity.adapters.AlarmesAdapter;
import com.ifpb.suaconsulta.database.AlarmeDAO;

public class MedicamentosActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private AlarmeDAO alarmeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_medicamentos);

        alarmeDAO = new AlarmeDAO(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbarLembretes);
        toolbar.setTitle("Lembretes de medicamentos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.Adapter adapter = new AlarmesAdapter(alarmeDAO.listar());

        recycler = findViewById(R.id.recyclerAlarmes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adiconar_lembrete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //trata do clique no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAdcLembrete:
                startActivity(new Intent(MedicamentosActivity.this, AdicionarAlarmeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
