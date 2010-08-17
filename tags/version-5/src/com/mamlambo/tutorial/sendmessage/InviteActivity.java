/*
 * Copyright (c) 2010, Lauren Darcey and Shane Conder
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are 
 * permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this list of 
 *   conditions and the following disclaimer.
 *   
 * * Redistributions in binary form must reproduce the above copyright notice, this list 
 *   of conditions and the following disclaimer in the documentation and/or other 
 *   materials provided with the distribution.
 *   
 * * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used
 *   to endorse or promote products derived from this software without specific prior 
 *   written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * <ORGANIZATION> = Mamlambo
 */
package com.mamlambo.tutorial.sendmessage;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InviteActivity extends Activity {

    private static final String DEBUG_TAG = "InviteActivity";
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_form);
    }

    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case CONTACT_PICKER_RESULT:
                Cursor cursor = null;
                String email = "";
                try {
                    Bundle extras = data.getExtras();
                    Set<String> keys = extras.keySet();
                    Iterator<String> iterate = keys.iterator();
                    while (iterate.hasNext()) {
                        String key = iterate.next();
                        Log.v(DEBUG_TAG, key + "[" + extras.get(key) + "]");
                    }

                    Uri result = data.getData();
                    Log.v(DEBUG_TAG, "Got a contact result: "
                            + result.toString());

                    // get the contact id from the Uri
                    String id = result.getLastPathSegment();

                    // query for everything email
                    cursor = getContentResolver().query(Email.CONTENT_URI,
                            null, Email.CONTACT_ID + "=?", new String[] { id },
                            null);

                    int emailIdx = cursor.getColumnIndex(Email.DATA);

                    // let's just get the first email
                    if (cursor.moveToFirst()) {

                        /*
                         * Iterate all columns. :) String columns[] =
                         * cursor.getColumnNames(); for (String column :
                         * columns) { int index = cursor.getColumnIndex(column);
                         * Log.v(DEBUG_TAG, "Column: " + column + " == [" +
                         * cursor.getString(index) + "]"); }
                         */

                        email = cursor.getString(emailIdx);

                        Log.v(DEBUG_TAG, "Got email: " + email);

                    } else {
                        Log.w(DEBUG_TAG, "No results");
                    }
                } catch (Exception e) {
                    Log.e(DEBUG_TAG, "Failed to get email data", e);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    EditText emailEntry = (EditText) findViewById(R.id.invite_email);
                    emailEntry.setText(email);
                    if (email.length() == 0) {
                        Toast.makeText(this, "No email found for contact.",
                                Toast.LENGTH_LONG).show();
                    }

                }

                break;
            }

        } else {
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }

}
