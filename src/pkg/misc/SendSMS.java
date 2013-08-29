package pkg.misc;

import java.util.HashMap;
import java.util.Map;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
  
public class SendSMS { 
  
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "ACf2d96f5711c0a523122b8905e61a0cc9";
  public static final String AUTH_TOKEN = "451fe15bce278bbd8046d9c15ed73b73";
  
  public static void sendSms(long phoneNumber, String confirmCode) throws TwilioRestException{
	  TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	  
	    // Build a filter for the SmsList
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("Body", "BookMarkers Validation Code\n"+confirmCode);
	    params.put("To",new Long(phoneNumber).toString());
	    params.put("From", "+14089142011");
	      
	    SmsFactory messageFactory = client.getAccount().getSmsFactory();
	    Sms message = messageFactory.create(params);
	    System.out.println(message.getSid());
  }
}