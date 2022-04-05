package com.zjc.base.module.draftHandle.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/draftHandle")
public class ImportController {

    @RequestMapping("/import")
    public String importDraft() {
        log.info("importDraft");
        return "draftHandle/import";
    }

    @RequestMapping("OCRImport")
    public String OCRImport() {
        log.info("OCRImport");
        return "draftHandle/OCRImport";
    }
}
