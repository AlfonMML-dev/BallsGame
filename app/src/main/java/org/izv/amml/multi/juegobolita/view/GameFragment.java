package org.izv.amml.multi.juegobolita.view;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.izv.amml.multi.juegobolita.R;
import org.izv.amml.multi.juegobolita.databinding.FragmentGameBinding;
import org.izv.amml.multi.juegobolita.model.Ball;
import org.izv.amml.multi.juegobolita.model.GraphicView;

import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment {
    FragmentGameBinding  binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_easy, container, false);
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("GF", "onViewCreated");
        Bundle bundle = getArguments();
        int nivel = bundle.getInt("Nivel");
        Log.v("GF", "onViewCreated, nivel = " + nivel);
        int totalSeconds = (int) (Math.random() * (15)) + 1;
        switch (nivel){
            case 1:
                GraphicView.numColors = 2;
                GraphicView.numBalls = 6;
                totalSeconds = totalSeconds < 5 || totalSeconds > 10 ? 5 : totalSeconds;
                break;
            case 2:
                GraphicView.numColors = 3;
                GraphicView.numBalls = 12;
                totalSeconds = totalSeconds < 10 ? 10 : totalSeconds;
                break;
            case 3:
                GraphicView.numColors = 4;
                GraphicView.numBalls = 18;
                totalSeconds = totalSeconds < 10 ? 10 : totalSeconds;
                break;
        }
        GraphicView.createBalls();

        Log.v("GF", "onViewCreated, totalSeconds = " + totalSeconds);
        totalSeconds = totalSeconds*1000;
        new CountDownTimer(totalSeconds, 1000) {
            public void onTick(long millisUntilFinished) {
                //TextView del fragmento
                binding.tvCounter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                ArrayList<Ball> balls = GraphicView.balls;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("balls", balls);
                MainActivity.music.stopMediaPlayer();
                NavHostFragment.findNavController(GameFragment.this)
                        .navigate(R.id.action_gameFragment_to_guessBallsFragment,bundle);
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("GF", "onDestroyView");
        binding = null;
    }
}