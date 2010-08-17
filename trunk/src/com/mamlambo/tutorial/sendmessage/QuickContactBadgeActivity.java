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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;

public class QuickContactBadgeActivity extends Activity {
    private static final String DEBUG_TAG = "QuickContactBadgeActivity";
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.badge);

        QuickContactBadge badgeSmall = (QuickContactBadge) findViewById(R.id.badge_small);
        badgeSmall.assignContactFromEmail("youremail@gmail.com", true);
        badgeSmall.setMode(ContactsContract.QuickContact.MODE_SMALL);

        QuickContactBadge badgeMedium = (QuickContactBadge) findViewById(R.id.badge_medium);
        // badgeMedium.assignContactFromEmail("unknown@gmail.com", true);
        badgeMedium.assignContactFromPhone("831-555-1212", true);
        badgeMedium.setImageResource(R.drawable.droid_small);

    }

    public void onPickContact(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case CONTACT_PICKER_RESULT:
                Uri contactUri = data.getData();
                FrameLayout badgeLargeHolder = (FrameLayout) findViewById(R.id.badge_holder_large);

                QuickContactBadge badgeLarge = new QuickContactBadge(this);
                badgeLarge.assignContactUri(contactUri);
                badgeLarge.setMode(ContactsContract.QuickContact.MODE_LARGE);
                badgeLarge.setImageResource(R.drawable.droid_small);
                badgeLargeHolder.addView(badgeLarge);
                break;
            }
        }
    }

}
