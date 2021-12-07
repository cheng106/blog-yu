package idv.cheng.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import idv.cheng.utils.entity.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class IpUtils {

    private static final Pattern IPV4Pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    private static final String RegionURL = "http://ip-api.com/json/%s";

    public static String getRegion(String ip) {
        String url = String.format(RegionURL, ip);
        Map<String, String> params = new HashMap<>();
        params.put("lang", "zh-TW");
        HttpResponse result;
        try {
            result = HttpClientUtil.doRequest(HttpClientUtil.airTableCreate(url, params.toString()));
        } catch (Exception e) {
            log.error("getRegion error:{}", e.getMessage());
            return "";
        }

        JSONObject json = (JSONObject) JSON.parse(result.getResponseBody());
        return StringUtils.isEmpty(json.getString("regionName")) ? "" : json.getString("regionName");

    }


    public static String getRealIP(HttpServletRequest request) {
        String ip;
        // 反向代理
        ip = request.getHeader("X-Forwarded-For");
        log.debug("this is IPUtils X-Forwarded-For ip:{}", ip);
        if (!StringUtils.isBlank(ip) && !ip.equals("unknown")) {
            // 取頭一個IP
            String rip = ip.split(",")[0];
            log.debug("this is IPUtils X-Forwarded-For rip:{}", rip);
            return rip;
        }

        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !ip.equals("unknown")) {
            log.debug("this is IPUtils X-Real-IP ip:{}", ip);
            return ip;
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (!StringUtils.isBlank(ip) && !ip.equals("unknown")) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (!StringUtils.isBlank(ip) && !ip.equals("unknown")) {
            log.debug("this is IPUtils WL-Proxy-Client-IP ip:{}", ip);
            return ip;
        }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if (!StringUtils.isBlank(ip) && !ip.equals("unknown")) {
            log.debug("this is IPUtils HTTP_CLIENT_IP ip:{}", ip);
            return ip;
        }

        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (!StringUtils.isBlank(ip) && !ip.equals("unknown")) {
            log.debug("this is IPUtils HTTP_X_FORWARDED_FOR ip:{}", ip);
            return ip;
        }

        ip = request.getRemoteAddr();
        log.debug("this is IPUtils RemoteAddr ip:{}", ip);
        return ip;
    }

    public static void main(String[] args) {
        System.out.println(getRegion("2409:8a5c:4a12:1e0:7d9f:4f7:2f23:80a1"));
        System.out.println(getRegion("220.135.16.58"));
    }

}
