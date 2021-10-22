package org.springframework.boot.sutdy.dataFlow.devtools;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nicky.Tang on 2021/9/28 5:07 下午
 *
 * @since 02.12.10
 */
@RestController
@RequestMapping
public class UserTestControllerTools {
    @RequestMapping("/doTest")
    public String doTest() {
        return "Hellow world!21115";
    }
}
