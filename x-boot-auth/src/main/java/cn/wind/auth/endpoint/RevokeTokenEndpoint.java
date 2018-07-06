//package cn.wind.auth.endpoint;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
//import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
///**
// *
// */
//@FrameworkEndpoint
//public class RevokeTokenEndpoint {
//    @Autowired
//    private ConsumerTokenServices consumerTokenServices;
//
//    @DeleteMapping(value = "/oauth/token")
//    public ResponseEntity revokeToken(String accessToken) {
//        if (consumerTokenServices.revokeToken(accessToken)) {
//            return getResponse("success",HttpStatus.OK);
//        } else {
//           return getResponse("failed",HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    private ResponseEntity getResponse(String body,HttpStatus httpStatus) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Cache-Control", "no-store");
//        headers.set("Pragma", "no-cache");
//        return new ResponseEntity(body, headers,httpStatus);
//    }
//}
