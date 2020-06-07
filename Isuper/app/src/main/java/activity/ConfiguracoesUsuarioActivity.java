package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;

import helper.ConfiguracaoFirebase;
import helper.UsuarioFirebase;
import isuper.com.br.R;
import model.Empresa;
import model.Usuario;

public class ConfiguracoesUsuarioActivity extends AppCompatActivity {
private EditText editUsuarioNome;
private  EditText editUsuarioEndereco;
private String idUsuario;
private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_usuario);

        inicializarComponentes();
        idUsuario = UsuarioFirebase.getIdUsuario();
        firebaseRef = ConfiguracaoFirebase.getFirebase();

        //Configurações Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações usuário");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recuperarDadosUsuario();

    }
    private  void recuperarDadosUsuario(){
        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuario);

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    editUsuarioNome.setText(usuario.getNome());
                    editUsuarioEndereco.setText(usuario.getEndereco());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void validarDadosUsuario(View view) {
        String nome = editUsuarioNome.getText().toString();
        String endereco = editUsuarioEndereco.getText().toString();
        if( !nome.isEmpty()){
            if( !endereco.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);
                usuario.setNome(nome);
                usuario.setEndereco(endereco);
                usuario.salvar();
                exibirMensagem("Dados Atualizados com sucesso");
                finish();

            }else{
                exibirMensagem("Digite seu endereço");
            }
        }else{
            exibirMensagem("Digite seu nome");
        }
    }
    private  void inicializarComponentes(){
        editUsuarioNome = findViewById(R.id.editUsuarioNome);
        editUsuarioEndereco = findViewById(R.id.editUsuarioEndereco);
    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }
}
