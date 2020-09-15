package com.jpatest.modules.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;

/**
 * @author Yuan
 */
@Controller
@RequestMapping("/qr")
@Api(tags= {"扫码上传"})
public class QrUploadController {
	
	@Autowired
	private ValueOperations<String,Object> valueOperations;
	
	@GetMapping("/checkLogin")
	@ResponseBody
    public String checkLogin(String uuid) {
        String userId = valueOperations.get(uuid)+"";
        
        return userId;
    }
	
	@GetMapping("/scanQr")
	public String scanQr(String uuid) {
	    valueOperations.set(uuid, "123456", 1000,TimeUnit.SECONDS);
	    return "html/scanUpload";
	}
}
