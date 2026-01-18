-- Insert test permissions
INSERT IGNORE INTO t_permission (id, permission_name, permission_key, created_by, created_date, updated_by, updated_date) VALUES
(1, 'User Read', 'USER_READ', 1, NOW(), 1, NOW()),
(2, 'User Write', 'USER_WRITE', 1, NOW(), 1, NOW()),
(3, 'Admin Access', 'ADMIN', 1, NOW(), 1, NOW());

-- Insert test roles
INSERT IGNORE INTO t_role (id, role_name, role_key, created_by, created_date, updated_by, updated_date) VALUES
(1, 'User Role', 'USER', 1, NOW(), 1, NOW()),
(2, 'Admin Role', 'ADMIN', 1, NOW(), 1, NOW());

-- Link roles with permissions
INSERT IGNORE INTO t_role_permission (role_id, permission_id) VALUES
(1, 1), -- USER role has USER_READ permission
(2, 1), -- ADMIN role has USER_READ permission
(2, 2), -- ADMIN role has USER_WRITE permission
(2, 3); -- ADMIN role has ADMIN permission