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
        int totalSeconds = 0;
        switch (nivel){
            case 1:
                GraphicView.numColors = 2;
                GraphicView.numBalls = 6;
                totalSeconds = (int) (Math.random() * (10-5+1)) + 5;
                break;
            case 2:
                GraphicView.numColors = 3;
                GraphicView.numBalls = 12;
                totalSeconds = (int) (Math.random() * (15-10+1)) + 1;
                break;
            case 3:
                GraphicView.numColors = 4;
                GraphicView.numBalls = 18;
                totalSeconds = (int) (Math.random() * (20-15+1)) + 1;
                break;
        }
        GraphicView.createBalls();

//        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.elbhico);

        Log.v("GF", "onViewCreated, totalSeconds = " + totalSeconds);
        totalSeconds = totalSeconds*1000;
        new CountDownTimer(totalSeconds, 1000) {
            public void onTick(long millisUntilFinished) {
                //TextView del fragmento
                binding.tvCounter.setText(String.valueOf(millisUntilFinished / 1000));
//                mp.start();
            }

            public void onFinish() {
                ArrayList<Ball> balls = GraphicView.balls;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("balls", balls);
                NavHostFragment.findNavController(GameFragment.this)
                        .navigate(R.id.action_gameFragment_to_guessBallsFragment,bundle);
//                mp.stop();
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