package com.example.aulafragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aulafragments.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btTeste;
    private TextView tvName, tvGender, tvAge, tvEmail, tvPhone, tvLocation;
    private ImageView ivUserPhoto;

    public OneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        tvName = view.findViewById(R.id.user_name);
        tvGender = view.findViewById(R.id.user_gender);
        tvAge = view.findViewById(R.id.user_age);
        tvEmail = view.findViewById(R.id.user_email);
        tvPhone = view.findViewById(R.id.user_phone);
        tvLocation = view.findViewById(R.id.user_location);
        ivUserPhoto = view.findViewById(R.id.user_photo);

        if (getArguments() != null) {
            User user = (User) getArguments().getSerializable("user");
            if (user != null) {
                atualizarTelaComUsuario(user);
            }
        }

        carregarUsuario();
        return view;
    }

    private void carregarUsuario() {
        Call<Result> call = new RetrofitConfig().getRandomUser().randomUser();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseBody = response.body();
                Log.d("response: ", responseBody.getResults().get(0).gender);
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getResults().get(0);
                    atualizarTelaComUsuario(user);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Context context = getContext();
                Toast.makeText(context, "Falha ao buscar usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarTelaComUsuario(User user) {
        if (user == null) return;

        String nomeCompleto = user.name.first + " " + user.name.last;
        String idade = String.valueOf(user.dob.age);
        String local = "📍 " + user.location.city + ", " + user.location.state + ", " + user.location.country;

        tvName.setText(nomeCompleto);
        tvGender.setText(user.gender);
        tvAge.setText(idade + " years old");
        tvEmail.setText(user.email);
        tvPhone.setText(user.phone);
        tvLocation.setText(local);

        Glide.with(getContext())
                .load(user.picture.large)
                .into(ivUserPhoto);
    }
}