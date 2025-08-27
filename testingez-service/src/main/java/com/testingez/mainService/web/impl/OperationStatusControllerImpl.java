package com.testingez.mainService.web.impl;

import com.testingez.mainService.web.OperationStatusController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/operation")
public class OperationStatusControllerImpl implements OperationStatusController {

    /*
    * This method leads to a page that says only positive stuff.
    */
    @Override
    @GetMapping("/success")
    public String success() {
        return "success";
    }

    /*
     * This method leads to a page that indicates something went wrong.
     */
    @Override
    @GetMapping("/failure")
    public String failure() {
        return "failure";
    }

}
