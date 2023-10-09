package com.example.inclass06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.inclass06.databinding.ContactListItemBinding;
import com.example.inclass06.databinding.FragmentContactsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
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
FragmentContactsBinding binding;
    ContactsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contacts");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ContactsAdapter();
        binding.recyclerView.setAdapter(adapter);

        getContacts();
    }
    class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{


        @NonNull
        @Override
        public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContactsViewHolder(ContactListItemBinding.inflate(getLayoutInflater(), parent, true));
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
            Contacts contacts = contactsArrayList.get(position);
            holder.setUpUI(contacts);
        }

        @Override
        public int getItemCount() {
            return contactsArrayList.size();
        }

        class ContactsViewHolder extends RecyclerView.ViewHolder{
            ContactListItemBinding mBinding;
            Contacts mContact;

            public ContactsViewHolder(@NonNull ContactListItemBinding vhBinding) {

                super(vhBinding.getRoot());
                this.mBinding = vhBinding;
            }
            void setUpUI(Contacts contacts){
                this.mContact = contacts;
                mBinding.textViewName.setText(mContact.Name);
                mBinding.textViewEmail.setText(mContact.Email);
                mBinding.textViewPhone.setText(mContact.Phone);
                mBinding.textViewPhoneType.setText(mContact.PhoneType);
            }
        }
    }
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    void getContacts(){
        contactsArrayList.clear();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseBody = response.body().string();
                    Log.d("demo","onResponse"+ responseBody);
                    try {
                        JSONObject json = new JSONObject(responseBody);
                        Log.d("demo1", json.toString());
                        JSONArray contactsJsonArray = json.getJSONArray("contacts");
                        Log.d("contacts",contactsJsonArray.toString());
                        for (int i = 0; i < contactsJsonArray.length(); i++) {
                            JSONObject object = contactsJsonArray.getJSONObject(i);
                            Contacts contacts = new Contacts();
                            contacts.setCid(object.getString("Cid"));
                            contacts.setEmail(object.getString("Email"));
                            contacts.setName(object.getString("Name"));
                            contacts.setPhone(object.getString("Phone"));
                            contacts.setPhoneType(object.getString("PhoneType"));
                            contactsArrayList.add(contacts);
                            Log.d("eachcontact",object.getString("Name"));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Log.d("error","error");
                }
            }
        });
    }
}