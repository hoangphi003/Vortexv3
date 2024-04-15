package com.techvortex.vortex.HomeControler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.techvortex.vortex.configuration.PaypalPaymentIntent;
import com.techvortex.vortex.configuration.PaypalPaymentMethod;
import com.techvortex.vortex.configuration.UtilsPaypal;
import com.techvortex.vortex.service.PaypalService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PaymentController {

    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/paypals")
    public String index() {
        return "/userthymeleaf/com.html";
    }

    @GetMapping("/paypal/{price}")
    public String pay(HttpServletRequest request, @PathVariable("price") Double price) {
        String cancelUrl = UtilsPaypal.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = UtilsPaypal.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "Get paid almost everywhere",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/paypal";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {

        return "userthymeleaf/cl";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved"))
                return "userthymeleaf/PaymentSuccess";
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }

        return "userthymeleaf/PaymentSuccess";
    }
}