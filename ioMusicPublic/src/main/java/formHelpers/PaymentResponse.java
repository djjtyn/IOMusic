//This class stores the stripe client secret keys when a payment is made 

package formHelpers;

public class PaymentResponse {
	
    private String clientSecretKey;
    
    public PaymentResponse(String clientSecretKey) {
      this.clientSecretKey = clientSecretKey;
    }

    public String getClientSecretKey() {
        return clientSecretKey;
    }

    public void setClientSecret(String clientSecretKey) {
        this.clientSecretKey = clientSecretKey;
    }

}
