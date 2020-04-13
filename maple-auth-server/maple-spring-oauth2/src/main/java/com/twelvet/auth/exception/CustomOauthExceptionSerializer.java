package com.twelvet.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {

    protected CustomOauthExceptionSerializer() {
        super(CustomOAuth2Exception.class);
    }

    @Override
    public void serialize(CustomOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        jgen.writeStartObject();
        jgen.writeObjectField("code", String.valueOf(value.getHttpErrorCode()));
        jgen.writeStringField("msg", value.getMessage());
        jgen.writeStringField("path",request.getServletPath());
        jgen.writeStringField("timestamp",String.valueOf(System.currentTimeMillis()));
        System.out.println("我在执行");
        if(value.getAdditionalInformation()!=null){
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jgen.writeStringField(key, add);
            }
        }
        jgen.writeEndObject();

    }
}
