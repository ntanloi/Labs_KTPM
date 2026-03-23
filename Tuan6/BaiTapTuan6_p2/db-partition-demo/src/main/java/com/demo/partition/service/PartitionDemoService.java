package com.demo.partition.service;

import com.demo.partition.entity.*;
import com.demo.partition.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PartitionDemoService {

    @PersistenceContext
    private EntityManager em;

    private final UserBasicRepository userBasicRepo;
    private final FuncOrderRepository orderRepo;
    private final FuncProductRepository productRepo;
    private final FuncAuditLogRepository auditRepo;

    // ----------------------------------------------------------------
    // HORIZONTAL PARTITIONING
    // Routing logic: gender == "male" -> table_user_01, else table_user_02
    // ----------------------------------------------------------------

    public String getHorizontalTableName(String gender) {
        return "male".equalsIgnoreCase(gender) ? "table_user_01" : "table_user_02";
    }

    /** Lấy users theo giới tính - chỉ query đúng partition, không scan toàn bộ */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUsersByGender(String gender) {
        String table = getHorizontalTableName(gender);
        String sql = "SELECT id, name, email, gender, age, city FROM " + table;
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();
        return toMapList(rows, "id", "name", "email", "gender", "age", "city");
    }

    /** Lấy tất cả users từ cả 2 partition (UNION ALL) */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAllUsersHorizontal() {
        String sql = "SELECT id, name, email, gender, age, city FROM vw_all_users";
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();
        return toMapList(rows, "id", "name", "email", "gender", "age", "city");
    }

    // ----------------------------------------------------------------
    // VERTICAL PARTITIONING
    // Tách cột: user_basic (thường xuyên) vs user_detail (ít dùng)
    // ----------------------------------------------------------------

    /** Chỉ lấy thông tin cơ bản - query nhẹ, nhanh */
    public List<UserBasic> getUsersBasicOnly() {
        return userBasicRepo.findAll();
    }

    /** Lấy đầy đủ thông tin (JOIN 2 bảng) - chỉ khi cần */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUsersWithDetail() {
        String sql = """
            SELECT b.id, b.name, b.email, b.gender,
                   d.address, d.phone, d.bio, d.avatar_url
            FROM user_basic b
            LEFT JOIN user_detail d ON b.id = d.id
            """;
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();
        return toMapList(rows, "id", "name", "email", "gender", "address", "phone", "bio", "avatar_url");
    }

    // ----------------------------------------------------------------
    // FUNCTIONAL PARTITIONING
    // Mỗi nghiệp vụ có bảng riêng: orders, products, audit_logs
    // ----------------------------------------------------------------

    public List<FuncOrder> getOrders() {
        return orderRepo.findAll();
    }

    public List<FuncProduct> getProducts() {
        return productRepo.findAll();
    }

    public List<FuncAuditLog> getAuditLogs() {
        return auditRepo.findAll();
    }

    // ----------------------------------------------------------------
    // COMPARISON: đo thời gian query để minh chứng performance
    // ----------------------------------------------------------------

    public Map<String, Object> comparePerformance() {
        Map<String, Object> result = new LinkedHashMap<>();

        // Horizontal: query 1 partition vs full scan
        long t1 = System.currentTimeMillis();
        getUsersByGender("male");
        long horizontalPartitioned = System.currentTimeMillis() - t1;

        long t2 = System.currentTimeMillis();
        getAllUsersHorizontal();
        long horizontalFull = System.currentTimeMillis() - t2;

        // Vertical: basic only vs full join
        long t3 = System.currentTimeMillis();
        getUsersBasicOnly();
        long verticalBasic = System.currentTimeMillis() - t3;

        long t4 = System.currentTimeMillis();
        getUsersWithDetail();
        long verticalFull = System.currentTimeMillis() - t4;

        result.put("horizontal_single_partition_ms", horizontalPartitioned);
        result.put("horizontal_full_scan_ms", horizontalFull);
        result.put("vertical_basic_only_ms", verticalBasic);
        result.put("vertical_full_join_ms", verticalFull);
        result.put("note", "Single partition query is faster than full scan. Basic-only is faster than full JOIN.");
        return result;
    }

    // ----------------------------------------------------------------
    // Helper
    // ----------------------------------------------------------------
    private List<Map<String, Object>> toMapList(List<Object[]> rows, String... cols) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < cols.length && i < row.length; i++) {
                map.put(cols[i], row[i]);
            }
            list.add(map);
        }
        return list;
    }
}
