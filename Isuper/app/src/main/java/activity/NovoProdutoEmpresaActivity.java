package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import helper.UsuarioFirebase;
import isuper.com.br.R;
import model.Produto;

public class NovoProdutoEmpresaActivity extends AppCompatActivity {
    private EditText editProdutoNome, editProdutoDescricao, editProdutoPreco;
    private FirebaseAuth autenticacao;
    private  String idUsuarioLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_produto_empresa);

        inicializarComponentes();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //Configurações da Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Produto");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validarDadosProduto(View view) {

        //Valida se os campos foram preenchidos
        String nome = editProdutoNome.getText().toString();
        String descricao = editProdutoDescricao.getText().toString();
        String preco = editProdutoPreco.getText().toString();


        if (!nome.isEmpty()) {
            if (!descricao.isEmpty()) {
                if (!preco.isEmpty()) {
                    Produto produto = new Produto();
                    produto.setIdUsuario(idUsuarioLogado);
                    produto.setNome(nome);
                    produto.setDescricao(descricao);
                    produto.setPreco(Double.parseDouble(preco));
                    produto.salvar();
                    finish();
                    exibirMensagem("Produto salvo com sucesso.");

                } else {
                    exibirMensagem("Digite uma preço");
                }
            } else {
                exibirMensagem("Digite uma descrição para o produto");
            }
        } else {
            exibirMensagem("Digite um nome para o produto");
        }
    }
        private void exibirMensagem(String texto){
            Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                    .show();
        }


    private void inicializarComponentes(){
        editProdutoNome = findViewById(R.id.editUsuarioNome);
        editProdutoDescricao = findViewById(R.id.editUsuarioEndereco);
        editProdutoPreco = findViewById(R.id.editProdutoPreco);

    }


}
