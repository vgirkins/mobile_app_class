// https://forums.bignerdranch.com/t/challenge-2-question/13561

package com.csci448.vgirkins.criminalintent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;


/**
 * Created by Tori on 2/4/2018.
 */

public class CrimeFragment extends Fragment {

    private static final String[] CONTACTS_PERMISSIONS = new String[] {
            Manifest.permission.READ_CONTACTS,
    };
    private static final int REQUEST_READ_CONTACT_PERMISSION = 0;

    private static final String ARG_CRIME_ID = "crime_id";
    private static final int REQUEST_CONTACT = 1;

    private Crime mCrime;
    private String mSuspectId;
    private EditText mTitleField;
    private EditText mDetailsField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    final Intent callSuspect = new Intent(Intent.ACTION_DIAL);
    private Button mSuspectButton;
    private Button mReportButton;
    private Button mCallSuspectButton;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activity
     */
    public interface Callbacks {
        void onCrimeUpdated(Crime crime);
    }


    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing here for now, just following the tutorial
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                updateCrime();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing here for now, just following the tutorial
            }
        });


        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                updateCrime();
            }
        });

        mReportButton = v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        mCallSuspectButton = v.findViewById(R.id.call_suspect);
        mCallSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasReadContactPermission()) {
                    callSuspect();
                }
                else {
                    requestPermissions(CONTACTS_PERMISSIONS, REQUEST_READ_CONTACT_PERMISSION);
                }
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if (mCrime.getSuspect() != null) {
            mSuspectButton.setText(mCrime.getSuspect());
        }

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.setEnabled(false);
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CONTACT && data != null) {
            // Get suspect name

            Uri contactUri = data.getData();
            // Specify which fields you want your query to return values for
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts._ID
            };
            // Perform your query - the contactUri is like a "where" clause here
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            try {
                // Double-check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }

                // Pull out the first column of the first row of data - that is your suspect's name
                c.moveToFirst();
                String suspect = c.getString(0);
                mSuspectId = c.getString(1);
                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);

            } finally {
                c.close();
            }

            // Now update the suspect's phone number
            if (hasReadContactPermission()) {
                updateSuspectPhone();
            }
            else {
                requestPermissions(CONTACTS_PERMISSIONS, REQUEST_READ_CONTACT_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode) {
            case REQUEST_READ_CONTACT_PERMISSION:
                if (hasReadContactPermission()) {
                    updateSuspectPhone();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateCrime() {
        mCallbacks.onCrimeUpdated(mCrime);
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        }
        else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        }
        else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }

    private boolean hasReadContactPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), CONTACTS_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void updateSuspectPhone() {
        String suspectPhoneNum = null;

        Uri phoneContactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] queryFieldsPhone = new String[] {
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE,
        };

        String mSelectionClause = ContactsContract.Data.CONTACT_ID + " = ?";

        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = mSuspectId;

        Cursor cPhone = getActivity().getContentResolver().query(phoneContactUri, queryFieldsPhone, mSelectionClause, mSelectionArgs, null);

        try {
            if (cPhone.getCount() == 0) {
                return;
            }

            while (cPhone.moveToNext()) {
                int phoneType = cPhone.getInt(cPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                    suspectPhoneNum = cPhone.getString(cPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    break;
                }
            }
        } finally {
            cPhone.close();
        }
        mCrime.setPhoneNumber(suspectPhoneNum);
    }

    private void callSuspect() {
        if (mSuspectId == null) {
            return;
        }

        Uri number = Uri.parse("tel:" + mCrime.getPhoneNumber());
        callSuspect.setData(number);
        startActivity(callSuspect);

    }
}
