package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RefreshToken {

    /**
     * refresh_ttl : 40320
     * ttl : 20160
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6Ly90aGlua3MuaWFzay5pbi9hcGkvdjIvdG9rZW5zIiwiaWF0IjoxNTEyMTE1MTUxLCJleHAiOjE1MTMzMjQ3NTEsIm5iZiI6MTUxMjExNTE1MSwianRpIjoiSVlwZG8xeWgydmd4Y1FMayJ9.EImYPdqDRAPN8g2veJ_jbKWLTDHlX0ZNBhkceyEo3SE
     */
    private int refresh_ttl;
    private int ttl;
    private String token;

    public void setRefresh_ttl(int refresh_ttl) {
        this.refresh_ttl = refresh_ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRefresh_ttl() {
        return refresh_ttl;
    }

    public int getTtl() {
        return ttl;
    }

    public String getToken() {
        return token;
    }
}
