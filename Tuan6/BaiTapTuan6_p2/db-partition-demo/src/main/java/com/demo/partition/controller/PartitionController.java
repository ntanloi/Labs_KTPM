package com.demo.partition.controller;

import com.demo.partition.service.PartitionDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partition")
@RequiredArgsConstructor
public class PartitionController {

    private final PartitionDemoService service;

    // ---- HORIZONTAL ----

    /** GET /api/partition/horizontal?gender=male  -> chỉ query table_user_01 */
    @GetMapping("/horizontal")
    public ResponseEntity<?> horizontal(@RequestParam(defaultValue = "male") String gender) {
        return ResponseEntity.ok(service.getUsersByGender(gender));
    }

    /** GET /api/partition/horizontal/all  -> query cả 2 bảng qua view */
    @GetMapping("/horizontal/all")
    public ResponseEntity<?> horizontalAll() {
        return ResponseEntity.ok(service.getAllUsersHorizontal());
    }

    // ---- VERTICAL ----

    /** GET /api/partition/vertical/basic  -> chỉ lấy cột cơ bản (nhanh) */
    @GetMapping("/vertical/basic")
    public ResponseEntity<?> verticalBasic() {
        return ResponseEntity.ok(service.getUsersBasicOnly());
    }

    /** GET /api/partition/vertical/full  -> JOIN đầy đủ 2 bảng */
    @GetMapping("/vertical/full")
    public ResponseEntity<?> verticalFull() {
        return ResponseEntity.ok(service.getUsersWithDetail());
    }

    // ---- FUNCTIONAL ----

    @GetMapping("/functional/orders")
    public ResponseEntity<?> orders() {
        return ResponseEntity.ok(service.getOrders());
    }

    @GetMapping("/functional/products")
    public ResponseEntity<?> products() {
        return ResponseEntity.ok(service.getProducts());
    }

    @GetMapping("/functional/logs")
    public ResponseEntity<?> logs() {
        return ResponseEntity.ok(service.getAuditLogs());
    }

    // ---- COMPARISON ----

    /** GET /api/partition/compare  -> so sánh thời gian query */
    @GetMapping("/compare")
    public ResponseEntity<?> compare() {
        return ResponseEntity.ok(service.comparePerformance());
    }
}
