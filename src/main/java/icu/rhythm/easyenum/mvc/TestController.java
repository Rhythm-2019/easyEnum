package icu.rhythm.easyenum.mvc;

import icu.rhythm.easyenum.example.DemoEnum;
import icu.rhythm.easyenum.example.NewsStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rhythm-2019
 * @date 2023/2/20
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("param")
    public String requestParam(@RequestParam("code") NewsStatus newsStatus) {
        return newsStatus.getDescription();
    }
    @GetMapping("path/{code}")
    public String pathVariable(@PathVariable("code") NewsStatus newsStatus) {
        return newsStatus.getDescription();
    }

    @PostMapping("requestBody")
    public String requestBody(@RequestBody Body requestBody) {
        return requestBody.getStatus().getDescription();
    }


    @GetMapping("responseBody/{code}")
    public Body responseBody(@PathVariable("code") DemoEnum newsStatus) {
        return new Body().setStatus(newsStatus);
    }
    @Data
    @Accessors(chain = true)
    public static class Body {
        private String id;
        private DemoEnum status;

    }

}
