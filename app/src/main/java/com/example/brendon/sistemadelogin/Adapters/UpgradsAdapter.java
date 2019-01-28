package com.example.brendon.sistemadelogin.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.Models.UsuarioLogado;
import com.example.brendon.sistemadelogin.R;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class UpgradsAdapter extends RecyclerView.Adapter<UpgradsAdapter.UpgradsViewHolder>{

    Context context;
    Box<Upgrade> boxUpgrads;
    Box<Personagem> boxPersonagem;
    Box<UsuarioLogado> boxDadosUserLogado;
    List<Upgrade> upgradeListDisponiveis;

    public UpgradsAdapter(Context context, Box<Personagem> boxPersonagem,Box<UsuarioLogado> boxDadosUserLogado, Box<Upgrade> boxUpgrads, List<Upgrade> upgradeListDisponiveis) {
        this.boxUpgrads = boxUpgrads;
        this.boxPersonagem = boxPersonagem;
        this.boxDadosUserLogado = boxDadosUserLogado;
        this.upgradeListDisponiveis = upgradeListDisponiveis;
        this.context = context;
    }

    @NonNull
    @Override
    public UpgradsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_ups, viewGroup, false);
        return new UpgradsViewHolder(view);
    }

    @SuppressLint({"SetTextI18n","RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull final UpgradsViewHolder holder, final int position) {
        final Upgrade upAtual = this.upgradeListDisponiveis.get(position);
        holder.txtNome.setText(upAtual.getNome());
        holder.txtvalor.setText(""+ upAtual.getValor());

        holder.txtvalor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idUserLogado = boxDadosUserLogado.getAll().get(0).getNun_id();
                int goldAtual = boxPersonagem.getAll().get(idUserLogado - 1).getGold();
                int valorUp = upAtual.getValor();
                if (goldAtual >= valorUp){
                    MediaPlayer mp = MediaPlayer.create(context, R.raw.level_up);
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                    mp.start();

                    int valorMelhoriaAdiconada = upAtual.getMelhoria();
                    int valorAtualClique = boxPersonagem.getAll().get(idUserLogado - 1).getPoderClique();
                    int novoValorClique = valorAtualClique * valorMelhoriaAdiconada;
                    int novoValorGold = goldAtual - valorUp;
                    Personagem personagem = boxPersonagem.get(idUserLogado);
                    personagem.setPoderClique(novoValorClique);
                    personagem.setGold(novoValorGold);
                    boxPersonagem.put(personagem);

                    boxUpgrads.remove(upAtual);
                    upgradeListDisponiveis.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeRemoved(position, getItemCount());

                }else{
                    Toast.makeText(context, "Blood Coins insuficiente", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return upgradeListDisponiveis.size();
    }


    public class UpgradsViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        Button txtvalor;

        public UpgradsViewHolder(View view) {
            super(view);
            txtNome = view.findViewById(R.id.Nome_ups);
            txtvalor = view.findViewById(R.id.valor);

        }
    }
}
