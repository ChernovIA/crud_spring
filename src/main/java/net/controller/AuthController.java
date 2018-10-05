package net.controller;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import net.model.User;
import net.model.oAuth2.ResponseEntityImp;
import net.model.oAuth2.UUser;
import net.service.RolesService;
import net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    private UserService userService;
    private final RolesService rolesService;
    private OAuth20Service service;
    //Client ID  74511676955-q4ba6phik8m3uk6t1uu496nie4vdod61.apps.googleusercontent.com
    //Secret key ZQmkT14-G9oe6bfA01j6HB08

    @Autowired
    public AuthController(RolesService rolesService, UserService userService) {
        this.rolesService = rolesService;
        this.userService = userService;
    }

    @GetMapping(value = "/auth/loginAuthVK")
    public String authByVK(@RequestParam(value = "code", defaultValue = "") String code, HttpServletRequest request) {
        String authString = "/profile";

        if (code.isEmpty()) {
            try {
                service = new ServiceBuilder("6709740").apiSecret("prs2ew50FOI1O1oUrSip")
                        .callback("http://localhost:8080/auth/loginAuthVK").build(VkontakteApi.instance());
                authString = service.getAuthorizationUrl();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            try {
                OAuth2AccessToken accessToken = service.getAccessToken(code);

                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "https://api.vk.com/method/users.get?v="
                        + VkontakteApi.VERSION);
                service.signRequest(accessToken, oAuthRequest);

                Response responseAuth = service.execute(oAuthRequest);
                //String body = responseAuth.getBody();
                Gson gson = new Gson();
                ResponseEntityImp uniUsers = gson.fromJson(responseAuth.getBody(), ResponseEntityImp.class);
                UUser uniUser = uniUsers.getResponse().get(0);

                authenteficateOAuthUser(uniUser, request);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            authString = "/profile";
        }

        return "redirect:"+authString;

    }

    @GetMapping(value = "/auth/loginAuthGoogle")
    public String authByGoogle(@RequestParam(value = "code", defaultValue = "") String code, HttpServletRequest request){
        String authString;

        if (code.isEmpty()) {

            service = new ServiceBuilder("74511676955-q4ba6phik8m3uk6t1uu496nie4vdod61.apps.googleusercontent.com")
                    .apiSecret("ZQmkT14-G9oe6bfA01j6HB08")
                    .callback("http://localhost:8080/auth/loginAuthGoogle")
                    .scope("profile")
                    .build(GoogleApi20.instance());

            authString = service.getAuthorizationUrl();

        }
        else {
            try {
                OAuth2AccessToken accessToken = service.getAccessToken(code);

                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v1/userinfo");
                service.signRequest(accessToken, oAuthRequest);

                Response responseAuth = service.execute(oAuthRequest);

                Gson gson = new Gson();
                UUser uniUser = gson.fromJson(responseAuth.getBody(), UUser.class);

                authenteficateOAuthUser(uniUser, request);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            authString = "/profile";
        }

        return "redirect:"+authString;
    }

    public void authenteficateOAuthUser(UUser uniUser, HttpServletRequest request){
        if (uniUser.getId() != null && !uniUser.getId().isEmpty()) {

            if (uniUser.getName() == null){
                uniUser.setName(uniUser.getFirst_name() + " "+uniUser.getLast_name());
            }

            User user = userService.getUser(uniUser.getId());
            if (user == null) {
                user = new User(uniUser.getId(),"", uniUser.getName());
                userService.addUser(user);
            } else {
                if (!user.getName().equals(uniUser.getName())
                        || user.getLogin().equals(uniUser.getId())) {
                    user.setName(uniUser.getName());
                    user.setLogin(uniUser.getId());

                    userService.upDateUser(user);
                }
            }
            /*Autologin user*/
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);

            // Create a new session and add the security context, without this all above useless
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        }
    }
}





//    @GetMapping(value = "/auth/loginAuthVK")
//    public String authByVK(@RequestParam(value = "code", defaultValue = "") String code, HttpServletRequest request){
//        String authString;
//        if (code.isEmpty()) {
//            authString = String.format("https://oauth.vk.com/authorize" +
//                            "?client_id=%s" +
//                            "&display=%s" +
//                            "&redirect_uri=%s" +
//                            "&scope=%s" +
//                            "&response_type=%s" +
//                            "&v=%s",
//                    "6709740", "page", "http://localhost:8080/auth/loginAuthVK", "", "code", "5.85");
//        }
//        else {
//
//            RestTemplate restTemplate = new RestTemplate();
//
//            authString = "https://oauth.vk.com/access_token";
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
//            param.add("client_id", "6709740");
//            param.add("client_secret", "prs2ew50FOI1O1oUrSip");
//            param.add("redirect_uri", "http://localhost:8080/auth/loginAuthVK");
//            param.add("code", code);
//
//            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(param, headers);
//            ResponseEntityImp<AccessToken> response = restTemplate.postForEntity(authString, httpEntity, AccessToken.class);
//            if (response.getStatusCode() == HttpStatus.OK){
//                AccessToken resBody = response.getBody();
//
//
//                MultiValueMap<String, String> paramUser = new LinkedMultiValueMap<>();
//                paramUser.add("user_ids", resBody.getUser_id());
//                paramUser.add("access_token", resBody.getAccess_token());
//                paramUser.add("v", "5.85");
//
//                HttpHeaders headersUser = new HttpHeaders();
//                headersUser.setContentType(MediaType.APPLICATION_JSON);
//
//                HttpEntity<MultiValueMap<String, String>> httpEntityUser = new HttpEntity<>(paramUser, headersUser);
//                ResponseEntityImp<ResponseEntityImp> uniUserResponse = restTemplate.postForEntity("https://api.vk.com/method/users.get", httpEntityUser, ResponseEntityImp.class);
//
//                if (uniUserResponse.getStatusCode() == HttpStatus.OK) {
//
//                    ResponseEntityImp uniUser = new ResponseEntityImp();//uniUserResponse.getBody();
//
//                    if (uniUser.getId() != null && !uniUser.getId().isEmpty()) {
//
////                        if (uniUser.getName() == null){
////                            uniUser.setName(uniUser.getFirst_name() + " "+uniUser.getLast_name());
////                        }
//
////                        User user = userService.getUserSocial(uniUser.getId());
////                        if (user == null) {
////                            user = new User(uniUser.getId(), "", uniUser.getName(), uniUser.getId());
////                            userService.addUser(user);
////                        } else {
////                            if (!user.getName().equals(uniUser.getName())
////                                    || !user.getIdoAuth().equals(uniUser.getId())) {
////                                user.setName(uniUser.getName());
////                                user.setLogin(uniUser.getId());
////                                user.setIdoAuth(uniUser.getId());
////
////                                userService.upDateUser(user);
////                            }
////                        }
////                        /*Autologin user*/
////                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
////                                user.getAuthorities());
////                        SecurityContextHolder.getContext().setAuthentication(token);
////
////                        // Create a new session and add the security context, without this all above useless
////                        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//                    }
//                }
//
//            }
//            else{
//                //не удалось...
//            }
//            authString = "/profile";
//        }
//
//        return "redirect:"+authString;
//    }

//    @GetMapping(value = "/auth/loginAuthGoogle")
//    public String authByGoogle(@RequestParam(value = "code", defaultValue = "") String code, HttpServletRequest request){
//        String authString;
//
//        if (code.isEmpty()) {
//            authString = String.format("https://accounts.google.com/o/oauth2/auth" +
//                            "?client_id=%s" +
//                            "&redirect_uri=%s" +
//                            "&response_type=%s" +
//                            "&scope=%s"+
//                            "&include_granted_scopes=%s",
//                    "74511676955-q4ba6phik8m3uk6t1uu496nie4vdod61.apps.googleusercontent.com",
//                    "http://localhost:8080/auth/loginAuthGoogle", "code",
//                    "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile",
//                    "true");
//        }
//        else {
//
//            RestTemplate restTemplate = new RestTemplate();
//            authString = "https://www.googleapis.com/oauth2/v3/token";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
//            param.add("client_id", "74511676955-q4ba6phik8m3uk6t1uu496nie4vdod61.apps.googleusercontent.com");
//            param.add("client_secret", "ZQmkT14-G9oe6bfA01j6HB08");
//            param.add("redirect_uri", "http://localhost:8080/auth/loginAuthGoogle");
//            param.add("grant_type", "authorization_code");
//            param.add("code", code);
//
//            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(param, headers);
//            ResponseEntity<AccessToken> responseAuth = restTemplate.postForEntity(authString, httpEntity, AccessToken.class);
//
//            if (responseAuth.getStatusCode() == HttpStatus.OK){
//                AccessToken accessToken = responseAuth.getBody();
//
//                MultiValueMap<String, String> paramUser = new LinkedMultiValueMap<>();
//                paramUser.add("access_token", accessToken.getAccess_token());
//                paramUser.add("alt", "json");
//
//                HttpHeaders headersUser = new HttpHeaders();
//                headersUser.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//                HttpEntity<MultiValueMap<String, String>> httpEntityUser = new HttpEntity<>(paramUser, headersUser);
//                ResponseEntity<UUser> uniUserResponse =
//                        restTemplate.getForEntity("https://www.googleapis.com/oauth2/v1/userinfo", UUser.class, httpEntityUser);
//
//                if (uniUserResponse.getStatusCode() == HttpStatus.OK) {
//                    UUser uniUser = uniUserResponse.getBody();// .getBody().getResponse().get(0);
//
//                    authenteficateOAuthUser(uniUser, request);
//                }
//            }
//            else{
//                //не удалось...
//            }
//            authString = "/profile";
//        }
//
//        return "redirect:"+authString;
//    }