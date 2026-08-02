package com.distributed_mircorservice.orderservice.controller;

import com.distributed_mircorservice.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Integer id) {

        String response = orderService.getOrderDtl(id);
        return ResponseEntity.ok("Order call successful. " + response);
    }

    /****** Only Java based Http connection and API Request *******/
//        HttpURLConnection httpURLConnection = null;
//        try{
//            String url = "http://localhost:8084/products/" + id;
//            URL obj = new URL(url);
//
//            /* Create an object of HttpURLConnection,
//             consider it like an envelope or request in which specify all the details like
//              URL, Request Methods and time etc. */
//            httpURLConnection = (HttpURLConnection) obj.openConnection();
//
//            // Set HTTP request Method and Headers
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setRequestProperty("Accept", "application/json");
//
//            // Set time to established TCP connection, timeout in millisecond
//            httpURLConnection.setConnectTimeout(100);
//            httpURLConnection.setReadTimeout(10000);
//
//            // Opens TCP Connection for triggered the http request and read response.
//            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String responseLine;
//            while ((responseLine = br.readLine()) != null){
//                response.append(responseLine);
//            }
//            br.close();
//            System.out.println("Response from server: " + response.toString());
//
//    } catch (Exception e){
//        e.printStackTrace();
//        } finally {
//            if (httpURLConnection != null) {
//                httpURLConnection.disconnect();
//            }
//        }
//        return ResponseEntity.ok("Order call is successful. Response: " + response);
//}

}
