package src.buildlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * java -cp ./lib/storm-demo-0.0.1-SNAPSHOT.jar:./lib/log4j-api-2.6.2.jar:./lib/log4j-core-2.6.2.jar  src.buildlog.OrdersInfoGenerator
 * @author Administrator
 *
 */
public class OrdersInfoGenerator {
	public enum paymentWays {
		Wechat,Alipay,Paypal
	}
	public enum merchantNames {
		优衣库,天猫,淘宝,咕噜大大,快乐宝贝,守望先峰,哈毒妇,Storm,Oracle,Java,CSDN,跑男,路易斯威登,
		暴雪公司,Apple,Sumsam,Nissan,Benz,BMW,Maserati
	}
	
	public enum productNames {
		黑色连衣裙, 灰色连衣裙, 棕色衬衫, 性感牛仔裤, 圆脚牛仔裤,塑身牛仔裤, 朋克卫衣,高腰阔腿休闲裤,人字拖鞋,
		沙滩拖鞋
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	Random random = new Random();
	float[] skuPriceGroup = {299,399,699,899,1000,2000};
	float[] discountGroup = {10,20,50,100};
	float totalPrice = 0;
	float discount = 0;
	float paymentPrice = 0;
	
	private static final Logger logger = LogManager.getLogger(OrdersInfoGenerator.class);
	private int logsNumber = 1000;
	
	public void generate() {
				
		for(int i = 0; i <= logsNumber; i++) {			
			logger.info(randomOrderInfo());			
		}
	}
	
	public String randomOrderInfo() {
		
			
		Date date = new Date();		
		
		String orderNumber = randomNumbers(5) + date.getTime();
		
		String orderDate = sdf.format(date);
		
		String paymentNumber = randomPaymentWays() + "-" + randomNumbers(8);
		
		String paymentDate = sdf.format(date);
		
		String merchantName = randomMerchantNames();
		
		String skuInfo = randomSkus();
		
		String priceInfo = calculateOrderPrice();
		
		return "orderNumber: " + orderNumber + " | orderDate: " + orderDate + " | paymentNumber: " +
			paymentNumber + " | paymentDate: " + paymentDate + " | merchantName: " + merchantName + 
			" | sku: " + skuInfo + " | price: " + priceInfo;
	}
		
	private String randomPaymentWays() {
		
		paymentWays[] paymentWayGroup = paymentWays.values();
		return paymentWayGroup[random.nextInt(paymentWayGroup.length)].name();
	}
	
	private String randomMerchantNames() {
		
		merchantNames[] merchantNameGroup = merchantNames.values();
		return merchantNameGroup[random.nextInt(merchantNameGroup.length)].name();
	}
	
	private String randomProductNames() {
		
		productNames[] productNameGroup = productNames.values();
		return productNameGroup[random.nextInt(productNameGroup.length)].name();
	}
	
	
	private String randomSkus() {
		
		
		
		
		String skuInfo ="[";
		totalPrice = 0;
		for(int i = 1; i<= 3; i++) {
			
			int skuNum = random.nextInt(3)+1;
			float skuPrice = skuPriceGroup[random.nextInt(skuPriceGroup.length)];
			float totalSkuPrice = skuPrice * skuNum;			
			String skuName = randomProductNames();
			String skuCode = randomCharactersAndNumbers(10);
			skuInfo += " skuName: " + skuName + " skuNum: " + skuNum + " skuCode: " + skuCode
					+ " skuPrice: " + skuPrice + " totalSkuPrice: " + totalSkuPrice + ";";		
			totalPrice += totalSkuPrice;
		}
		
		
		skuInfo += " ]";
		
		return skuInfo;
	}
	
	private String calculateOrderPrice() {
		
		Random random = new Random();
		discount = discountGroup[random.nextInt(discountGroup.length)];
		paymentPrice = totalPrice - discount;
		
		String priceInfo = "[ totalPrice: " + totalPrice + " discount: " + discount + " paymentPrice: " + paymentPrice +" ]";
		
		return priceInfo;
	}
	
	private String randomCharactersAndNumbers(int length) {
		
		String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
		String randomCharacters = "";  

                for (int i = 0; i < length; i++) {  
        	  randomCharacters += characters.charAt(random.nextInt(characters.length()));  
                }  
                return randomCharacters;  
	}
	
	private String randomNumbers(int length) {
		
		String characters = "0123456789";
		String randomNumbers = "";   
                for (int i = 0; i < length; i++) {  
        	      randomNumbers += characters.charAt(random.nextInt(characters.length()));  
                }  
               return randomNumbers;		
	}
	
	public static void main(String[] args) {
		OrdersInfoGenerator generator = new OrdersInfoGenerator();
		generator.generate();
	}

}
