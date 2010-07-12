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

import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SendMessageActivity extends Activity {

    private static final String LOG_TAG = "EmailLauncherActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Send a simple plain text email (no contents supplied)
    public void sendSimpleEmail(View button) {
        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            startActivity(emailIntent);
        } catch (Exception e) {
            Log.e(LOG_TAG, "sendSimpleEmail() failed to start activity.", e);
            Toast.makeText(this, "No handler", Toast.LENGTH_LONG).show();
        }
    }

    public void sendPlainTextEmail(View button) {
        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            String aEmailList[] = { getResources().getString(
                    R.string.email_address) };
            String aEmailCCList[] = { getResources().getString(
                    R.string.email_address_cc) };
            String aEmailBCCList[] = { getResources().getString(
                    R.string.email_address_bcc) };

            emailIntent
                    .putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
            emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
            emailIntent.putExtra(android.content.Intent.EXTRA_BCC,
                    aEmailBCCList);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    getResources().getString(R.string.email_subject));

            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    getResources().getString(R.string.email_message));

            startActivity(emailIntent);
        } catch (Exception e) {
            Log.e(LOG_TAG, "sendPlainTextEmail() failed to start activity.", e);
            Toast.makeText(this, "No handler", Toast.LENGTH_LONG).show();
        }
    }

    public void chooseEmail(View button) {

        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            startActivity(Intent.createChooser(emailIntent,
                    "Send your email in:"));
        } catch (Exception e) {
            Log.e(LOG_TAG, "chooseEmail() failed to start activity.", e);
            Toast.makeText(this, "No handler", Toast.LENGTH_LONG).show();
        }
    }

    public void sendPictureMessage(View button) {
        try {
            Intent picMessageIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            picMessageIntent.setType("image/jpeg");
            File downloadedPic = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "q.jpeg");

            picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri
                    .fromFile(downloadedPic));

            startActivity(Intent.createChooser(picMessageIntent, getResources()
                    .getString(R.string.chooser_pic)));
        } catch (Exception e) {
            Log.e(LOG_TAG, "sendPictureMessage() failed to start activity.", e);
            Toast.makeText(this, "No handler", Toast.LENGTH_LONG).show();
        }
    }
    
    
    public void launchForm(View button) {

        Intent launchFormActivity = new Intent(this, FormActivity.class); 
        startActivity(launchFormActivity);

    }

}