# Feign Client With Custom Error Handling

This project is to illustrate the coding of how to throw business exception from Web Services, and then get and display in Spring Boot MVC via FeignClient

## The effect

### Get the list from Web Services
![List](/_images/list.PNG?raw=true "List")

### Create Record by HTTP Post to Web Services
![Add](/_images/create-1.PNG?raw=true "Add")
![Added](/_images/create-2.PNG?raw=true "Added")

### When add duplicate Student Code, error throw from Web Services and display in Web view
![Error](/_images/error.PNG?raw=true "Error")

## Code explanation

### <u>Web Service side</u>
1. When error occur in RestController, it will throw <i><b>CustomException</b></i> and handled in @ControllerAdvice.
![Controller](/_images/code-1.PNG?raw=true "Controller")

2. The @ExceptionHandler method will return a HTTP 400 Bad Request, together with a response body. The response body is a <b><i>Map<String, String></b></i>. Which is extracting the <i><b>CustomException</b></i> content and transform to Key-Value pair <b><i>Map<String, String></b></i> 
![ControllerAdvice](/_images/code-2.png?raw=true "ControllerAdvice")

### <u>Web MVC side</u>
1. The FeignClient consume the web services in this demo
![feign](/_images/code-feign.PNG?raw=true "feign")

2. When there are HTTP error (4xx, 5xx) returned from FeignClient, it will throw FeignException. In the FeignException, there are two pieces information can be used. <br />
&nbsp;&nbsp;&nbsp;&nbsp;a. The Message <br />
&nbsp;&nbsp;&nbsp;&nbsp;b. The Response Body. <br />
In my demo, I pass the error message in form of <b><i>Map<String, String></b></i>. So that you can cast the response to Map<String, String>, and then get the error message, error code, or even any key-value pair up to your design. <br />
In fact, you can put any object in the ResponseBody in the @ControllerAdvice. On the Web MVC side, you can transform the reponse body back to object by deserialize the response body. 
![mvc](/_images/code-mvc.png?raw=true "mvc")
