package com.example.user.jolp_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menur extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menur);

        Intent intent = getIntent();
        id = (String) intent.getStringExtra("id");

//        ImageButton rent = (ImageButton)findViewById(R.id.button_rent);
//        rent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(Menur.this, MapsMarkerActivity.class);
//                intent1.putExtra("id",id);
//                startActivity(intent1);
//            }
//        });
//        ImageButton  turn = (ImageButton)findViewById(R.id.button_return);
//        turn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(Menur.this, Return.class);
//                intent1.putExtra("id",id);
//                startActivity(intent1);
//            }
//        });
        ImageButton stat = (ImageButton)findViewById(R.id.btn_stat);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Menur.this, Statistics.class);
//                intent1.putExtra("id",id);
                startActivity(intent1);
            }
        });

        ImageButton em = (ImageButton)findViewById(R.id.btn_em);
        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Menur.this, Emergency.class);
//                intent1.putExtra("id",id);
                startActivity(intent1);
            }
        });
//        ImageButton card = (ImageButton)findViewById(R.id.button_card);
//        card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(Menur.this, Card.class);
//                startActivity(intent1);
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_view_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
