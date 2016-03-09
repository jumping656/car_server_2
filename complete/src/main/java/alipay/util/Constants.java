package alipay.util;
/**
 * Created by ejiping on 2016/3/8.
 */
public class Constants {
	public static final String PARTNER = "2088611090934090";
	public static final String KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALQt779LHoifM0yx42v5KpMTmXHSvPVngavyfwBOnszSf3E4JDBedwpcq8fdkAEf8lMwj1NJiZgLANIbC2nta+1Z3m8FXQSusjIuxOeaRMsRHgqKm5ghBpi53J2stejVyk093XwVLGhFNHzfgfCEw8bIKV6Gize/GsjxeTKMMCIFAgMBAAECgYEAqro4SUeRuB05y7tnZkEkEVbp98ua1uHsIhTWOGFG4Ye30H1E9TwdigkDkI+lyxsNT7xcoqu0oAgVEAEmUInyz85ZrgnLWfc3E7QdTqE2gWoXEjpNbZnzLH6x0Txj1NNIoEtqqoCjl8x9Kjr58oXB1o0gq0sAH//kicG98kaXPGECQQDa1TcbiGermQbEfnuZp08ti0u3rBRnGYW5kYcmy/ykBE0DmCftaUZ2bWZacQZ1IWEoUAY85jdC5nifMDkgI3ANAkEA0sgVBN9HcsVsH9tI2zt0IMgG2ZDgOWfPYZLW6bBq7jAp+KuhvAR6tUBqvJpc6vjQik/6n3OcXxTGOwcE564D2QJAE7QxExSUKRpNRrIQ1QQAmBw7xB0FdUcSCGR3I+IoLGiKurEfnzvkcVNmbOxSZYeM5QFXnnIoNajk4YiJDEDEnQJBAJlYpSoRQsq15G7cuwl8HIVI4Orh4q8A3jgPezkydujos3BNdXHzFE7PGZgy+UydTiKCA0SD+IyX8Yu6FJB04ikCQGkJHZVDNSVuuoX+HF1caAU9xLs54uxD+P1UfSEs4bZsC6bZG0dvVjDZCkOnYI1xDtMK3O6ObBC3NAj8H4V+zGA=";
	public static final String SELLER_EMAIL = "kwings@163.com";
	//商户公钥,提交给支付宝
	//MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0Le+/Sx6InzNMseNr+SqTE5lx0rz1Z4Gr8n8ATp7M0n9xOCQwXncKXKvH3ZABH/JTMI9TSYmYCwDSGwtp7WvtWd5vBV0ErrIyLsTnmkTLER4KipuYIQaYudydrLXo1cpNPd18FSxoRTR834HwhMPGyCle

	// 服务器异步通知页面路径
	// 需http://格式的完整路径，不能加?id=123这类自定义参数
	public static String NOTIFY_URL = "http://121.42.45.228:8080/car/pay/async";
	// 页面跳转同步通知页面路径
	// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	public static String return_url = "";
}
