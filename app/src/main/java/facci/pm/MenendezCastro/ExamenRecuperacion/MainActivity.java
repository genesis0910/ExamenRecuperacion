package facci.pm.MenendezCastro.ExamenRecuperacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button get, post;
    EditText cedula, materia, estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button) findViewById(R.id.button_get);
        post = (Button) findViewById(R.id.button_post);

        cedula= (EditText) findViewById(R.id.editText_cedula);
        materia= (EditText) findViewById(R.id.editText_materia);
        estado= (EditText) findViewById(R.id.editText_estado);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealizarPost();
            }
        });
    }

    public void RealizarPost() {

        String url = "https://backend-posts.herokuapp.com/checkin";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("cedula:", cedula.getText().toString());
                params.put("fecha:","12022020");
                params.put("hora:", "12");
                params.put("minuto:", "02");
                params.put("segundo:", "03");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
