package com.example.brendon.sistemadelogin.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.UsuarioLogado;
import com.example.brendon.sistemadelogin.R;

import java.util.List;

import io.objectbox.Box;

public class UpgradsAdapter extends RecyclerView.Adapter<UpgradsAdapter.UpgradsViewHolder>{

    Context context;
    Box<Upgrade> boxUpgrads;
    Box<Personagem> boxPersonagem;
    Box<UsuarioLogado> boxDadosUserLogado;
    List<Upgrade> upgradeListDisponiveis;
    TextView txt_info_sem_gold;

    public UpgradsAdapter(Context context,TextView txt_info_sem_gold, Box<Personagem> boxPersonagem,Box<UsuarioLogado> boxDadosUserLogado, Box<Upgrade> boxUpgrads, List<Upgrade> upgradeListDisponiveis) {
        this.boxUpgrads = boxUpgrads;
        this.boxPersonagem = boxPersonagem;
        this.boxDadosUserLogado = boxDadosUserLogado;
        this.upgradeListDisponiveis = upgradeListDisponiveis;
        this.txt_info_sem_gold = txt_info_sem_gold;
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
        int valorParaComparacao = upAtual.getValor();
        holder.txtNome.setText(upAtual.getNome());
        holder.txtvalor.setText(""+ upAtual.getValor());
        setImageInUp(holder, valorParaComparacao);

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
                    Animation anima_texto = new TranslateAnimation(0,0,0,-100);
                    anima_texto.setDuration(1500);
                    txt_info_sem_gold.startAnimation(anima_texto);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return upgradeListDisponiveis.size();
    }

    private void setImageInUp(final UpgradsViewHolder holder, int valor){
        if(valor == 100) {
            holder.imageUp.setImageResource(R.drawable.forca1);
        }else if(valor == 700) {
            holder.imageUp.setImageResource(R.drawable.forca2);
        }else if(valor == 1900) {
            holder.imageUp.setImageResource(R.drawable.forca1);
        }else if(valor == 3500) {
            holder.imageUp.setImageResource(R.drawable.arma1);
        }else if(valor == 10000) {
            holder.imageUp.setImageResource(R.drawable.arma2);
        }else if(valor == 25000) {
            holder.imageUp.setImageResource(R.drawable.arma3);
        }else if(valor == 43300) {
            holder.imageUp.setImageResource(R.drawable.arma4);
        }else if(valor == 70000) {
            holder.imageUp.setImageResource(R.drawable.forca1);
        }else if(valor == 100000) {
            holder.imageUp.setImageResource(R.drawable.forca3);
        }else if(valor == 260000) {
            holder.imageUp.setImageResource(R.drawable.forca4);
        }else if(valor == 600000) {
            holder.imageUp.setImageResource(R.drawable.forca4);
        }else if(valor == 999999) {
            holder.imageUp.setImageResource(R.drawable.forca5);
        }else if(valor == 2700000) {
            holder.imageUp.setImageResource(R.drawable.arma5);
        }else if(valor == 4400000) {
            holder.imageUp.setImageResource(R.drawable.forca6);
        }

    }

    class UpgradsViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        Button txtvalor;
        ImageView imageUp;

        public UpgradsViewHolder(View view) {
            super(view);

            txtNome = view.findViewById(R.id.Nome_ups);
            txtvalor = view.findViewById(R.id.valor);
            imageUp = view.findViewById(R.id.icon_ups);

        }
    }
}
