package org.izv.amml.multi.juegobolita.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.izv.amml.multi.juegobolita.R;
import org.izv.amml.multi.juegobolita.databinding.FragmentGuessBallsBinding;
import org.izv.amml.multi.juegobolita.model.Ball;
import org.izv.amml.multi.juegobolita.model.GraphicView;

import java.util.ArrayList;
import java.util.HashMap;


public class GuessBallsFragment extends Fragment {

    FragmentGuessBallsBinding binding;
    ArrayList<Ball> balls;
//    int numColors;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_easy, container, false);
        binding = FragmentGuessBallsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view.getContext());
    }

    private void initialize(Context context) {
        Bundle bundle = getArguments();
        balls = bundle.getParcelableArrayList("balls");
        binding.btComprobar.setOnClickListener(v -> {
            if(!checkFields()){
                Toast toast = Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else{
                if (!checkAnswer(correctAnswerBallsPerColor(), getUserInputData())){
                    Toast toast = Toast.makeText(context, "Has fallado", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    int numIntentos = Integer.parseInt(binding.tVNumIntentos.getText().toString());
                    if(numIntentos > 1){
                        binding.tVNumIntentos.setText(String.valueOf(--numIntentos));
                    } else{
                        NavHostFragment.findNavController(GuessBallsFragment.this)
                                .navigate(R.id.action_guessBallsFragment_to_FirstFragment);
                    }
                } else{
                    Toast toast = Toast.makeText(context, "Has acertado", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    NavHostFragment.findNavController(GuessBallsFragment.this)
                            .navigate(R.id.action_guessBallsFragment_to_FirstFragment);
                }
            }
        });
    }

    public boolean checkFields(){
        boolean result = true;
        if(binding.eTRojo.getText().toString().isEmpty()
                || binding.eTAmarillo.getText().toString().isEmpty()
                || binding.eTAzul.getText().toString().isEmpty()
                || binding.eTVerde.getText().toString().isEmpty()){
            result = false;
        }
        return result;
    }

    private boolean checkAnswer(HashMap<Integer, Integer> correctAnswerBallsPerColor, 
                                HashMap<Integer, Integer> getUserInputData){
        boolean result = true;
        for (Integer idColor : correctAnswerBallsPerColor.keySet()) {
            if(correctAnswerBallsPerColor.get(idColor).intValue() 
                    != getUserInputData.get(idColor).intValue()){
                result = false;
                break;
            }
        }
        return result;
    }

    private HashMap<Integer, Integer> correctAnswerBallsPerColor(){
        HashMap<Integer, Integer> result = new HashMap<>();
        int[][] numBallsPerColor = new int[GraphicView.colors.size()][2];
        for (int i = 0; i < numBallsPerColor.length; i++) {
            numBallsPerColor[i][0] = GraphicView.colors.get(i);
            for (int j = 0; j < balls.size(); j++) {
                if(numBallsPerColor[i][0] == balls.get(j).paint.getColor()){
                    numBallsPerColor[i][1]++;
                }
            }
            result.put(numBallsPerColor[i][0], numBallsPerColor[i][1]);
        }
        return result;
    }

    private HashMap<Integer, Integer> getUserInputData(){
        HashMap<Integer, Integer> result = new HashMap<>();
        int numRojo = 0;
        int numAmarillo = 0;
        int numAzul = 0;
        int numVerde = 0;
        if(GraphicView.colors.size() >= 2){
            numRojo = Integer.parseInt(binding.eTRojo.getText().toString());
            numAmarillo = Integer.parseInt(binding.eTAmarillo.getText().toString());
        }
        if(GraphicView.colors.size() >= 3){
            numAzul = Integer.parseInt(binding.eTAzul.getText().toString());
        }
        if(GraphicView.colors.size() == 4){
            numVerde = Integer.parseInt(binding.eTVerde.getText().toString());
        }
        int[] value_eT_colors = {numRojo, numAmarillo, numAzul, numVerde};
        for (int i = 0; i < value_eT_colors.length; i++) {
            result.put(GraphicView.colors.get(i), value_eT_colors[i]);
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}