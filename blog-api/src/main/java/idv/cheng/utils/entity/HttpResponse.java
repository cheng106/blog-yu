package idv.cheng.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpResponse {
    private Integer statusCode;
    private String responseBody;
}
