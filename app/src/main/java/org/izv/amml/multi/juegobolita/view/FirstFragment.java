package org.izv.amml.multi.juegobolita.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.izv.amml.multi.juegobolita.R;
import org.izv.amml.multi.juegobolita.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RadioGroup rgLevel;
    private RadioButton rbtFacil, rbtMedio, rbtDificil;
    private int dificultad;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Show toolbar
        if(MainActivity.supportActionBar != null){
            ActionBar supportActionBar = MainActivity.supportActionBar;
            supportActionBar.show();
        }

        rgLevel = binding.rgLevel;
        rbtFacil = binding.rbtFacil;
        rbtMedio = binding.rbtMedio;
        rbtDificil = binding.rbtDificil;
        binding.btJugarFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idSelected = rgLevel.getCheckedRadioButtonId();
                if(rbtFacil.getId() == idSelected){
                    dificultad = 1;
                }
                if(rbtMedio.getId() == idSelected){
                    dificultad = 2;
                }
                if(rbtDificil.getId() == idSelected){
                    dificultad = 3;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("Nivel", dificultad);
                MainActivity.music.playSoundPool(getContext(), R.raw.ui_quirky_levelselected);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}