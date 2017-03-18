package br.com.caxamba.agenda.receiver;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.caxamba.agenda.converter.AlunoConverter;
import br.com.caxamba.agenda.dao.AlunoDAO;
import br.com.caxamba.agenda.modelo.Aluno;

/**
 * Created by pedro on 18/03/2017.
 */

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,"Aguarde","Enviando aluno", true, true);
    }

    @Override
    protected String doInBackground(Object... params) {
        
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter converter = new AlunoConverter();
        String json = converter.coverteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {

        dialog.dismiss();
        Toast.makeText(context,(String) resposta, Toast.LENGTH_LONG).show();
    }

}
