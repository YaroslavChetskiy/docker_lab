package com.jcwc.dbpractice.controller;

import com.jcwc.dbpractice.dto.MagazineDTO;
import com.jcwc.dbpractice.entity.Magazine;
import com.jcwc.dbpractice.service.MagazineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping
    public ResponseEntity<List<MagazineDTO>> getAllMagazines() {
        return ResponseEntity.ok(magazineService.getAllMagazines());
    }

}
