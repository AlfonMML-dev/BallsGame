package org.izv.amml.multi.juegobolita.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.izv.amml.multi.juegobolita.R;
import org.izv.amml.multi.juegobolita.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Hide toolbar
        if(MainActivity.supportActionBar != null){
            ActionBar supportActionBar = MainActivity.supportActionBar;
            supportActionBar.hide();
        }

        //Obtenemos el bundle del primer fragmento, el cual nos indica la dificultad de la partida
        Bundle bundle = getArguments();
        int nivel = bundle.getInt("Nivel");
        TranslateAnimation an  = new TranslateAnimation( 0.0f,0.0f,0.0F,3600.0f);
        an.setDuration(3000);
        binding.tVReady.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                MainActivity.music.playMediaPlayer(getContext(), R.raw.are_you_ready);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_gameFragment,bundle);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}