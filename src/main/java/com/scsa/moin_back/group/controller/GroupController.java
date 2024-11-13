package com.scsa.moin_back.group.controller;

import com.scsa.moin_back.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group/*")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public List<GroupDTO> getGroup(){

    }
}
