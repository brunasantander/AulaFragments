package com.example.aulafragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aulafragments.user.User;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spinnerGender, spinnerNationality;
    private Button buttonSearch;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private OneFragment oneFragment = new OneFragment();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentManager=getParentFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        return inflater.inflate(R.layout.fragment_two, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinnerGender = view.findViewById(R.id.spinner_gender);
        spinnerNationality = view.findViewById(R.id.spinner_nationality);
        buttonSearch = view.findViewById(R.id.button_search);

        // Setup dos spinners
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item,
                new String[]{"all", "male", "female"});
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item,
                new String[]{"all","AU", "BR", "CA", "CH", "DE", "DK", "ES", "FI", "FR", "GB", "IE", "IN", "IR", "MX", "NL", "NO", "NZ", "RS", "TR", "UA", "US"});
        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNationality.setAdapter(nationalityAdapter);

        buttonSearch.setOnClickListener(v -> buscarUsuario());
    }

    private void buscarUsuario() {
        String genero = spinnerGender.getSelectedItem().toString();
        String nacionalidade = spinnerNationality.getSelectedItem().toString();

        // Ajustes para chamada
        String genderParam = genero.equals("all") ? null : genero;
        String natParam = nacionalidade.equals("all") ? null : nacionalidade;

        Call<Result> call = new RetrofitConfig()
                .getRandomUser()
                .randomUserFiltered(genderParam, natParam);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getResults().get(0);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);

                    oneFragment.setArguments(bundle);

                    trocarFragment(oneFragment);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Context context = getContext();
                Toast.makeText(context, "Falha ao buscar usuário com filtro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void trocarFragment(Fragment fragment) {
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}