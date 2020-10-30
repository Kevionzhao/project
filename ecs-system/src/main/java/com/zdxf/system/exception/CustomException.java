package com.zdxf.system.exception;

import com.zdxf.system.result.ResultJson;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}
