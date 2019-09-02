package com.example.ssoservernew.controller;

import com.example.ssoservernew.util.HttpClientUtils;
import com.example.ssoservernew.util.HttpRequestMethedEnum;
import org.apache.http.entity.mime.content.FileBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FaceLoginController {

    public static String API_URL_FACE_DETECTION = "http://127.0.0.1:9877/faceDetection";
    public static String API_URL_FACE_RECONGNITION = "http://127.0.0.1:9877/faceRecongnition";
    public static final Logger logger = LoggerFactory.getLogger(FaceLoginController.class);

    @Value("${web.upload-path}")
    private String path;

    @GetMapping("/webCamera")
    public String webCamera() {
        return "webCamera";
    }

    @GetMapping("/uploadSuccess")
    public String uploadSuccess(Model model){
        String file = "blob.png";
        FileBody cbFileBody = new FileBody(new File(path + file));
        Map<String, Object> params = new HashMap<>();
        params.put("file", cbFileBody);
        String result = HttpClientUtils.sendHttp(HttpRequestMethedEnum.HttpPost,API_URL_FACE_RECONGNITION, params, null);
        logger.info("uploadSuccess ,result:{}",result);
        if(result.equals("error result")){
            return "error_1";
        }
        String recongnitionName = result.split("'")[1];
        logger.info("uploadSuccess, recongnitionName:{}", recongnitionName);
        model.addAttribute("user", recongnitionName);
        model.addAttribute("file", file);
        return "faceLogin";
    }
    @ResponseBody
    @PostMapping("/faceUpload")
    public String faceUpload(@RequestParam("file") MultipartFile file){
        String fileName=file.getOriginalFilename();
        logger.info("faceLogin , receive fine name:{}", fileName);
        File dest = new File(path + fileName + ".png");
        // 5判断目录是否存在
        if (!dest.getParentFile().exists()) {
            // 不存在，创建
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "Success";
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Fail";
    }

    @PostMapping("/faceLoginHandel")
    public String faceLoginHandel(){
        return "faceLoginHandel";
    }
}
