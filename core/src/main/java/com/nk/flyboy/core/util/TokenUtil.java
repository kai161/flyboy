package com.nk.flyboy.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

/**
 * Created on 2017/5/4.
 * 基于 jwt(json web token)协议
 * 包括三部分：
 * 1、header{"typ": 协议类型,"alg": 加密类型}
 * 2、payload{iss：Issuer，发行者 sub：Subject，主题 aud：Audience，观众 exp：Expiration time，过期时间 nbf：Not before iat：Issued at，发行时间 jti：JWT ID}
 * 3、signature 前两部分的加密签名
 *
 * 每部分用base64编码后 用"."连接
 *
 */
public class TokenUtil {

    private static String key="dsebrgos5lae2jvhoq";
    private static String DEFAULT_HEADER="{typ:jwt,alg:HS256}";

    private static String getDefaultPayload(){
        //默认有效期30分钟
        LocalDateTime expTime= LocalDateTime.now().plusMinutes(30);
        long exp=expTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        return  "{\"exp\":"+exp+"}";
    }

    public static String generatorToken(String header,String payload){
        String baseHeader= Base64.getEncoder().encodeToString(getHeader(header).getBytes());
        String basePayload=Base64.getEncoder().encodeToString(getPayload(payload).getBytes());
        String encode=baseHeader+"."+basePayload;
        String baseSignature=Base64.getEncoder().encodeToString(sign(encode));
        String token=encode+"."+baseSignature;
        return token;
    }

    public static boolean verifyToken(String token){
        if(token!=null){
            String[] splitToken= StringUtils.tokenizeToStringArray(token,".");
            if(splitToken.length==3){
                String encode=splitToken[0]+"."+splitToken[1];
                String sign=Base64.getEncoder().encodeToString(sign(encode));
                if(splitToken[2].equals(sign)){
                    ObjectMapper mapper=new ObjectMapper();
                    try {
                        String payload=new String(Base64.getDecoder().decode(splitToken[1].getBytes()));
                        //判断是否有过去校验
                        JsonNode nodes= mapper.readTree(payload);
                        JsonNode expTimeNode=nodes.get("exp");
                        long expTime=expTimeNode==null?Long.MAX_VALUE:expTimeNode.asLong();
                        long currentTime=LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
                        if(currentTime<expTime){
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }

    protected static byte[] sign(String signStr){
        return HmacUtil.encodeHmacSHA256(signStr.getBytes(),key.getBytes());
    }

    private static String getHeader(String header) {
        if(header==null){
            return DEFAULT_HEADER;
        }
        return header;
    }


    private static String getPayload(String payload) {
        if(payload==null){
            return getDefaultPayload();
        }
        return payload;
    }


    public static void main(String[] args) {

        String payload="{\"test\":2213}";
        String token=generatorToken(null,payload);

        System.out.println(token);

        System.out.println(verifyToken(token));
    }
}
