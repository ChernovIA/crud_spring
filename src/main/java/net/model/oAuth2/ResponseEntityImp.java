package net.model.oAuth2;


import java.util.List;

public class ResponseEntityImp {

    private List<UUser> response;

    public List<UUser> getResponse() {
        return response;
    }

    public void setResponse(List<UUser> response) {
        this.response = response;
    }
}
