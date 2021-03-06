package com.burakerol.android.finalprojesi;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak-PC on 26.12.2017.
 */

public class urun extends AppCompatActivity {

    Spinner kategori;
    EditText ad,fiyat,stok;
    Button ekle;
    ArrayList<HashMap<String,String>> urunler,kategoriisimleri;
    String urunadlari[],kadlari[];
    int kategoriler[];
    ListView lv;
    ArrayAdapter<String> adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun);

        kategori=(Spinner) findViewById(R.id.kategoriSpin);
        ad=(EditText) findViewById(R.id.txtad);
        fiyat=(EditText) findViewById(R.id.txtfiyat);
        stok=(EditText) findViewById(R.id.txtstok);
        ekle=(Button) findViewById(R.id.Ekle);
        lv=(ListView) findViewById(R.id.lv);

        Database db=new Database(getApplicationContext());
        kategoriisimleri=db.kategoriListe();
        urunler=db.urunListe();
        urunadlari=new String[urunler.size()];
        for (int i = 0; i < urunler.size(); i++) {
            urunadlari[i] = urunler.get(i).get("u_ad");
        }

        kadlari = new String[kategoriisimleri.size()];
        kategoriler= new int[kategoriisimleri.size()];
        for (int i = 0; i < kategoriisimleri.size(); i++) {
            kadlari[i] = kategoriisimleri.get(i).get("kat_isim");
            kategoriler[i] = Integer.parseInt(kategoriisimleri.get(i).get("kat_id"));
        }

        ArrayAdapter<String> adaptor2 =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,kadlari);
        adaptor2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(adaptor2);


        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int onay = 0;
                String isim,ukategori;
                int ustok,ufiyat;
                isim=ad.getText().toString().trim();
                ustok=Integer.parseInt(stok.getText().toString().trim());
                ufiyat=Integer.parseInt(fiyat.getText().toString().trim());
                ukategori=kategori.getSelectedItem().toString().trim();
                Database db=new Database(getApplicationContext());


                urunler = db.urunListe();
                urunadlari = new String[urunler.size()];
                for (int i = 0; i < urunler.size(); i++) {
                    if(isim.equals(urunler.get(i).get("u_ad")))
                    {
                        onay=1;
                        Toast.makeText(getApplicationContext(),"Bu ürün adı sistemimizde mevcuttur. Lütfen değiştirin",Toast.LENGTH_LONG).show();
                    }
                }
                if(onay!=1)
                {
                    db.urunEkle (isim,ukategori,ustok,ufiyat);
                    db.close();

                    finish();
                    startActivity(getIntent());}
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,urunadlari);
        lv.setAdapter(adapter);


    }
}
