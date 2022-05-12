package Entities;

public class paymentInfo {
	public String[] creditPhrases;
	public String CVV;
	public int ZerliCoins;
	public boolean newUser;
	public String expirationMonth;
	public String expirationYear;
	public paymentInfo(String cn,String cvv,String expMonth,String expYear,String zerlicoins,String newUserDisc)
	{
		creditPhrases = new String[] {cn.substring(0, 4),cn.substring(4,8),cn.substring(8,12),cn.substring(12,16)};
		newUser = newUserDisc.equals("1") ? true : false;
		expirationMonth = expMonth;
		expirationYear = expYear;
		CVV = cvv;
		ZerliCoins = Integer.parseInt(zerlicoins);
	}
}
