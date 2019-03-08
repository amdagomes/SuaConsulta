package com.ifpb.suaconsulta.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.AlarmeDAO;
import com.ifpb.suaconsulta.reciever.AlarmeReciever;
import com.ifpb.suaconsulta.model.Alarme;
import com.ifpb.suaconsulta.service.AlarmeService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdicionarAlarme extends AppCompatActivity {

    private Alarme alarme;
    private AlarmeDAO alarmeDAO;
    private Toolbar toolbar;
    private EditText medicamento, dosagem, duracaoDias, intervaloHoras;
    private TextView dataInicio;
    private Button buttonAdicionar;
    private SimpleDateFormat dateFormat;
    private String pattern;
    private Calendar time;

    private BroadcastReceiver receiver;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_alarme);

        alarmeDAO = new AlarmeDAO(getApplicationContext());
        receiver = new AlarmeReciever();

        toolbar = findViewById(R.id.toolbarAdicionarAlarme);
        toolbar.setTitle("Adicionar alarme");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time = Calendar.getInstance();
        pattern = "dd-MM-yyyy HH:mm";
        dateFormat = new SimpleDateFormat(pattern);
        inicializarComponentes();
        uploadInicioTramento();

        dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataInicioTratamento();
            }
        });

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medicamento.getText().toString().isEmpty() | duracaoDias.getText().toString().isEmpty() | intervaloHoras.getText().toString().isEmpty()){
                    Toast.makeText(AdicionarAlarme.this, "Preencha os campos obrigatorios", Toast.LENGTH_SHORT).show();
                } else{
                    alarme = new Alarme();
                    alarme.setMedicamento(medicamento.getText().toString());
                    alarme.setDosagem(dosagem.getText().toString());
                    alarme.setDuracaoDias(Integer.parseInt(duracaoDias.getText().toString()));
                    alarme.setIntervaloHoras(Integer.parseInt(intervaloHoras.getText().toString()));
                    alarme.setInicio(dateFormat.format(time.getTime()));

                    alarmeDAO.adiciona(alarme);
                    ativarAlarme();
                }
            }
        });
    }

    private void ativarAlarme() {
        Intent intent = new Intent(AdicionarAlarme.this, AlarmeReciever.class);
        intent.setAction("com.ifpb.suaconsulta");
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), 1000, pendingIntent);
    }

    private void uploadInicioTramento() {
        dataInicio.setText(dateFormat.format(time.getTime()));
    }

    private void setDataInicioTratamento(){
        new DatePickerDialog(this, dialogDate, time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setHoraInicioTratamento(){
        new TimePickerDialog(this, dialogTime, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener dialogDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            time.set(Calendar.YEAR, year);
            time.set(Calendar.MONTH, month);
            time.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setHoraInicioTratamento();
        }
    };

    TimePickerDialog.OnTimeSetListener dialogTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);
            uploadInicioTramento();
        }
    };

    private void inicializarComponentes() {
        medicamento = findViewById(R.id.editAdcAlarmeMedicamento);
        dosagem = findViewById(R.id.editAdcAlarmeDosagem);
        dataInicio = findViewById(R.id.textAdcAlarmeInicio);
        duracaoDias = findViewById(R.id.editAdcAlarmeDias);
        intervaloHoras = findViewById(R.id.editAdcAlarmeIntervalo);
        buttonAdicionar = findViewById(R.id.buttonAdcAlarme);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                new IntentFilter(AlarmeReciever.ACTION_ALARM)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
