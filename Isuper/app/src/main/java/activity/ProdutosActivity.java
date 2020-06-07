package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterProduto;
import helper.ConfiguracaoFirebase;
import isuper.com.br.R;
import model.Empresa;
import model.Produto;

public class ProdutosActivity extends AppCompatActivity {

    private RecyclerView recyclerProdutosCardapio;
    private ImageView imageEmpresaProdutos;
    private TextView textNomeEmpresaProdutos;
    private Empresa empresaSelecionada;
    private String idEmpresa;

    private AdapterProduto adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private DatabaseReference firebaserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        inicializarComponentes();
        firebaserRef = ConfiguracaoFirebase.getFirebase();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            empresaSelecionada =(Empresa) bundle.getSerializable("empresa");

            textNomeEmpresaProdutos.setText(empresaSelecionada.getNome());
            idEmpresa = empresaSelecionada.getIdUsuario();
            String url = empresaSelecionada.getUrlImagem();
            Picasso.get().load(url).into(imageEmpresaProdutos);
        }
        //Configurações da Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Produtos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerProdutosCardapio.setLayoutManager(new LinearLayoutManager(this));
        recyclerProdutosCardapio.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtos, this);
        recyclerProdutosCardapio.setAdapter(adapterProduto);


        recuperarProdutos();


    }
    private void recuperarProdutos(){
        DatabaseReference produtosRef = firebaserRef
                .child("produtos")
                .child(idEmpresa);
        produtosRef.addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                produtos.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    produtos.add(ds.getValue(Produto.class));
                }
                adapterProduto.notifyDataSetChanged();

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }
    private void inicializarComponentes(){
        recyclerProdutosCardapio = findViewById(R.id.recyclerPordutosCardapio);
        imageEmpresaProdutos = findViewById(R.id.imageEmpresaProdutos);
        textNomeEmpresaProdutos = findViewById(R.id.textNomeEmpresaProdutos);
    }
}