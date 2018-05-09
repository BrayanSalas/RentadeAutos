package markintoch.rentcar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.Calendar;

import markintoch.rentcar.Recycler.RecyclerActivity;

@SuppressWarnings("serial")
public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Serializable {
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    private FirebaseAuth mAuth;
    String estab[];
    int position;
    Button buttonBuscar;
    EditText fechaInicio;
    EditText fechaDevolucion;
    EditText horaInicio;
    EditText horaDevolucion;
    ImageButton ibtnFechaInicio, ibtnFechaFin, ibtnHoraInicio, ibtnHoraFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        estab = getResources().getStringArray(R.array.establecimientos);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.establecimientos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //EDITTEXT
        fechaInicio = findViewById(R.id.recogerFecha);
        fechaDevolucion = findViewById(R.id.devolucionFecha);
        horaInicio = findViewById(R.id.recogerHora);
        horaDevolucion = findViewById(R.id.devolucionHora);

        //BOTONES
        buttonBuscar = findViewById(R.id.buttonBuscar);
        ibtnFechaInicio = findViewById(R.id.ibtnFechaInicial);
        ibtnFechaFin = findViewById(R.id.ibtnFechaFin);
        ibtnHoraInicio = findViewById(R.id.ibtnHoraInicial);
        ibtnHoraFin = findViewById(R.id.ibtnHoraFin);

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Revisar campos vacios
                boolean camposLlenos = validate(new EditText[]{fechaInicio, fechaDevolucion, horaInicio, horaDevolucion});

                //Revisar fechas
                boolean revisarFechas = validarFecha(new EditText[]{fechaInicio, fechaDevolucion, horaInicio, horaDevolucion});

                if (camposLlenos && revisarFechas) {
                    String newfechaInicio = fechaInicio.getText().toString();
                    String newfechaDevolucion = fechaDevolucion.getText().toString();
                    String newhoraInicio = horaInicio.getText().toString();
                    String newhoraDevolucion = horaDevolucion.getText().toString();
                    fechaInicio.setText("");
                    fechaDevolucion.setText("");
                    horaInicio.setText("");
                    horaDevolucion.setText("");
                    Intent intent = new Intent(SearchActivity.this, RecyclerActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("newfechaInicio", newfechaInicio);
                    intent.putExtra("newfechaDevolucion", newfechaDevolucion);
                    intent.putExtra("newhoraInicio", newhoraInicio);
                    intent.putExtra("newhoraDevolucion", newhoraDevolucion);
                    intent.putExtra("establecimiento", estab[position]);
                    startActivity(intent);

                }
            }
        });

        ibtnFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha(1);
            }
        });

        ibtnFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha(0);
            }
        });

        ibtnHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerHora(1);
            }
        });

        ibtnHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerHora(0);
            }
        });

    }//onCreate



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mAuth.signOut();
                Toast.makeText(this, "La sesión ha sido cerrada", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SearchActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.carrosRentados:
                Intent carRent = new Intent(SearchActivity.this, RentadosActivity.class);
                startActivity(carRent);
                return true;





            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //La posicion se actualiza cada que elige un diferente establecimiento
        position = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private boolean validate(EditText[] fields){
        for(int i=0; i<fields.length; i++){
            EditText currentField=fields[i];
            if(currentField.getText().toString().equals("")){
                //currentField.setError("Este campo es requerido");
                Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean validarFecha(EditText[] editTexts){
        EditText fechaInicio = editTexts[0];
        EditText fechaFin = editTexts[1];
        EditText horaInicial = editTexts[2];
        EditText horaFinal = editTexts[3];

        if(!fechaInicio.getText().toString().equals("") && !fechaFin.getText().toString().equals("")){
            String fInicial = fechaInicio.getText().toString();
            String fFin = fechaFin.getText().toString();
            String hInicial = horaInicial.getText().toString();
            String hFin = horaFinal.getText().toString();

            int diaIni = Integer.parseInt(fInicial.substring(0,2));
            int diaFin = Integer.parseInt(fFin.substring(0,2));
            int mesIni = Integer.parseInt(fInicial.substring(3,5));
            int mesFin = Integer.parseInt(fFin.substring(3,5));
            int añoIni = Integer.parseInt(fInicial.substring(6,10));
            int añoFin = Integer.parseInt(fFin.substring(6,10));
            int horaIni = Integer.parseInt(hInicial.substring(0,2));
            int horaFin = Integer.parseInt(hFin.substring(0,2));
            int minIni = Integer.parseInt(hInicial.substring(3,5));
            int minFin = Integer.parseInt(hFin.substring(3,5));

            if ((diaIni>diaFin && mesIni==mesFin && añoIni==añoFin) || (diaIni>diaFin && mesIni>mesFin && añoIni==añoFin) || (diaIni>diaFin && mesIni>mesFin && añoIni>añoFin) ||(diaIni==diaFin && mesIni>mesFin && añoIni>añoFin) || (diaIni==diaFin && mesIni==mesFin && añoIni>añoFin)){
                Toast.makeText(this, "Revise la fecha", Toast.LENGTH_SHORT).show();
                //INCORRECTA
                return false;
            }

            if((horaIni>horaFin) || (horaIni==horaFin && minIni>minFin)){
                Toast.makeText(this, "Revise la hora", Toast.LENGTH_SHORT).show();
                return false;
            }


        }else{
            return false;
        }

        //CORRECTA
        return true;
    }

    private void obtenerFecha(final int validar){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                    //Muestro la fecha con el formato deseado
                    //1 si es inicial, 0 si es devolucion
                    if(validar == 1){
                            fechaInicio.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                    }else{
                            fechaDevolucion.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                    }
            }
        },anio, mes, dia);
        recogerFecha.show();

    }

    private void obtenerHora(final int validar){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                    //Muestro la hora con el formato deseado
                    if(validar == 1){
                        horaInicio.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                    }else{
                        horaDevolucion.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                    }
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return super.onKeyDown(keyCode, event);
    }
}

