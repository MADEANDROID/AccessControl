package com.example.accesscontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Formulario extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    // variables globales
    TextView ocupacion;
    Spinner ocupaciones;
    Boolean radio;

    EditText nombre;
    EditText paterno;
    EditText materno;
    EditText ci;
    EditText edad;
    EditText correo;
    EditText celular;
    EditText dpto;
    EditText insti;
    RadioButton radio1;
    RadioButton radio2;
    RadioGroup rg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        ocupacion = (TextView) findViewById(R.id.ocu);
        ocupaciones = (Spinner) findViewById(R.id.idSpinnerOcu);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ocupaciones, android.R.layout.simple_spinner_item);
        ocupaciones.setAdapter(adapter);

        ocupaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

                //ocupacion.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void eventoOnClick(View view) {
        Toast.makeText(getApplicationContext(),
                "vaya ajuste y conexiones y verifique si su telefono cuenta con NFC",Toast.LENGTH_LONG).show();
    }

    public void onclick(View view) {
        String url = "http://192.168.0.25:8080/api/usuarios";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        nombre = (EditText) findViewById(R.id.etNombre);
        paterno = (EditText) findViewById(R.id.etApPat);
        materno = (EditText) findViewById(R.id.etApMat);
        ci = (EditText) findViewById(R.id.etCi);
        edad = (EditText) findViewById(R.id.etEdad);
        correo = (EditText) findViewById(R.id.etCorreo);
        celular = (EditText) findViewById(R.id.etCelular);
        dpto = (EditText) findViewById(R.id.etDpto);
        insti = (EditText) findViewById(R.id.etInst);
        radio1 = ( RadioButton ) findViewById(R.id.idRadio1);
        radio2 = ( RadioButton ) findViewById(R.id.idRadio2);
        rg1 = ( RadioGroup ) findViewById(R.id.rg1);

//lista de json - crear un objeto a enviar en json
        JSONObject obJson = new JSONObject();
        try {
            obJson.put( "nombre", nombre.getText());
            obJson.put( "ap_paterno", paterno.getText());
            obJson.put( "ap_materno", materno.getText() );
            obJson.put( "ci", ci.getText() );
            obJson.put( "edad", Integer.parseInt(String.valueOf(edad.getText())) );
            obJson.put( "nfc", true );
            obJson.put( "correo", correo.getText() );
            obJson.put( "ocupacion", "informatico" );
            obJson.put( "celular", celular.getText() );
            obJson.put( "dpto", dpto.getText() );
            obJson.put( "institucion", insti.getText() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                insti.setText((CharSequence) response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                insti.setText(error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

        Intent miIntent= new Intent(Formulario.this,DatosAceptados1.class);
        startActivity(miIntent);
        finish();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if( radio1.isChecked())
        {
            radio = true;
        }
        if( radio2.isChecked())
        {
            radio = false;
        }
    }
}