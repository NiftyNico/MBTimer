package mb.android.redalert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.util.Log;

public class Settings implements Serializable {

	private class Secure implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -6412919568306513707L;
		
		private String encrypt(String seed, String cleartext) throws Exception {
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] result = encrypt(rawKey, cleartext.getBytes());
			return toHex(result);
		}

		private String decrypt(String seed, String encrypted) throws Exception {
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(rawKey, enc);
			return new String(result);
		}

		private byte[] getRawKey(byte[] seed) throws Exception {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(seed);
			kgen.init(128, sr); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			return raw;
		}

		private byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(clear);
			return encrypted;
		}

		private byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(encrypted);
			return decrypted;
		}

		/*private String toHex(String txt) {
			return toHex(txt.getBytes());
		}

		private String fromHex(String hex) {
			return new String(toByte(hex));
		}*/

		private byte[] toByte(String hexString) {
			int len = hexString.length() / 2;
			byte[] result = new byte[len];
			for (int i = 0; i < len; i++)
				result[i] = Integer.valueOf(
						hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
			return result;
		}

		private String toHex(byte[] buf) {
			if (buf == null)
				return "";
			StringBuffer result = new StringBuffer(2 * buf.length);
			for (int i = 0; i < buf.length; i++) {
				appendHex(result, buf[i]);
			}
			return result.toString();
		}

		private final static String HEX = "0123456789ABCDEF";

		private void appendHex(StringBuffer sb, byte b) {
			sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
		}
	}

	public static final String SETTINGS_FILE = "Red_Alert_Settings.data";
	private static final long serialVersionUID = 235836905978792519L;

	String userName;
	String passWord;
	int passColor;
	int passImage;
	Date alarmTime;
	Boolean repeatDaily;

	public Settings(Context ctxt) {
		
		try {
			ObjectInputStream ois = new ObjectInputStream(ctxt.openFileInput(SETTINGS_FILE));
			Settings s = (Settings) ois.readObject();
			ois.close();
			Secure sCrypt = new Secure();

			this.userName = sCrypt.decrypt("fortheloveofgoddontseethis", s.userName);
			this.passWord = sCrypt.decrypt("fortheloveofgoddontseethis", s.passWord);
			this.passColor = s.passColor;
			this.passImage = s.passImage;
			this.alarmTime = s.alarmTime;
			this.repeatDaily = s.repeatDaily;
		} catch (Exception ex) {
			Log.v("Serialization Read Error : ", ex.getMessage());
			ex.printStackTrace();

			this.userName = "";
			this.passWord = "";
			this.passColor = -1;
			this.passImage = -1;
			this.alarmTime = null;
			this.repeatDaily = false;
		}
	}

	public void save(Context ctxt) {
		Secure sCrypt = new Secure();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(ctxt.openFileOutput(SETTINGS_FILE, Context.MODE_PRIVATE));
			userName = sCrypt.encrypt("fortheloveofgoddontseethis",userName);
			passWord = sCrypt.encrypt("fortheloveofgoddontseethis",passWord);
			
			oos.writeObject(this);
			oos.flush();
			oos.close();
			Log.v("Save Success! : ", SETTINGS_FILE);
		} catch (Exception ex) {
			Log.v("Serialization Save Error : ", ex.getMessage() + "\n" + ex.toString());
			ex.printStackTrace();
		}
	}

	/*public Settings loadSerializedObject(Context ctxt) {
		try {
			ObjectInputStream ois = new ObjectInputStream(ctxt.openFileInput(SETTINGS_FILE));
			Object o = ois.readObject();
			ois.close();
			return ((Settings) o);
		} catch (Exception ex) {
			Log.v("Serialization Read Error : ", ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}*/

	public String whereToPark() {
		if (passColor < 0)
			return "No Pass Information Registered";
		BigInteger today = new BigInteger(Long.toString(Calendar.getInstance()
				.getTime().getTime()));
		BigInteger sinceEpoch = today.subtract(Constants.Times.EPOCH);
		BigInteger week = new BigInteger(Integer.toString(Constants.Times.WEEK));

		int weekNo = sinceEpoch.divide(week).intValue();

		if (passColor > 4)
			return "Onsite";

		switch ((passColor + weekNo) % 5) {
		case 0:
			return "Onsite";
		case 1:
			return "Offsite";
		case 2:
			return "Farm Bureau";
		case 3:
			return "Offsite";
		case 4:
			return "Offsite";
		default:
			return "This should absolutely never ever happen";
		}
	}
	
	public void resetSettings(Context ctxt){
		ctxt.deleteFile(SETTINGS_FILE); 
	}
}
